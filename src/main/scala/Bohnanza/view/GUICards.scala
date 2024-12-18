package Bohnanza.view

import Bohnanza.model
import javafx.geometry.{Insets, Pos}
import javafx.scene.layout.{HBox, VBox}
import javafx.scene.control.Label
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle

class GUICards {
  def getCardPanel(card: model.card): VBox = {
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

  def BlueCard: VBox = {
    val vbox = new VBox()
    vbox.setSpacing(5)
    vbox.setAlignment(Pos.CENTER)
    vbox.setPadding(new Insets(0))
    vbox.getChildren.addAll(
      new Label(model.gamedata.beans(0)),
      new Label(model.gamedata.priceBlaue.mkString(", "))
    )
    vbox.setStyle("-fx-border-color: black; -fx-border-width: 1;")
    vbox.setPrefWidth(1920)
    vbox.setPrefHeight(1000)
    vbox
  }

  def FireCard: VBox = {
    val vbox = new VBox()
    vbox.setSpacing(5)
    vbox.setAlignment(Pos.CENTER)
    vbox.setPadding(new Insets(0))
    vbox.getChildren.addAll(
      new Label(model.gamedata.beans(1)),
      new Label(model.gamedata.priceFeuer.mkString(", "))
    )
    vbox.setStyle("-fx-border-color: black; -fx-border-width: 1;")
    vbox.setPrefWidth(1920)
    vbox.setPrefHeight(1000)
    vbox
  }

  def PigCard: VBox = {
    val vbox = new VBox()
    vbox.setSpacing(5)
    vbox.setAlignment(Pos.CENTER)
    vbox.setPadding(new Insets(0))
    vbox.getChildren.addAll(
      new Label(model.gamedata.beans(2)),
      new Label(model.gamedata.priceSau.mkString(", "))
    )
    vbox.setStyle("-fx-border-color: black; -fx-border-width: 1;")
    vbox.setPrefWidth(1920)
    vbox.setPrefHeight(1000)
    vbox
  }

  def VomitCard: VBox = {
    val vbox = new VBox()
    vbox.setSpacing(5)
    vbox.setAlignment(Pos.CENTER)
    vbox.setPadding(new Insets(0))
    vbox.getChildren.addAll(
      new Label(model.gamedata.beans(3)),
      new Label(model.gamedata.priceBrech.mkString(", "))
    )
    vbox.setStyle("-fx-border-color: black; -fx-border-width: 1;")
    vbox.setPrefWidth(1920)
    vbox.setPrefHeight(1000)
    vbox
  }

  def SoyesCard: VBox = {
    val vbox = new VBox()
    vbox.setSpacing(5)
    vbox.setAlignment(Pos.CENTER)
    vbox.setPadding(new Insets(0))
    vbox.getChildren.addAll(
      new Label(model.gamedata.beans(4)),
      new Label(model.gamedata.priceSoja.mkString(", "))
    )
    vbox.setStyle("-fx-border-color: black; -fx-border-width: 1;")
    vbox.setPrefWidth(1920)
    vbox.setPrefHeight(1000)
    vbox
  }

  def EyesCard: VBox = {
    val vbox = new VBox()
    vbox.setSpacing(5)
    vbox.setAlignment(Pos.CENTER)
    vbox.setPadding(new Insets(0))
    vbox.getChildren.addAll(
      new Label(model.gamedata.beans(5)),
      new Label(model.gamedata.priceAugen.mkString(", "))
    )
    vbox.setStyle("-fx-border-color: black; -fx-border-width: 1;")
    vbox.setPrefWidth(1920)
    vbox.setPrefHeight(1000)
    vbox
  }

  def RedCard: VBox = {
    val vbox = new VBox()
    vbox.setSpacing(5)
    vbox.setAlignment(Pos.CENTER)
    vbox.setPadding(new Insets(0))
    vbox.getChildren.addAll(
      new Label(model.gamedata.beans(6)),
      new Label(model.gamedata.priceRote.mkString(", "))
    )
    vbox.setStyle("-fx-border-color: black; -fx-border-width: 1;")
    vbox.setPrefWidth(1920)
    vbox.setPrefHeight(1000)
    vbox
  }

  def GartenCard: VBox = {
    val vbox = new VBox()
    vbox.setSpacing(5)
    vbox.setAlignment(Pos.CENTER)
    vbox.setPadding(new Insets(0))
    vbox.getChildren.addAll(
      new Label(model.gamedata.beans(0)),
      new Label(model.gamedata.priceGarten.mkString(", "))
    )
    vbox.setStyle("-fx-border-color: black; -fx-border-width: 1;")
    vbox.setPrefWidth(1920)
    vbox.setPrefHeight(1000)
    vbox
  }
}