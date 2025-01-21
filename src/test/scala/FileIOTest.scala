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

  "loadDynamicGamedata" should "load dynamic game data from a string" in {
    val xmlContent =
      """<dynamicGamedata>
        |  <drawnCards>Bean1, Bean2, </drawnCards>
        |  <playingPlayer>TestPlayer</playingPlayer>
        |  <plantCount>2</plantCount>
        |  <playerCount>3</playerCount>
        |  <cardsToPlant>Bean3, Bean4, </cardsToPlant>
        |  <playingPlayerID>1</playingPlayerID>
        |  <plant1or2>1</plant1or2>
        |</dynamicGamedata>""".stripMargin

    fileIO.loadDynamicGamedata(xmlContent)

    dynamicGamedata.drawnCards.map(_.beanName) shouldEqual ArrayBuffer("Bean1", "Bean2")
    dynamicGamedata.playingPlayer.get.playerName shouldEqual "TestPlayer"
    dynamicGamedata.plantCount shouldEqual 2
    dynamicGamedata.playerCount shouldEqual 3
    dynamicGamedata.cardsToPlant.map(_.beanName) shouldEqual ArrayBuffer("Bean3", "Bean4")
    dynamicGamedata.playingPlayerID shouldEqual 1
    dynamicGamedata.plant1or2 shouldEqual 1
  }

  "findPlayerWithName" should "find a player by name" in {
    val player = new player("TestPlayer", ArrayBuffer())
    dynamicGamedata.players += player

    val result = fileIO.findPlayerWithName("TestPlayer")
    result shouldEqual player

    dynamicGamedata.players.clear()
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

  "save" should "save players to an XML file" in {
    val player = new player("TestPlayer", ArrayBuffer())
    dynamicGamedata.players += player

    fileIO.save(dynamicGamedata.players)

    val savedContent = Source.fromFile("GameSave.xml").mkString
    savedContent should include("<playerName>TestPlayer</playerName>")

    new File("GameSave.xml").delete()
    dynamicGamedata.players.clear()
  }

  "playerToFile" should "convert a player to XML" in {
    val player = new player("TestPlayer", ArrayBuffer())
    val result = fileIO.playerToFile(player).toString()

    result should include("<playerName>TestPlayer</playerName>")
  }

  "dynamicGamedateToFile" should "convert dynamic game data to XML" in {
    dynamicGamedata.drawnCards = ArrayBuffer(new card("Bean1",1, Array(1)), new card("Bean2",1, Array(1)))
    dynamicGamedata.playingPlayer = Some(new player("TestPlayer", ArrayBuffer()))
    dynamicGamedata.plantCount = 2
    dynamicGamedata.playerCount = 3
    dynamicGamedata.cardsToPlant = ArrayBuffer(new card("Bean3",1, Array(1)), new card("Bean4",1, Array(1)))
    dynamicGamedata.playingPlayerID = 1
    dynamicGamedata.plant1or2 = 1

    val result = fileIO.dynamicGamedateToFile().toString()

    result should include("<drawnCards>Bean1, Bean2, </drawnCards>")
    result should include("<playingPlayer>TestPlayer</playingPlayer>")
    result should include("<plantCount>2</plantCount>")
    result should include("<playerCount>3</playerCount>")
    result should include("<cardsToPlant>Bean3, Bean4, </cardsToPlant>")
    result should include("<playingPlayerID>1</playingPlayerID>")
    result should include("<plant1or2>1</plant1or2>")
  }

  "ArrayBufferToString" should "convert an ArrayBuffer of cards to a string" in {
    val cards = ArrayBuffer(new card("Bean1",1, Array(1)), new card("Bean2", 1, Array(1)))
    val result = fileIO.ArrayBufferToString(cards)

    result shouldEqual "Bean1, Bean2, "
  }

  "toStringArray" should "convert a player to an ArrayBuffer of strings" in {
    val player = new player("TestPlayer", ArrayBuffer(new card("Bean1", 1, Array(1)), new card("Bean2", 1, Array(1))))
    val result = fileIO.toStringArray(player)

    result shouldEqual ArrayBuffer("TestPlayer", "Bean1, Bean2, ", "", "", "", "0", "Unknown", "")
  }

  "StringToArrayBuffer" should "convert a string to an ArrayBuffer of cards" in {
    val str = "Bean1, Bean2, "
    val result = fileIO.StringToArrayBuffer(str)

    result.map(_.beanName) shouldEqual ArrayBuffer("Bean1", "Bean2")
  }

  "stringToCard" should "convert a string to a card" in {
    val card: card = model.modelBase.card("bean", 1, Array(1))


    val result = fileIO.stringToCard("Bean1")
    result shouldEqual card

  }

  "newPlayer" should "create a new player" in {
    val player = fileIO.newPlayer("TestPlayer", ArrayBuffer(), ArrayBuffer(), ArrayBuffer(), ArrayBuffer(), 10, playerState().state, "plant")

    player.playerName shouldEqual "TestPlayer"
    player.gold shouldEqual 10
    player.state.stateToString() shouldEqual "Active"
    player.lastMethodUsed shouldEqual "plant"
  }
}