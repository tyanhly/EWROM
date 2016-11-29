import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD

object GraphXApp {

    def main(args: Array[String]) {
        val vertexArray = Array(
            (1L, ("Alice", 28)),
            (2L, ("Bob", 27)),
            (3L, ("Charlie", 65)),
            (4L, ("David", 42)),
            (5L, ("Ed", 55)),
            (6L, ("Fran", 50)))

        val edgeArray = Array(
            Edge(2L, 1L, 7),
            Edge(2L, 4L, 2),
            Edge(3L, 2L, 4),
            Edge(3L, 6L, 3),
            Edge(4L, 1L, 1),
            Edge(5L, 2L, 2),
            Edge(5L, 3L, 8),
            Edge(5L, 6L, 3)
        )

        val sc = new SparkContext(new SparkConf().setAppName("GraphXApp"))
        val vertexRDD: RDD[(Long, (String, Int))] =
            sc.parallelize(vertexArray)

        val edgeRDD: RDD[Edge[Int]] =
            sc.parallelize(edgeArray)

        val graph: Graph[(String, Int), Int] = Graph(vertexRDD, edgeRDD)
        
        // Solution 1
        graph.vertices.filter { case (id, (name, age)) => age > 30 }.collect.foreach {
            case (id, (name, age)) => println(s"$name is $age")
        }

        // Solution 2
        graph.vertices.filter(v => v._2._2 > 30).collect.foreach(v => println(s"${v._2._1} is ${v._2._2}"))

        // Solution 3
        for ((id, (name, age)) <- graph.vertices.filter { case (id, (name, age)) => age > 30 }.collect) {
            println(s"$name is $age")
        }
        sc.stop()
    }
}  
