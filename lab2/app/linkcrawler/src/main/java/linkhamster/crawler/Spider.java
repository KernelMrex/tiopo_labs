package linkhamster.crawler;

import linkhamster.crawler.http.Response;

import java.net.URL;

public interface Spider
{
    Response get(URL url);
}
