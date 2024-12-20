package Bohnanza.controller.controllerBase

import Bohnanza.controller.ControllerComponent
import Bohnanza.controller.controllerBase.gamelogic
import Bohnanza.model.modelBase.{CardObserver, ObserverData, card, dynamicGamedata, fieldBuilder, gameDataFunc, gamedata, player}
import Bohnanza.model.modelBase
import Bohnanza.view.viewBase
import Bohnanza.view.viewBase.playerInput
import Bohnanza.{model, view}

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

object Utility extends ControllerComponent {
  ObserverData.addObserver(CardObserver)
  def plantInfo(): card = {
    val cardname = dynamicGamedata.cardsToPlant(0).beanName
    modelBase.dynamicGamedata.cardsToPlant -= Utility.findCardWithName(cardname)
    
    val card: card = findCardWithName(cardname)
    modelBase.dynamicGamedata.cardsToPlant += card
    card
    
    
  }

  def plantPreperation(player: Option[player]): String = {
    var plantCard: card = card(gamedata.beans(2),modelBase.gamedata.weights(0), modelBase.gamedata.priceBlaue)
    if (!modelBase.dynamicGamedata.cardsToPlant.isEmpty) {
      plantCard = plantInfo()
    }
    val gameUpdateLog = new StringBuilder()
    if (plantCard.equals(card(modelBase.gamedata.beans(2),modelBase.gamedata.weights(0), modelBase.gamedata.priceBlaue))) {
      ""
    }
    else if (isPlantable(player, plantCard) && player.get.playerHand.contains(plantCard)) {
      gamelogic.plant(plantCard, player)
      gameDataFunc.takeNewCard(player)
      gameUpdateLog.append(s"${player.get.name} pflanzt $plantCard\n")
      gameUpdateLog.toString
    } else {
      ""
    }
  }

  def findCardWithName(name: String): card = {
    var i = 0
    for (i <- 0 until modelBase.gamedata.cards.length) {
      if (modelBase.gamedata.cards(i).beanName == name) {
        return modelBase.gamedata.cards(i)
      }
    }
    card(modelBase.gamedata.beans(0),modelBase.gamedata.weights(0), modelBase.gamedata.priceBlaue)
  }

  def plantDrawnCard(player: Option[player], card: card): Unit = {
    if (isPlantable(player, card)) {
      gamelogic.plant(card, player)
    }
  }

  def emptyPlantfieldNr(player: Option[player]): Int = {
    if (player.get.plantfield1.isEmpty) {
      return 1
    } else if (player.get.plantfield2.isEmpty) {
      return 2
    } else if (player.get.plantfield3.isEmpty) {
      return 3
    } else return -1
  }

  def selectCardToPlant(cards: ArrayBuffer[card]): card = {
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
    card(modelBase.gamedata.beans(2),modelBase.gamedata.weights(0), modelBase.gamedata.priceBlaue)
  }

  def findCardId(player: player, card: card): Int = {
    player.playerHand.indexOf(card)
  }

  def selectPlayer(): Option[player] = {
    if (modelBase.dynamicGamedata.playerCount <= modelBase.dynamicGamedata.playingPlayerID-1) {
      modelBase.dynamicGamedata.playingPlayerID = 0
      modelBase.dynamicGamedata.playingPlayer = Some(modelBase.dynamicGamedata.players(modelBase.dynamicGamedata.playingPlayerID))
      modelBase.dynamicGamedata.playingPlayer
    }
    else {
      modelBase.dynamicGamedata.playingPlayer = Some(modelBase.dynamicGamedata.players(modelBase.dynamicGamedata.playingPlayerID))
      modelBase.dynamicGamedata.playingPlayerID += 1
      modelBase.dynamicGamedata.playingPlayer
    }
  }

  def isPlantable(player: Option[player], bean: card): Boolean = {
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

  def chooseOrEmpty(playerID: Option[player], card: card): Int = {
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
    playerInput.keyListener()
    var Nr = modelBase.dynamicGamedata.plant1or2
    if(Nr == -1)
    if(Nr == 0) {
      println(modelBase.gamedata.keineKorrekteNR)
      viewBase.playerInput.keyListener()
      Nr = modelBase.dynamicGamedata.plant1or2
    }
    if(Nr <= 1) {
      modelBase.dynamicGamedata.cardsToPlant += playingPlayer.get.playerHand(0)
      gamelogic.plant(modelBase.dynamicGamedata.cardsToPlant(0),modelBase.dynamicGamedata.playingPlayer)
      modelBase.gameDataFunc.takeNewCard(playingPlayer)
      Nr = 1
    }
    if(Nr >= 2){
      modelBase.dynamicGamedata.cardsToPlant += playingPlayer.get.playerHand(0)
      modelBase.dynamicGamedata.cardsToPlant += playingPlayer.get.playerHand(1)
      gamelogic.plant(modelBase.dynamicGamedata.cardsToPlant(0),modelBase.dynamicGamedata.playingPlayer)
      gamelogic.plant(modelBase.dynamicGamedata.cardsToPlant(1),modelBase.dynamicGamedata.playingPlayer)
      modelBase.gameDataFunc.takeNewCard(playingPlayer)
      modelBase.gameDataFunc.takeNewCard(playingPlayer)

      Nr = 2}
    println(plantSelectString(playingPlayer))
    playingPlayer.get.lastMethodUsed = "plant1or2"
    Nr
  }

  def plantAllSelectedCards(plantCount : Integer): Unit = {
    var i = 0
    while (i < plantCount) {
      Utility.plantPreperation(modelBase.dynamicGamedata.playingPlayer)
      i += 1
    }
  }

  def plant1or2ThreadInterrupt(): Unit = {
    modelBase.dynamicGamedata.readerThreadPlant1or2.interrupt()
    fieldBuilder.buildGrowingFieldStr(modelBase.dynamicGamedata.playingPlayer)
  }

  def returnGoldValue(plantfield : ArrayBuffer[card]): Int = {
    if(!plantfield.isEmpty){
      checkPlantAmount(plantfield(0),plantfield)
    }
    0
  }

  def checkPlantAmount(card: card, plantfield : ArrayBuffer[card]): Int = {
    var cardSteps = 1
    while(plantfield.size >= card.price(cardSteps)){
      cardSteps += 1
    }
    card.price(cardSteps)
  }

  def weightedRandom(): card = {
    val allcards = ArrayBuffer[card]()
    for (i <- 1 to modelBase.gamedata.cards.size) {
      for (h <- 1 to modelBase.gamedata.cards(i - 1).weightCount)
        allcards.addOne(modelBase.gamedata.cards(i - 1))
    }
    val rand = Random.nextInt(allcards.size)
    allcards(rand).beanName match {
      case "Blaue" => modelBase.gamedata.cards(0).weightCount -= 1
      case "Feuer" => modelBase.gamedata.cards(1).weightCount -= 1
      case "Sau" => modelBase.gamedata.cards(2).weightCount -= 1
      case "Brech" => modelBase.gamedata.cards(3).weightCount -= 1
      case "Soja" => modelBase.gamedata.cards(4).weightCount -= 1
      case "Augen" => modelBase.gamedata.cards(5).weightCount -= 1
      case "Rote" => modelBase.gamedata.cards(6).weightCount -= 1
      case "Garten" => modelBase.gamedata.cards(7).weightCount -= 1
    }
    ObserverData.updateCards()
    allcards(rand)

  }

  def plantSelectString(player: Option[player]): String = {
    var s: String = ""
    s += modelBase.gamedata.selectPlantCard
    s += modelBase.gameDataFunc.playerHandToString(player.get.playerHand)
    s
  }

  def deletePlayerBecauseBug(): Unit = {
    if(model.modelBase.dynamicGamedata.players.size != model.modelBase.dynamicGamedata.playerCount){
      for(i <- 1 to model.modelBase.dynamicGamedata.players.size-model.modelBase.dynamicGamedata.playerCount){
        model.modelBase.dynamicGamedata.players.remove(model.modelBase.dynamicGamedata.players.size-1)
      }
      println(model.modelBase.dynamicGamedata.players.size)
    }
  }
}