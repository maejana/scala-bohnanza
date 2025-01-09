package Bohnanza.controller.controllerBase

import Bohnanza.controller.ControllerComponent
import Bohnanza.model.modelBase.{CardObserver, ObserverData, card, dynamicGamedata, fieldBuilder, gameDataFunc, gamedata, player}
import Bohnanza.model.modelBase
import Bohnanza.view.viewBase
import Bohnanza.view.viewBase.playerInput
import Bohnanza.{model, view}
import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class Utility(ObserverData: ObserverDataComponent, dynamicGamedata: dynamicGamedataComponent, gamedata: gamedataComponent, gamelogic: gamelogicComponent, gameDataFunc: gameDataFuncComponent, fieldBuilder: fieldBuilderComponent) {
  ObserverData.addObserver(CardObserver)
  
  def plantInfo(): card = {

    val cardname = dynamicGamedata.cardsToPlant(0).beanName
    dynamicGamedata.cardsToPlant -= findCardWithName(cardname)
    val card: card = findCardWithName(cardname)
    dynamicGamedata.cardsToPlant += card
    card
  }

  override def plantPreperation(player: Option[player]): String = {
    //var plantCard: card = card(gamedata.beans(2),modelBase.gamedata.weights(0), modelBase.gamedata.priceBlaue)
    //if (!modelBase.dynamicGamedata.cardsToPlant.isEmpty) {
    val plantCard = plantInfo()
    //}
    val gameUpdateLog = new StringBuilder()
    if (plantCard.equals(card(gamedata.beans(2),gamedata.weights(0), gamedata.priceBlaue))) {
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

  override def findCardWithName(name: String): card = {
    var i = 0
    for (i <- 0 until gamedata.cards.length) {
      if (gamedata.cards(i).beanName == name) {
        return gamedata.cards(i)
      }
    }
    card(gamedata.beans(0),gamedata.weights(0), gamedata.priceBlaue)
  }

  override def plantDrawnCard(player: Option[player], card: card): Unit = {
    if (isPlantable(player, card)) {
      gamelogic.plant(card, player)
    }
  }

  override def emptyPlantfieldNr(player: Option[player]): Int = {
    if (player.get.plantfield1.isEmpty) {
      return 1
    } else if (player.get.plantfield2.isEmpty) {
      return 2
    } else if (player.get.plantfield3.isEmpty) {
      return 3
    } else return -1
  }

  override def selectCardToPlant(cards: ArrayBuffer[card]): card = {
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
    card(gamedata.beans(2),gamedata.weights(0), gamedata.priceBlaue)
  }

  override def findCardId(player: player, card: card): Int = {
    player.playerHand.indexOf(card)
  }

  def selectPlayer(): Option[player] = {
    if (dynamicGamedata.playerCount <= dynamicGamedata.playingPlayerID) {
      dynamicGamedata.playingPlayerID = 0
      dynamicGamedata.playingPlayer = Some(dynamicGamedata.players(dynamicGamedata.playingPlayerID))
      dynamicGamedata.playingPlayer

    }
    else {
      dynamicGamedata.playingPlayer = Some(dynamicGamedata.players(dynamicGamedata.playingPlayerID))
      dynamicGamedata.playingPlayerID += 1
      dynamicGamedata.playingPlayer
    }
  }

  override def isPlantable(player: Option[player], bean: card): Boolean = {
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

  override def chooseOrEmpty(playerID: Option[player], card: card): Int = {
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

  override def plant1or2(playingPlayer: Option[player]): Int = {
    playerInput.keyListener()
    var Nr = dynamicGamedata.plant1or2
    if(Nr == -1)
    if(Nr == 0) {
      println(gamedata.keineKorrekteNR)
      viewBase.playerInput.keyListener()
      Nr = dynamicGamedata.plant1or2
    }
    if(Nr == 1) {
      dynamicGamedata.cardsToPlant += playingPlayer.get.playerHand(0)
      gamelogic.plant(dynamicGamedata.cardsToPlant(0),dynamicGamedata.playingPlayer)
      gameDataFunc.takeNewCard(playingPlayer)
      Nr = 1
    }
    if(Nr == 2){
      dynamicGamedata.cardsToPlant += playingPlayer.get.playerHand(0)
      dynamicGamedata.cardsToPlant += playingPlayer.get.playerHand(1)
      gamelogic.plant(dynamicGamedata.cardsToPlant(0),dynamicGamedata.playingPlayer)
      gamelogic.plant(dynamicGamedata.cardsToPlant(1),dynamicGamedata.playingPlayer)
      gameDataFunc.takeNewCard(playingPlayer)
      gameDataFunc.takeNewCard(playingPlayer)

      Nr = 2}
    println(plantSelectString(playingPlayer))
    playingPlayer.get.lastMethodUsed = "plant1or2"
    Nr
  }

  override def plantAllSelectedCards(plantCount : Integer): Unit = {
    var i = 0
    while (i < plantCount) {
      plantPreperation(dynamicGamedata.playingPlayer)
      i += 1
    }
  }


  def plant1or2ThreadInterrupt(): Unit = {
    dynamicGamedata.readerThreadPlant1or2.interrupt()
    fieldBuilder.buildGrowingFieldStr(dynamicGamedata.playingPlayer)

  }

  override def returnGoldValue(plantfield : ArrayBuffer[card]): Int = {
    if(!plantfield.isEmpty){
      checkPlantAmount(plantfield(0),plantfield)
    }
    0
  }

  override def checkPlantAmount(card: card, plantfield : ArrayBuffer[card]): Int = {
    var cardSteps = 1
    while(plantfield.size >= card.price(cardSteps)){
      cardSteps += 1
    }
    card.price(cardSteps)
  }

  override def weightedRandom(): card = {
    val allcards = ArrayBuffer[card]()
    for (i <- 1 to gamedata.cards.size) {
      for (h <- 1 to gamedata.cards(i - 1).weightCount)
        allcards.addOne(gamedata.cards(i - 1))
    }
    val rand = Random.nextInt(allcards.size)
    allcards(rand).beanName match {
      case "Blaue" => gamedata.cards(0).weightCount -= 1
      case "Feuer" => gamedata.cards(1).weightCount -= 1
      case "Sau" => gamedata.cards(2).weightCount -= 1
      case "Brech" => gamedata.cards(3).weightCount -= 1
      case "Soja" => gamedata.cards(4).weightCount -= 1
      case "Augen" => gamedata.cards(5).weightCount -= 1
      case "Rote" => gamedata.cards(6).weightCount -= 1
      case "Garten" => gamedata.cards(7).weightCount -= 1
    }
    ObserverData.updateCards()
    allcards(rand)

  }

  override def plantSelectString(player: Option[player]): String = {
    var s: String = ""
    s += gamedata.selectPlantCard
    s += gameDataFunc.playerHandToString(player.get.playerHand)
    s
  }

  def deletePlayerBecauseBug(): Unit = {
    if(dynamicGamedata.players.size != dynamicGamedata.playerCount){
      for(i <- 1 to dynamicGamedata.players.size-dynamicGamedata.playerCount){
        dynamicGamedata.players.remove(dynamicGamedata.players.size-1)

      }
      println(dynamicGamedata.players.size)
    }
  }

  override def plant(cards: card, playerID: Option[player]): Unit = {
    val fieldNr = chooseOrEmpty(playerID, cards)
    fieldNr match {
      case 1 => playerID.get.plantfield1.addOne(cards)
      case 2 => playerID.get.plantfield2.addOne(cards)
      case 3 => playerID.get.plantfield3.addOne(cards)
      case -1 => println("Kein Feld frei zum anpflanzen")
      case _ => // No available fields
    }
    playerID.get.playerHand -= cards
  }

  override def harvest(field: Int): Unit = {
    field match {
      case 1 => dynamicGamedata.playingPlayer.get.gold += returnGoldValue(dynamicGamedata.playingPlayer.get.plantfield1)
        dynamicGamedata.playingPlayer.get.plantfield1.clear()
      case 2 => dynamicGamedata.playingPlayer.get.gold += returnGoldValue(dynamicGamedata.playingPlayer.get.plantfield2)
        dynamicGamedata.playingPlayer.get.plantfield2.clear()
      case 3 => dynamicGamedata.playingPlayer.get.gold += returnGoldValue(dynamicGamedata.playingPlayer.get.plantfield3)
        dynamicGamedata.playingPlayer.get.plantfield3.clear()
    }
  }

  def trade(playingPlayer: player, tradePartner: player, card1: card, card2: card): Unit = {
    val playerCard = playingPlayer.playerHand(findCardId(playingPlayer, card1))
    val traderCard = tradePartner.playerHand(findCardId(tradePartner, card2))
    playingPlayer.playerHand(findCardId(playingPlayer, card1)) = traderCard
    tradePartner.playerHand(findCardId(tradePartner, card2)) = playerCard
  }
}