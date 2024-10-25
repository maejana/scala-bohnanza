package Test
import scala.util.Random


object B1 {
  @main def run: Unit = {
    println("Welcome to Bohnanza")
    println("Wie viele Spieler spielen?")
    println(initGame)


    // Anbaufeld ist (2x7)x10 groß
    // Spiekarte 6x9
    // Jeder Spieler braucht 2 Felder (evtl 3.) Handkarten
  }

  def weightedRandom(): String = {
    val hand = Array("Blaue", "Feuer", "Sau", "Brech", "Soja", "Augen", "Rote", "Garten")
    //
    val weights = Array(20, 18, 16, 14, 12, 10, 8, 6)
    val cumulativeWeights = weights.scanLeft(0)(_ + _).tail
    val totalWeight = cumulativeWeights.last

    val rand = Random.nextInt(totalWeight)

    val index = cumulativeWeights.indexWhere(_ > rand)
    hand(index)
  }

  //val playerName = "Miesepetriger Mustafa Van Meckerich"
  def initPlayer(name: String): String = {
    val playerName = name
    var growingFieldText: String =
      s"""
          $playerName:
              ___________
             |     |     |
             |     |     |
             |_____|_____|
          """

    for (i <- 1 to 4) {
      growingFieldText += weightedRandom() + ", "
    }
    growingFieldText += weightedRandom()
    growingFieldText
  }

  def initGame: String = {
    var str = ""
    val playerCount = scala.io.StdIn.readInt()
    val playernames: Array[String] = new Array[String](playerCount)
    println("Namen eingeben:")
    for (i <- 1 to playerCount) {

      playernames(i - 1) = scala.io.StdIn.readLine()
    }
    
    for (i <- 1 to playerCount) {
      str += initPlayer(playernames(i - 1))
    }
    str
  }


}