package Bohnanza.model
import Bohnanza.controller.ObserverTrait

object CardObserver extends ObserverTrait {
  override def update(): Unit = {
    println("Cards wurden geupdated!")
    // Weitere Logik zur Aktualisierung der Karten
  }
}