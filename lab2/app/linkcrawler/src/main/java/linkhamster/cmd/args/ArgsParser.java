package linkhamster.cmd.args;

import linkhamster.cmd.args.exception.InvalidArgumentException;
import picocli.CommandLine;

final public class ArgsParser
{
    private final String[] args;

    public ArgsParser(String[] args)
    {
        this.args = args;
    }

    public LinkCrawlerCommand parse() throws InvalidArgumentException
    {
        try
        {
            var linkCrawlerCommand = new LinkCrawlerCommand();
            createCommandLine(linkCrawlerCommand).parseArgs(args);
            return linkCrawlerCommand;
        }
        catch (CommandLine.ParameterException exception)
        {
            throw new InvalidArgumentException();
        }
    }

    private CommandLine createCommandLine(Object command)
    {
        return new CommandLine(command);
    }
}
