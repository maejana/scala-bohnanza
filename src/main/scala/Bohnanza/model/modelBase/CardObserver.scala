package Bohnanza.model.modelBase

import Bohnanza.controller.ObserverTrait

class CardObserver extends ObserverTrait {
  override def update(): Unit = {
    println("Cards wurden geupdated!")
    // Weitere Logik zur Aktualisierung der Karten
  }
}