import scala.util.Random

object B1 {
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
    println(randomizeHand().mkString(", "))
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
  def randomizeHand(): Array[String] = {
    val hand = Array("Blaue","Feuer","Sau","Brech","Soja","Augen","Rote","Garten")
    val handCards : Array[String] = new Array[String](5)
    for (i <- 1 to 5) {
      val zufallsZahl = Random.nextInt(8) + 1
      handCards(i-1) = hand(zufallsZahl-1)
    }
    return handCards
  }



}