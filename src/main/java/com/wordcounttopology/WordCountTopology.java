// package com.wordcounttopology;

// import org.apache.storm.topology.ConfigurableTopology;
// import org.apache.storm.topology.TopologyBuilder;

// public class WordCountTopology extends ConfigurableTopology {

//     public static void main(String[] args) throws Exception {
//         ConfigurableTopology.start(new WordCountTopology(), args);
//     }

//     @Override
//     protected int run(String[] args) throws Exception {
//         TopologyBuilder builder = new TopologyBuilder();

//         builder.setSpout("word-count-spout", new WordCountSpout(), 1);
        
//         builder.setBolt("word-count-bolt", new WordCountBolt(), 1)
//                 .shuffleGrouping("word-count-spout");

//         conf.setDebug(true);
//         String topologyName = "word-count-topology";
//         conf.setNumWorkers(3);
//         if(args != null && args.length > 0) {
//             topologyName = args[0];
//         }

//         return submit(topologyName, conf, builder);
//     }
// }

/**
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.  The ASF licenses this file to you under the Apache License, Version
 * 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

 package com.wordcounttopology;

 import org.apache.storm.Config;
 import org.apache.storm.topology.ConfigurableTopology;
 import org.apache.storm.topology.TopologyBuilder;
 import org.apache.storm.tuple.Fields;
 public class WordCountTopology extends ConfigurableTopology {
    public static void main(String[] args) throws Exception {
        ConfigurableTopology.start(new WordCountTopology(), args);
    }
 
    protected int run(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();
 
        builder.setSpout("spout", new RandomSentenceSpout(), 4);
 
        builder.setBolt("split", new SplitSentenceBolt(), 4).shuffleGrouping("spout");
        builder.setBolt("count", new WordCountBolt(), 4).fieldsGrouping("split", new Fields("word"));
 
        Config conf = new Config();
        conf.setMaxTaskParallelism(3);
        conf.setDebug(true);
 
        String topologyName = "word-count";
 
        conf.setNumWorkers(3);
 
        if (args != null && args.length > 0) {
            topologyName = args[0];
        }
        return submit(topologyName, conf, builder);
    }
}
 