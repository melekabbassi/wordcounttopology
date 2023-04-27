package com.wordcounttopology;

import org.apache.storm.topology.ConfigurableTopology;
import org.apache.storm.topology.TopologyBuilder;

public class WordCountTopology extends ConfigurableTopology {

    public static void main(String[] args) throws Exception {
        ConfigurableTopology.start(new WordCountTopology(), args);
    }

    @Override
    protected int run(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("word-count-spout", new WordCountSpout(), 1);
        
        builder.setBolt("word-count-bolt", new WordCountBolt(), 1)
                .shuffleGrouping("word-count-spout");

        conf.setDebug(true);
        String topologyName = "word-count-topology";
        conf.setNumWorkers(3);
        if(args != null && args.length > 0) {
            topologyName = args[0];
        }

        return submit(topologyName, conf, builder);
    }
}
