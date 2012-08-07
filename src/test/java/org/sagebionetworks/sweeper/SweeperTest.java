package org.sagebionetworks.sweeper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
/**
 * Unit test for simple Sweeper.
 */
public class SweeperTest {
	
	Sweeper sweeper;
	
    @Before
    public void setup() {
    	List<SweepConfiguration> configList = null;
    	sweeper = new Sweeper("testId", configList);
    }
    
    @Test
    public void testSweeper() {
    	List<File> files = new ArrayList<File>();
    	
		sweeper.sweep(null, files);
		
    }
    
    @Test
    public void testREFinder() {

    }
}
