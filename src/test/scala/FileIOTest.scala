import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.mutable.ArrayBuffer
import java.io.{File, PrintWriter}
import scala.io.Source
import scala.xml.PrettyPrinter
import play.api.libs.json.{JsObject, Json}
import Bohnanza.model.modelBase.{FileIO, card, dynamicGamedata, gamedata, player}
import Bohnanza.model
import Bohnanza.controller.controllerBase.playerState

class FileIOTest extends AnyFlatSpec with Matchers {
  val fileIO = new FileIO()

  "loadFileToString" should "load the content of a file as a string" in {
    val testFile = "testFile.txt"
    val testContent = "This is a test file."
    val pw = new PrintWriter(new File(testFile))
    pw.write(testContent)
    pw.close()

    val result = fileIO.loadFileToString(testFile)
    result shouldEqual testContent

    new File(testFile).delete()
  }

  "loadPlayers" should "load players from a string" in {
    val xmlContent =
      """<players>
        |  <player>
        |    <playerName>TestPlayer</playerName>
        |    <playerHand>Bean1, Bean2, </playerHand>
        |    <plantField1>Bean3, </plantField1>
        |    <plantField2>Bean4, </plantField2>
        |    <plantField3>Bean5, </plantField3>
        |    <gold>10</gold>
        |    <state>Active</state>
        |    <lastMethodUsed>plant</lastMethodUsed>
        |  </player>
        |</players>""".stripMargin

    val players = fileIO.loadPlayers(xmlContent)
    players should not be empty
    players.head.playerName shouldEqual "TestPlayer"
    players.head.playerHand.map(_.beanName) shouldEqual ArrayBuffer("Bean1", "Bean2")
    players.head.plantfield1.map(_.beanName) shouldEqual ArrayBuffer("Bean3")
    players.head.plantfield2.map(_.beanName) shouldEqual ArrayBuffer("Bean4")
    players.head.plantfield3.map(_.beanName) shouldEqual ArrayBuffer("Bean5")
    players.head.gold shouldEqual 10
    players.head.state.stateToString() shouldEqual "Active"
    players.head.lastMethodUsed shouldEqual "plant"
  }

  "findPlayerWithName" should "find a player by name" in {
    val player = new player("TestPlayer", ArrayBuffer())
    dynamicGamedata.players += player

    val result = fileIO.findPlayerWithName("TestPlayer")
    result shouldEqual player

    dynamicGamedata.players.clear()
  }


  "save" should "save players to an XML file" in {
    val player = new player("TestPlayer", ArrayBuffer())
    dynamicGamedata.players += player

    fileIO.save(dynamicGamedata.players)

    val savedContent = Source.fromFile("GameSave.xml").mkString
    savedContent should include("<playerName>TestPlayer</playerName>")

    new File("GameSave.xml").delete()
    dynamicGamedata.players.clear()
  }

  "playerToFileJ" should "do nothing" in {
    val player = new player("TestPlayer", ArrayBuffer())
    val result = fileIO.playerToFileJ(player)

    result shouldEqual Json.obj()
  }

  "dynamicGamedateToFileJ" should "do nothing" in {
    val result = fileIO.dynamicGamedateToFileJ()

    result shouldEqual Json.obj()
  }

  "ArrayBufferToString" should "convert an ArrayBuffer of cards to a string" in {
    val cards = ArrayBuffer(new card("Bean1",1, Array(1)), new card("Bean2", 1, Array(1)))
    val result = fileIO.ArrayBufferToString(cards)

    result shouldEqual "Bean1, Bean2, "
  }

  "toStringArray" should "convert a player to an ArrayBuffer of strings" in {
    val player = new player("TestPlayer", ArrayBuffer(new card("Bean1", 1, Array(1)), new card("Bean2", 1, Array(1))))
    val result = fileIO.toStringArray(player)

    result shouldEqual ArrayBuffer("TestPlayer", "Bean1, Bean2, ", "", "", "", "0", "Plays", " ")
  }

  "StringToArrayBuffer" should "convert a string to an ArrayBuffer of cards" in {
    val str = "Bean1, Bean2, "
    val result = fileIO.StringToArrayBuffer(str)

    result.map(_.beanName) shouldEqual ArrayBuffer("Blaue", "Blaue", "Blaue")
  }

  "stringToCard" should "convert a string to a card" in {
    val card = new card("Bean1", 1, Array(1))

    val result = fileIO.stringToCard("Bean1")
    result shouldEqual gamedata.cards(0)
  }

  "newPlayer" should "create a new player" in {
    val player = fileIO.newPlayer("TestPlayer", ArrayBuffer(), ArrayBuffer(), ArrayBuffer(), ArrayBuffer(), 10, playerState().state, "plant")

    player.playerName shouldEqual "TestPlayer"
    player.gold shouldEqual 10
    player.state.stateToString() shouldEqual "DontPlays"
    player.lastMethodUsed shouldEqual "plant"
  }
}