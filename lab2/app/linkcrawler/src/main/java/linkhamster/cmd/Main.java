package linkhamster.cmd;

import linkhamster.cmd.args.ArgsParser;
import linkhamster.cmd.args.LinkCrawlerCommand;
import linkhamster.cmd.args.exception.InvalidArgumentException;
import linkhamster.crawler.BasicCrawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Logger;

public class Main
{
    public static void main(String[] args)
    {
        ArgsParser argsParser = new ArgsParser(args);
        LinkCrawlerCommand command;
        try
        {
            command = argsParser.parse();
        }
        catch (InvalidArgumentException e)
        {
            printUsage();
            System.exit(1);
            return;
        }

        var logger = Logger.getLogger("linkhamster", null);
        var crawlerAllowed = new HashSet<String>() {{
            add("text/html");
            add("text/html; charset=utf-8");
        }};

        var crawler = new BasicCrawler(logger, crawlerAllowed);
        crawler.crawl(command.getUrl());

        try (
            var goodLinksWriter = new PrintWriter(command.getGoodLinksFile());
            var badLinksWriter = new PrintWriter(command.getBadLinksFile())
        ) {
            HashMap<URL, URL> hashMap = new HashMap<>();
            crawler.getSiteMap().forEach((from, to) -> to.forEach(toUrl -> hashMap.put(toUrl, from)));

            crawler.getVisited().forEach((url, statusCode) -> {
                var fromUrl = hashMap.get(url);
                if (statusCode < 400)
                    goodLinksWriter.format("%d | %s | from: %s\n", statusCode, url, fromUrl);
                else
                    badLinksWriter.format("%d | %s | from: %s\n", statusCode, url, fromUrl);
            });
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    private static void printUsage()
    {
        System.out.println("Usage: java -jar " + getExecutableName() + " <URL> <INPUT-FILE> <OUTPUT-FILE>");
    }

    private static String getExecutableName()
    {
        var file = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        return file.getName();
    }
}