package linkhamster.crawler.failsafe;

import java.util.function.Supplier;

public class RetryCommand<T> {

    private final int maxRetries;
    private final int throttleTimeoutMillis;

    public RetryCommand(int maxRetries, int throttleTimeoutMillis)
    {
        this.maxRetries = maxRetries;
        this.throttleTimeoutMillis = throttleTimeoutMillis;
    }

    // Takes a function and executes it, if fails, passes the function to the retry command
    public T run(Supplier<T> function)
    {
        try
        {
            return function.get();
        }
        catch (Exception e)
        {
            try
            {
                Thread.sleep(throttleTimeoutMillis);
            }
            catch (InterruptedException ignored)
            {}

            return retry(function);
        }
    }

    private T retry(Supplier<T> function) throws RuntimeException
    {
        int retryCounter = 0;
        while (retryCounter < maxRetries)
        {
            try
            {
                return function.get();
            }
            catch (Exception ex)
            {
                retryCounter++;
                if (retryCounter >= maxRetries)
                {
                    throw new RuntimeException("Command failed on all of " + maxRetries + " retries", ex);
                }
            }
        }
        throw new RuntimeException("Command failed on all of " + maxRetries + " retries");
    }
}