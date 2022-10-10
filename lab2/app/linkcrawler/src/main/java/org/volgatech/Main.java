package org.volgatech;

import picocli.CommandLine;

public class Main
{
    public static void main(String[] args)
    {
        Args argsTar = new Args();
        new CommandLine(argsTar).parseArgs(args);

        for (String url : argsTar.rawUrl)
        {
            System.out.println(url);
        }
    }
}