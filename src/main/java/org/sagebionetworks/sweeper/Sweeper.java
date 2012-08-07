package org.sagebionetworks.sweeper;

import java.io.File;
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
	final List<SweepConfiguration> configList;

	final String EC2_INSTANCE_ID;
	
    public Sweeper(String instanceId, List<SweepConfiguration> configList) {
    	this.EC2_INSTANCE_ID = instanceId;
		this.configList = configList;
	}

	public static void main( String[] args )
    {
		ArrayList<SweepConfiguration> configs = new ArrayList<SweepConfiguration>();
		
    	Sweeper testSweep = new Sweeper("fakeId", configs);
    }

    public AmazonS3 s3Authenticate(String accessKeyID, String secretKey) {
      AWSCredentials myCredentials = new BasicAWSCredentials(
                                 accessKeyID, secretKey);
      return new AmazonS3Client(myCredentials);
    }
    
	public void sweep(SweepConfiguration config, List<File> filesToSweep) {
		for (File file : filesToSweep) {
			String key = config.fileNameToKey(EC2_INSTANCE_ID, file.getName());
			pushToS3(config.getS3BucketName(), file, key);
		}
		
	}

	private void pushToS3(String s3BucketName, File file, String key) {
		// TODO Auto-generated method stub
		
	}

}
