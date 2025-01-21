package Bohnanza.model.modelBase

import Bohnanza.controller.State

import java.io.{File, PrintWriter}
import scala.collection.mutable.ArrayBuffer
import scala.xml.{Elem, NodeSeq, PrettyPrinter}
import Bohnanza.model.modelBase.player
import Bohnanza.controller.controllerBase.playerState
import Bohnanza.model.FileIOTrait
import play.api.libs.json.{JsObject, Json}

import scala.io.Source

class FileIO extends FileIOTrait {
  def loadFileToString(file: String): String = {
    val source = Source.fromFile(file)
    source.mkString
  }
  override def loadDynamicGamedata(file: String): Unit = {
    val xmlData = scala.xml.XML.loadString(file) // Lädt die XML-Datei

    // Zugriff auf den <dynamicGamedata>-Knoten
    val dynamicNode = xmlData \ "dynamicGamedata"

    // Daten auslesen
    val drawnCards = StringToArrayBuffer((dynamicNode \ "drawnCards").text)
    val playingPlayer = findPlayerWithName((dynamicNode \ "playingPlayer").text)
    val plantCount = (dynamicNode \ "plantCount").text.toInt
    val playerCount = (dynamicNode \ "playerCount").text.toInt
    val cardsToPlant = StringToArrayBuffer((dynamicNode \ "cardsToPlant").text)
    val playingPlayerID = (dynamicNode \ "playingPlayerID").text.toInt
    val plant1or2 = (dynamicNode \ "plant1or2").text.toInt

    dynamicGamedata.drawnCards = drawnCards
    dynamicGamedata.playingPlayer = Some(playingPlayer)
    dynamicGamedata.plantCount = plantCount
    dynamicGamedata.playerCount = playerCount
    dynamicGamedata.cardsToPlant = cardsToPlant
    dynamicGamedata.playingPlayerID = playingPlayerID
    dynamicGamedata.plant1or2 = plant1or2
  }

  override def findPlayerWithName(str : String): player = {
    println(str)
    for(player :player <- dynamicGamedata.players){
      if(player.playerName.equals(str)){
        return player
      }
    }
    dynamicGamedata.players(0)
  }


  override def loadPlayers(file: String): ArrayBuffer[player] = {
    // Lädt die XML-Datei
    val xmlData = scala.xml.XML.loadString(file)

    println(xmlData.text)


    // ArrayBuffer für Spieler
    val players = ArrayBuffer[player]()

    // Iteriere über alle <player>-Knoten mit einer normalen for-Schleife
    for (playerNode <- ((xmlData \ "players") \ "player")) {
      val playerName = (playerNode \ "playerName").text
      val playerHand = (playerNode \ "playerHand").text // Ensure correct case in XML
      val plantField1 = (playerNode \ "plantField1").text
      val plantField2 = (playerNode \ "plantField2").text
      val plantField3 = (playerNode \ "plantField3").text
      val gold = (playerNode \ "gold").text
      val state = (playerNode \ "state").text
      val lastMethodUsed = (playerNode \ "lastMethodUsed").text

      println(s"Loaded player: $playerName")

      // Spieler-Objekt erstellen und hinzufügen
      val loadedPlayer = newPlayer(
        playerName,
        StringToArrayBuffer(playerHand),
        StringToArrayBuffer(plantField1),
        StringToArrayBuffer(plantField2),
        StringToArrayBuffer(plantField3),
        gold.toInt,
        playerState().StringToState(state),
        lastMethodUsed
      )

      // Spieler zur Liste hinzufügen
      players += loadedPlayer
    }

    // Rückgabe der Spieler
    players
  }


  override def load(): Unit = {
    dynamicGamedata.players = loadPlayers(loadFileToString("Gamesave.xml"))
    loadDynamicGamedata(loadFileToString("Gamesave.xml"))
  }

  def save(Players: ArrayBuffer[player]): Unit = {
    val pw = new PrintWriter(new File("GameSave.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)

    // Wrap everything in the <open> and <close> tags
    val openTag = <open>
      {dynamicGamedateToFile()}<players>
        {Players.map(player => playerToFile(player))}
      </players>
    </open>

    val xml = prettyPrinter.format(openTag) // Format the XML

    pw.write(xml) // Write the XML to the file
    pw.close() // Close the file
  }

  override def playerToFile(player: player): Elem = {
    val playerArray = toStringArray(player)

    // Sicherheit: Überprüfung auf ausreichende Länge und Verwendung von Fallback-Werten
    def safeValue(index: Int, fallback: String = "Unknown"): String =
      if (index < playerArray.length) scala.xml.Utility.escape(playerArray(index)) else fallback

    <player>
      <playerName>{safeValue(0)}</playerName>
      <playerHand>{safeValue(1)}</playerHand>
      <plantField1>{safeValue(2)}</plantField1>
      <plantField2>{safeValue(3)}</plantField2>
      <plantField3>{safeValue(4)}</plantField3>
      <gold>{safeValue(5)}</gold>
      <state>{safeValue(6)}</state>
      <lastMethodUsed>{safeValue(7)}</lastMethodUsed>
    </player>
  }


  override def dynamicGamedateToFile(): Elem = {
    val dynamicGamedataXML: Elem =
      <dynamicGamedata>
        <drawnCards>{scala.xml.Utility.escape(ArrayBufferToString(dynamicGamedata.drawnCards))}</drawnCards>
        <playingPlayer>{scala.xml.Utility.escape(dynamicGamedata.playingPlayer.map(_.playerName).getOrElse("Unknown"))}</playingPlayer>
        <plantCount>{dynamicGamedata.plantCount.toString}</plantCount>
        <playerCount>{dynamicGamedata.playerCount.toString}</playerCount>
        <cardsToPlant>{scala.xml.Utility.escape(ArrayBufferToString(dynamicGamedata.cardsToPlant))}</cardsToPlant>
        <playingPlayerID>{dynamicGamedata.playingPlayerID.toString}</playingPlayerID>
        <plant1or2>{dynamicGamedata.plant1or2.toString}</plant1or2>
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

  override def dynamicGamedateToFileJ(): JsObject = {
    Json.obj(
      "drawnCards" -> ArrayBufferToString(dynamicGamedata.drawnCards),
      "playingPlayer" -> dynamicGamedata.playingPlayer.map(_.playerName).getOrElse("Unknown"),
      "plantCount" -> dynamicGamedata.plantCount,
      "playerCount" -> dynamicGamedata.playerCount,
      "cardsToPlant" -> ArrayBufferToString(dynamicGamedata.cardsToPlant),
      "playingPlayerID" -> dynamicGamedata.playingPlayerID,
      "plant1or2" -> dynamicGamedata.plant1or2
    )
  }

  override def playerToFileJ(player:player): JsObject= {
    Json.obj(
      "drawnCards" -> ArrayBufferToString(dynamicGamedata.drawnCards),
      "playingPlayer" -> dynamicGamedata.playingPlayer.map(_.playerName).getOrElse("Unknown"),
      "plantCount" -> dynamicGamedata.plantCount,
      "playerCount" -> dynamicGamedata.playerCount,
      "cardsToPlant" -> ArrayBufferToString(dynamicGamedata.cardsToPlant),
      "playingPlayerID" -> dynamicGamedata.playingPlayerID,
      "plant1or2" -> dynamicGamedata.plant1or2
    )
  }

}
