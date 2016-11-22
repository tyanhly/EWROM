import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object SparkWordCountApp {
    def main(args: Array[String]) {

        val sc = new SparkContext(new SparkConf().setAppName("SparkWordCount"))

        val input = sc.textFile("/vagrant/setup/SherlockHolmes.txt")

        val count = input.flatMap(line ⇒ line.split(" "))
            .map(word ⇒ (word, 1))
            .reduceByKey(_ + _)

        count.saveAsTextFile("outfile")
        println("OK")
        sc.stop()
    }
} 
