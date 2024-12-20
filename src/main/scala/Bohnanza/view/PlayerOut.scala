package Bohnanza.view

import Bohnanza.model
import Bohnanza.view.viewBase.FXGUi.plantInPlantfield
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.Label
import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.text.Font
import scalafx.scene.control.Button

trait PlayerOut {
  def playerScene: VBox
  def handCards: VBox
  def playerInfo: HBox
  def plantFields(int: Int): VBox
  def undoButton: Button
}

