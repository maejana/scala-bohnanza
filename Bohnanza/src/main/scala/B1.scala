import scala.util.Random

object BohnanzaWS {
  @main def run: Unit = {
    println("Welcome to Bohnanza")
    initGame()


    // Anbaufeld ist (2x7)x10 groß
    // Spiekarte 6x9
    // Jeder Spieler braucht 2 Felder (evtl 3.) Handkarten


  }

  //val playerName = "Miesepetriger Mustafa Van Meckerich"
  def initPlayer(name: String): Unit = {
    val playerName = name
    val growingFieldText =
      s"""
        $playerName:
            ___________
           |     |     |
           |     |     |
           |_____|_____|
        """
    println(growingFieldText)
    println(weightedRandom().mkString(", "))
  }
  def setPlayername(): String = {
    println("Bitte geben Sie die Namen der Spieler ein:")

    scala.io.StdIn.readLine()
  }
  def initGame(): Unit = {
    println("Wie viele Spieler spielen?")
    val playerCount = scala.io.StdIn.readInt()
    val playernames : Array[String] = new Array[String](playerCount)
    println("Bitte geben Sie die Namen der Spieler ein:")
    for (i <- 1 to playerCount) {
      playernames(i-1) = scala.io.StdIn.readLine()
    }
    for (i <- 1 to playerCount) {
      initPlayer(playernames(i-1))
    }
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



}