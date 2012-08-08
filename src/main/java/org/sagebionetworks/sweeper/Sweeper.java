package org.sagebionetworks.sweeper;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;

/**
 * An application for collecting log files from an ec2 instance and collecting
 * them into s3 buckets. Configurable on what log-file patterns to match, and
 * which s3 bucket they should be swept to.
 * 
 */
public class Sweeper {
	private final List<SweepConfiguration> configList;

	private final String ec2InstanceId;
	private AmazonS3 client;

	public Sweeper(AmazonS3 client, String instanceId,
			List<SweepConfiguration> configList) {
		this.client = client;
		this.ec2InstanceId = instanceId;
		this.configList = configList;
	}

	public AmazonS3 getClient() {
		return client;
	}

	public void setClient(AmazonS3 client) {
		this.client = client;
	}

	public void validateConfigurations() {
		List<String> myBucketNames = new ArrayList<String>();

		for (Bucket bucket : client.listBuckets()) {
			myBucketNames.add(bucket.getName());
		}

		for (SweepConfiguration config : configList) {
			boolean bucketExists = client.doesBucketExist(config
					.getS3BucketName());
			boolean isInMyBuckets = myBucketNames.contains(config
					.getS3BucketName());
			if (bucketExists && isInMyBuckets) {
				continue;
			} else if (bucketExists) {
				// Could also try to add a unique identifier to the front/back
				// of bucketname
				// but this requires a lot more work ;)
				throw new RuntimeException("Bucketname: \""
						+ config.getS3BucketName()
						+ "\" already exists and is not owned by us!");
			}
		}
	}

	public void sweepAllFiles() {
		for (SweepConfiguration config : configList) {
			List<File> files = findFiles(config);
			sweep(config, files);
		}
	}

	public void sweep(SweepConfiguration config, List<File> filesToSweep) {
		for (File file : filesToSweep) {
			String key = config.fileNameToKey(ec2InstanceId, file.getName());
			client.putObject(config.getS3BucketName(), key, file);
		}
	}

	public List<File> findFiles(SweepConfiguration config) {
		File dir = new File(config.getLogBaseDir());
		File[] files = dir.listFiles(config.getFilter());

		return Arrays.asList(files);
	}

}
