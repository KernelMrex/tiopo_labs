package org.volgatech.args;

import picocli.CommandLine.Parameters;

import java.net.URL;

final public class LinkCrawlerCommand
{
    @Parameters(paramLabel = "URL", description = "URL to crawl")
    private URL url;

    public URL getUrl()
    {
        return url;
    }
}
