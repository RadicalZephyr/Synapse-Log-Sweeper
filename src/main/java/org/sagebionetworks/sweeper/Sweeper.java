package org.sagebionetworks.sweeper;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

/**
 * An application for collecting log files from an ec2 instance and
 * collecting them into s3 buckets.  Configurable on what log-file
 * patterns to match, and which s3 bucket they should be swept to.
 *
 * Can also specify an renaming scheme for movement to s3.
 *
 */
public class Sweeper
{
  final List<SweepConfiguration> configList;

  final String ec2InstanceId;
  AmazonS3 client;

    public Sweeper(AmazonS3 client, String instanceId, List<SweepConfiguration> configList) {
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
