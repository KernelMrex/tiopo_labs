package org.volgatech.linkcrawler.crawler.http;

import java.net.http.HttpHeaders;
import java.util.Optional;

public class Response<T>
{
    private final HttpHeaders headers;
    private final T body;
    private final int statusCode;

    public Response(HttpHeaders headers, T body, int statusCode)
    {
        this.headers = headers;
        this.body = body;
        this.statusCode = statusCode;
    }

    public T getBody()
    {
        return body;
    }

    public int getStatusCode()
    {
        return statusCode;
    }

    public Optional<String> getContentType()
    {
        return headers.firstValue("Content-Type");
    }
}
