package Bohnanza.model

case class player(name: String, hand: Array[String], field : String) {
  val playerName = name
  var playerHand = hand
  var playerField = field
  var plantfield1 = ""
  var plantfield2 = ""
  var plantfield3 = ""
  var gold = 0
}
