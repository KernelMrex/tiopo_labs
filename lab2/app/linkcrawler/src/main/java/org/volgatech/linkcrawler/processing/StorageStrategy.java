package org.volgatech.linkcrawler.processing;

import java.net.URL;
import java.util.HashMap;

public class StorageStrategy<T> implements LinkProcessingStrategy<T>
{
    private final HashMap<URL, T> storageWithStatusCode;

    public StorageStrategy()
    {
        this.storageWithStatusCode = new HashMap<>();
    }

    @Override
    public void process(URL uri, T additionalData)
    {
        this.storageWithStatusCode.put(uri, additionalData);
    }

    public HashMap<URL, T> getStorage()
    {
        return storageWithStatusCode;
    }
}
