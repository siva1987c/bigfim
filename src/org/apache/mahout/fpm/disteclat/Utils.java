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
package org.apache.mahout.fpm.disteclat;

import static java.lang.Integer.parseInt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.mahout.fpm.eclat.util.Item;
import org.apache.mahout.fpm.hadoop.util.IntArrayWritable;

public class Utils {
  
  public static List<Item> readTidLists(Configuration conf, Path path) throws IOException, URISyntaxException {
    SequenceFile.Reader r = new SequenceFile.Reader(FileSystem.get(new URI("file:///"), conf), path, conf);
    
    List<Item> items = new ArrayList<Item>();
    
    Text key = new Text();
    IntArrayWritable value = new IntArrayWritable();
    
    while (r.next(key, value)) {
      Writable[] tidListsW = value.get();
      
      int[] tids = new int[tidListsW.length];
      
      for (int i = 0; i < tidListsW.length; i++) {
        tids[i] = ((IntWritable) tidListsW[i]).get();
      }
      
      items.add(new Item(parseInt(key.toString()), tids.length, tids));
    }
    r.close();
    
    return items;
  }
  
  public static Map<Integer,Integer> readSingletonsOrder(Path path) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(path.toString()));
    
    String order = reader.readLine().trim();
    reader.close();
    
    Map<Integer,Integer> orderMap = new HashMap<Integer,Integer>();
    String[] split = order.split(" ");
    int ix = 0;
    for (String item : split) {
      orderMap.put(Integer.valueOf(item), ix++);
    }
    return orderMap;
  }
}
