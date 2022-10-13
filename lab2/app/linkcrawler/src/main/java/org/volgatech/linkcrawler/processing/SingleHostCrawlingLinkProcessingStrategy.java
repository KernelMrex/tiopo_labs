package org.volgatech.linkcrawler.processing;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

final public class SingleHostCrawlingLinkProcessingStrategy implements LinkProcessingStrategy
{
    private final Set<URL> internalLinks;
    private final Set<URL> externalLinks;
    private final URL hostUrl;

    public SingleHostCrawlingLinkProcessingStrategy(URL hostUrl)
    {
        this.internalLinks = new HashSet<>();
        this.externalLinks = new HashSet<>();
        this.hostUrl = hostUrl;
    }

    @Override
    public void process(URL url)
    {
        if (!hostUrl.getHost().equals(url.getHost()))
        {
            externalLinks.add(url);
            return;
        }
        internalLinks.add(url);
    }

    public Set<URL> getInternalLinks()
    {
        return internalLinks;
    }

    public Set<URL> getExternalLinks()
    {
        return externalLinks;
    }
}
