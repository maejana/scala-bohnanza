package Test.controller

import Test.model

import java.awt.event.KeyListener
import scala.collection.mutable.ArrayBuffer
import scala.language.postfixOps
import scala.util.Random
import org.jline.terminal.{Terminal, TerminalBuilder}
import org.jline.keymap.{BindingReader, KeyMap}


object UIlogic{
  val terminal: Terminal = TerminalBuilder.builder().system(true).build()
  terminal.enterRawMode()
  val reader = terminal.reader()
  def weightedRandom(): String = {
    var allcards = ArrayBuffer[String]()
    for(i <- 1 to model.gamedata.cards.size){
      for(h <- 1 to model.gamedata.cards(i-1).weightCount)
      allcards.addOne(model.gamedata.cards(i-1).beanName)
    }
    val rand = Random.nextInt(allcards.size)
    allcards(rand) match
      case "Blaue" => model.gamedata.cards(0).weightCount -= 1
      case "Feuer" => model.gamedata.cards(1).weightCount -= 1
      case "Sau" => model.gamedata.cards(2).weightCount -= 1
      case "Brech" => model.gamedata.cards(3).weightCount -= 1
      case "Soja" => model.gamedata.cards(4).weightCount -= 1
      case "Augen" => model.gamedata.cards(5).weightCount -= 1
      case "Rote" => model.gamedata.cards(6).weightCount -= 1
      case "Garten" => model.gamedata.cards(7).weightCount -= 1

    allcards(rand)
  }
  def initPlayer(name: String): String = {
    val playerName = name
    var growingFieldText: String =
      s"""
          ${playerName}:
             Field 1:

             Field 2:

             Field 3:


          """
    val weights = model.gamedata.weights
    var hand: Array[String] = Array("","","","","")
    for (i <- 1 to 4) {
      hand(i-1) = weightedRandom()
      growingFieldText += hand(i-1) + ", "
    }
    hand(4) = weightedRandom()
    growingFieldText += hand(4)
    model.gamedata.players += model.player(playerName,hand,growingFieldText)
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
  def isPlantable(player :model.player, bean: String): Boolean = {
    if(!player.hand.contains(bean)){
      return false
    }
    if(player.plantfield1.contains(bean) || player.plantfield2.contains(bean) || player.plantfield3.contains(bean)){
      return true
    }
    else if(player.plantfield1 == ""|| player.plantfield2 == ""|| player.plantfield3 == ""){
      return true
    }
    else {
      return false
    }
  }
  def plantSelectString(player: model.player): String = {
    var s: String = ""
    s += model.gamedata.selectPlantCard
    s += player.hand.mkString("", ", ", "")
    s
  }
  def keyListener(): Int = {
    val key = reader.read()
    key match {
      case '1' => 1
      case '2' => 2
    }
  }
  def buildGrowingFieldStr(playingPlayer : model.player): String = {
    val growingFieldText: String =
      s"""
                               ${playingPlayer.playerName}:
                                  Field 1:
                               ${playingPlayer.plantfield1}
                                  Field 2:
                               ${playingPlayer.plantfield2}
                                  Field 3:
                               ${playingPlayer.plantfield3}

                               """
    growingFieldText
  }
}
