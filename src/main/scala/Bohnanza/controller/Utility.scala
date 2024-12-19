package Bohnanza.controller

import Bohnanza.view
import Bohnanza.model
import Bohnanza.model.{card, player}
import scala.util.Random
import scala.collection.mutable.ArrayBuffer
import model.ObserverData
import model.CardObserver

object Utility extends ControllerComponent {
  ObserverData.addObserver(CardObserver)
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
    }
    else if (isPlantable(player, plantCard) && player.get.playerHand.contains(plantCard)) {
      gamelogic.plant(plantCard, player)
      model.gameDataFunc.takeNewCard(player)
      gameUpdateLog.append(s"${player.get.name} pflanzt $plantCard\n")
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
    if (model.dynamicGamedata.playerCount <= model.dynamicGamedata.playingPlayerID-1) {
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
      model.gameDataFunc.takeNewCard(playingPlayer)
      Nr = 1
    }
    if(Nr >= 2){
      model.dynamicGamedata.cardsToPlant += playingPlayer.get.playerHand(0)
      model.dynamicGamedata.cardsToPlant += playingPlayer.get.playerHand(1)
      gamelogic.plant(model.dynamicGamedata.cardsToPlant(0),model.dynamicGamedata.playingPlayer)
      gamelogic.plant(model.dynamicGamedata.cardsToPlant(1),model.dynamicGamedata.playingPlayer)
      model.gameDataFunc.takeNewCard(playingPlayer)
      model.gameDataFunc.takeNewCard(playingPlayer)

      Nr = 2}
    println(plantSelectString(playingPlayer))
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

  def returnGoldValue(plantfield : ArrayBuffer[model.card]): Int = {
    if(!plantfield.isEmpty){
      checkPlantAmount(plantfield(0),plantfield)
    }
    0
  }

  def checkPlantAmount(card: model.card, plantfield : ArrayBuffer[model.card]): Int = {
    var cardSteps = 1
    while(plantfield.size >= card.price(cardSteps)){
      cardSteps += 1
    }
    card.price(cardSteps)
  }

  def weightedRandom(): model.card = {
    val allcards = ArrayBuffer[model.card]()
    for (i <- 1 to model.gamedata.cards.size) {
      for (h <- 1 to model.gamedata.cards(i - 1).weightCount)
        allcards.addOne(model.gamedata.cards(i - 1))
    }
    val rand = Random.nextInt(allcards.size)
    allcards(rand).beanName match {
      case "Blaue" => model.gamedata.cards(0).weightCount -= 1
      case "Feuer" => model.gamedata.cards(1).weightCount -= 1
      case "Sau" => model.gamedata.cards(2).weightCount -= 1
      case "Brech" => model.gamedata.cards(3).weightCount -= 1
      case "Soja" => model.gamedata.cards(4).weightCount -= 1
      case "Augen" => model.gamedata.cards(5).weightCount -= 1
      case "Rote" => model.gamedata.cards(6).weightCount -= 1
      case "Garten" => model.gamedata.cards(7).weightCount -= 1
    }
    ObserverData.updateCards()
    allcards(rand)

  }

  def plantSelectString(player: Option[model.player]): String = {
    var s: String = ""
    s += model.gamedata.selectPlantCard
    s += model.gameDataFunc.playerHandToString(player.get.playerHand)
    s
  }
}