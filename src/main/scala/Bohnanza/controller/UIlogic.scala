package Bohnanza.controller

import Bohnanza.model
import Bohnanza.model.gameDataFunc

import scala.collection.mutable.ArrayBuffer
import scala.language.postfixOps
import scala.util.Random
import org.jline.terminal.{Terminal, TerminalBuilder}
import org.jline.keymap.{BindingReader, KeyMap}


object UIlogic{
  val terminal: Terminal = TerminalBuilder.builder().system(true).build()
  def weightedRandom(): model.card = {
    var allcards = ArrayBuffer[model.card]()
    for(i <- 1 to model.gamedata.cards.size){
      for(h <- 1 to model.gamedata.cards(i-1).weightCount)
      allcards.addOne(model.gamedata.cards(i-1))
    }
    val rand = Random.nextInt(allcards.size)
    allcards(rand).beanName match
      case "Blaue"=> model.gamedata.cards(0).weightCount -= 1
      case "Feuer" => model.gamedata.cards(1).weightCount -= 1
      case "Sau" => model.gamedata.cards(2).weightCount -= 1
      case "Brech" => model.gamedata.cards(3).weightCount -= 1
      case "Soja" => model.gamedata.cards(4).weightCount -= 1
      case "Augen" => model.gamedata.cards(5).weightCount -= 1
      case "Rote" => model.gamedata.cards(6).weightCount -= 1
      case "Garten" => model.gamedata.cards(7).weightCount -= 1

    allcards(rand)
  }
  def plantSelectString(player: model.player): String = {
    var s: String = ""
    s += model.gamedata.selectPlantCard
    s += model.gameDataFunc.playerHandToString(player.playerHand)
    s
  }
}
