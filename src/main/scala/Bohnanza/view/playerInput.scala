package Bohnanza.view
import Bohnanza.model

object playerInput {
  def keyListener(): Int = {
    val key = scala.io.StdIn.readLine().strip()
    key match {
      case "0" => 0
      case "1" => 1
      case "2" => 2
      case "M" => handleInput.handleInputF(model.dynamicGamedata.playingPlayer)
        -1
      case _ => println(model.gamedata.keineKorrekteNR)
        keyListener()
    }
  }
  def playercount(): Unit={
    val count = scala.io.StdIn.readInt()
    model.dynamicGamedata.playerCount = count
    count
  }
  def playername(): String = {
    val name = scala.io.StdIn.readLine()
    name
  }
}
