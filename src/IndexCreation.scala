import org.apache.spark.{SparkConf, SparkContext}

object IndexCreation {
  def main(args: Array[String]) = {
    val conf = new SparkConf().setAppName("IndexCreation").setMaster("local")
    val sc = new SparkContext(conf)

    val collection = sc.textFile("data/wikipedia_mapreduce.txt")
    val collectionWithIds = collection.zipWithIndex()

    val tokenized = collectionWithIds.map{case (text, docId) => (docId, text.split("[\\W]+").map(token => token.toLowerCase))}
    val tokens = tokenized.flatMap{case (docId, tokens) => tokens.map(token => (token, Set(docId)))}
    val tokensGrouped = tokens.reduceByKey((docs1, docs2) => docs1 ++ docs2).map{case (token, docs) => (token, docs.toSeq.sorted)}

    val tokenSorted = tokensGrouped.sortByKey().map{case (token, docs) => s"$token -> ${docs.mkString(",")}"}
    tokenSorted.saveAsTextFile("results/spark/wikipedia_mapreduce_index")
  }
}
