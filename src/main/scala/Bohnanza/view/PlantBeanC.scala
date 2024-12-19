package Bohnanza.view

import Bohnanza.model
import Bohnanza.view.PlantBeanInView
import Bohnanza.controller

object PlantBeanC {
  case class PlantNone() extends PlantBeanInView{
    override def plantBean(): Unit = {
      controller.Utility.selectPlayer()
      controller.playerState.handle(model.dynamicGamedata.playingPlayer)
      FXGUi.stage.scene = new scalafx.scene.Scene(FXGUi.spielerRunde())
    }
  }
  case class PlantOne() extends PlantBeanInView {
    override def plantBean(): Unit = {
      val cardToPlant: model.card = model.dynamicGamedata.playingPlayer.get.playerHand(0)
      controller.Utility.isPlantable(model.dynamicGamedata.playingPlayer, cardToPlant)
      FXGUi.stage.scene = new scalafx.scene.Scene(FXGUi.spielerRunde())
      FXGUi.stage.maximized = true
    }
  }
  
  case class PlantTwo() extends PlantBeanInView {
    override def plantBean(): Unit = {
      val cardToPlant1 = model.dynamicGamedata.playingPlayer.get.playerHand(0)
      val cardToPlant2 = model.dynamicGamedata.playingPlayer.get.playerHand(1)
      if (controller.Utility.isPlantable(model.dynamicGamedata.playingPlayer, cardToPlant1) &&
        controller.Utility.isPlantable(model.dynamicGamedata.playingPlayer, cardToPlant2)) {
        controller.Utility.plantDrawnCard(model.dynamicGamedata.playingPlayer, cardToPlant1)
        controller.Utility.plantDrawnCard(model.dynamicGamedata.playingPlayer, cardToPlant2)
        FXGUi.stage.scene = new scalafx.scene.Scene(FXGUi.spielerRunde())
        FXGUi.stage.maximized = true
      } else {
        println("Cannot plant these beans in the current fields.")
      }
    }
  }
  
  def plantBean(count: Int): Unit = {
    count match {
      case 0 => PlantNone().plantBean()
      case 1 => PlantOne().plantBean()
      case 2 => PlantTwo().plantBean()
    }
  }
  

}
