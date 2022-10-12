package org.volgatech;

import org.volgatech.args.ArgsParser;
import org.volgatech.args.LinkCrawlerCommand;
import org.volgatech.args.exception.InvalidArgumentException;
import org.volgatech.crawler.BadLinksPageCrawler;
import org.volgatech.crawler.HttpReader;
import org.volgatech.crawler.StoreLinkProcessingStrategy;

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