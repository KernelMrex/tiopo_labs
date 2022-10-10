package org.volgatech;

import picocli.CommandLine.Parameters;

public class Args
{
    @Parameters(paramLabel = "URL", description = "one or more URL to crawl")
    String[] rawUrl;
}
