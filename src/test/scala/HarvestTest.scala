package Bohnanza.model

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.mockito.Mockito.*
import org.mockito.ArgumentMatchers.*

import scala.collection.mutable.ArrayBuffer
import Bohnanza.model.modelBase.{player, *}
import Bohnanza.controller.UtilityComponent
import Bohnanza.controller.controllerBase.Utility

class HarvestTest extends AnyFlatSpec with Matchers {

  trait MockUtilityComponent extends UtilityComponent {
    val utility: Utility = new Utility {
      override def harvest(field: Int): Unit = {
        field match {
          case 1 =>
            dynamicGamedata.playingPlayer.get.gold += returnGoldValue(dynamicGamedata.playingPlayer.get.plantfield1)
            dynamicGamedata.playingPlayer.get.plantfield1.clear()
          case 2 =>
            dynamicGamedata.playingPlayer.get.gold += returnGoldValue(dynamicGamedata.playingPlayer.get.plantfield2)
            dynamicGamedata.playingPlayer.get.plantfield2.clear()
          case 3 =>
            dynamicGamedata.playingPlayer.get.gold += returnGoldValue(dynamicGamedata.playingPlayer.get.plantfield3)
            dynamicGamedata.playingPlayer.get.plantfield3.clear()
        }
      }

      override def returnGoldValue(field: ArrayBuffer[card]): Int = field.length * 2

      // Stub implementations for all other methods
      override def checkPlantAmount(card: card, plantfield: ArrayBuffer[card]): Int = 0
      override def chooseOrEmpty(playerID: Option[player], card: card): Int = 0
      override def deletePlayerBecauseBug(): Unit = {}
      override def drawCards(): ArrayBuffer[card] = ArrayBuffer.empty[card]
      override def emptyPlantfieldNr(player: Option[player]): Int = 0
      override def findCardId(player: player, card: card): Int = 0
      override def findCardWithName(name: String): card = new card("Test", 0, Array(1, 2, 3))
      override def initGame: String = ""
      override def initPlayer(name: String): String = ""
      override def isPlantable(player: Option[player], bean: card): Boolean = false
      override def plant1or2(playingPlayer: Option[player]): Int = 0
      override def plant1or2ThreadInterrupt(): Unit = {}
      override def plantAllSelectedCards(plantCount: Int): Unit = {}
      override def plantDrawnCard(player: Option[player], card: card): Unit = {}
      override def plantInfo(): card = new card("Test", 0, Array(1, 2, 3))
      override def plantPreperation(player: Option[player]): String = ""
      override def plantSelectString(player: Option[player]): String = ""
      override def playerFieldToString(field: ArrayBuffer[card]): String = ""
      override def playerHandToString(hand: ArrayBuffer[card]): String = ""
      override def selectCardToPlant(cards: ArrayBuffer[card]): card = new card("Test", 0, Array(1, 2, 3))
      override def selectPlayer(): Option[player] = None
      override def takeNewCard(player: Option[player]): Unit = {}
      override def weightedRandom(): card = new card("Test", 0, Array(1, 2, 3))
    }
  }

  "harvest" should "correctly harvest from the specified field and update the player's gold" in new MockUtilityComponent {
    override def checkPlantAmount(card: card, plantfield: ArrayBuffer[card]): Int = 0
    override def returnGoldValue(field: ArrayBuffer[card]): Int = field.length * 2
    override def chooseOrEmpty(playerID: Option[player], card: card): Int = 0

    override def deletePlayerBecauseBug(): Unit = {}

    override def drawCards(): ArrayBuffer[card] = ArrayBuffer.empty[card]

    override def emptyPlantfieldNr(player: Option[player]): Int = 0

    override def findCardId(player: player, card: card): Int = 0

    override def findCardWithName(name: String): card = new card("Test", 0, Array(1, 2, 3))

    override def initGame: String = ""

    override def initPlayer(name: String): String = ""

    override def isPlantable(player: Option[player], bean: card): Boolean = false

    override def plant1or2(playingPlayer: Option[player]): Int = 0

    override def plant1or2ThreadInterrupt(): Unit = {}

    override def plantAllSelectedCards(plantCount: Int): Unit = {}

    override def plantDrawnCard(player: Option[player], card: card): Unit = {}

    override def plantInfo(): card = new card("Test", 0, Array(1, 2, 3))

    override def plantPreperation(player: Option[player]): String = ""

    override def plantSelectString(player: Option[player]): String = ""

    override def playerFieldToString(field: ArrayBuffer[card]): String = ""

    override def playerHandToString(hand: ArrayBuffer[card]): String = ""

    override def selectCardToPlant(cards: ArrayBuffer[card]): card = new card("Test", 0, Array(1, 2, 3))

    override def selectPlayer(): Option[player] = None

    override def takeNewCard(player: Option[player]): Unit = {}

    override def weightedRandom(): card = new card("Test", 0, Array(1, 2, 3))
    // Mock the player
    val mockPlayer = mock(classOf[player])
    val mockField1 = ArrayBuffer(new card("Blaue", 1, Array(1, 2, 3)), new card("Feuer", 2, Array(1, 2, 3)))
    val mockField2 = ArrayBuffer(new card("Sau", 3, Array(1, 2, 3)))
    val mockField3 = ArrayBuffer.empty[card]

    when(mockPlayer.plantfield1).thenReturn(mockField1)
    when(mockPlayer.plantfield2).thenReturn(mockField2)
    when(mockPlayer.plantfield3).thenReturn(mockField3)
    when(mockPlayer.gold).thenReturn(10)
    doNothing().when(mockPlayer).gold_=(anyInt())

    // Mock the dynamic game data
    val mockDynamicData = mock(classOf[dynamicGamedata.type])
    when(mockDynamicData.playingPlayer).thenReturn(Some(mockPlayer))

    // Test harvesting from field 1
    utility.harvest(1)
    verify(mockPlayer).gold_=(14) // 10 + 2 * 2
    mockField1 shouldBe empty

    // Reset and test harvesting from field 2
    when(mockPlayer.gold).thenReturn(14)
    utility.harvest(2)
    verify(mockPlayer).gold_=(16) // 14 + 2 * 1
    mockField2 shouldBe empty

    // Reset and test harvesting from field 3
    when(mockPlayer.gold).thenReturn(16)
    utility.harvest(3)
    verify(mockPlayer).gold_=(16) // 16 + 2 * 0
    mockField3 shouldBe empty
    
  }
}