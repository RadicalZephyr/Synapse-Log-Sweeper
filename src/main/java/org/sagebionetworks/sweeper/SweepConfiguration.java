package org.sagebionetworks.sweeper;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SweepConfiguration {
	private class NameFilter implements FileFilter {

		final private Pattern pattern;

		private NameFilter(Pattern pattern) {
			this.pattern = pattern;
		}

		public boolean accept(File pathname) {
			if (pathname.isFile()) {
				Matcher m = pattern.matcher(pathname.getName());
				return m.matches();
			}
			return false;
		}

	}

	final private String logBaseDir;
	/**
	 * The unique portion of the S3 Bucket name that this log configuration
	 * should be swept to. Note that ".sagebionetworks.org" is appended to it
	 * automatically.
	 */
	final private String s3BucketName;
	/**
	 * The "singleton" instance of the NameFilter associated with this
	 * SweepConfig. It's filtering is based on the logExpression constructor
	 * parameter.
	 */
	final private SweepConfiguration.NameFilter filter;

	/**
	 * Constructs a SweepConfiguration object that signifies one set of log
	 * files (that match logExpression) that are found under logBaseDir to be
	 * swept into the S3 bucket s3BucketName.
	 * 
	 * @param logBaseDir
	 *            full pathname to the root log directory
	 * @param logExpression
	 *            a regular expression that matches all desired logs - see
	 *            {@link SweepConfiguration#filter}
	 * @param s3BucketName
	 *            see {@link SweepConfiguration#s3BucketName}
	 */
	public SweepConfiguration(String logBaseDir, String logExpression,
			String s3BucketName) {
		this.logBaseDir = logBaseDir;
		this.s3BucketName = s3BucketName + ".sagebionetworks.org";
		this.filter = new SweepConfiguration.NameFilter(
				Pattern.compile(logExpression));
	}

	public String getLogBaseDir() {
		return logBaseDir;
	}

	public String getS3BucketName() {
		return s3BucketName;
	}

	public FileFilter getFilter() {
		return filter;
	}

	/**
	 * The defined transformation from log file name to S3 key. The important
	 * thing here is that some reasonably unique identifier is part of the key,
	 * because Elastic Beanstalk will make it so that we'll have as many files
	 * per-roll interval as that roll interval had active instances.
	 * 
	 * @param ec2Id
	 * @param filename
	 * @return
	 */
	public String fileNameToKey(String ec2Id, String filename) {
		return String.format("%s-%s", filename, ec2Id);
	}

}
