package Bohnanza.view.viewBase

import Bohnanza.model.modelBase
import Bohnanza.model.modelBase.{dynamicGamedata, gamedata}
import Bohnanza.{model, view}
import Bohnanza.view.PlayerOut
import Bohnanza.view.viewBase.FXGUi.plantInPlantfield
import Bohnanza.view.viewBase.{FieldsCase, GUICards}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.{HBox, Priority, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.text.Font

class PlayerOutClass extends PlayerOut {
  override def playerScene: VBox = {
    new VBox {
      spacing = 5
      padding = Insets(5)
      alignment = Pos.TopLeft
      children = Seq(
        new VBox {
          spacing = 5
          padding = Insets(5)
          alignment = Pos.TopLeft
          children = Seq(
            playerInfo,
            handCards,
            {if(dynamicGamedata.playingPlayer.get.plantfield1.isEmpty) {
              plantFields(1)
            } else if(modelBase.dynamicGamedata.playingPlayer.get.plantfield1.nonEmpty){
              plantFields(2)
            } else {
              plantFields(3)
            }

            }
          )
        },
        undoButton
      )
    }
  }

  override def playerInfo: HBox = {
    new HBox {
      spacing = 5
      alignment = Pos.Center
      children = Seq(
        new Label(s"Spieler: ${modelBase.dynamicGamedata.playingPlayer.get.playerName}") {
          font = Font.font("Arial", 18)
          textFill = Color.DarkGreen
        },
        new Label(s"${gamedata.coinsString} ${modelBase.dynamicGamedata.playingPlayer.get.gold}") {
          font = Font.font("Arial", 18)
        }
      )
    }
  }

  override def handCards: VBox = {
    new VBox {
      spacing = 2
      alignment = Pos.TopLeft
      style = "-fx-border-color: black; -fx-border-width: 1;"
      maxWidth = 600
      children = modelBase.dynamicGamedata.playingPlayer.get.playerHand.map { card =>
        new VBox(GUICards().getCardPanel(card)) {
          spacing = 2
          maxWidth = 600
          hgrow = Priority.Never
        }
      }
    }
  }

  override def plantFields(fieldNumber: Int): VBox = {
    fieldNumber match {
      case 0 => new VBox {
        spacing = 2
        children = Seq(new Label("Feld") {
          font = Font.font("Arial", 18)
        })
      }
      case 1 => FieldsCase.plantField(1)
      case 2 => FieldsCase.plantField(2)
      case 3 => FieldsCase.plantField(3)
      case _ => new VBox {
        spacing = 2
        children = Seq(new Label("Invalid field number") {
          font = Font.font("Arial", 18)
        })
      }
    }
  }

  override def undoButton: Button = {
    new Button("Undo") {
      font = Font.font("Arial", 18)
      onAction = _ => {

      }
    }
  }
}