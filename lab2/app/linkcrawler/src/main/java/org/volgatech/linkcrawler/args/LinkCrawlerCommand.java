package org.volgatech.linkcrawler.args;

import picocli.CommandLine.Parameters;

import java.io.File;
import java.net.URL;

final public class LinkCrawlerCommand
{
    @Parameters(paramLabel = "URL", description = "URL to crawl")
    private URL url;

    @Parameters(paramLabel = "GOOD-LINKS-FILE", description = "Good links file path")
    private File goodLinksFile;

    @Parameters(paramLabel = "BAD-LINKS-FILE", description = "Bad links file path")
    private File badLinksFile;

    public URL getUrl()
    {
        return url;
    }

    public File getGoodLinksFile()
    {
        return goodLinksFile;
    }

    public File getBadLinksFile()
    {
        return badLinksFile;
    }
}
