package linkhamster.crawler;

import linkhamster.crawler.failsafe.RetryCommand;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;

public class BasicCrawler implements Crawler
{
    private final Spider spider;
    private final Parser parser;
    private final HashMap<URL, Integer> visited;
    @SuppressWarnings("CollectionContainsUrl")
    private final Set<URL> discovered;
    private final Queue<URL> urlQueue;
    private final Logger logger;
    private final HashMap<URL, HashSet<URL>> siteMap;

    private final Set<String> allowedContentTypes;

    public BasicCrawler(Logger logger, Set<String> allowedContentTypes)
    {
        this.logger = logger;
        this.allowedContentTypes = allowedContentTypes;

        parser = new InternalUrlsParser();
        spider = new SingleThreadSpider();
        urlQueue = new LinkedList<>();
        visited = new HashMap<>();
        discovered = new HashSet<>();
        siteMap = new HashMap<>();
    }

    @Override
    public void crawl(URL url)
    {
        int counter = 0;
        urlQueue.add(url);

        while (!urlQueue.isEmpty())
        {
            var urlToProcess = urlQueue.poll();
            logger.info(String.format("Fetch [%d out of %d] %s", counter++, discovered.size(), urlToProcess.toString()));
            new RetryCommand<>(5, 60 * 1000).run(() -> {
                processOne(urlToProcess); return null;
            });
        }
    }

    public Map<URL, Integer> getVisited()
    {
        return visited;
    }

    public HashMap<URL, HashSet<URL>> getSiteMap() {
        return siteMap;
    }

    private void processOne(URL urlToProcess)
    {
        var resp = spider.get(urlToProcess);
        visited.put(urlToProcess, resp.statusCode());

        if (
            resp.contentType().isEmpty()
            || allowedContentTypes.stream().noneMatch(resp.contentType().get()::equalsIgnoreCase)
        ) {
            return;
        }

        try (var pageContentStream = resp.body())
        {
            var links = parser.searchLinks(
                pageContentStream,
                new ParserParameters(urlToProcess.toString())
            );

            processPageLinks(links, urlToProcess);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                resp.body().close();
            }
            catch (IOException ignore)
            {}
        }
    }

    private void processPageLinks(Set<String> links, URL processableUrl)
    {
        links.stream()
            .map(this::convertToURL) // tries to construct URLs from given links
            .filter(Optional::isPresent) // removes all empty urls
            .map(Optional::get)  // map from Set<Optional<URL>> to Set<URL>
            .filter(url -> !visited.containsKey(url)) // removes all visited
            .filter(url -> processableUrl.getHost().equals(url.getHost())) // removes all external urls
            .map(this::removeAnchor) // removing '#.*' anchors
            .filter(url -> !discovered.contains(url)) // check if already discovered this url
            .forEach(url -> {
                if (siteMap.containsKey(processableUrl))
                {
                    siteMap.get(processableUrl).add(url);
                }
                else
                {
                    siteMap.put(processableUrl, new HashSet<>() {{ add(url); }});
                }

                discovered.add(url);
                urlQueue.add(url);
            });
    }

    private Optional<URL> convertToURL(String link)
    {
        try
        {
            return Optional.of(new URL(link));
        }
        catch (MalformedURLException exception)
        {
            return Optional.empty();
        }
    }

    private URL removeAnchor(URL url)
    {
        var str = url.toString();
        var hashIndex = str.lastIndexOf("#");
        if (hashIndex > 0)
        {
            try
            {
                return new URL(str.substring(0, hashIndex));
            }
            catch (MalformedURLException ignored) {}
        }
        return url;
    }
}
