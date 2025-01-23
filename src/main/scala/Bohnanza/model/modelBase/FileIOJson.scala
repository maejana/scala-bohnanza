package Bohnanza.model.modelBase

import Bohnanza.controller.State
import java.io.{File, PrintWriter}
import scala.collection.mutable.ArrayBuffer
import play.api.libs.json._
import Bohnanza.model.FileIOTrait
import scala.xml.Elem

class FileIOJson extends FileIOTrait {

  override def loadDynamicGamedata(file: String): Unit = {
    val jsonData = scala.io.Source.fromFile(file).mkString
    val parsedData = Json.parse(jsonData)

    val dynamicData = (parsedData \ "dynamicGamedata").as[JsObject]

    dynamicGamedata.drawnCards = StringToArrayBuffer((dynamicData \ "drawnCards").as[String])
    dynamicGamedata.playingPlayer = Some(findPlayerWithName((dynamicData \ "playingPlayer").as[String]))
    dynamicGamedata.plantCount = (dynamicData \ "plantCount").as[Int]
    dynamicGamedata.playerCount = (dynamicData \ "playerCount").as[Int]
    dynamicGamedata.cardsToPlant = StringToArrayBuffer((dynamicData \ "cardsToPlant").as[String])
    dynamicGamedata.playingPlayerID = (dynamicData \ "playingPlayerID").as[Int]
    dynamicGamedata.plant1or2 = (dynamicData \ "plant1or2").as[Int]
  }

  override def findPlayerWithName(name: String): player = {
    dynamicGamedata.players.find(_.playerName == name).getOrElse(dynamicGamedata.players.head)
  }

  override def loadPlayers(file: String): ArrayBuffer[player] = {
    val jsonData = scala.io.Source.fromFile(file).mkString
    val parsedData = Json.parse(jsonData)

    val playersArray = (parsedData \ "players").as[Seq[JsObject]]
    val players = ArrayBuffer[player]()

    playersArray.foreach { playerData =>
      val playerName = (playerData \ "playerName").as[String]
      val playerHand = StringToArrayBuffer((playerData \ "playerHand").as[String])
      val plantField1 = StringToArrayBuffer((playerData \ "plantField1").as[String])
      val plantField2 = StringToArrayBuffer((playerData \ "plantField2").as[String])
      val plantField3 = StringToArrayBuffer((playerData \ "plantField3").as[String])
      val gold = (playerData \ "gold").as[Int]
      val state = Bohnanza.controller.controllerBase.playerState().StringToState((playerData \ "state").as[String])
      val lastMethodUsed = (playerData \ "lastMethodUsed").as[String]

      val loadedPlayer = newPlayer(playerName, playerHand, plantField1, plantField2, plantField3, gold, state, lastMethodUsed)
      players += loadedPlayer
    }

    players
  }

  override def load(): Unit = {
    dynamicGamedata.players = loadPlayers("Gamesave.json")
    loadDynamicGamedata("Gamesave.json")
  }

  override def save(players: ArrayBuffer[player]): Unit = {
    val dynamicDataJSON = Json.toJson(dynamicGamedateToFileJ())
    val playersJSON = players.map(playerToFile) // playersJSON bleibt unverändert

    implicit val playerWrites: Writes[player] = Writes { player =>
      Json.obj(
        "playerName" -> player.playerName,
        "playerHand" -> ArrayBufferToString(player.playerHand),
        "plantField1" -> ArrayBufferToString(player.plantfield1),
        "plantField2" -> ArrayBufferToString(player.plantfield2),
        "plantField3" -> ArrayBufferToString(player.plantfield3),
        "gold" -> player.gold,
        "state" -> player.state.stateToString(),
        "lastMethodUsed" -> player.lastMethodUsed
      )
    }

    val fullData = Json.obj(
      "dynamicGamedata" -> dynamicDataJSON,
      "players" -> Json.toJson(players) // Players wird mit der neuen `Writes` serialisiert
    )

    val pw = new PrintWriter(new File("Gamesave.json"))
    pw.write(Json.prettyPrint(fullData))
    pw.close()

    
  }

  override def playerToFileJ(player: player): JsObject = {
    Json.obj(
      "playerName" -> player.playerName,
      "playerHand" -> ArrayBufferToString(player.playerHand),
      "plantField1" -> ArrayBufferToString(player.plantfield1),
      "plantField2" -> ArrayBufferToString(player.plantfield2),
      "plantField3" -> ArrayBufferToString(player.plantfield3),
      "gold" -> player.gold,
      "state" -> player.state.stateToString(),
      "lastMethodUsed" -> player.lastMethodUsed
    )
  }
  override def playerToFile(player:player): Elem = {
  val elem: Elem = <player> e</player>
  elem 
  }

  
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

  override def ArrayBufferToString(cards: ArrayBuffer[card]): String = {
    cards.map(_.beanName).mkString(", ")
  }

  override def StringToArrayBuffer(str: String): ArrayBuffer[card] = {
    val arrayBuffer = ArrayBuffer[card]()
    str.split(", ").foreach { cardName =>
      arrayBuffer += stringToCard(cardName)
    }
    arrayBuffer
  }

  override def stringToCard(str: String): card = {
    gamedata.cards.find(_.beanName == str).getOrElse(gamedata.cards.head)
  }

  override def newPlayer(playerName: String, playerHand: ArrayBuffer[card], plantField1: ArrayBuffer[card], plantField2: ArrayBuffer[card], plantField3: ArrayBuffer[card], gold: Int, state: State, lastMethodUsed: String): player = {
    val player = new player(playerName, playerHand)
    player.plantfield1 = plantField1
    player.plantfield2 = plantField2
    player.plantfield3 = plantField3
    player.gold = gold
    player.state = state
    player.lastMethodUsed = lastMethodUsed
    player
  }
  override def dynamicGamedateToFile(): Elem ={
    val elem: Elem = <dynamicGamedata> e</dynamicGamedata>
    elem 
  }

  override def toStringArray(Player: player): ArrayBuffer[String] = {
    val buffer = new ArrayBuffer[String]()
    buffer
  }

}
