package org.volgatech.linkcrawler.processing;

import java.net.URL;

public interface LinkProcessingStrategy<T>
{
    void process(URL uri, T additionalData);
}
