package org.volgatech.linkcrawler;

import org.volgatech.linkcrawler.args.ArgsParser;
import org.volgatech.linkcrawler.args.LinkCrawlerCommand;
import org.volgatech.linkcrawler.args.exception.InvalidArgumentException;
import org.volgatech.linkcrawler.crawler.BadLinksPageCrawler;
import org.volgatech.linkcrawler.crawler.http.Reader;
import org.volgatech.linkcrawler.processing.LinkDirectionStrategy;
import org.volgatech.linkcrawler.processing.StorageStrategy;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.http.HttpClient;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

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

        var httpClient = HttpClient.newHttpClient();
        var httpReader = new Reader(httpClient);

        var linkDirectionProcessingStrategy = new LinkDirectionStrategy(command.getUrl());
        var goodLinksStorageProcessingStrategy = new StorageStrategy<Integer>();
        var badLinksStorageProcessingStrategy = new StorageStrategy<Integer>();

        var crawler = new BadLinksPageCrawler(
                httpReader,
                linkDirectionProcessingStrategy,
                goodLinksStorageProcessingStrategy,
                badLinksStorageProcessingStrategy,
                new HashSet<>() {{
                    add("text/html; charset=UTF-8");
                    add("text/html; charset=iso-8859-1");
                }}
        );

        var allParsedLinks = new HashSet<URL>();
        Queue<URL> queue = new LinkedList<>();
        queue.add(command.getUrl());

        try
        {
            while (!queue.isEmpty())
            {
                URL urlToCrawl = queue.poll();
                crawler.crawl(urlToCrawl);

                var currentParsedLinks = linkDirectionProcessingStrategy.getInternalLinks();
                currentParsedLinks.removeAll(allParsedLinks);
                allParsedLinks.addAll(currentParsedLinks);

                // TODO: replace with logger and counter of current link to parse
                System.out.printf("Parsed %s New links %d Total: %d%n", urlToCrawl.toString(), currentParsedLinks.size(), allParsedLinks.size());

                queue.addAll(currentParsedLinks);
            }
        }
        catch (Exception e)
        {
            System.err.println("Error while crawling " + command.getUrl().toString());
            System.exit(1);
            return;
        }

        try
        {
            dumpLinks(command.getGoodLinksFile(), goodLinksStorageProcessingStrategy.getStorage());
            dumpLinks(command.getBadLinksFile(), badLinksStorageProcessingStrategy.getStorage());
        }
        catch (IOException e)
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
        var file = new File(Main.class.getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .getPath());
        return file.getName();
    }

    private static void dumpLinks(File file, Map<URL, Integer> links) throws IOException
    {
        try (var writer = new PrintWriter(file))
        {
            int total = 0;
            for (var entry : links.entrySet())
            {
                writer.println(entry.getValue().toString() + " " + entry.getKey());
                writer.flush();
                total++;
            }
            writer.printf(
                    "Total links: %d. Created at: %s%n",
                    total,
                    DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now())
            );
            writer.flush();
        }
    }
}