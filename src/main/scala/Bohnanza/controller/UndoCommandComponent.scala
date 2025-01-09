package Bohnanza.controller

import Bohnanza.model.modelBase.player

trait UndoCommandComponent {
  def doStep(player: Option[player]): Unit

  def undoStep(player: Option[player]): Unit

  def redoStep(player: Option[player]): Unit
  
  def matchState(): Unit
}
