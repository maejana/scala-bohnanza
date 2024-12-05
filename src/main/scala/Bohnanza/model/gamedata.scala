package Bohnanza.model

import scala.collection.mutable.{ArrayBuffer, ArrayStack}

object gamedata {
  val welcome = "Welcome to Bohnanza\n"
  val menu = "Druecke M um das Menue anzuzeigen"
  val playerCountQuestion = "Wie viele Spieler spielen?"
  val beans = Array("Blaue", "Feuer", "Sau", "Brech", "Soja", "Augen", "Rote", "Garten")
  val selectPlantCard = "Gib ein welche Bohne du Pflanzen moechtest Bsp: Sau: \n"
  val errorBeanNotInHand = "Diese Bohne ist nicht in deiner Hand!"
  val errorPlantingField = "Diese Bohnenfeld existiert nicht!"
  val errorInputNotInt = "Eingabe ist keine Integer zwischen 1 und 2 \n"
  val plantAmountQuestion = "Pflanze eine oder zwei Bohnen an, indem du 1 oder 2 eingibst."
  val drawCardText = "Waehle wie viele Bohnen du behaelst indem du 0, 1 oder 2 eingibst."
  val newOrOldField = "Du hast diese Bohne schonmal angepflanz, willst du sie im gleichen Feld anpflanzen? Gib die Nummer des Feldes, auf welches du pflanzen willst an."
  var drawnCardName = "Gib den Namen der Bohne ein die du anpflanzen moechtest."
  val keineKorrekteNR: String = "Das Falsch!"
  val keineKorrekteBohne: String = "Das keine Bohne!"
  val undoSuccessful: String = "Undo successful."
  val redoSuccessful: String = "Redo successful."
  val exiting: String = "Exiting..."
  val Undo: String = "U. Undo"
  val Redo: String = "R. Redo"
  val Exit: String = "E. Exit"
  val bohnanza: String = "Bohnanza"
  val play: String = "Spielen"
  val continue: String = "Weiter"
  val weights = Array(20, 18, 16, 14, 12, 10, 8, 6)
  val priceSoja: Array[Int] = Array(2, 4, 6, 7)
  val priceFeuer: Array[Int] = Array(3, 6, 8, 9)
  val priceBlaue: Array[Int] = Array(4, 6, 8, 10)
  val priceSau: Array[Int] = Array(3, 5, 7, 8)
  val priceAugen: Array[Int] = Array(2, 4, 5, 6)
  val priceBrech: Array[Int] = Array(3, 5, 6, 7)
  val priceRote: Array[Int] = Array(2, 3, 4, 5)
  val priceGarten: Array[Int] = Array(2, 3)
  val cards = Array(card(beans(0),weights(0), priceBlaue),card(beans(1),weights(1),priceFeuer),card(beans(2),weights(2),priceSau),card(beans(3),weights(3),priceBrech),card(beans(4),weights(4),priceSoja),card(beans(5),weights(5),priceAugen),card(beans(6),weights(6),priceRote),card(beans(7),weights(7),priceGarten))
}