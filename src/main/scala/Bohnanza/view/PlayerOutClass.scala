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

class PlayerOutClass extends PlayerOut{
  override def playerScene: VBox = {
    new VBox {
      new VBox {
        spacing = 5
        padding = Insets(10)
        alignment = Pos.TopLeft

        children = Seq (
          playerInfo,
          handCards,
          plantFields(0),
          plantFields(1),
          plantFields(2),
          plantFields(3)
          
        )
      }
        
      }
    }

  override def playerInfo: HBox = {
    new HBox {
      spacing = 5
      alignment = Pos.Center
      children = Seq(
        new Label(s"Spieler: ${model.dynamicGamedata.playingPlayer}") {
          font = Font.font("Arial", 24)
          textFill = Color.DarkGreen
        },
        new Label(model.gamedata.coinsString + model.dynamicGamedata.playingPlayer.get.gold) {
          font = Font.font("Arial", 24)
        }
      )
    }
  }

  override def handCards: VBox = {
    new VBox {
      spacing = 0
      alignment = Pos.BottomRight
      style = "-fx-border-color: black; -fx-border-width: 2;"
      children = model.dynamicGamedata.playingPlayer.get.playerHand.map { card =>
        new VBox(GUICards().getCardPanel(card)) {
        }
      }
    }
  }


  override def plantFields(fieldNumber: Int): VBox = {
    fieldNumber match {
      case 0 => new VBox {
        children = Seq(new Label("Feld"))
      }
      case 1 => FieldsCase.plantField(1)
      case 2 => FieldsCase.plantField(2)
      case 3 => FieldsCase.plantField(3)
      case _ => new VBox {
        children = Seq(new Label("Invalid field number"))
      }
    }
  }
}


     

