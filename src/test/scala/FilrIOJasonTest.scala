package test
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.mockito.Mockito._
import org.mockito.ArgumentMatchers._
import play.api.libs.json._
import scala.collection.mutable.ArrayBuffer
import java.io.{File, PrintWriter}
import scala.io.Source
import Bohnanza.model.modelBase.{FileIOJson, card, dynamicGamedata, player}


class FileIOJsonTest extends AnyFlatSpec with Matchers {

  "FileIOJson" should "correctly load dynamic game data from a JSON file" in {
    val testJson =
      """
        |{
        |  "dynamicGamedata": {
        |    "drawnCards": "Card1, Card2",
        |    "playingPlayer": "Player1",
        |    "plantCount": 2,
        |    "playerCount": 4,
        |    "cardsToPlant": "Card3, Card4",
        |    "playingPlayerID": 1,
        |    "plant1or2": 2
        |  }
        |}
      """.stripMargin

    val file = File.createTempFile("testDynamicGamedata", ".json")
    val writer = new PrintWriter(file)
    writer.write(testJson)
    writer.close()

    val fileIO = new FileIOJson
    fileIO.loadDynamicGamedata(file.getAbsolutePath)

    dynamicGamedata.drawnCards.map(_.beanName) shouldEqual ArrayBuffer("Card1", "Card2")
    dynamicGamedata.playingPlayer.get.playerName shouldEqual "Player1"
    dynamicGamedata.plantCount shouldEqual 2
    dynamicGamedata.playerCount shouldEqual 4
    dynamicGamedata.cardsToPlant.map(_.beanName) shouldEqual ArrayBuffer("Card3", "Card4")
    dynamicGamedata.playingPlayerID shouldEqual 1
    dynamicGamedata.plant1or2 shouldEqual 2

    file.delete()
  }

  it should "correctly load players from a JSON file" in {
    val testJson =
      """
        |{
        |  "players": [
        |    {
        |      "playerName": "Player1",
        |      "playerHand": "Card1, Card2",
        |      "plantField1": "Card3",
        |      "plantField2": "Card4",
        |      "plantField3": "",
        |      "gold": 5,
        |      "state": "SomeState",
        |      "lastMethodUsed": "SomeMethod"
        |    }
        |  ]
        |}
      """.stripMargin

    val file = File.createTempFile("testPlayers", ".json")
    val writer = new PrintWriter(file)
    writer.write(testJson)
    writer.close()

    val fileIO = new FileIOJson
    val players = fileIO.loadPlayers(file.getAbsolutePath)

    players.length shouldEqual 1
    val player = players.head
    player.playerName shouldEqual "Player1"
    player.playerHand.map(_.beanName) shouldEqual ArrayBuffer("Card1", "Card2")
    player.plantfield1.map(_.beanName) shouldEqual ArrayBuffer("Card3")
    player.plantfield2.map(_.beanName) shouldEqual ArrayBuffer("Card4")
    player.plantfield3 shouldBe empty
    player.gold shouldEqual 5
    player.state.stateToString() shouldEqual "SomeState"
    player.lastMethodUsed shouldEqual "SomeMethod"

    file.delete()
  }

  it should "correctly save dynamic game data and players to a JSON file" in {
    val player1 = new player("Player1", ArrayBuffer(FileIOJson().stringToCard("Card1"), FileIOJson().stringToCard("Card2")))
    player1.plantfield1 = ArrayBuffer(FileIOJson().stringToCard("Card3"))
    player1.plantfield2 = ArrayBuffer(FileIOJson().stringToCard("Card4"))
    player1.gold = 5
    player1.state = Bohnanza.controller.controllerBase.playerState().StringToState("SomeState")
    player1.lastMethodUsed = "SomeMethod"

    dynamicGamedata.drawnCards = ArrayBuffer(FileIOJson().stringToCard("Card1"), FileIOJson().stringToCard("Card2"))
    dynamicGamedata.playingPlayer = Some(player1)
    dynamicGamedata.plantCount = 2
    dynamicGamedata.playerCount = 4
    dynamicGamedata.cardsToPlant = ArrayBuffer(FileIOJson().stringToCard("Card3"), FileIOJson().stringToCard("Card4"))
    dynamicGamedata.playingPlayerID = 1
    dynamicGamedata.plant1or2 = 2

    val fileIO = new FileIOJson()
    val file = File.createTempFile("testSave", ".json")

    fileIO.save(ArrayBuffer(player1))

    val jsonData = Source.fromFile("Gamesave.json").mkString
    val parsedData = Json.parse(jsonData)

    (parsedData \ "playerName").head.as[String] shouldEqual "Player1"
    (parsedData \ "plantCount").head.as[Int] shouldEqual 2
    (parsedData \ "cardsToPlant").head.as[String] shouldEqual "Card3, Card4"

    file.delete()
  }
}