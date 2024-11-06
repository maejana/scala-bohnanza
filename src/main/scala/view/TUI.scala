package Test.view

import Test.{controller, model}

object TUI {
  def setUpTUI(): Unit = {
    println(model.gamedata.welcome)
    println(model.gamedata.playerCountQuestion)
    println(controller.setuplogic.initGame)
  }
  def gameUpdate(): Unit = {
    var i = 0
    while (true){
      val playingPlayer: model.player = model.gamedata.players(i)
      println(playingPlayer.playerName+ ":")
      println(model.gamedata.selectPlantCard + ":")
      println(playingPlayer.hand.mkString("", ", ", ""))
      var bool = true
      while(bool) {
        val Line = scala.io.StdIn.readLine().split((" "), 2)
        val plantCard = Line(0)
        val fieldNr = Line(1).toInt
        if (playingPlayer.hand.contains(plantCard)) {
          controller.gamelogic.plant(plantCard, fieldNr, playingPlayer)
          bool = false
        } else {
          println(model.gamedata.errorBeanNotInHand)
        }
      }
      
      println("works")
    }
  }
}