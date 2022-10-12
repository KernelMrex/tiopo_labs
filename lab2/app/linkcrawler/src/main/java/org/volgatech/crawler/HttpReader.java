package org.volgatech.crawler;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

final public class HttpReader
{
    private final HttpClient httpClient;

    public HttpReader(HttpClient httpClient)
    {
        this.httpClient = httpClient;
    }

    InputStream get(URL url) throws IOException, InterruptedException, URISyntaxException
    {
        var request = HttpRequest.newBuilder(url.toURI()).GET().build();
        var resp = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());
        return resp.body();
    }
}
