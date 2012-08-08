package org.sagebionetworks.sweeper;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SweepConfiguration {
  private class NameFilter implements FileFilter {

    public boolean accept(File pathname) {
      if (pathname.isFile()) {
        Matcher m = logExpression.matcher(pathname.getName());
        return m.matches();
      }
      return false;
    }
    
  }
  
  final private String logBaseDir;
  final private Pattern logExpression;
  final private String s3BucketName;
  final private SweepConfiguration.NameFilter filter;
  
  public SweepConfiguration(String logBaseDir, String logExpression,
      String s3BucketName) {
    this.logBaseDir = logBaseDir;
    this.logExpression = Pattern.compile(logExpression);
    this.s3BucketName = s3BucketName;
    this.filter = new SweepConfiguration.NameFilter();
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

  public FileFilter getFilter() {
    return filter;
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
