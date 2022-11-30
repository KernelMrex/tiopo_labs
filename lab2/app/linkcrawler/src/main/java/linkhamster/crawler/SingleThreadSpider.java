package linkhamster.crawler;

import linkhamster.crawler.http.Header;
import linkhamster.crawler.http.Response;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;

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
            var req = HttpRequest.newBuilder(urlToURI(url)).GET().build();
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

    private static URI urlToURI(URL url) throws URISyntaxException
    {
        var string = url.toString();
        var pos = string.lastIndexOf('/') + 1;
        return new URI(string.substring(0, pos) + URLEncoder.encode(string.substring(pos), Charset.defaultCharset()));
    }
}
