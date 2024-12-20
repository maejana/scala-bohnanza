package Bohnanza.model

import Bohnanza.controller.controllerBase.playerState
import Bohnanza.controller.State
import Bohnanza.model.modelBase.{card, player}

import scala.collection.mutable.ArrayBuffer


trait FactoryT {
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