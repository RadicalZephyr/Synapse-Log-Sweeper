package org.sagebionetworks.sweeper;

public class SweepConfiguration {
	
	final String logBaseDir;
	final String logExpression;
	final String s3BucketName;
	final String renamingPattern;
	
	public SweepConfiguration(String logBaseDir, String logExpression,
			String s3BucketName, String renamingPattern) {
		this.logBaseDir = logBaseDir;
		this.logExpression = logExpression;
		this.s3BucketName = s3BucketName;
		this.renamingPattern = renamingPattern;
	}

	public String getLogBaseDir() {
		return logBaseDir;
	}

	public String getLogExpression() {
		return logExpression;
	}

	public String getS3BucketName() {
		return s3BucketName;
	}

	public String getRenamingPattern() {
		return renamingPattern;
	}

	public String fileNameToKey(String eC2_INSTANCE_ID) {
		// TODO Auto-generated method stub
		return null;
	}

}
