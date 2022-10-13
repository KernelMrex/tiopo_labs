package org.volgatech.linkcrawler.crawler;

import org.volgatech.linkcrawler.http.HttpReader;
import org.volgatech.linkcrawler.processing.LinkProcessingStrategy;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Pattern;

final public class BadLinksPageCrawler
{
    private final HttpReader httpReader;
    private final LinkProcessingStrategy linkProcessingStrategy;

    public BadLinksPageCrawler(
            HttpReader httpReader,
            LinkProcessingStrategy linkProcessingStrategy
    ) {
        this.httpReader = httpReader;
        this.linkProcessingStrategy = linkProcessingStrategy;
    }

    public void crawl(URL url) throws IOException, InterruptedException, URISyntaxException
    {
        try (var uriStream = httpReader.get(url))
        {
            scanForLinks(url, uriStream);
        }
    }

    private void scanForLinks(URL url, InputStream in)
    {
        var scanner = new Scanner(in, StandardCharsets.UTF_8);
        var pattern = Pattern.compile("href=\"(.*?)\"");
        while (scanner.findWithinHorizon(pattern, 0) != null)
        {
            var match = scanner.match();
            try
            {
                var link = match.group(1);
                URL foundUrl = new URL(!link.startsWith("http") ? (url.getProtocol() + "://" + url.getHost() + url.getPath() + link) : link);
                linkProcessingStrategy.process(foundUrl);
            }
            catch (MalformedURLException ignored) {}
        }
    }
}
