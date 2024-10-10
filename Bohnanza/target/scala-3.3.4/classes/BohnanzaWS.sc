object BohnanzaWS {
  @main def run: Unit = {
    println("Welcome to Bohnanza")
    drawPlayerField()


    // Anbaufeld ist (2x7)x10 groß
    // Spiekarte 6x9
    // Jeder Spieler braucht 2 Felder (evtl 3.) Handkarten


  }

  val growingFieldText =
    """
           _____
      |
      |
      |_____
        """

  def drawPlayerField(): Unit = {
    for (i <- 1 to 2) {
      println(growingFieldText)
    }
  }
}