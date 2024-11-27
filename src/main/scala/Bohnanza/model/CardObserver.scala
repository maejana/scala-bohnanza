package Bohnanza.model
import Bohnanza.controller.ObserverTrait

object CardObserver extends ObserverTrait {
  override def update(): Unit = {
    println("Cards have been updated!")
    // Weitere Logik zur Aktualisierung der Karten
  }
}