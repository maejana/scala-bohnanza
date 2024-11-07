package Test.controller

import Test.model

import scala.collection.mutable.ArrayBuffer
import scala.language.postfixOps
import scala.util.Random

object UIlogic {
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
  def gameUpdate(): String = {
    var i = 0
    var p = 0
    var round = 1
    val gameUpdateLog = new StringBuilder

    while (round <= 10) {
      val playingPlayer: model.player = model.gamedata.players(p)
      println(playingPlayer.playerName + ":\n")
      // Spieler kann eine Karte wählen )
      println(model.gamedata.plantAmountQuestion)
      var bool = true
      var plantCount = 0
      while (bool){
        val Nr = scala.io.StdIn.readLine()
        if(Nr == "1" || Nr == "2"){
          plantCount = Nr.toInt
          bool = false
        }
        else{
          println(model.gamedata.errorInputNotInt)
          bool = true
        }
      }
      println(plantSelectString(playingPlayer))
      while(i < plantCount) {
        val Line = scala.io.StdIn.readLine().split(" ", 2)
        val plantCard = Line(0)
        val fieldNrStr = Line(1)

        if (playingPlayer.hand.contains(plantCard) && isPlantable(playingPlayer, plantCard) && (fieldNrStr == "1" || fieldNrStr == "2" || fieldNrStr == "3")) {
          val fieldNr = fieldNrStr.toInt
          gamelogic.plant(plantCard, fieldNr, playingPlayer)
          gameUpdateLog.append(s"${playingPlayer.name} pflanzt $plantCard auf Feld $fieldNr\n")
          i += 1
        } else {
          println(model.gamedata.errorBeanNotInHand)
          println(model.gamedata.errorPlantingField + "\n")
        }
      }
      var growingFieldText: String =
        s"""
                          ${playingPlayer.playerName}:
                             Field 1:
                          ${playingPlayer.plantfield1}
                             Field 2:
                          ${playingPlayer.plantfield2}
                             Field 3:
                          ${playingPlayer.plantfield3}

                          """
      gameUpdateLog.append(growingFieldText)
      round += 1
      p += 1
    }
    gameUpdateLog.toString // Gibt das gesamte Log als String zurück
  }
  private def isPlantable(player :model.player, bean: String): Boolean = {
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
  private def plantSelectString(player : model.player): String={
    var s :String = ""
    s += model.gamedata.selectPlantCard
    s += player.hand.mkString("", ", ", "")
    s
  }
}
