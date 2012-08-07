package org.sagebionetworks.sweeper;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
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

	final String ec2InstanceId;
	
    public Sweeper(String instanceId, List<SweepConfiguration> configList) {
    	this.ec2InstanceId = instanceId;
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
			String key = config.fileNameToKey(ec2InstanceId, file.getName());
			pushToS3(config.getS3BucketName(), file, key);
		}
		
	}

	private void pushToS3(String s3BucketName, File file, String key) {
		// TODO Auto-generated method stub
		
	}

	public List<File> findFiles(SweepConfiguration config) {
		File dir = new File(config.getLogBaseDir());
		File[] files = dir.listFiles(config.getFilter());
		
		return Arrays.asList(files);
	}

}
