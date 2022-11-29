package linkhamster.crawler;

import linkhamster.crawler.http.Header;
import linkhamster.crawler.http.Response;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SingleThreadSpider implements Spider
{
    private final HttpClient httpClient;

    public SingleThreadSpider()
    {
        httpClient = HttpClient.newHttpClient();
    }

    @Override
    public Response get(URL url)
    {
        try
        {
            var req = HttpRequest.newBuilder(url.toURI()).GET().build();
            var resp = httpClient.send(req, HttpResponse.BodyHandlers.ofInputStream());
            return new Response(
                resp.statusCode(),
                resp.headers().firstValue(Header.CONTENT_TYPE),
                resp.body()
            );
        }
        catch (URISyntaxException | InterruptedException | IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
