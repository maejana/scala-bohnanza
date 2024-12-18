package Bohnanza.view

import scalafx.application.{JFXApp3, Platform}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, ComboBox, Label, TextField}
import scalafx.scene.layout.{BorderPane, HBox, VBox}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.text.Font
import scalafx.scene.paint.Color
import Bohnanza.model
import Bohnanza.controller
import Bohnanza.view.GUICards
import scala.collection.mutable.ListBuffer

import scala.util.{Try, Failure, Success}


object FXGUi extends JFXApp3 {
  val Nr: Int = 0
  var playerStep = 0
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
          stage.fullScreen = true
        }
      }
    )
  }

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
      model.dynamicGamedata.playerCount = count
      model.dynamicGamedata.readerThread.interrupt()
      namenEingeben(count)
    }

    children = Seq(label, dropdown, button)
  }

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

      val childrenBuffer = ListBuffer[scalafx.scene.Node]()

      childrenBuffer += new HBox {
        spacing = 10
        alignment = Pos.Center
        children = Seq(
          playerOut(), // Left section
          new VBox {
            spacing = 10
            alignment = Pos.Center
            // children = fields // Right section
          }
        )
      }

      if (playerStep == 0) {
        childrenBuffer += new Label(model.gamedata.plantAmountQuestion) {
          font = Font.font("Arial", 24)
          textFill = Color.Black
        }
        childrenBuffer += new HBox {
          spacing = 10
          alignment = Pos.Center
          children = Seq(
            new Button("1") {
              onAction = _ => {
                plantBean(model.dynamicGamedata.playingPlayer.get.playerHand(0))
                playerStep += 1
                stage.scene = new Scene(spielerRunde())
              }
            },
            new Button("2") {
              onAction = _ => {
                plantBean(model.dynamicGamedata.playingPlayer.get.playerHand(0))
                plantBean(model.dynamicGamedata.playingPlayer.get.playerHand(1))
                playerStep += 1
                stage.scene = new Scene(spielerRunde())
              }
            }
          )
        }
      }

      if (playerStep == 1) {
        childrenBuffer += new Button("Draw and Plant Cards") {
          onAction = _ => {
            playerStep += 1
            stage.scene = new Scene(drawAndPlantCards())
            model.dynamicGamedata.playingPlayer = controller.Utility.selectPlayer()
            controller.playerState.handle(model.dynamicGamedata.playingPlayer)
            playerStep == 0
          }

        }

      }

      children = childrenBuffer.toSeq
    }
  }


  def disableSaveButton(button: Button, textField: TextField): Unit = {
    button.disable = true
    textField.editable = false
  }

  def plantBean(bean: model.card): Unit = {
    model.dynamicGamedata.cardsToPlant += bean
    controller.Utility.plantPreperation(model.dynamicGamedata.playingPlayer)
    val beanToPlant = model.gameDataFunc.playerFieldToString(model.dynamicGamedata.cardsToPlant)
    Platform.runLater {
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
    }
    println(s"Planted bean: ${bean.beanName}")
  }

  def plantInPlantfield(bean: String): VBox = {
    new VBox {
      spacing = 5
      padding = Insets(2)
      style = "-fx-border-color: black; -fx-border-width: 1;"
      prefWidth = 500
      prefHeight = 100 // Set a valid double value

      children = Seq(
        new Label(model.gamedata.plantfield) {
          font = Font.font("Arial", 24)
        },
        new Label(bean) {
          font = Font.font("Arial", 24)
        },
        new Button(model.gamedata.continue) {
          onAction = _ => {
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
    new VBox {
      spacing = 10
      padding = Insets(10)
      alignment = Pos.Center

      children = Seq(
        new Label("Drawn Cards") {
          font = Font.font("Arial", 24)
          textFill = Color.Black
        },
        new HBox {
          spacing = 10
          alignment = Pos.Center
          children = Seq(
            new Label(card1.toString) {
              font = Font.font("Arial", 18)
            },
            new Label(card2.toString) {
              font = Font.font("Arial", 18)
            }
          )
        },
        new HBox {
          spacing = 10
          alignment = Pos.Center
          children = Seq(
            new Button("Plant 0") {
              onAction = _ => stage.scene = new Scene(spielerRunde())
            },
            new Button("Plant 1") {
              onAction = _ => {
                plantBean(card1)
                stage.scene = new Scene(spielerRunde())
              }
            },
            new Button("Plant Both") {
              onAction = _ => {
                plantBean(card1)
                plantBean(card2)
                stage.scene = new Scene(spielerRunde())
              }
            }
          )
        }
      )
    }
  }
    /*
    spacing: This property defines the amount of space between the children of a container. For example, in a VBox, it sets the vertical space between the child nodes.

      alignment: This property specifies how the children of a container are aligned within the container. For example, Pos.CENTER aligns the children in the center of the container.
    padding: This property sets the space between the container's border and its children. It is defined using Insets, which can specify different padding values for the top, right, bottom, and left sides.

    */
}
