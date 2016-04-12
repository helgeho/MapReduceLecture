object NumberSum {
  val numbers = Seq("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

  def main(args: Array[String]) = {
    val input = Seq("two", "seven", "one", "five")
    println(input)
    val mapped = input.map(numbers.indexOf)
    println(mapped)
    val result = mapped.reduce(_ + _)
    println(result)

    // parallel one-liner
    println(input.par.map(numbers.indexOf).sum)
  }
}
