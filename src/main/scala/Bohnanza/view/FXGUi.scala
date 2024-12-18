package Bohnanza.view

import scalafx.application.{JFXApp3, Platform}
import scalafx.scene.{Node, Scene}
import scalafx.scene.control.{Button, ComboBox, Label, TextField}
import scalafx.scene.layout.{BorderPane, HBox, VBox}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.text.Font
import scalafx.scene.paint.Color
import Bohnanza.model
import Bohnanza.controller
import Bohnanza.view.GUICards
import scalafx.scene.Node

import scala.util.{Failure, Success, Try}

object FXGUi extends JFXApp3 {

  var playerStep: Int = 0
  val childrenBuffer: scala.collection.mutable.Buffer[javafx.scene.Node] = scala.collection.mutable.Buffer()

  val Nr: Int = 0
  val playerPanel: VBox = new VBox(10) {
    padding = Insets(10)
  }

  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
      title = "Bohnanza"
      scene = new Scene(startScene())
      fullScreen = true
    }
  }

  def startScene(): VBox = new VBox {
    spacing = 2
    alignment = Pos.Center
    padding = Insets(2)

    children = Seq(
      new Label("Willkommen zu Bohnanza!") {
        font = Font.font("Arial", 48)
        textFill = Color.DarkGreen
      },
      new Button("Play") {
        font = Font.font("Arial", 24)
        onAction = _ => {
          stage.scene = new Scene(namenEingebenSeite())
          stage.fullScreen = true
        }
      }
    )
  }

  def spieleranzahlEingeben(): VBox = new VBox {
    spacing = 2
    alignment = Pos.Center
    padding = Insets(2)

    val label = new Label("Wie viele Spieler spielen?")
    val dropdown = new ComboBox(Seq("2", "3", "4", "5")) {
      prefWidth = 100
    }
    val button = new Button("Hinzufügen")

    button.onAction = _ => {
      val count = dropdown.value().toInt
      model.dynamicGamedata.playerCount = count
      model.dynamicGamedata.readerThread.interrupt()
      namenEingeben(count)
    }

    children = Seq(label, dropdown, button)
  }

  def namenEingebenSeite(): BorderPane = new BorderPane {
    padding = Insets(5)
    top = new Label(model.gamedata.bohnanza) {
      font = Font.font("Arial", 36)
      alignmentInParent = Pos.Center
    }
    center = new VBox {
      spacing = 2
      alignment = Pos.Center
      padding = Insets(2, 10, 0, 10) // Adjust padding to move elements closer to the top
      children = Seq(
        spieleranzahlEingeben(),
        playerPanel,
        new Button(model.gamedata.continue) {
          onAction = _ => {
            stage.scene = new Scene(spielerRunde())
            stage.fullScreen = true
          }
        }
      )
    }
  }

  def namenEingeben(anzahl: Int): Unit = {
    playerPanel.children.clear()

    for (i <- 1 to anzahl) {
      playerPanel.children.add(addPlayer(i))
    }
  }

  def addPlayer(nr: Int): HBox = new HBox {
    spacing = 5
    alignment = Pos.Center

    val label = new Label(s"$nr.")
    val textField = new TextField {
      prefWidth = 200
      promptText = "Spielername eingeben"
    }
    val buttonSave = new Button("Speichern")

    buttonSave.onAction = _ => {
      model.gameDataFunc.initPlayer(textField.text())
      controller.Utility.selectPlayer()
      if (textField.text().nonEmpty) {
        println(s"Spieler $nr: ${textField.text()}")
        model.dynamicGamedata.NameReaderThread.interrupt()
        disableSaveButton(buttonSave, textField)
      }
    }

    children = Seq(label, textField, buttonSave)
  }

  def spielerRunde(): VBox = {
    new VBox {
      spacing = 10
      padding = Insets(10)
      alignment = Pos.Center

      children = Seq(
        new HBox {
          spacing = 10
          alignment = Pos.Center
          children = Seq(
            playerOut(), // Linker Bereich
            new VBox {
              spacing = 10
              alignment = Pos.Center
              // children = fields // Rechter Bereich
            }
          )
        },
        new Label(model.gamedata.plantAmountQuestion) {
          font = Font.font("Arial", 24)
          textFill = Color.Black
        },
        new HBox {
          spacing = 10
          alignment = Pos.Center
          children = Seq(
            new Button("1") {
              onAction = _ => plantBean(1)
            },
            new Button("2") {
              onAction = _ => plantBean(2)
            },
            new Button("Zwei Karten ziehe und pflanzen"){
              onAction = _ => stage.scene = new Scene (drawAndPlantCards())
            }
          )
        }
      )

      if (playerStep == 1) {
        childrenBuffer += new Button("Draw and Plant Cards") {
          onAction = _ => {
            playerStep += 1
            stage.scene = new Scene(drawAndPlantCards())
            controller.Utility.selectPlayer()
            controller.playerState.handle(model.dynamicGamedata.playingPlayer)
            playerStep = 0
          }
        }
      }

    }
  }

  def disableSaveButton(button: Button, textField: TextField): Unit = {
    button.disable = true
    textField.editable = false
  }

  def plantBean(i: Int): Unit = {
    i match {
      case 0 =>
        model.dynamicGamedata.playingPlayer = controller.Utility.selectPlayer()
        controller.playerState.handle(model.dynamicGamedata.playingPlayer)
      case 1 =>
        model.dynamicGamedata.cardsToPlant += model.dynamicGamedata.playingPlayer.get.playerHand(0)
        controller.Utility.plantPreperation(model.dynamicGamedata.playingPlayer)
        val beanToPlant = model.gameDataFunc.playerFieldToString(model.dynamicGamedata.cardsToPlant)
        stage.scene = new Scene {
          root = new VBox {

            spacing = 1
            padding = Insets(1)
            alignment = Pos.Center
            children = Seq(
              playerOut(),
              plantInPlantfield(beanToPlant)
            )
          }
        }
        stage.fullScreen = true
      case 2 =>
        model.dynamicGamedata.cardsToPlant += model.dynamicGamedata.playingPlayer.get.playerHand(0)
        model.dynamicGamedata.cardsToPlant += model.dynamicGamedata.playingPlayer.get.playerHand(1)
        controller.Utility.plantPreperation(model.dynamicGamedata.playingPlayer)
        stage.scene = new Scene {
          root = new VBox {
            spacing = 1
            padding = Insets(1)
            alignment = Pos.Center
            children = Seq(
              new HBox {
                spacing = 1
                alignment = Pos.Center
                children = Seq(
                  playerOut(),
                  plantInPlantfield(model.dynamicGamedata.cardsToPlant(0).toString),
                  if (model.dynamicGamedata.playingPlayer.get.playerHand.size > 1) {
                    plantInPlantfield(model.dynamicGamedata.cardsToPlant(1).toString)
                  } else {
                    new Label("")
                  }
                )
              }
            )
          }
        }
        stage.fullScreen = true
        println(s"Planted bean 1: ${model.dynamicGamedata.cardsToPlant(0).beanName}")
        if (model.dynamicGamedata.cardsToPlant.size > 1) {
          println(s"Planted bean 2: ${model.dynamicGamedata.cardsToPlant(1).beanName}")
        }
        stage.fullScreen = true
    }
  }

  def plantInPlantfield(bean: String): VBox = {
    new VBox {
      spacing = 5
      padding = Insets(2)
      style = "-fx-border-color: black; -fx-border-width: 1;"
      prefWidth = 500
      prefHeight = 100

      children = Seq(
        new Label(model.gamedata.plantfield) {
          font = Font.font("Arial", 24)
        },
        new Label(bean) {
          font = Font.font("Arial", 24)
        },
        new Button(model.gamedata.continue) {
          onAction = _ => {
            model.dynamicGamedata.playingPlayer = controller.Utility.selectPlayer()
            controller.playerState.handle(model.dynamicGamedata.playingPlayer)
            stage.scene = new Scene(spielerRunde())
            stage.fullScreen = true
          }
        }
      )
    }
  }

  def playerOut(): VBox = {
    new VBox {
      spacing = 5
      padding = Insets(10)
      alignment = Pos.Center

      val playerName: String = model.dynamicGamedata.playingPlayer.get.playerName
      children = Seq(
        new VBox {
          spacing = 5
          alignment = Pos.Center
          children = Seq(
            new HBox {
              spacing = 5
              alignment = Pos.Center
              children = Seq(
                new Label(s"Spieler: $playerName") {
                  font = Font.font("Arial", 24)
                  textFill = Color.DarkGreen
                },
                new Label(model.gamedata.coinsString + ":" + model.dynamicGamedata.playingPlayer.get.gold) {
                  font = Font.font("Arial", 24)
                }
              )
            },
            new VBox {
              spacing = 0
              alignment = Pos.BottomRight
              style = "-fx-border-color: black; -fx-border-width: 2;"
              children = model.dynamicGamedata.playingPlayer.get.playerHand.map { card =>
                new VBox(GUICards().getCardPanel(card)) {
                }
              }
            }
          )
        }
      )
    }
  }

  def drawAndPlantCards(): VBox = {
    val card1 = controller.UIlogic.weightedRandom()
    val card2 = controller.UIlogic.weightedRandom()
    model.dynamicGamedata.cardsToPlant = scala.collection.mutable.ArrayBuffer(card1, card2)
    new VBox {
      spacing = 10
      padding = Insets(10)
      alignment = Pos.Center

      children = Seq(
        new Label(model.dynamicGamedata.playingPlayer.get.playerName) {
          font = Font.font("Arial", 24)
          textFill = Color.Black
        },
        new Label("Drawn Cards") {
          font = Font.font("Arial", 24)
          textFill = Color.Black
        },
        new HBox {
          spacing = 10
          alignment = Pos.Center
          children = Seq(
            new VBox(GUICards().getCardPanel(card1)),
            new VBox(GUICards().getCardPanel(card2))
          )
        },
        new HBox {
          spacing = 10
          alignment = Pos.Center
          children = Seq(
            new Button("Plant 0") {
              onAction = _ => plantBean(0)
            },
            new Button("Plant 1") {
              onAction = _ => plantBean(1)
            },
            new Button("Plant Both") {
              onAction = _ => plantBean(2)
            }
          )
        }
      )
    }
  }
}