package Test.controller

import Test.model

object Utility {
  def keepOrTrade(): Unit = {
    println(model.gamedata.drawCardText)
    var keepCardNr = UIlogic.keyListener()
    keepCardNr match
      case 0 =>
      case 1 =>
      case 2 =>
  }
  def findCardId(player: model.player, card: model.card): Int = {
    player.playerHand.indexOf(card.beanName)
  }
  def plant1or2(playingPlayer : model.player): Unit = {
    val Nr = UIlogic.keyListener()
    println(UIlogic.plantSelectString(playingPlayer))
  }
  def selectPlayer(p : Int): model.player = {
    val playingPlayer: model.player = model.gamedata.players(p)
    println(playingPlayer.playerName + ":\n")
    playingPlayer
  }
  def plantInfo(): String{

  }
}
