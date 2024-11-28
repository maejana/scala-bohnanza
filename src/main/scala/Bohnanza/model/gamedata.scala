package Bohnanza.model



import scala.collection.mutable.ArrayBuffer

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
  val weights = Array(20, 18, 16, 14, 12, 10, 8, 6)
  val price: ArrayBuffer[Int] = ArrayBuffer(2, 4, 6, 8)
  var cards = Array(card(beans(0),weights(0),price),card(beans(1),weights(1),price),card(beans(2),weights(2),price),card(beans(3),weights(3),price),card(beans(4),weights(4),price),card(beans(5),weights(5),price),card(beans(6),weights(6),price),card(beans(7),weights(7),price))
  var players = ArrayBuffer[player]()
  var drawnCards: ArrayBuffer[card] = ArrayBuffer[card]()
  var playingPlayer: Bohnanza.model.player = null
  
}