import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import Bohnanza.model.modelBase._
import Bohnanza.controller.State
import play.api.libs.json.Json

import scala.collection.mutable.ArrayBuffer


class FileIOJsonTest extends AnyFlatSpec with Matchers {

  "FileIOJson" should "load players from a JSON file" in {
    val jsonContent =
      """{
        |  "players": [
        |    {
        |      "playerName": "TestPlayer",
        |      "playerHand": ["Bean1", "Bean2"],
        |      "plantField1": ["Bean3"],
        |      "plantField2": ["Bean4"],
        |      "plantField3": ["Bean5"],
        |      "gold": 10,
        |      "state": "Active",
        |      "lastMethodUsed": "plant"
        |    }
        |  ]
        |}""".stripMargin

    val fileIO = new FileIOJson
    val players = fileIO.loadPlayers(jsonContent)
    players.size shouldEqual 1
    players.head.playerName shouldEqual "TestPlayer"
    players.head.playerHand.map(_.beanName) shouldEqual ArrayBuffer("Bean1", "Bean2")
    players.head.plantfield1.map(_.beanName) shouldEqual ArrayBuffer("Bean3")
    players.head.plantfield2.map(_.beanName) shouldEqual ArrayBuffer("Bean4")
    players.head.plantfield3.map(_.beanName) shouldEqual ArrayBuffer("Bean5")
    players.head.gold shouldEqual 10
    players.head.state.stateToString() shouldEqual "Active"
    players.head.lastMethodUsed shouldEqual "plant"
  }

  it should "load dynamic game data from a JSON file" in {
    val jsonContent =
      """{
        |  "dynamicGamedata": {
        |    "drawnCards": ["Bean1", "Bean2"],
        |    "playingPlayer": {
        |      "playerName": "TestPlayer",
        |      "playerHand": ["Bean1", "Bean2"],
        |      "plantField1": ["Bean3"],
        |      "plantField2": ["Bean4"],
        |      "plantField3": ["Bean5"],
        |      "gold": 10,
        |      "state": "Active",
        |      "lastMethodUsed": "plant"
        |    },
        |    "plantCount": 2,
        |    "playerCount": 1,
        |    "cardsToPlant": ["Bean3", "Bean4"],
        |    "playingPlayerID": 0,
        |    "plant1or2": 1
        |  }
        |}""".stripMargin

    val fileIO = new FileIOJson
    fileIO.loadDynamicGamedata(jsonContent)

    dynamicGamedata.drawnCards.map(_.beanName) shouldEqual ArrayBuffer("Bean1", "Bean2")
    dynamicGamedata.playingPlayer.map(_.playerName).getOrElse("") shouldEqual "TestPlayer"
    dynamicGamedata.plantCount shouldEqual 2
    dynamicGamedata.playerCount shouldEqual 1
    dynamicGamedata.cardsToPlant.map(_.beanName) shouldEqual ArrayBuffer("Bean3", "Bean4")
    dynamicGamedata.playingPlayerID shouldEqual 0
    dynamicGamedata.plant1or2 shouldEqual 1
  }
}