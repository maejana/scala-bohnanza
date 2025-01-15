package Bohnanza.view.viewBase

import Bohnanza.model
import Bohnanza.model.modelBase
import Bohnanza.model.modelBase.dynamicGamedata
import FXGUi.{boogalooFont, plantInPlantfield}
import Bohnanza.view.Fields
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.Label
import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.text.Font

object FieldsCase {


  case class OneField() extends Fields {
    override def plantField(): VBox = {
      new VBox {
        style = "-fx-background-color: #F5F5DC;"
        spacing = 5
        alignment = Pos.Center
        children = Seq(
          new Label("Plant Field 1") {
            font = boogalooFont
          },
          plantInPlantfield(dynamicGamedata.playingPlayer.get.plantfield1.headOption.map(_.beanName).getOrElse(""))
        )
      }
    }
  }

  case class TwoFields() extends Fields {

    override def plantField(): VBox = {
      new VBox {

        children = Seq(
          new VBox {
            style = "-fx-background-color: #F5F5DC;"
            spacing = 5
            alignment = Pos.Center
            children = Seq(
              new Label("Plant Field 1") {
                font = boogalooFont
              },
              plantInPlantfield(modelBase.dynamicGamedata.playingPlayer.get.plantfield1.headOption.map(_.beanName).getOrElse(""))
            )
          },
          new VBox {
            style = "-fx-background-color: brown;"
            spacing = 5
            alignment = Pos.Center
            children = Seq(
              new Label("Plant Field 2") {
                font = boogalooFont
              },
              plantInPlantfield(modelBase.dynamicGamedata.playingPlayer.get.plantfield2.headOption.map(_.beanName).getOrElse(""))
            )
          }
        )
      }
    }
  }

  case class ThreeFields() extends Fields {
    override def plantField(): VBox = {
      new VBox {
        style = "-fx-background-color: #F5F5DC;"
        children = Seq(
          new VBox {
            spacing = 5
            alignment = Pos.Center
            children = Seq(
              new Label("Plant Field 1") {
                font = boogalooFont
              },
              plantInPlantfield(modelBase.dynamicGamedata.playingPlayer.get.plantfield1.headOption.map(_.beanName).getOrElse(""))
            )
          },
          new VBox {
            style = "-fx-background-color: #F5F5DC;"
            spacing = 5
            alignment = Pos.Center
            children = Seq(
              new Label("Plant Field 2") {
                font = boogalooFont
              },
              plantInPlantfield(modelBase.dynamicGamedata.playingPlayer.get.plantfield2.headOption.map(_.beanName).getOrElse(""))
            )
          },
          new VBox {
            style = "-fx-background-color: #F5F5DC;"
            spacing = 5
            alignment = Pos.Center
            children = Seq(
              new Label("Plant Field 3") {
                font = boogalooFont
              },
              plantInPlantfield(modelBase.dynamicGamedata.playingPlayer.get.plantfield3.headOption.map(_.beanName).getOrElse(""))
            )
          }
        )
      }
      
    }
  }
  def plantField(i: Int): VBox = {
    i match {
      case 1 => OneField().plantField()
      case 2 => TwoFields().plantField()
      case 3 => ThreeFields().plantField()
    }
  }
}


