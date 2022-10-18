package org.volgatech.linkcrawler.crawler.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

final public class Reader
{
    private final HttpClient httpClient;

    public Reader(HttpClient httpClient)
    {
        this.httpClient = httpClient;
    }

    public Response<Void> head(URL url) throws URISyntaxException, IOException, InterruptedException
    {
        var req = HttpRequest.newBuilder(url.toURI()).method("HEAD", HttpRequest.BodyPublishers.noBody()).build();
        var resp = httpClient.send(req, HttpResponse.BodyHandlers.discarding());
        return new Response<>(resp.headers(), resp.body(), resp.statusCode());
    }

    public Response<InputStream> get(URL url) throws IOException, InterruptedException, URISyntaxException
    {
        var request = HttpRequest.newBuilder(url.toURI()).GET().build();
        var resp = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());
        return new Response<>(resp.headers(), resp.body(), resp.statusCode());
    }
}
