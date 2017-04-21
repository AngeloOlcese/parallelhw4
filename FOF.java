import org.apache.hadoop.io.mapreduce;

public class FOF {
  public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {
  }

  public static class Reduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {
  }
}
