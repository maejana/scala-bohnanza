package test

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import scala.collection.mutable.ArrayBuffer
import Bohnanza.model.modelBase.{card, player}

class playerTest extends AnyFlatSpec with Matchers with MockitoSugar {

  "A player" should "be initialized with the correct values" in {
    val hand = ArrayBuffer(mock[card], mock[card])
    val playerName = "TestPlayer"
    val testPlayer = player(playerName, hand)

    testPlayer.playerName should be(playerName)
    testPlayer.playerHand should be(hand)
    testPlayer.plantfield1 should be(ArrayBuffer.empty[card])
    testPlayer.plantfield2 should be(ArrayBuffer.empty[card])
    testPlayer.plantfield3 should be(ArrayBuffer.empty[card])
    testPlayer.gold should be(0)
    testPlayer.state should not be null
  }

  it should "restore its state correctly" in {
    val hand = ArrayBuffer(mock[card], mock[card])
    val playerName = "TestPlayer"
    val testPlayer = player(playerName, hand)
    val newState = player("NewPlayer", ArrayBuffer(mock[card]))

    testPlayer.restore(newState)

    testPlayer.playerName should be("NewPlayer")
    testPlayer.playerHand should be(newState.playerHand)
    testPlayer.plantfield1 should be(newState.plantfield1)
    testPlayer.plantfield2 should be(newState.plantfield2)
    testPlayer.plantfield3 should be(newState.plantfield3)
    testPlayer.gold should be(newState.gold)
    testPlayer.state should be(newState.state)
  }

  it should "create a correct copy of its state" in {
    val hand = ArrayBuffer(mock[card], mock[card])
    val playerName = "TestPlayer"
    val testPlayer = player(playerName, hand)
    val copiedPlayer = testPlayer.copyState()

    copiedPlayer.playerName should be(testPlayer.playerName)
    copiedPlayer.playerHand should be(testPlayer.playerHand)
    copiedPlayer.plantfield1 should be(testPlayer.plantfield1)
    copiedPlayer.plantfield2 should be(testPlayer.plantfield2)
    copiedPlayer.plantfield3 should be(testPlayer.plantfield3)
    copiedPlayer.gold should be(testPlayer.gold)
    copiedPlayer.state should be(testPlayer.state)
  }
}