package Bohnanza.controller

import Bohnanza.view
import Bohnanza.model
import Bohnanza.model.{card, player}

import scala.collection.mutable.ArrayBuffer

object Utility {

  def plantInfo(): model.card = {
    val cardname = model.dynamicGamedata.cardsToPlant(0).beanName
    model.dynamicGamedata.cardsToPlant -= Utility.findCardWithName(cardname)
    
    val card: model.card = findCardWithName(cardname)
    model.dynamicGamedata.cardsToPlant += card
    card
    
    
  }

  def plantPreperation(player: Option[model.player]): String = {
    var plantCard: model.card = card(model.gamedata.beans(2),model.gamedata.weights(0), model.gamedata.priceBlaue)
    if (!model.dynamicGamedata.cardsToPlant.isEmpty) {
      plantCard = plantInfo()
    }
    val gameUpdateLog = new StringBuilder()
    if (plantCard.equals(card(model.gamedata.beans(2),model.gamedata.weights(0), model.gamedata.priceBlaue))) {
      ""
    } else if (isPlantable(player, plantCard) && player.exists(_.playerHand.contains(plantCard))) {
      gamelogic.plant(plantCard, player)
      val cardIndex = player.get.playerHand.indexOf(plantCard)
      if (cardIndex >= 0 && cardIndex < player.get.playerHand.size) {
        model.gameDataFunc.takeNewCard(player, plantCard)
        gameUpdateLog.append(s"${player.get.name} pflanzt $plantCard\n")
      }
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

  def plantDrawnCard(player: Option[model.player], card: model.card): Unit = {
    if (isPlantable(player, card)) {
      gamelogic.plant(card, player)
    }
  }

  def emptyPlantfieldNr(player: Option[model.player]): Int = {
    if (player.get.plantfield1.isEmpty) {
      return 1
    } else if (player.get.plantfield2.isEmpty) {
      return 2
    } else if (player.get.plantfield3.isEmpty) {
      return 3
    } else return -1
  }

  def selectCardToPlant(cards: ArrayBuffer[model.card]): model.card = {
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

  def selectPlayer(): Option[Bohnanza.model.player] = {
    if (model.dynamicGamedata.playerCount <= model.dynamicGamedata.playingPlayerID) {
      model.dynamicGamedata.playingPlayerID = 0
      model.dynamicGamedata.playingPlayer = Some(model.dynamicGamedata.players(model.dynamicGamedata.playingPlayerID))
      model.dynamicGamedata.playingPlayer
    }
    else {
      model.dynamicGamedata.playingPlayer = Some(model.dynamicGamedata.players(model.dynamicGamedata.playingPlayerID))
      model.dynamicGamedata.playingPlayerID += 1
      model.dynamicGamedata.playingPlayer
    }
  }
  def isPlantable(player: Option[model.player], bean: model.card): Boolean = {
    if (player.get.plantfield1.contains(bean.beanName) || player.get.plantfield2.contains(bean.beanName) || player.get.plantfield3.contains(bean.beanName)) {
      return true
    }
    else if (player.get.plantfield1.isEmpty || player.get.plantfield2.isEmpty || player.get.plantfield3.isEmpty) {
      return true
    }
    else {
      return false
    }
  }

  def chooseOrEmpty(playerID: Option[model.player], card: model.card): Int = {
    if (playerID.get.plantfield1.contains(card)) {
      return 1
    } else if (playerID.get.plantfield2.contains(card)) {
      return 2
    } else if (playerID.get.plantfield3.contains(card)) {
      return 3
    } else {
      emptyPlantfieldNr(playerID)
    }
  }

  def plant1or2(playingPlayer: Option[player]): Int = {
    view.playerInput.keyListener()
    var Nr = model.dynamicGamedata.plant1or2
    if(Nr == -1)
    if(Nr == 0) {
      println(model.gamedata.keineKorrekteNR)
      view.playerInput.keyListener()
      Nr = model.dynamicGamedata.plant1or2
    }
    if(Nr <= 1) {
      model.dynamicGamedata.cardsToPlant += playingPlayer.get.playerHand(0)
      gamelogic.plant(model.dynamicGamedata.cardsToPlant(0),model.dynamicGamedata.playingPlayer)
      model.gameDataFunc.takeNewCard(playingPlayer, playingPlayer.get.playerHand(0))
      Nr = 1
    }
    if(Nr >= 2){
      model.dynamicGamedata.cardsToPlant += playingPlayer.get.playerHand(0)
      model.dynamicGamedata.cardsToPlant += playingPlayer.get.playerHand(1)
      gamelogic.plant(model.dynamicGamedata.cardsToPlant(0),model.dynamicGamedata.playingPlayer)
      gamelogic.plant(model.dynamicGamedata.cardsToPlant(1),model.dynamicGamedata.playingPlayer)
      model.gameDataFunc.takeNewCard(playingPlayer, playingPlayer.get.playerHand(0))
      model.gameDataFunc.takeNewCard(playingPlayer, playingPlayer.get.playerHand(1))

      Nr = 2}
    println(UIlogic.plantSelectString(playingPlayer))
    playingPlayer.get.lastMethodUsed = "plant1or2"
    Nr
  }

  def plantAllSelectedCards(plantCount : Integer): Unit = {
    var i = 0
    while (i < plantCount) {
      Utility.plantPreperation(model.dynamicGamedata.playingPlayer)
      i += 1
    }
  }

  def plant1or2ThreadInterrupt(): Unit = {
    model.dynamicGamedata.readerThreadPlant1or2.interrupt()
    model.fieldBuilder.buildGrowingFieldStr(model.dynamicGamedata.playingPlayer)
  }
}