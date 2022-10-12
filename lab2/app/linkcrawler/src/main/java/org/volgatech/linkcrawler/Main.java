package org.volgatech.linkcrawler;

import org.volgatech.linkcrawler.crawler.BadLinksPageCrawler;
import org.volgatech.linkcrawler.http.HttpReader;
import org.volgatech.linkcrawler.processing.StoreLinkProcessingStrategy;
import org.volgatech.linkcrawler.args.ArgsParser;
import org.volgatech.linkcrawler.args.LinkCrawlerCommand;
import org.volgatech.linkcrawler.args.exception.InvalidArgumentException;

import java.io.File;
import java.net.http.HttpClient;

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
        var httpReader = new HttpReader(httpClient);
        var linkStorageStrategy = new StoreLinkProcessingStrategy();
        var crawler = new BadLinksPageCrawler(httpReader, linkStorageStrategy);

        try
        {
            crawler.crawl(command.getUrl());
        }
        catch (Exception e)
        {
            System.err.println("Error while crawling " + command.getUrl().toString());
            System.exit(1);
            return;
        }

        linkStorageStrategy.getLinkList().forEach(System.out::println);
    }

    private static void printUsage()
    {
        System.out.println("Usage: java -jar " + getExecutableName() + " <URL>");
    }

    private static String getExecutableName()
    {
        var file = new File(Main.class.getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .getPath());
        return file.getName();
    }
}