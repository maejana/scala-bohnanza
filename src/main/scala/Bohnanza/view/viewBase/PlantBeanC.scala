package Bohnanza.view.viewBase

import Bohnanza.controller.controllerBase
import Bohnanza.controller.controllerBase.{Utility, playerState}
import Bohnanza.model.modelBase
import Bohnanza.model.modelBase.{card, dynamicGamedata}
import Bohnanza.{controller, model}
import Bohnanza.view.PlantBeanInView

object PlantBeanC {
  case class PlantNone() extends PlantBeanInView{
    override def plantBean(): Unit = {
      Utility().selectPlayer()
      playerState().handle(dynamicGamedata.playingPlayer)
      FXGUi.stage.scene = new scalafx.scene.Scene(FXGUi.spielerRunde())
    }
  }
  case class PlantOne() extends PlantBeanInView {
    override def plantBean(): Unit = {
      model.modelBase.dynamicGamedata.cardsToPlant.clear()
      model.modelBase.dynamicGamedata.cardsToPlant += modelBase.dynamicGamedata.playingPlayer.get.playerHand(0)
      if(controllerBase.Utility().isPlantable(modelBase.dynamicGamedata.playingPlayer, model.modelBase.dynamicGamedata.cardsToPlant(0))){
        controllerBase.Utility().plantPreperation(model.modelBase.dynamicGamedata.playingPlayer)
      }
    
      FXGUi.stage.scene = new scalafx.scene.Scene(FXGUi.playerOut())
      FXGUi.stage.maximized = true
    }
  }
  
  case class PlantTwo() extends PlantBeanInView {
    override def plantBean(): Unit = {
      model.modelBase.dynamicGamedata.cardsToPlant.clear()
      model.modelBase.dynamicGamedata.cardsToPlant += modelBase.dynamicGamedata.playingPlayer.get.playerHand(0)
      model.modelBase.dynamicGamedata.cardsToPlant += modelBase.dynamicGamedata.playingPlayer.get.playerHand(1)
      if (controllerBase.Utility().isPlantable(modelBase.dynamicGamedata.playingPlayer, model.modelBase.dynamicGamedata.cardsToPlant(0)) &&
        controllerBase.Utility().isPlantable(modelBase.dynamicGamedata.playingPlayer, model.modelBase.dynamicGamedata.cardsToPlant(1))) {
        controllerBase.Utility().plant(model.modelBase.dynamicGamedata.cardsToPlant(0), modelBase.dynamicGamedata.playingPlayer)
        controllerBase.Utility().plant(model.modelBase.dynamicGamedata.cardsToPlant(1), modelBase.dynamicGamedata.playingPlayer)
        FXGUi.stage.scene = new scalafx.scene.Scene(FXGUi.playerOut())
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
