package Bohnanza.model.modelBase

import Bohnanza.controller.State

import java.io.{File, PrintWriter}
import scala.collection.mutable.ArrayBuffer
import scala.xml.{Elem, NodeSeq, PrettyPrinter}
import Bohnanza.model.modelBase.player
import Bohnanza.controller.controllerBase.playerState
import Bohnanza.model.FileIOTrait

class FileIO extends FileIOTrait {

  override def loadDynamicGamedataFromXML(file: String): Unit = {
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

  override def findPlayerWithName(str : String): player = {
    for(player :player <- dynamicGamedata.players){
      if(player.playerName.equals(str)){
        return player
      }
    }
    dynamicGamedata.players(0)
  }


  override def loadPlayersFromXML(file: String): ArrayBuffer[player] = {
    val xmlData = scala.xml.XML.loadFile(file) // Lädt die XML-Datei
    val players = ArrayBuffer[player]() // ArrayBuffer für Spieler

    // Iteriere über alle <player>-Knoten
    (xmlData \\ "player").foreach { playerNode =>
      val playerName = (playerNode \ "@playerName").text
      val playerHand = (playerNode \ "@playerhand").text
      val plantField1 = (playerNode \ "@plantfield1").text
      val plantField2 = (playerNode \ "@plantfield2").text
      val plantField3 = (playerNode \ "@plantfield3").text
      val gold = (playerNode \ "@gold").text
      val state = (playerNode \ "@state").text
      val lastMethodUsed = (playerNode \ "@lastMethodUsed").text

      // Spieler-Objekt erstellen und hinzufügen
      val loadedPlayer = newPlayer(playerName, StringToArrayBuffer(playerHand), StringToArrayBuffer(plantField1),StringToArrayBuffer(plantField2), StringToArrayBuffer(plantField3), gold.toInt, playerState().StringToState(state), lastMethodUsed)
      players += loadedPlayer
    }

    players // Rückgabe der Spieler
  }

  override def load(): Unit = {
    dynamicGamedata.players = loadPlayersFromXML("Gamesave.xml")
    loadDynamicGamedataFromXML("Gamesave.xml")
  }

  override def save(Players : ArrayBuffer[player]): Unit = {
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

  override def playerToXML(player: player): Elem = {
    val playerArray = toStringArray(player)

    // Sicherheit: Überprüfung auf ausreichende Länge und Verwendung von Fallback-Werten
    def safeValue(index: Int, fallback: String = "Unknown"): String =
      if (index < playerArray.length) scala.xml.Utility.escape(playerArray(index)) else fallback

    <player>
      <playerName>
        {safeValue(0)}
      </playerName>
      <playerHand>
        {safeValue(1)}
      </playerHand>
      <plantField1>
        {safeValue(2)}
      </plantField1>
      <plantField2>
        {safeValue(3)}
      </plantField2>
      <plantField3>
        {safeValue(4)}
      </plantField3>
      <gold>
        {safeValue(5)}
      </gold>
      <state>
        {safeValue(6)}
      </state>
      <lastMethodUsed>
        {safeValue(7)}
      </lastMethodUsed>
    </player>
  }


  override def dynamicGamedateToXML(): Elem = {
    val dynamicGamedataXML: Elem =
      <dynamicGamedata>
        <drawnCards>
          {scala.xml.Utility.escape(ArrayBufferToString(dynamicGamedata.drawnCards))}
        </drawnCards>
        <playingPlayer>
          {scala.xml.Utility.escape(dynamicGamedata.playingPlayer.map(_.playerName).getOrElse("Unknown"))}
        </playingPlayer>
        <plantCount>
          {dynamicGamedata.plantCount.toString}
        </plantCount>
        <playerCount>
          {dynamicGamedata.playerCount.toString}
        </playerCount>
        <cardsToPlant>
          {scala.xml.Utility.escape(ArrayBufferToString(dynamicGamedata.cardsToPlant))}
        </cardsToPlant>
        <playingPlayerID>
          {dynamicGamedata.playingPlayerID.toString}
        </playingPlayerID>
        <plant1or2>
          {dynamicGamedata.plant1or2.toString}
        </plant1or2>
      </dynamicGamedata>

    // Pretty print the XML for logging/debugging
    val prettyPrinter = new PrettyPrinter(120, 4)
    val formattedXML = prettyPrinter.format(dynamicGamedataXML)
    println(formattedXML)

    dynamicGamedataXML
  }


  override def ArrayBufferToString(plantfield: ArrayBuffer[card]): String = {
    val str = new StringBuilder()
    for (card <- plantfield) {
      str.append(card.beanName)
      str.append(", ")
    }
    str.result()
  }

  override def toStringArray(Player: player): ArrayBuffer[String] = {
    val playerString = ArrayBuffer[String]()
    playerString.addOne(Player.playerName)
    playerString.addOne(ArrayBufferToString(Player.playerHand))
    playerString.addOne(ArrayBufferToString(Player.plantfield1))
    playerString.addOne(ArrayBufferToString(Player.plantfield2))
    playerString.addOne(ArrayBufferToString(Player.plantfield3))
    playerString.addOne(Player.gold.toString)
    playerString.addOne(Player.state.stateToString())
    playerString.addOne(Player.lastMethodUsed)
    playerString
  }
  //Todo: Methoden schreiben um jede einzelne Variable aus einem String zu rekonstruieren

  override def StringToArrayBuffer(str: String): ArrayBuffer[card] = {
    val result = str.split(",")
    val array: Array[String] = str.split(",")
    val arrayBuffer = new ArrayBuffer[card]()
    for(stri: String <- array){
      arrayBuffer.addOne(stringToCard(stri))
    }
    arrayBuffer
  }

  override def stringToCard(str: String): card = {
    for (card: card <- gamedata.cards) {
      if (card.beanName.equals(str)) {
        return card
      }
    }
    gamedata.cards(0)
  }

  override def newPlayer(PlayerName: String, Playerhand: ArrayBuffer[card], Plantfield1: ArrayBuffer[card], Plantfield2: ArrayBuffer[card], Plantfield3: ArrayBuffer[card], Gold: Int, State: State, LastMethodUsed: String): player = {
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
