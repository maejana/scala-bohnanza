package Bohnanza.view

import Bohnanza.model
import javafx.geometry.{Insets, Pos}
import javafx.scene.layout.{HBox, VBox}
import javafx.scene.control.Label
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import scalafx.scene.text.Font

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
      new Label(model.gamedata.beans(0)) {
        setFont(Font.font("Arial", 24))
      },
      new Label(model.gamedata.priceBlaue.mkString(", ")) {
        setFont(Font.font("Arial", 24))
      }
    )
    vbox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: lightblue;-fx-border-radius: 10;")
    vbox.setPrefWidth(200)
    vbox.setPrefHeight(100)
    vbox
  }

  def FireCard: VBox = {
    val vbox = new VBox()
    vbox.setSpacing(2)
    vbox.setAlignment(Pos.CENTER)
    vbox.setPadding(new Insets(0))
    vbox.getChildren.addAll(
      new Label(model.gamedata.beans(1)) {
        setFont(Font.font("Arial", 24))
      },
      new Label(model.gamedata.priceFeuer.mkString(", ")) {
        setFont(Font.font("Arial", 24))
      }
    )
    vbox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: orange;-fx-border-radius: 10;")
    vbox.setPrefWidth(200)
    vbox.setPrefHeight(100)
    vbox
  }

  def PigCard: VBox = {
    val vbox = new VBox()
    vbox.setSpacing(5)
    vbox.setAlignment(Pos.CENTER)
    vbox.setPadding(new Insets(0))
    vbox.getChildren.addAll(
      new Label(model.gamedata.beans(2)) {
        setFont(Font.font("Arial", 24))
      },
      new Label(model.gamedata.priceSau.mkString(", ")) {
        setFont(Font.font("Arial", 24))
      }
    )
    vbox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: lightpink;-fx-border-radius: 10;")
    vbox.setPrefWidth(200)
    vbox.setPrefHeight(100)
    vbox
  }

  def VomitCard: VBox = {
    val vbox = new VBox()
    vbox.setSpacing(5)
    vbox.setAlignment(Pos.CENTER)
    vbox.setPadding(new Insets(0))
    vbox.getChildren.addAll(
      new Label(model.gamedata.beans(3)) {
        setFont(Font.font("Arial", 24))
      },
      new Label(model.gamedata.priceBrech.mkString(", ")) {
        setFont(Font.font("Arial", 24))
      }
    )
    vbox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: brown;-fx-border-radius: 10;")
    vbox.setPrefWidth(200)
    vbox.setPrefHeight(100)
    vbox
  }

  def SoyesCard: VBox = {
    val vbox = new VBox()
    vbox.setSpacing(5)
    vbox.setAlignment(Pos.CENTER)
    vbox.setPadding(new Insets(0))
    vbox.getChildren.addAll(
      new Label(model.gamedata.beans(4)) {
        setFont(Font.font("Arial", 24))
      },
      new Label(model.gamedata.priceSoja.mkString(", ")) {
        setFont(Font.font("Arial", 24))
      }
    )
    vbox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: beige;-fx-border-radius: 10;")
    vbox.setPrefWidth(200)
    vbox.setPrefHeight(100)
    vbox
  }

  def EyesCard: VBox = {
    val vbox = new VBox()
    vbox.setSpacing(5)
    vbox.setAlignment(Pos.CENTER)
    vbox.setPadding(new Insets(0))
    vbox.getChildren.addAll(
      new Label(model.gamedata.beans(5)) {
        setFont(Font.font("Arial", 24))
      },
      new Label(model.gamedata.priceAugen.mkString(", ")) {
        setFont(Font.font("Arial", 24))
      }
    )
    vbox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: blue;-fx-border-radius: 10;")
    vbox.setPrefWidth(200)
    vbox.setPrefHeight(100)
    vbox
  }

  def RedCard: VBox = {
    val vbox = new VBox()
    vbox.setSpacing(5)
    vbox.setAlignment(Pos.CENTER)
    vbox.setPadding(new Insets(0))
    vbox.getChildren.addAll(
      new Label(model.gamedata.beans(6)) {
        setFont(Font.font("Arial", 24))
      },
      new Label(model.gamedata.priceRote.mkString(", ")) {
        setFont(Font.font("Arial", 24))
      }
    )
    vbox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: red;-fx-border-radius: 10;")
    vbox.setPrefWidth(200)
    vbox.setPrefHeight(100)
    vbox
  }

  def GartenCard: VBox = {
    val vbox = new VBox()
    vbox.setSpacing(5)
    vbox.setAlignment(Pos.CENTER)
    vbox.setPadding(new Insets(0))
    vbox.getChildren.addAll(
      new Label(model.gamedata.beans(7)){
        setFont(Font.font("Arial", 24))
      },
      new Label(model.gamedata.priceGarten.mkString(", ")) {
        setFont(Font.font("Arial", 24))
      }
        )
    vbox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: green;-fx-border-radius: 10;")
    vbox.setPrefWidth(200)
    vbox.setPrefHeight(100)
    vbox
  }
}