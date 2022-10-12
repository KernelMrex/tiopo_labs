package org.volgatech.linkcrawler.processing;

import java.net.URL;

public interface LinkProcessingStrategy
{
    void process(URL uri);
}
