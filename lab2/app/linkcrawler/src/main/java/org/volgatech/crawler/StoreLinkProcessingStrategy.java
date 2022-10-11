package org.volgatech.crawler;

import java.net.URL;
import java.util.ArrayList;

final public class StoreLinkProcessingStrategy implements LinkProcessingStrategy
{
    private final ArrayList<URL> linkList;

    public StoreLinkProcessingStrategy()
    {
        this.linkList = new ArrayList<>();
    }

    @Override
    public void process(URL url)
    {
        linkList.add(url);
    }

    public ArrayList<URL> getLinkList()
    {
        return linkList;
    }
}
