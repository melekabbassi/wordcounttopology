package com.wordcounttopology;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;


// import org.apache.storm.spout.SpoutOutputCollector;
// import org.apache.storm.task.TopologyContext;
// import org.apache.storm.topology.OutputFieldsDeclarer;
// import org.apache.storm.topology.base.BaseRichSpout;
// import org.apache.storm.tuple.Fields;
// import org.apache.storm.tuple.Values;

// import java.util.Map;
// import java.util.Random;

// public class WordCountSpout extends BaseRichSpout {

//     private SpoutOutputCollector collector;
//     private Random random;
//     private String[] words = {"hello", "world", "apache", "storm", "test", "word", "count", "topology", "bolt", "spout", "tuple", "task", "project"};

//     @Override
//     public void open(Map<String, Object> conf, TopologyContext context, SpoutOutputCollector collector) {
//         this.collector = collector;
//         this.random = new Random();
//     }

//     @Override
//     public void nextTuple() {
//         String word = words[random.nextInt(words.length)];
//         collector.emit(new Values(word));
//     }

//     @Override
//     public void declareOutputFields(OutputFieldsDeclarer declarer) {
//         declarer.declare(new Fields("word"));
//     }
// }


public class RandomSentenceSpout extends BaseRichSpout {
    SpoutOutputCollector collector;
    Random random;


    @Override
    public void open(Map<String, Object> conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
        this.random = new Random();
    }

    @Override
    public void nextTuple() {
        Utils.sleep(10);
        String[] sentences = new String[]{
            sentence("the cow jumped over the moon"), sentence("an apple a day keeps the doctor away"),
            sentence("four score and seven years ago"),
            sentence("snow white and the seven dwarfs"), sentence("i am at two with nature")
        };
        final String sentence = sentences[random.nextInt(sentences.length)];

        this.collector.emit(new Values(sentence), UUID.randomUUID());
    }

    protected String sentence(String input) {
        return input;
    }

    @Override
    public void ack(Object id) {
    }

    @Override
    public void fail(Object id) {
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));
    }
}