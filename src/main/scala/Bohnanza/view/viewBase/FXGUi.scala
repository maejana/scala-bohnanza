package Bohnanza.view.viewBase

import Bohnanza.controller.controllerBase
import Bohnanza.controller.controllerBase.{Utility, playerState}
import Bohnanza.model.modelBase
import Bohnanza.model.modelBase.{dynamicGamedata, gamedata}
import Bohnanza.{controller, model, view}
import scalafx.application.{JFXApp3, Platform}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.{Node, Scene}
import scalafx.scene.control.{Button, ComboBox, Label, TextField}
import scalafx.scene.layout.{BorderPane, HBox, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.text.Font

import scala.util.{Failure, Success, Try}
object FXGUi extends JFXApp3 {
  var playerStep: Int = 0
  val childrenBuffer: scala.collection.mutable.Buffer[javafx.scene.Node] = scala.collection.mutable.Buffer()

  val Nr: Int = 0
  val playerPanel: VBox = new VBox(10) {
    padding = Insets(10)
  }
  val boogalooFont: Font = Font.loadFont("file:///C:/Users/euule/Documents/GitHub/scala-bohnanza/src/resources/Boogaloo-Regular.ttf", 24)

  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
      title = "Bohnanza"
      resizable = true
      maximized = true
      scene = new Scene(startScene())
      onCloseRequest = _ => {
        Platform.exit()
        System.exit(0)
      }
    }
  }

  def startScene(): VBox = new VBox {
    style = "-fx-background-color: #FF5B61;"
    spacing = 2
    alignment = Pos.Center
    padding = Insets(2)

    children = Seq(
      new Label("Willkommen zu Bohnanza!") {
        font = boogalooFont
        textFill = Color.Green
      },
      new Button("Play") {
        font = boogalooFont
        onAction = _ => {
          stage.scene = new Scene(namenEingebenSeite())
          stage.fullScreen = true
        }
      }
    )
  }

  def spieleranzahlEingeben(): VBox = new VBox {
    style = "-fx-background-color: yellow;"
    spacing = 2
    alignment = Pos.Center
    padding = Insets(2)

    val label = new Label("Wie viele Spieler spielen?") {
      font = boogalooFont
    }
    val dropdown = new ComboBox(Seq("2", "3", "4", "5")) {
      prefWidth = 100
    }
    val button: Button = new Button("Hinzufügen") {
      font = boogalooFont
    }

    button.onAction = _ => {
      val count = dropdown.value().toInt
      dynamicGamedata.playerCount = count
      modelBase.dynamicGamedata.readerThread.interrupt()
      namenEingeben(count)
      stage.fullScreen = true
    }

    children = Seq(label, dropdown, button)
  }

  def namenEingebenSeite(): BorderPane = new BorderPane {
    style = "-fx-background-color: yellow;"
    padding = Insets(5)
    top = new Label(gamedata.bohnanza) {
      font = boogalooFont
      alignmentInParent = Pos.Center
      textFill = Color.Green
    }
    center = new VBox {
      spacing = 2
      alignment = Pos.Center
      padding = Insets(2, 10, 0, 10) // Adjust padding to move elements closer to the top
      children = Seq(
        spieleranzahlEingeben(),
        playerPanel,
        new Button(modelBase.gamedata.continue) {
          font = boogalooFont

          onAction = _ => {
            Utility().deletePlayerBecauseBug()
            Utility().selectPlayer()
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
    style = "-fx-background-color: yellow;"
    spacing = 5
    alignment = Pos.Center

    val label = new Label(s"$nr.") {
      font = boogalooFont
    }
    val textField = new TextField {
      prefWidth = 200
      promptText = "Spielername eingeben"
      font = boogalooFont
    }
    val buttonSave = new Button("Speichern") {
      font = boogalooFont
    }

    buttonSave.onAction = _ => {
      if (textField.text().nonEmpty) {
        modelBase.dynamicGamedata.NameReaderThread.interrupt()
        controllerBase.Utility().initPlayer(textField.text())
        println(s"Spieler $nr: ${textField.text()}")
        disableSaveButton(buttonSave, textField)
      }
    }

    children = Seq(label, textField, buttonSave)
  }

  def spielerRunde(): VBox = {
    new VBox {
      style = "-fx-background-color: yellow;"
      spacing = 10
      padding = Insets(10)
      alignment = Pos.Center

      children = Seq(
        new HBox {
          spacing = 10
          alignment = Pos.Center
          children = Seq(
            playerOut(),
            new VBox {
              spacing = 10
              alignment = Pos.Center
              // children = fields // Rechter Bereich
            }
          )
        },
        new Label(modelBase.gamedata.plantAmountQuestion) {
          font = boogalooFont
          textFill = Color.DarkGreen
        },
        new HBox {
          spacing = 10
          alignment = Pos.Center
          children = Seq(
            new Button("1") {
              font = boogalooFont
              onAction = _ => plantBean(1)
                controllerBase.Utility().plant1or2ThreadInterrupt()
               // println(model.fieldBuilder.buildGrowingFieldStr(model.dynamicGamedata.playingPlayer))
            },
            new Button("2") {
              font = boogalooFont
              onAction = _ => plantBean(2)
                controllerBase.Utility().plant1or2ThreadInterrupt()
                //controllerBase.Utility.plant1or2ThreadInterrupt()
                //println(model.fieldBuilder.buildGrowingFieldStr(model.dynamicGamedata.playingPlayer))
            },
            new Button("Zwei Karten ziehe und pflanzen"){
              font = boogalooFont
              onAction = _ => {
                stage.scene = new Scene (drawAndPlantCards())
                stage.fullScreen = true 
              }
            }
          )
        }
      )

      if (playerStep == 1) {
        childrenBuffer += new Button("Draw and Plant Cards") {
          font = boogalooFont
          onAction = _ => {
            playerStep += 1
            stage.scene = new Scene(drawAndPlantCards())
            stage.maximized = true
            //controllerBase.Utility.selectPlayer()
            playerState().handle(modelBase.dynamicGamedata.playingPlayer)
            playerStep = 0
          }
        }
      }


    }
  }

  def disableSaveButton(button: Button, textField: TextField): Unit = {
    button.font = boogalooFont
    button.disable = true
    textField.editable = false
  }

  def plantBean(i: Int): Unit = {
    PlantBeanC.plantBean(i)
    stage.scene = new Scene(playerOut())
    stage.fullScreen = true 
  }

  def plantInPlantfield(bean: String): VBox = {
    new VBox {
      spacing = 5
      padding = Insets(2)
      children = Seq(
        new VBox {
          spacing = 5
          padding = Insets(2)
          style = "-fx-border-color: black; -fx-border-width: 1;"
          prefWidth = 500
          prefHeight = 100
          children = Seq(
            new Label(modelBase.gamedata.plantfield) {
              font = boogalooFont
              textFill = Color.DarkGreen
            },
            GUICards().getCardPanel(convertStringToCard(bean))

          )
        },
        new Button(modelBase.gamedata.continue) {
          font = boogalooFont
          onAction = _ => {

            //controllerBase.Utility.selectPlayer()
            controllerBase.playerState().handle(modelBase.dynamicGamedata.playingPlayer)
            stage.scene = new Scene(spielerRunde())
            stage.fullScreen = true
          }
        }
      )
    }
  }

  def playerOut(): VBox = {
    new PlayerOutClass().playerScene

  }


  def drawAndPlantCards(): VBox = {
    val card1 = controllerBase.Utility().weightedRandom()
    val card2 = controllerBase.Utility().weightedRandom()
    modelBase.dynamicGamedata.cardsToPlant = scala.collection.mutable.ArrayBuffer(card1, card2)
    new VBox {
      spacing = 10
      padding = Insets(10)
      alignment = Pos.Center

      children = Seq(
        new Label(modelBase.dynamicGamedata.playingPlayer.get.playerName) {
          font = boogalooFont
          textFill = Color.Green
        },
        new Label("Drawn Cards") {
          font = boogalooFont
          textFill = Color.Green
        },
        new VBox {
          spacing = 10
          alignment = Pos.Center
          children = Seq(
            GUICards().getCardPanel(card1),
            GUICards().getCardPanel(card2)
          )
        },
        new HBox {
          spacing = 10
          alignment = Pos.Center
          children = Seq(
            new Button("Plant 0") {
              font = boogalooFont
              onAction = _ => {
                plantBean(0)
                stage.maximized = true
              }
            },
            new Button("Plant 1") {
              font = boogalooFont
              onAction = _ => {
                plantBean(1)
                stage.maximized = true
              }
            },
            new Button("Plant Both") {
              font = boogalooFont
              onAction = _ => {
                plantBean(2)
                stage.maximized = true
              }
            }
          )
        }
      )
    }
  }

  def convertStringToCard(bean: String): modelBase.card = {
    bean match {
      case "Blaue" => modelBase.gamedata.cards(0)
      case "Feuer" => modelBase.gamedata.cards(1)
      case "Sau" => modelBase.gamedata.cards(2)
      case "Brech" => modelBase.gamedata.cards(3)
      case "Soja" => modelBase.gamedata.cards(4)
      case "Augen" => modelBase.gamedata.cards(5)
      case "Rote" => modelBase.gamedata.cards(6)
      case "Garten" => modelBase.gamedata.cards(7)
      case _ => val noCard: modelBase.card = modelBase.card("NoCard", 20, Array(4, 6, 8, 10))
        noCard
    }
  }
}