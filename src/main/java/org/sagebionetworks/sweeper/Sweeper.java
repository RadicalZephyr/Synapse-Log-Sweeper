package org.sagebionetworks.sweeper;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

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
	List<SweepConfiguration> configList;
	
    public Sweeper(List<SweepConfiguration> configList) {
		this.configList = configList;
	}

	public static void main( String[] args )
    {
		ArrayList<SweepConfiguration> configs = new ArrayList<SweepConfiguration>();
		
    	Sweeper testSweep = new Sweeper(configs);
    }

    public AmazonS3 s3Authenticate(String accessKeyID, String secretKey) {
      AWSCredentials myCredentials = new BasicAWSCredentials(
                                 accessKeyID, secretKey);
      return new AmazonS3Client(myCredentials);
    }

}
