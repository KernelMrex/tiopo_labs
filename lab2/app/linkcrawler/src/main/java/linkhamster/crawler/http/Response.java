package linkhamster.crawler.http;

import java.io.InputStream;
import java.util.Optional;

public record Response(int statusCode, Optional<String> contentType, InputStream body)
{
}
