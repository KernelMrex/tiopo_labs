package org.volgatech.crawler;

import java.net.URL;

final public class BadLinksPageCrawler
{
    private LinkProcessingStrategy linkProcessingStrategy;

    public BadLinksPageCrawler(
            LinkProcessingStrategy linkProcessingStrategy
    ) {
        this.linkProcessingStrategy = linkProcessingStrategy;
    }

    public void crawl(URL url)
    {
        // TODO: save http page in buffer, parse and execute link precessing strategy
    }
}
