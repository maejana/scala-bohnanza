package Bohnanza.model

import Bohnanza.controller.playerState

import scala.collection.mutable.ArrayBuffer
import Bohnanza.controller.playerState.State

object FactoryP {
  trait Factory {
    def createPlayer(
                      name: String,
                      hand: ArrayBuffer[card],
                      plantfield1: ArrayBuffer[card] = ArrayBuffer(),
                      plantfield2: ArrayBuffer[card] = ArrayBuffer(),
                      plantfield3: ArrayBuffer[card] = ArrayBuffer(),
                      gold: Int = 0,
                      state: State = playerState.DontPlays()
                    ): player

  }

  class PlayerFactory extends Factory {

    override def createPlayer(
                      name: String,
                      hand: ArrayBuffer[card],
                      plantfield1: ArrayBuffer[card] = ArrayBuffer(),
                      plantfield2: ArrayBuffer[card] = ArrayBuffer(),
                      plantfield3: ArrayBuffer[card] = ArrayBuffer(),
                      gold: Int = 0,
                      state: State = playerState.DontPlays()
                    ): player = {
      val newPlayer = player(name, hand)
      newPlayer.plantfield1 = plantfield1
      newPlayer.plantfield2 = plantfield2
      newPlayer.plantfield3 = plantfield3
      newPlayer.gold = gold
      newPlayer.state = state
      newPlayer
    }
  }
}