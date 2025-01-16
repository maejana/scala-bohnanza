package Bohnanza.model.modelBase

import Bohnanza.controller.State

import java.io.{File, PrintWriter}
import scala.collection.mutable.ArrayBuffer
import scala.xml.{Elem, NodeSeq, PrettyPrinter}
import Bohnanza.model.modelBase.player
import Bohnanza.controller.controllerBase.playerState

class FileIO {

  def loadDynamicGamedataFromXML(file: String): Unit = {
    val xmlData = scala.xml.XML.loadFile(file) // Lädt die XML-Datei

    // Zugriff auf den <dynamicGamedata>-Knoten
    val dynamicNode = xmlData \ "dynamicGamedata"

    // Daten auslesen
    val drawnCards = StringToArrayBuffer((dynamicNode \ "@drawnCards").text)
    val playingPlayer = findPlayerWithName((dynamicNode \ "@playingPlayer").text)
    val plantCount = (dynamicNode \ "@plantCount").text.toInt
    val playerCount = (dynamicNode \ "@playerCount").text.toInt
    val cardsToPlant = StringToArrayBuffer((dynamicNode \ "@cardsToPlant").text)
    val playingPlayerID = (dynamicNode \ "@playingPlayerID").text.toInt
    val plant1or2 = (dynamicNode \ "@plant1or2").text.toInt


    dynamicGamedata.drawnCards = drawnCards
    dynamicGamedata.playingPlayer = Some(playingPlayer)
    dynamicGamedata.plantCount = plantCount
    dynamicGamedata.playerCount = playerCount
    dynamicGamedata.cardsToPlant = cardsToPlant
    dynamicGamedata.playingPlayerID = playingPlayerID
    dynamicGamedata.plant1or2 = plant1or2
  }

  def findPlayerWithName(str : String): player = {
    for(player :player <- dynamicGamedata.players){
      if(player.playerName.equals(str)){
        return player
      }
    }
    dynamicGamedata.players(0)
  }


  def loadPlayersFromXML(file: String): ArrayBuffer[player] = {
    val xmlData = scala.xml.XML.loadFile(file) // Lädt die XML-Datei
    val players = ArrayBuffer[player]() // ArrayBuffer für Spieler

    // Iteriere über alle <player>-Knoten
    (xmlData \\ "player").foreach { playerNode =>
      val playerName = (playerNode \ "@playerName").text
      val playerHand = (playerNode \ "@playerhand").text
      val plantField1 = (playerNode \ "@plantfield1").text
      val plantField2 = (playerNode \ "@plantfield2").text
      val plantField3 = (playerNode \ "@plantfield3").text
      val gold = (playerNode \ "@gold").text.toInt
      val state = (playerNode \ "@state").text
      val lastMethodUsed = (playerNode \ "@lastMethodUsed").text

      // Spieler-Objekt erstellen und hinzufügen
      val loadedPlayer = newPlayer(playerName, StringToArrayBuffer(playerHand), StringToArrayBuffer(plantField1),StringToArrayBuffer(plantField2), StringToArrayBuffer(plantField3),gold, playerState().StringToState(state), lastMethodUsed)
      players += loadedPlayer
    }

    players // Rückgabe der Spieler
  }

  def load(): Unit = {
    dynamicGamedata.players = loadPlayersFromXML("Gamesave.xml")
    loadDynamicGamedataFromXML("Gamesave.xml")
  }

  def save(Players : ArrayBuffer[player]): Unit = {
    val pw = new PrintWriter(new File("GameSave.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(dynamicGamedateToXML())
    pw.write(xml)
    for (Player: player <- Players) {
      val xml = prettyPrinter.format(playerToXML(Player))
      pw.write(xml)
    }
    pw.close
  }

  def playerToXML(Player: player) = {
    val playerarray = toStringArray(Player)
    <player>
      playerName={playerarray(0)}
      playerhand={playerarray(1)}
      plantfield1={playerarray(2)}
      plantfield2={playerarray(3)}
      plantfield3={playerarray(4)}
      gold={playerarray(5)}
      state={playerarray(6)}
      lastMethodUsed={playerarray(7)}
    </player>
  }
  def dynamicGamedateToXML() = {
    <dynamicGamedata>
      drawnCards={ArrayBufferToString(dynamicGamedata.drawnCards)}
      playingPlayer={dynamicGamedata.playingPlayer.get.playerName}
      plantCount={dynamicGamedata.plantCount.toString}
      playerCount={dynamicGamedata.playerCount.toString}
      cardsToPlant={ArrayBufferToString(dynamicGamedata.cardsToPlant)}
      playingPlayerID={dynamicGamedata.playingPlayerID.toString}
      plant1or2={dynamicGamedata.plant1or2.toString}
    </dynamicGamedata>
  }



  def ArrayBufferToString(plantfield: ArrayBuffer[card]): String = {
    val str = new StringBuilder()
    for (card <- plantfield) {
      str.append(card.beanName)
      str.append(", ")
    }
    str.result()
  }

  def toStringArray(Player: player): Array[String] = {
    val playerString = Array[String]()
    playerString(0) = Player.playerName
    playerString(1) = ArrayBufferToString(Player.playerHand)
    playerString(2) = ArrayBufferToString(Player.plantfield1)
    playerString(3) = ArrayBufferToString(Player.plantfield2)
    playerString(4) = ArrayBufferToString(Player.plantfield3)
    playerString(5) = Player.gold.toString
    playerString(6) = Player.state.stateToString()
    playerString(7) = Player.lastMethodUsed
    playerString
  }
  //Todo: Methoden schreiben um jede einzelne Variable aus einem String zu rekonstruieren

  def StringToArrayBuffer(str: String): ArrayBuffer[card] = {
    val result = str.split(",")
    val array: Array[String] = str.split(",")
    val arrayBuffer = new ArrayBuffer[card]()
    for(stri: String <- array){
      arrayBuffer.addOne(stringToCard(stri))
    }
    arrayBuffer
  }

  def stringToCard(str: String): card = {
    for (card: card <- gamedata.cards) {
      if (card.beanName.equals(str)) {
        return card
      }
    }
    gamedata.cards(0)
  }

  def newPlayer(PlayerName: String, Playerhand: ArrayBuffer[card], Plantfield1: ArrayBuffer[card], Plantfield2: ArrayBuffer[card], Plantfield3: ArrayBuffer[card], Gold: Int, State: State, LastMethodUsed: String): player = {
    val player = new player(PlayerName, Playerhand)
    player.plantfield1 = Plantfield1
    player.plantfield2 = Plantfield2
    player.plantfield3 = Plantfield3
    player.gold = Gold
    player.state = State
    player.lastMethodUsed = LastMethodUsed
    player
  }
  //Todo: alle Daten aus Player oder aus dynamicGamedata müssen aus einem String wieder zur den ursprünglichen Datentypen Konvertiert und eingelesen werden.(Bsp: playersArray -> player -> playername(ist ein String) && playerhand -> alle fünfKartenNamen als String abspeichern und später daraus die fünf karten in eine playerhand zurück konvertieren)
  //Done: alle Daten aus Player oder aus dynamicGamedata müssen als String in XML abgespeichert werden
}
