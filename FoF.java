import java.io.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class FoF {
  public static class FoFMapper extends Mapper<Object, Text, Text, IntWritable> {
    //Value to be used as a dummy value
    private static IntWritable dummy = new IntWriteable(1);
    //Iterable used to hold triad String
    private Text triad = new Text();

    public void map(Object key, Text value, Context c) throws IOException {
      String[] input = value.toString().split(" ");

      String[] triadStrings = new String[3];
      triadStrings[0] = input[0];
      for (int i = 1; i < input.length;i++){
        triadStrings[1] = input[i];
        for (int j = i+1; j < input.length; j++){
          triadStrings[2] = input[j];
          Arrays.sort(triadStrings);
          triad.set(triadStrings[0] + " " + triadStrings[1] + " " + triadStrings[2]);
          c.write(triad, dummy);
        }
      }
    }
  }

  public static class FoFReducer extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {
    public void reduce(Text key, Iterable<IntWritable> value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {

    }
  }
}
