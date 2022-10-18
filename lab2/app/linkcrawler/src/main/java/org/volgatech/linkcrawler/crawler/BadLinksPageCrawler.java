package org.volgatech.linkcrawler.crawler;

import org.volgatech.linkcrawler.crawler.http.Reader;
import org.volgatech.linkcrawler.processing.LinkProcessingStrategy;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

final public class BadLinksPageCrawler
{
    private final Reader httpReader;
    private final LinkProcessingStrategy<Void> queueProcessing;
    private final LinkProcessingStrategy<Integer> goodLinksStorageProcessing;
    private final LinkProcessingStrategy<Integer> badLinksStorageProcessing;
    private final Set<String> allowedContentTypes;

    public BadLinksPageCrawler(
            Reader httpReader,
            LinkProcessingStrategy<Void> queueProcessing,
            LinkProcessingStrategy<Integer> goodLinksStorageProcessing,
            LinkProcessingStrategy<Integer> badLinksStorageProcessing,
            Set<String> allowedContentTypes
    ) {
        this.httpReader = httpReader;
        this.queueProcessing = queueProcessing;
        this.goodLinksStorageProcessing = goodLinksStorageProcessing;
        this.badLinksStorageProcessing = badLinksStorageProcessing;
        this.allowedContentTypes = allowedContentTypes;
    }

    // TODO: add proxies and multithreading
    public void crawl(URL url) throws IOException, InterruptedException, URISyntaxException
    {
        // TODO: fix error
        var headResp = httpReader.head(url);

        if (headResp.getContentType().isEmpty() || !allowedContentTypes.contains(headResp.getContentType().get()))
        {
            return;
        }

        if (headResp.getStatusCode() >= 400)
        {
            badLinksStorageProcessing.process(url, headResp.getStatusCode());
            return;
        }

        goodLinksStorageProcessing.process(url, headResp.getStatusCode());
        try (var body = httpReader.get(url).getBody())
        {
            scanForLinks(url, body);
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
                parseUrl(url, match.group(1)).ifPresent(value -> queueProcessing.process(value, null));
            }
            catch (MalformedURLException ignored) {}
        }
    }

    private Optional<URL> parseUrl(URL baseUrl, String link) throws MalformedURLException
    {
        if (link.startsWith("tel:") || link.startsWith("mailto:"))
        {
            return Optional.empty();
        }

        if (link.startsWith("http") || link.startsWith("//"))
        {
            return Optional.of(new URL(link));
        }

        var path = Paths
                .get(link.startsWith("/") ? link : (baseUrl.getPath() + "/" + link))
                .normalize()
                .toString();

        return Optional.of(new URL(String.format(
                "%s://%s%s",
                baseUrl.getProtocol(),
                baseUrl.getHost(),
                removeSuffix(path)
        )));
    }

    private String removeSuffix(String value)
    {
        return value.endsWith("#") ? value.substring(0, value.length() - 1) : value;
    }
}
