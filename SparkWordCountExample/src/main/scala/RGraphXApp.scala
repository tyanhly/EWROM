import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD
import io.vcode.eworm.entity.REntity
import io.vcode.eworm.entity.RAction
import io.vcode.eworm.entity.RAttribute

object RGraphXApp {

    def main(args: Array[String]) {
        val vertexArray = Array(
            (1L, REntity.getInstance(
                "Person",
                Array(
                    RAttribute.getInstance[String]("name", "Tung Ly"),
                    RAttribute.getInstance[Int]("old", 32)
                ),
                Array(
                    RAction.getInstance[Int]("Sleep", 8)
                )
            )),
            (2L, REntity.getInstance(
                "Person",
                Array(
                    RAttribute.getInstance[String]("name", "TTTT"),
                    RAttribute.getInstance[String]("name1", "TTTT"),
                    RAttribute.getInstance[String]("name2", "TTTT"),
                    RAttribute.getInstance[String]("name3", "TTTT"),
                    RAttribute.getInstance[Int]("old", 11)
                ),
                Array(
                    RAction.getInstance[Int]("Sleep", 8)
                )
            )),
            (3L, REntity.getInstance(
                "Person",
                Array(
                    RAttribute.getInstance[String]("name", "TT3333TT"),
                    RAttribute.getInstance[String]("name1", "TT3333TT"),
                    RAttribute.getInstance[String]("name2", "TTT333T"),
                    RAttribute.getInstance[String]("name3", "TT333TT"),
                    RAttribute.getInstance[Int]("old", 11)
                ),
                Array(
                    RAction.getInstance[Int]("Sleep", 8)
                )
            ))
        );

        val edgeArray = Array(
            Edge(2L, 1L, Array("old")),
            Edge(2L, 3L, Array("old", "name2", "name1")),
            Edge(1L, 2L, Array("old", "name"))
        )

        val sc = new SparkContext(new SparkConf().setAppName("GraphXApp"))
        val vertexRDD: RDD[(Long, REntity)] =
            sc.parallelize(vertexArray)

        val edgeRDD: RDD[Edge[Array[String]]] =
            sc.parallelize(edgeArray)

        val graph: Graph[REntity, Array[String]] = Graph(vertexRDD, edgeRDD)

        // Solution 1
        graph.vertices.filter { case (id, entity) => entity.attributes.length > 2}.collect.foreach {
            case (id, entity) => println("name: " + entity.attributes(0).name + "; value: " + entity.attributes(0).value)
        }
        
        // Solution 3
        for ((id, entity) <- graph.vertices.filter { case (id, entity) => entity.attributes.length > 2 }.collect) {
            println("name: " + entity.attributes(0).name + "; value: " + entity.attributes(0).value)
        }

        for (triplet <- graph.triplets.collect) {
            println(s"${triplet.srcAttr.attributes(0).value} co quan he ${triplet.dstAttr.attributes(0).value}")
        }

        for (triplet <- graph.triplets.filter(t => t.attr.length > 1).collect) {
            println(s"${triplet.srcAttr.attributes(0).value} quan he voi ${triplet.dstAttr.attributes(0).value} 2 thuoc tinh tro len ")
        }
        sc.stop()
    }
}  
