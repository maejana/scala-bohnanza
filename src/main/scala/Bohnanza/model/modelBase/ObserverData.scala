package Bohnanza.model.modelBase

import Bohnanza.controller.{ObserverTrait, SubjectTrait}

object ObserverData extends SubjectTrait {
  var observers = List[ObserverTrait]()
  def updateCards(): Unit = {
    notifyObservers()
  }
  override def notifyObservers(): Unit = {
    observers.foreach(_.update())

  }
  override def addObserver(observer: ObserverTrait): Unit = {
    observers ::= observer
  }
}