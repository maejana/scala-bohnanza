package Bohnanza.view

import Bohnanza.model
import Bohnanza.view.FXGUi.plantInPlantfield
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.Label
import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.text.Font

import Bohnanza.view.PlayerOut
import Bohnanza.view

class PlayerOutClass extends PlayerOut {
  override def playerScene: VBox = {
    new VBox {
      spacing = 5
      padding = Insets(10)
      alignment = Pos.TopLeft
      children = Seq(
        new VBox {
          spacing = 5
          padding = Insets(10)
          alignment = Pos.TopLeft
          children = Seq(
            playerInfo,
            handCards,
            {if(model.dynamicGamedata.playingPlayer.get.plantfield1.isEmpty) {
              plantFields(1)
            } else if(model.dynamicGamedata.playingPlayer.get.plantfield1.nonEmpty){
              plantFields(2)
            } else {
              plantFields(3)
            }
              
            }
          )
        }
      )
    }
  }

  override def playerInfo: HBox = {
    new HBox {
      spacing = 5
      alignment = Pos.Center
      children = Seq(
        new Label(s"Spieler: ${model.dynamicGamedata.playingPlayer.get.playerName}") {
          font = Font.font("Arial", 18)
          textFill = Color.DarkGreen
        },
        new Label(s"${model.gamedata.coinsString} ${model.dynamicGamedata.playingPlayer.get.gold}") {
          font = Font.font("Arial", 18)
        }
      )
    }
  }

  override def handCards: VBox = {
    new VBox {
      spacing = 5
      alignment = Pos.TopLeft
      style = "-fx-border-color: black; -fx-border-width: 1;"
      children = model.dynamicGamedata.playingPlayer.get.playerHand.map { card =>
        new VBox(GUICards().getCardPanel(card)) {
          spacing = 5
        }
      }
    }
  }

  override def plantFields(fieldNumber: Int): VBox = {
    fieldNumber match {
      case 0 => new VBox {
        spacing = 5
        children = Seq(new Label("Feld") {
          font = Font.font("Arial", 18)
        })
      }
      case 1 => FieldsCase.plantField(1)
      case 2 => FieldsCase.plantField(2)
      case 3 => FieldsCase.plantField(3)
      case _ => new VBox {
        spacing = 5
        children = Seq(new Label("Invalid field number") {
          font = Font.font("Arial", 18)
        })
      }
    }
  }
}