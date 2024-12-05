package Bohnanza.view

import Bohnanza.model

import scala.swing._
import javax.swing.BorderFactory


class GUICards () {
  def getCardPanel(card: model.card): Panel = {
    card.beanName match {
      case "Blaue" => BlueCard
      case "Feuer" => FireCard
      case "Sau" => PigCard
      case "Brech" => VomitCard
      case "Soja" => SoyesCard
      case "Augen" => EyesCard
      case "Rote" => RedCard
      case "Garten" => GartenCard
    }
  }

  def BlueCard: Panel = {
    new BoxPanel(Orientation.Horizontal) {
      border = BorderFactory.createLineBorder(java.awt.Color.BLACK)
      preferredSize = new Dimension(1920, 1000)
      contents += new Label(model.gamedata.beans(0))
      contents += new Label(model.gamedata.priceBlaue.mkString(", ", ", ", ", "))

    }

  }

  def FireCard: Panel = {
    new BoxPanel(Orientation.Horizontal) {
      border = BorderFactory.createLineBorder(java.awt.Color.BLACK)
      preferredSize = new Dimension(1920, 1000)
      contents += new Label(model.gamedata.beans(1))
      contents += new Label(model.gamedata.priceFeuer.mkString(", ", ", ", ", "))

    }

  }

  def PigCard: Panel = {
    new BoxPanel(Orientation.Horizontal) {
      border = BorderFactory.createLineBorder(java.awt.Color.BLACK)
      preferredSize = new Dimension(1920, 1000)
      contents += new Label(model.gamedata.beans(2))
      contents += new Label(model.gamedata.priceSau.mkString(", ", ", ", ", "))

    }

  }

  def VomitCard: Panel = {
    new BoxPanel(Orientation.Horizontal) {
      border = BorderFactory.createLineBorder(java.awt.Color.BLACK)
      preferredSize = new Dimension(1920, 1000)
      contents += new Label(model.gamedata.beans(3))
      contents += new Label(model.gamedata.priceBrech.mkString(", ", ", ", ", "))

    }
  }

  def SoyesCard: Panel = {
    new BoxPanel(Orientation.Horizontal) {
      border = BorderFactory.createLineBorder(java.awt.Color.BLACK)
      preferredSize = new Dimension(1920, 1000)
      contents += new Label(model.gamedata.beans(4))
      contents += new Label(model.gamedata.priceSoja.mkString(", ", ", ", ", "))

    }

  }

  def EyesCard: Panel = {
    new BoxPanel(Orientation.Horizontal) {
      border = BorderFactory.createLineBorder(java.awt.Color.BLACK)
      preferredSize = new Dimension(1920, 1000)
      contents += new Label(model.gamedata.beans(5))
      contents += new Label(model.gamedata.priceAugen.mkString(", ", ", ", ", "))

    }

  }

  def RedCard: Panel = {
    new BoxPanel(Orientation.Horizontal) {
      border = BorderFactory.createLineBorder(java.awt.Color.BLACK)
      preferredSize = new Dimension(1920, 1000)
      contents += new Label(model.gamedata.beans(6))
      contents += new Label(model.gamedata.priceRote.mkString(", ", ", ", ", "))

    }
  }

  def GartenCard: Panel = {
    new BoxPanel(Orientation.Horizontal) {
      border = BorderFactory.createLineBorder(java.awt.Color.BLACK)
      preferredSize = new Dimension(1920, 1000)
      contents += new Label(model.gamedata.beans(0))
      contents += new Label(model.gamedata.priceGarten.mkString(", "))

    }

  }
}
