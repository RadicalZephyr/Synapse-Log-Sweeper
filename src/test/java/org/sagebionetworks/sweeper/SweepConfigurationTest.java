package org.sagebionetworks.sweeper;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SweepConfigurationTest {
	final String logBaseDir = "";
	final String logExpression = "repo-activity(.*?).gz";
	final String s3BucketName = "org.sagebionetworks.logs";
	
	final String EC2_INSTANCE_ID = "testingId";
	
	SweepConfiguration config = new SweepConfiguration(logBaseDir, logExpression, s3BucketName);
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFileNameToKey() {
		String filename = "repo-activity-2012-08-07:08.gz";
		String ec2Key = config.fileNameToKey(EC2_INSTANCE_ID, filename);
		assertEquals(filename + "-" + EC2_INSTANCE_ID, ec2Key);
	}

}
