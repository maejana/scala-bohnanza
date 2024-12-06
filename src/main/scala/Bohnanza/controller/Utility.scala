package Bohnanza.controller

import Bohnanza.view
import Bohnanza.model
import Bohnanza.model.{card, player}

import scala.collection.mutable.ArrayBuffer

object Utility {

  def plantInfo(): model.card = {
    val cardname = scala.io.StdIn.readLine()
    
    var card: model.card = findCardWithName(cardname)
    model.dynamicGamedata.cardsToPlant += card
    card
    
    
  }

  def plantPreperation(player: model.player): String = {
    var plantCard: model.card = card(model.gamedata.beans(2),model.gamedata.weights(0), model.gamedata.priceBlaue)
    if (model.dynamicGamedata.cardsToPlant.isEmpty) {
      plantCard = plantInfo()
     
    }
    val gameUpdateLog = new StringBuilder()
    if (plantCard.equals(card(model.gamedata.beans(2),model.gamedata.weights(0), model.gamedata.priceBlaue))) {
      ""
    }
    else if (isPlantable(player, plantCard) && player.playerHand.contains(plantCard)) {
      gamelogic.plant(plantCard, player)
      model.gameDataFunc.takeNewCard(player, plantCard)
      gameUpdateLog.append(s"${player.name} pflanzt $plantCard\n")
      gameUpdateLog.toString
    } else {
      ""
    }
  }

  def findCardWithName(name: String): model.card = {
    var i = 0
    for (i <- 0 until model.gamedata.cards.length) {
      if (model.gamedata.cards(i).beanName == name) {
        return model.gamedata.cards(i)
      }
    }
    card(model.gamedata.beans(0),model.gamedata.weights(0), model.gamedata.priceBlaue)
  }

  def plantDrawnCard(player: model.player, card: model.card): Unit = {
    if (isPlantable(player, card)) {
      gamelogic.plant(card, player)
    }
  }

  def emptyPlantfieldNr(player: model.player): Int = {
    if (player.plantfield1.isEmpty) {
      return 1
    } else if (player.plantfield2.isEmpty) {
      return 2
    } else if (player.plantfield3.isEmpty) {
      return 3
    } else return -1
  }

  def selectCardToPlant(cards: ArrayBuffer[model.card], player: model.player): model.card = {
    var bool = true
    while (bool) {
      val cardToPlant = plantInfo()
      if (cards.contains(cardToPlant)) {
        bool = false
        return cardToPlant
      } else {
        bool = true
      }
    }
    card(model.gamedata.beans(2),model.gamedata.weights(0), model.gamedata.priceBlaue)
  }

  def findCardId(player: model.player, card: model.card): Int = {
    player.playerHand.indexOf(card)
  }

  def selectPlayer(): player = {
    if (model.dynamicGamedata.playerCount < model.dynamicGamedata.playingPlayerID){
      model.dynamicGamedata.playingPlayerID = 0
      model.dynamicGamedata.playingPlayer = model.dynamicGamedata.players(0)
      model.dynamicGamedata.playingPlayer
    }
    else {
      model.dynamicGamedata.playingPlayer = model.dynamicGamedata.players(model.dynamicGamedata.playingPlayerID)
      model.dynamicGamedata.playingPlayerID += 1
      model.dynamicGamedata.playingPlayer
    }
  }
  def isPlantable(player: model.player, bean: model.card): Boolean = {
    if (player.plantfield1.contains(bean.beanName) || player.plantfield2.contains(bean.beanName) || player.plantfield3.contains(bean.beanName)) {
      return true
    }
    else if (player.plantfield1.isEmpty || player.plantfield2.isEmpty || player.plantfield3.isEmpty) {
      return true
    }
    else {
      return false
    }
  }

  def chooseOrEmpty(playerID: model.player, card: model.card): Int = {
    if (playerID.plantfield1.contains(card)) {
      return 1
    } else if (playerID.plantfield2.contains(card)) {
      return 2
    } else if (playerID.plantfield3.contains(card)) {
      return 3
    } else {
      emptyPlantfieldNr(playerID)
    }
  }

  def plant1or2(playingPlayer: player): Int = {
    var Nr = view.playerInput.keyListener()
    if(Nr == -1)
    if(Nr == 0) {
      println(model.gamedata.keineKorrekteNR)
      Nr = view.playerInput.keyListener()
    }
    if(Nr < 1) Nr = 1
    if(Nr > 2) Nr = 2
    println(UIlogic.plantSelectString(playingPlayer))
    playingPlayer.lastMethodUsed = "plant1or2"
    Nr
  }

  def plantAllSelectedCards(plantCount : Integer): Unit = {
    var i = 0
    while (i < plantCount) {
      if (!Utility.plantPreperation(model.dynamicGamedata.playingPlayer).equals("")) {
        i += 1
      }
      else {
        println(model.gamedata.keineKorrekteBohne)
      }
    }
  }
}