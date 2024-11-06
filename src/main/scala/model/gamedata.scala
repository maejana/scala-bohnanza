package Test.model

import scala.collection.mutable.ArrayBuffer

object gamedata {
  val welcome = "Welcome to Bohnanza"
  val playerCountQuestion = "Wie viele Spieler spielen?"
  val beans = Array("Blaue", "Feuer", "Sau", "Brech", "Soja", "Augen", "Rote", "Garten")
  val weights = Array(20, 18, 16, 14, 12, 10, 8, 6)
  var cards = Array(card(beans(0),weights(0)),card(beans(1),weights(1)),card(beans(2),weights(2)),card(beans(3),weights(3)),card(beans(4),weights(4)),card(beans(5),weights(5)),card(beans(6),weights(6)),card(beans(7),weights(7)))
  val selectPlantCard = "Gib ein welche Bohne du Pflanzen möchtest und in welches Feld Bsp: Sau 1"
  val errorBeanNotInHand = "Diese Bohne ist nicht in deiner Hand!"
  var players = ArrayBuffer[player]()
}