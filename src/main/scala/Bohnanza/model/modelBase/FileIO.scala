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
    dynamicGamedata.players = playersAttr

  }

  def load(): Unit = {
    loadPlayers
    loadDynamicGamedata()
  }

  def savePlayers(Players: ArrayBuffer[player]): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("player.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)
    for(Player: player <- Players){
      val xml = prettyPrinter.format(playerToXML(Player))
      pw.write(xml)
    }
    pw.close
  }

  def saveDynamicGamedata(): Unit = {
    val pw = new PrintWriter(new File("dynamicGamedata.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(dynamicGamedateToXML())
    pw.write(xml)
    pw.close
  }
  def saveXML(): Unit = {
    savePlayers(dynamicGamedata.players)
    saveDynamicGamedata()
  }

  def playerToXML(Player: player) = {
    <player>
      playerName={Player.playerName}
      playerhand={Player.playerHand}
      plantfield1={Player.plantfield1}
      plantfield2={Player.plantfield2}
      plantfield3={Player.plantfield3}
      gold={Player.gold}
      state={Player.state}
      lastMethodUsed={Player.lastMethodUsed}
    </player>
  }
  def dynamicGamedateToXML() = {
    <dynamicGamedata>
      players={dynamicGamedata.players}
      drawnCards={dynamicGamedata.drawnCards}
      playingPlayer={dynamicGamedata.playingPlayer}
      plantCount={dynamicGamedata.plantCount}
      playerNameBuffer={dynamicGamedata.playerNameBuffer}
      playerCount={dynamicGamedata.playerCount}
      readerThread={dynamicGamedata.readerThread}
      readerThreadPlant1or2={dynamicGamedata.readerThreadPlant1or2}
      NameReaderThread={dynamicGamedata.NameReaderThread}
      cardsToPlant={dynamicGamedata.cardsToPlant}
      playingPlayerID={dynamicGamedata.playingPlayerID}
      plant1or2={dynamicGamedata.plant1or2}
    </dynamicGamedata>
  }


  //Todo: alle Daten aus Player oder aus dynamicGamedata müssen als String in XML abgespeichert werden und später aus einem String wieder zur den ursprünglichen Datentypen Konvertiert und eingelesen werden.(Bsp: playersArray -> player -> playername(ist ein String) && playerhand -> alle fünfKartenNamen als String abspeichern und später daraus die fünf karten in eine playerhand zurück konvertieren)
}
