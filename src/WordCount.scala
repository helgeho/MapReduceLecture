import scala.io.Source

object WordCount {
  def main(args: Array[String]) = {
    val lines = Source.fromFile("data/wikipedia_mapreduce.txt").getLines().toList

    // map
    val mapped = lines.map{ line =>
      for (word <- line.split("[\\W]+"))
        yield (word.toLowerCase(), 1)
    }

    // shuffle
    val shuffled = mapped.flatMap(words => words).groupBy{case (key, value) => key}
    println(shuffled)

    // reduce
    val reduced = shuffled.map{case (key, values) =>
      val word = key
      var result = 0
      for ((word, count) <- values) result += count
      (word, result)
    }
    println(reduced)

    // parallel one-liner
    println(lines.par.flatMap(_.split("[\\W]+")).groupBy(word => word).map{case (word, occurrences) => (word, occurrences.size)})
  }
}