package org.volgatech.linkcrawler.processing;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

final public class StoreLinkProcessingStrategy implements LinkProcessingStrategy
{
    private final Set<URL> linkList;

    public StoreLinkProcessingStrategy()
    {
        this.linkList = new HashSet<>();
    }

    @Override
    public void process(URL url)
    {
        linkList.add(url);
    }

    public Set<URL> getLinkList()
    {
        return linkList;
    }
}
