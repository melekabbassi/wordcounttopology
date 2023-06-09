package com.wordcounttopology;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;


// import org.apache.storm.task.OutputCollector;
// import org.apache.storm.task.TopologyContext;
// import org.apache.storm.topology.base.BaseRichBolt;
// import org.apache.storm.tuple.Fields;
// import org.apache.storm.tuple.Tuple;
// import org.apache.storm.tuple.Values;
// import org.apache.storm.utils.TupleUtils;

// import java.util.HashMap;
// import java.util.Map;

// public class WordCountBolt extends BaseRichBolt {

//     private OutputCollector collector;
//     private Map<String, Integer> counts;

//     @Override
//     public void prepare(Map<String, Object> topoConf, TopologyContext context, OutputCollector collector) {
//         this.collector = collector;
//         this.counts = new HashMap<String, Integer>();
//     }

//     @Override
//     public void execute(Tuple tuple) {
//         if (TupleUtils.isTick(tuple)) {
//             // handle tick tuple
//             System.out.println("Received tick tuple, triggering emit of current window counts");
//             for (Map.Entry<String, Integer> entry : counts.entrySet()) {
//                 collector.emit(new Values(entry.getKey(), entry.getValue()));
//             }
//             return;
//         }

//         String word = tuple.getStringByField("word");
//         int count = counts.getOrDefault(word, 0) + 1;
//         counts.put(word, count);
//         collector.emit(new Values(word, count));
//     }

//     @Override
//     public void declareOutputFields(org.apache.storm.topology.OutputFieldsDeclarer declarer) {
//         declarer.declare(new Fields("word", "count"));
//     }
// }

public class WordCountBolt extends BaseBasicBolt {
    Map<String, Integer> counts = new HashMap<>();

    @Override
    public void execute(Tuple tuple, BasicOutputCollector collector) {
        String word = tuple.getString(0);
        Integer count = counts.get(word);
        if (count == null) {
            count = 0;
        }
        count++;
        counts.put(word, count);
        collector.emit(new Values(word, count));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word", "count"));
    }
}