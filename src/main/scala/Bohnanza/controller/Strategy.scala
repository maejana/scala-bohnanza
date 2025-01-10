package Bohnanza.controller

import Bohnanza.model.modelBase.{card, player}

import scala.collection.mutable.ArrayBuffer

trait Strategy {
  def execute(cards: ArrayBuffer[card], player: Option[player]): Boolean

}
