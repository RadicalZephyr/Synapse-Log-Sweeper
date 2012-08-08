package org.sagebionetworks.sweeper;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.amazonaws.services.s3.AmazonS3;

/**
 * Unit test for simple Sweeper.
 */
public class SweeperTest {

	AmazonS3 mockS3;
	Sweeper sweeper;

	@Before
	public void setUp() {
		mockS3 = mock(AmazonS3.class);
		this.sweeper = new Sweeper(mockS3, "testId", null);
	}

	@Test
	public void testSweepNullConfig() {
		List<File> files = new ArrayList<File>();

		sweeper.sweep(null, files);

	}

	@Test
	public void testFindFiles() {
		runFindFiles("repo-trace-profile");
		runFindFiles("repo-slow-profile");
		runFindFiles("repo-activity");
	}

	private void runFindFiles(String logBaseName) {
		List<File> actualFiles = buildFileList("src/test/resources/"
				+ logBaseName, ".2012-08-11-", subRange("0", 0, 10), ".gz");

		String logExpression = logBaseName + "\\.\\d{4}-\\d{2}-\\d{2}-\\d{2}\\.gz";

		SweepConfiguration config = new SweepConfiguration("src/test/resources", logExpression, "");
		List<File> files = sweeper.findFiles(config);

		Comparator<File> c = new Comparator<File>() {

			public int compare(File o1, File o2) {
				return o1.getName().compareTo(o2.getName());
			}

		};
		// We should have found the same number of files
		assertEquals(actualFiles.size(), files.size());

		Collections.sort(actualFiles, c);
		Collections.sort(files, c);

		for (int i = 0; i < actualFiles.size() && i < files.size(); i++) {
			File validation = actualFiles.get(i);
			File test = files.get(i);
			assertEquals(validation.getAbsolutePath(), test.getAbsolutePath());
		}
	}

	@Test
	public void testSweepAll() {
		List<SweepConfiguration> configList = new ArrayList<SweepConfiguration>();
		SweepConfiguration configActivity = new SweepConfiguration(
				"src/test/resources", "repo-activity\\.\\d{4}-\\d{2}-\\d{2}-\\d{2}\\.gz", "");
		SweepConfiguration configSlow = new SweepConfiguration(
				"src/test/resources", "repo-slow-profile\\.\\d{4}-\\d{2}-\\d{2}-\\d{2}\\.gz", "");
		SweepConfiguration configTrace = new SweepConfiguration(
				"src/test/resources", "repo-trace-profile\\.\\d{4}-\\d{2}-\\d{2}-\\d{2}\\.gz", "");

		configList.add(configActivity);
		configList.add(configSlow);
		configList.add(configTrace);

		Sweeper localSweeper = new Sweeper(mockS3, "testId", configList);

		localSweeper.sweepAllFiles();
		verify(mockS3, times(27)).putObject(anyString(), anyString(), (File) anyObject());
	}

	private String[] subRange(String prefix, int start, int end) {
		String[] arr = new String[end - start];
		for (int i = start; i < end; i++) {
			arr[i] = String.format("%s%d", prefix, i);
		}
		return arr;
	}

	private List<File> buildFileList(String baseName, String datePrefix,
			String[] dateSubRange, String dateSuffix) {
		List<File> fileList = new ArrayList<File>();

		for (String subDate : dateSubRange) {
			File file = new File(String.format("%s%s%s%s", baseName, datePrefix, subDate, dateSuffix));
			if (file.exists()) {
				fileList.add(file);
			}
		}

		return fileList;
	}
}
