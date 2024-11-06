package Test.model

import scala.collection.mutable.ArrayBuffer

object gamedata {
  val welcome = "Welcome to Bohnanza"
  val playerCountQuestion = "Wie viele Spieler spielen?"
  val beans = Array("Blaue", "Feuer", "Sau", "Brech", "Soja", "Augen", "Rote", "Garten")
  val weights = Array(20, 18, 16, 14, 12, 10, 8, 6)
  val selectPlantCard = "Gib ein welche Bohne du Pflanzen möchtest und in welches Feld Bsp: Sau 1"
  val errorBeanNotInHand = "Diese Bohne ist nicht in deiner Hand!"
  var players = ArrayBuffer[player]()
}