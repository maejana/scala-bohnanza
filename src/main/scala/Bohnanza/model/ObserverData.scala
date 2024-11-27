package Bohnanza.model

import Bohnanza.controller.{ObserverTrait, SubjectTrait}

object ObserverData extends SubjectTrait {
  var observers = List[ObserverTrait]()
  def updateCards(newCards: Array[card]): Unit = {
    gamedata.cards = newCards
    notifyObservers()
  }
  override def notifyObservers(): Unit = {
    observers.foreach(_.update())

  }
  override def addObserver(observer: ObserverTrait): Unit = {
    observers ::= observer
  }
}