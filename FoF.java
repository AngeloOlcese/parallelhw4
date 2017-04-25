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
    private static IntWritable dummy = new IntWritable(1);
    //Iterable used to hold triad String
    private Text triad = new Text();

    public void map(Object key, Text value, Context c) throws IOException, InterruptedException {
      String[] input = value.toString().split(" ");

      //Create int array to hold three values, initiate first value
      int[] triadVals = new int[3];
      int curr = Integer.parseInt(input[0]);
      //Go through the friends list and create all triads, then output in sorted order
      for (int i = 1; i < input.length; i++){
        triadVals[0] = curr;
        triadVals[1] = Integer.parseInt(input[i]);
        for (int j = i+1; j < input.length; j++){
          triadVals[2] = Integer.parseInt(input[j]);
          if (!(triadVals[0] == triadVals[1] || triadVals[0] == triadVals[1] || triadVals[1] == triadVals[2])) {
            Arrays.sort(triadVals);
            triad.set(Integer.toString(triadVals[0]) + " " + Integer.toString(triadVals[1]) + " " + Integer.toString(triadVals[2]));
            c.write(triad, dummy);
          }
        }
      }
    }
  }

  public static class FoFReducer extends  Reducer<Text, IntWritable, Text, IntWritable> {
    public void reduce(Text triad, Iterable<IntWritable> value, Context c) throws IOException, InterruptedException {
      int repeated = 0;

      for (IntWritable v : value) {
          repeated += v.get();
      }

      if (repeated > 1) {
        String[] tripleStrings = StringUtils.split(triad.toString(), " ");

        c.write(triad, null);
        triad.set(tripleStrings[1] + " " + tripleStrings[0] + " " + tripleStrings[2]);
        c.write(triad, null);
        triad.set(tripleStrings[2] + " " + tripleStrings[0] + " " + tripleStrings[1]);
        c.write(triad, null);
      }
    }
  }

  public static void main(String[] args) throws Exception {
    if (args.length != 2) {
      System.out.println("Usage: FOF <inputFile> <outputFile>");
      return;
    }

    //Create configuration and configure hadoop job
    Configuration conf = new Configuration();
    Job job = new Job(conf, "FoF");
    job.setJarByClass(FoF.class);

    //Point the job to our map/reduce classes
    job.setMapperClass(FoFMapper.class);
    job.setReducerClass(FoFReducer.class);

    //Set value types for key and value
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);

    //Set input and output information
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));

    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }

}
