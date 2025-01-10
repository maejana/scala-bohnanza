package Bohnanza.model.modelBase

import Bohnanza.controller.controllerBase.playerState
import Bohnanza.controller.State
import Bohnanza.model.modelBase.card
import Bohnanza.model.FactoryT
import Bohnanza.controller.playerStateComponent

import scala.collection.mutable.ArrayBuffer


object FactoryP {
  

  class PlayerFactory() extends FactoryT {

    override def createPlayer(
                      name: String,
                      hand: ArrayBuffer[card],
                      plantfield1: ArrayBuffer[card] = ArrayBuffer(),
                      plantfield2: ArrayBuffer[card] = ArrayBuffer(),
                      plantfield3: ArrayBuffer[card] = ArrayBuffer(),
                      gold: Int = 0,
                      state: State = playerState().DontPlays()
                    ): player = {
      val newPlayer = player(name, hand. 
      newPlayer.plantfield1 = plantfield1
      newPlayer.plantfield2 = plantfield2
      newPlayer.plantfield3 = plantfield3
      newPlayer.gold = gold
      newPlayer.state = state
      newPlayer
    }
  }
}