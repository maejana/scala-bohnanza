package Bohnanza.view

import scalafx.application.{JFXApp3, Platform}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label, ComboBox, TextField}
import scalafx.scene.layout.{VBox, HBox, BorderPane}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.text.Font
import scalafx.scene.paint.Color

import Bohnanza.model
import Bohnanza.controller
import Bohnanza.view.GUICards


object FXGUi extends JFXApp3 {

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
    spacing = 20
    alignment = Pos.Center
    padding = Insets(50)

    children = Seq(
      new Label("Willkommen zu Bohnanza!") {
        font = Font.font("Arial", 48)
        textFill = Color.DarkGreen
      },
      new Button("Play") {
        font = Font.font("Arial", 24)
        onAction = _ => {
          stage.scene = new Scene(namenEingebenSeite())
        }
      }
    )
  }


  /** Spieleranzahl eingeben */
  def spieleranzahlEingeben(): VBox = new VBox {
    spacing = 10
    alignment = Pos.Center

    val label = new Label("Wie viele Spieler spielen?")
    val dropdown = new ComboBox(Seq("2", "3", "4", "5")) {
      prefWidth = 100
    }
    val button = new Button("Hinzufügen")

    button.onAction = _ => {
      val count = dropdown.value().toInt
      namenEingeben(count)
    }

    children = Seq(label, dropdown, button)
  }

  /** Namen der Spieler eingeben */
  def namenEingebenSeite(): BorderPane = new BorderPane {
    padding = Insets(10)
    top = new Label("Bohnanza") {
      font = Font.font("Arial", 36)
      alignmentInParent = Pos.Center
    }
    center = spieleranzahlEingeben()
    bottom = new VBox {
      spacing = 5
      alignment = Pos.Center
      children = Seq(
        playerPanel,
        new Button(model.gamedata.continue) {
          onAction = _ => {
            stage.scene = new Scene(spielerRunde())
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

  /** Eingabefeld für jeden Spieler */
  def addPlayer(nr: Int): HBox = new HBox {
    spacing = 10
    alignment = Pos.CenterLeft

    val label = new Label(s"$nr.")
    val textField = new TextField {
      prefWidth = 200
      promptText = "Spielername eingeben"
    }
    val buttonSave = new Button("Speichern")

    buttonSave.onAction = _ => {
      model.gameDataFunc.initPlayer(textField.text())
      model.dynamicGamedata.playingPlayer = controller.Utility.selectPlayer()
      if (textField.text().nonEmpty) {
        println(s"Spieler $nr: ${textField.text()}")
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
            }
          )
        }
      )
    }
  }


  def disableSaveButton(button: Button, textField: TextField): Unit = {
    button.disable = true
    textField.editable = false
  }

  def plantBean (i: Int): Unit = {
    i match {
      case 1 =>
        model.dynamicGamedata.cardsToPlant += model.dynamicGamedata.playingPlayer.get.playerHand(0)
        controller.Utility.plantPreperation(model.dynamicGamedata.playingPlayer)
        val beanToPlant = model.gameDataFunc.playerFieldToString(model.dynamicGamedata.cardsToPlant)
        stage.scene = new Scene {
          root = new VBox {
            spacing = 10
            padding = Insets(10)
            alignment = Pos.Center
            children = Seq(
              playerOut(),
              plantInPlantfield(beanToPlant)
            )
          }
        }
      case 2 =>
        model.dynamicGamedata.cardsToPlant += model.dynamicGamedata.playingPlayer.get.playerHand(0)
        model.dynamicGamedata.cardsToPlant += model.dynamicGamedata.playingPlayer.get.playerHand(1)
        controller.Utility.plantPreperation(model.dynamicGamedata.playingPlayer)
        stage.scene = new Scene {
          root = new VBox {
            spacing = 5
            padding = Insets(10)
            alignment = Pos.Center
            children = Seq(
              new HBox {
                spacing = 0
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
    }
    stage.scene().getWindow.sizeToScene()
  }

  def plantInPlantfield(bean: String): VBox = {
    new VBox {
      spacing = 10
      padding = Insets(10)
      style = "-fx-border-color: black; -fx-border-width: 1;"
      prefWidth = 1000
      prefHeight = 200

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
          }
        }
      )
    }
  }

  def playerOut(): VBox = {
    new VBox {
      spacing = 10
      padding = Insets(10)
      alignment = Pos.Center

      val playerName: String = model.dynamicGamedata.playingPlayer.get.playerName
      children = Seq(
        new HBox {
          spacing = 10
          alignment = Pos.Center
          children = Seq(
            new Label ("Spieler Runde") {
              font = Font.font("Arial", 36)
              textFill = Color.Green
            },

            new Label(s"Spieler: $playerName") {
              font = Font.font("Arial", 24)
              textFill = Color.DarkGreen
            },
            new Label(model.gamedata.coinsString + ":" + model.dynamicGamedata.playingPlayer.get.gold) {
              font = Font.font("Arial", 24)
            },
            new VBox {
              spacing = 10
              alignment = Pos.BottomRight
              style = "-fx-boarder-color: black; -fx-boarder-width: 2;"
              children = model.dynamicGamedata.playingPlayer.get.playerHand.map { card =>
                new Label(GUICards().getCardPanel(card).toString) {
                  font = Font.font("Arial", 24)

                }
              }
            }
            
          )
        }
      )
    }

  }


}