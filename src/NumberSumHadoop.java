import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class NumberSumHadoop {
    static class NumberMapper extends Mapper<LongWritable, Text, NullWritable, IntWritable> {
        private static List<String> numbers = Arrays.asList("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine");

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            int mapped = numbers.indexOf(value.toString());
            context.write(NullWritable.get(), new IntWritable(mapped));
        }
    }

    static class SumReducer extends Reducer<NullWritable, IntWritable, NullWritable, IntWritable> {
        @Override
        protected void reduce(NullWritable key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int result = 0;
            for (IntWritable value : values) {
                result += value.get();
            }
            context.write(key, new IntWritable(result));
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Job job = Job.getInstance();
        job.setJarByClass(NumberSumHadoop.class);

        job.setMapperClass(NumberMapper.class);
        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setReducerClass(SumReducer.class);
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(IntWritable.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        TextInputFormat.addInputPath(job, new Path("data/numbers.txt"));
        TextOutputFormat.setOutputPath(job, new Path("results/hadoop/numbersum"));

        job.waitForCompletion(true);
    }
}
