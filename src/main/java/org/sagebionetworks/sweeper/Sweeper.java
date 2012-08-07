package org.sagebionetworks.sweeper;

/**
 * An application for collecting log files from an ec2 instance and
 * collecting them into s3 buckets.  Configurable on what log-file
 * patterns to match, and which s3 bucket they should be swept to.
 * 
 * Can also specify an renaming scheme for movement to s3.
 *
 */
public class Sweeper 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
}
