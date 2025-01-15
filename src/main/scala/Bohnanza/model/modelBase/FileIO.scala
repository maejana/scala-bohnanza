package Bohnanza.model.modelBase

import java.io.{File, PrintWriter}
import scala.collection.mutable.ArrayBuffer
import scala.xml.{NodeSeq, PrettyPrinter}

class FileIO {

  def loadPlayers: Unit = {

  }
  def loadDynamicGamedata(): Unit = {
    val file = scala.xml.XML.loadFile("dynamicGamedata.xml")
    val playersAttr = (file \\ "player" \ "@players")
    val players = playersAttr.text
    //...

    //Todo: Daten auslesen
    //Todo: methoden aufrufen um die Daten zu Konvertieren und anbzuspeichern...


  }

  def load(): Unit = {
    loadPlayers
    loadDynamicGamedata()
  }

  def save(Players : ArrayBuffer[player]): Unit = {
    val pw = new PrintWriter(new File("GameSave.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(dynamicGamedateToXML())
    pw.write(xml)
    for (Player: player <- Players) {
      val xml = prettyPrinter.format(playerToXML(Player))
      pw.write(xml)
    }
    pw.close
  }

  def playerToXML(Player: player) = {
    val playerarray = Player.toStringArray()
    <player>
      playerName={playerarray(0)}
      playerhand={playerarray(1)}
      plantfield1={playerarray(2)}
      plantfield2={playerarray(3)}
      plantfield3={playerarray(4)}
      gold={playerarray(5)}
      state={playerarray(6)}
      lastMethodUsed={playerarray(7)}
    </player>
  }
  def dynamicGamedateToXML() = {
    <dynamicGamedata>
      drawnCards={dynamicGamedata.ArrayBufferToString(dynamicGamedata.drawnCards)}
      playingPlayer={dynamicGamedata.playingPlayer.get.playerName}
      plantCount={dynamicGamedata.plantCount.toString}
      playerCount={dynamicGamedata.playerCount.toString}
      cardsToPlant={dynamicGamedata.ArrayBufferToString(dynamicGamedata.cardsToPlant)}
      playingPlayerID={dynamicGamedata.playingPlayerID.toString}
      plant1or2={dynamicGamedata.plant1or2.toString}
    </dynamicGamedata>
  }

  //Todo: alle Daten aus Player oder aus dynamicGamedata müssen aus einem String wieder zur den ursprünglichen Datentypen Konvertiert und eingelesen werden.(Bsp: playersArray -> player -> playername(ist ein String) && playerhand -> alle fünfKartenNamen als String abspeichern und später daraus die fünf karten in eine playerhand zurück konvertieren)
  //Done: alle Daten aus Player oder aus dynamicGamedata müssen als String in XML abgespeichert werden
}
