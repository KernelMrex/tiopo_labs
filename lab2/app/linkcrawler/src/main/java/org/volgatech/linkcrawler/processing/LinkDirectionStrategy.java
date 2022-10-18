package org.volgatech.linkcrawler.processing;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

final public class LinkDirectionStrategy implements LinkProcessingStrategy<Void>
{
    private final Set<URL> internalLinks;
    private final URL hostUrl;

    public LinkDirectionStrategy(URL hostUrl)
    {
        this.internalLinks = new HashSet<>();
        this.hostUrl = hostUrl;
    }

    @Override
    public void process(URL url, Void additionalData)
    {
        if (hostUrl.getHost().equals(url.getHost()))
        {
            internalLinks.add(url);
        }
    }

    public Set<URL> getInternalLinks()
    {
        return internalLinks;
    }
}
