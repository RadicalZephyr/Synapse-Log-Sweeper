package org.sagebionetworks.sweeper;

public class SweepConfiguration {
	
	String logBaseDir;
	String logExpression;
	String s3BucketName;
	String renamingPattern;
	
	public SweepConfiguration(String logBaseDir, String logExpression,
			String s3BucketName, String renamingPattern) {
		this.logBaseDir = logBaseDir;
		this.logExpression = logExpression;
		this.s3BucketName = s3BucketName;
		this.renamingPattern = renamingPattern;
	}

}
