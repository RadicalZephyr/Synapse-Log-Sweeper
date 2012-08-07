package org.sagebionetworks.sweeper;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.amazonaws.services.s3.AmazonS3;
/**
 * Unit test for simple Sweeper.
 */
public class SweeperTest {
	
	Sweeper sweeper;
	AmazonS3 mockS3;
	
    @Before
    public void setup() {
    	List<SweepConfiguration> configList = new ArrayList<SweepConfiguration>();
    	SweepConfiguration config = new SweepConfiguration("src/test/resources", "repo-activity\\.log", "");
    	configList.add(config);
    	mockS3 = mock(AmazonS3.class);
    	sweeper = new Sweeper(mockS3, "testId", configList);
    }
    
    @Test
    public void testSweeper() {
    	List<File> files = new ArrayList<File>();
    	
		sweeper.sweep(null, files);
		
    }
    
    @Test
    public void testFindFiles() {
    	List<File> actualFiles = new ArrayList<File>();
    	actualFiles.add(new File("src/test/resources/repo-activity.log"));
    	
    	SweepConfiguration config = new SweepConfiguration("src/test/resources", "repo-activity\\.log", "");
		List<File> files = sweeper.findFiles(config);
		
		assertEquals(actualFiles.size(), files.size());
		
		for (int i = 0; i < actualFiles.size() && i < files.size(); i++) {
			File validation = actualFiles.get(i);
			File test = files.get(i);
			assertEquals(validation.getAbsolutePath(), test.getAbsolutePath());
		}
    }

    @Test
    public void testSweepAll() {
    	sweeper.sweepAllFiles();
    	verify(mockS3).putObject(anyString(), anyString(), (File) anyObject());
    }
}
