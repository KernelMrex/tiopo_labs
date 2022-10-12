package org.volgatech.crawler;

import java.net.URL;

public interface LinkProcessingStrategy
{
    void process(URL uri);
}
