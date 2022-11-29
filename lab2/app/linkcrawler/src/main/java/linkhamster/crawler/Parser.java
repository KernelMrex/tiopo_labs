package linkhamster.crawler;

import java.io.InputStream;
import java.util.Set;

// Reads data and returns set of string per page
public interface Parser
{
    Set<String> searchLinks(InputStream pageReader, ParserParameters parameters);
}
