package Bohnanza.view.viewBase

import Bohnanza.controller.controllerBase
import Bohnanza.controller.controllerBase.{Utility, playerState}
import Bohnanza.model.modelBase
import Bohnanza.model.modelBase.{card, dynamicGamedata}
import Bohnanza.{controller, model}
import Bohnanza.view.PlantBeanInView
import Bohnanza.view.viewBase.FXGUi

object PlantBeanC {
  case class PlantNone() extends PlantBeanInView{
    override def plantBean(): Unit = {
      Utility.selectPlayer()
      playerState.handle(dynamicGamedata.playingPlayer)
      FXGUi.stage.scene = new scalafx.scene.Scene(FXGUi.spielerRunde())
    }
  }
  case class PlantOne() extends PlantBeanInView {
    override def plantBean(): Unit = {
      val cardToPlant: card = modelBase.dynamicGamedata.playingPlayer.get.playerHand(0)
      controllerBase.Utility.plantPreperation(model.modelBase.dynamicGamedata.playingPlayer)
      FXGUi.stage.scene = new scalafx.scene.Scene(FXGUi.spielerRunde())
      FXGUi.stage.maximized = true
    }
  }
  
  case class PlantTwo() extends PlantBeanInView {
    override def plantBean(): Unit = {
      val cardToPlant1 = modelBase.dynamicGamedata.playingPlayer.get.playerHand(0)
      val cardToPlant2 = modelBase.dynamicGamedata.playingPlayer.get.playerHand(1)
      if (controllerBase.Utility.isPlantable(modelBase.dynamicGamedata.playingPlayer, cardToPlant1) &&
        controllerBase.Utility.isPlantable(modelBase.dynamicGamedata.playingPlayer, cardToPlant2)) {
        controllerBase.Utility.plantPreperation(model.modelBase.dynamicGamedata.playingPlayer)
        controllerBase.Utility.plantPreperation(model.modelBase.dynamicGamedata.playingPlayer)
        controllerBase.Utility.plantDrawnCard(modelBase.dynamicGamedata.playingPlayer, cardToPlant1)
        controllerBase.Utility.plantDrawnCard(modelBase.dynamicGamedata.playingPlayer, cardToPlant2)
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
