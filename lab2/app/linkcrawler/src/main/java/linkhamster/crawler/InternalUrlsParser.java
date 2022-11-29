package linkhamster.crawler;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class InternalUrlsParser implements Parser
{
    @Override
    public Set<String> searchLinks(InputStream in, ParserParameters parameters)
    {
        var parsedUrls = new HashSet<String>();

        try
        {
            var doc = Jsoup.parse(in, null, parameters.baseUri());
            var links = doc.select("a[href]");
            links.forEach(link -> parsedUrls.add(link.attr("abs:href")));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        return parsedUrls;
    }
}
