package org.sagebionetworks.sweeper;

import java.util.regex.Pattern;

public class SweepConfiguration {
	
	final String logBaseDir;
	final Pattern logExpression;
	final String s3BucketName;
	
	public SweepConfiguration(String logBaseDir, String logExpression,
			String s3BucketName) {
		this.logBaseDir = logBaseDir;
		this.logExpression = Pattern.compile(logExpression);
		this.s3BucketName = s3BucketName;
	}

	public String getLogBaseDir() {
		return logBaseDir;
	}

	public Pattern getLogExpression() {
		return logExpression;
	}

	public String getS3BucketName() {
		return s3BucketName;
	}

	/**
	 * Joins the original filename to the ec2Id with a "-"
	 * @param ec2Id
	 * @param filename
	 * @return
	 */
	public String fileNameToKey(String ec2Id, String filename) {
		return String.format("%s-%s", filename, ec2Id);
	}
	

}
