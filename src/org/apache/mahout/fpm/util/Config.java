/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.mahout.fpm.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * Configuration for Dist-Eclat and BigFIM algorithms
 * 
 * @author Sandy Moens & Emin Aksehirli
 */
public class Config {
  
  public static final String VERSION = "version";
  public static final String CLOSED_SETS_OPTIMIZATION_KEY = "closed_sets_optimization";
  public static final String INPUT_FILE_KEY = "input_file";
  public static final String MAPRED_TASK_TIMEOUT_KEY = "mapred.task.timeout";
  public static final String MIN_SUP_KEY = "minsup";
  public static final String MIN_FREQ_KEY = "minfreq";
  public static final String NUMBER_OF_LINES_KEY = "number_of_lines_read";
  public static final String NUMBER_OF_MAPPERS_KEY = "number_of_mappers";
  public static final String OUTPUT_DIR_KEY = "output_dir";
  public static final String PREFIX_LENGTH_KEY = "prefix_length";
  public static final String WRITE_SETS_KEY = "write_sets";
  public static final String SUBDB_SIZE = "sub_db_size";
  
  private Properties props;
  
  public void readConfig(String configFile) {
    props = new Properties();
    try {
      props.load(new FileInputStream(configFile));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public void readConfig(InputStreamReader inputStreamReader) {
    props = new Properties();
    try {
      props.load(inputStreamReader);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public void printConfig() {
    for (Entry<Object,Object> prop : props.entrySet()) {
      System.out.println("[Config]: " + prop.getKey() + ": " + prop.getValue());
    }
  }
  
  public boolean isValid() {
    return !getInputFile().equals("") && !getOutputDir().equals("") && (getMinSup() != -1 || getMinFreq() != -1);
  }
  
  public boolean getClosedSetsOptimization() {
    return Boolean.parseBoolean(props.getProperty(CLOSED_SETS_OPTIMIZATION_KEY, "false"));
  }
  
  public String getInputFile() {
    return props.getProperty(INPUT_FILE_KEY, "");
  }
  
  public int getMinSup() {
    return Integer.parseInt(props.getProperty(MIN_SUP_KEY, "-1"));
  }
  
  public double getMinFreq() {
    return Double.parseDouble(props.getProperty(MIN_FREQ_KEY, "-1"));
  }
  
  public int getNumberOfMappers() {
    return Integer.parseInt(props.getProperty(NUMBER_OF_MAPPERS_KEY, "1"));
  }
  
  public String getOutputDir() {
    return props.getProperty(OUTPUT_DIR_KEY, "");
  }
  
  public long getMapredTaskTimeout() {
    return Long.parseLong(props.getProperty(MAPRED_TASK_TIMEOUT_KEY, "1000")) * 60 * 60 * 1000;
  }
  
  public int getPrefixLength() {
    return Integer.parseInt(props.getProperty(PREFIX_LENGTH_KEY, "1"));
  }
  
  public boolean getWriteSets() {
    return Boolean.parseBoolean(props.getProperty(WRITE_SETS_KEY, "true"));
  }
  
  public static void printHelp() {
    System.out.println("Please specify: [configFile]");
    System.out.println("For more information on config file please check example folder.");
  }
}
