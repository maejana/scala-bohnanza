package Bohnanza.view
import Bohnanza.model

import scala.util.{Failure, Success, Try}
import scala.language.postfixOps

object playerInput {
  def keyListener(): Int = {
    val key = scala.io.StdIn.readLine().strip()
    key match {
      case "0" => 0
      case "1" => 1
      case "2" => 2
      case "M" => handleInput.handleInputF(model.dynamicGamedata.playingPlayer)
        -1
      case _ => println(model.gamedata.keineKorrekteNR)
        keyListener()
    }
  }
  def playercount(): Unit={
    val count = readConsoleThread()
    if(model.dynamicGamedata.playerCount == 0) {
      model.dynamicGamedata.playerCount = count
    }
    println(model.dynamicGamedata.playerCount)
  }
  def playername(): String = {
    val name = readNameConsoleThread()
    name
  }

  def readConsoleThread(): Int = {
    @volatile var input: Int = 0

    model.dynamicGamedata.readerThread = new Thread(() => {
      input = Try(scala.io.StdIn.readInt()) match
        case Success(value) => value
        case _ => -1
    })
      // Starte den Reader-Thread
    model.dynamicGamedata.readerThread.start()

    // Warte, bis der Reader-Thread beendet ist
    model.dynamicGamedata.readerThread.join()

    // Gebe das Ergebnis zurück (oder einen Standardwert, falls keine Eingabe erfolgt ist)
    input // -1 als Default, falls keine Eingabe erfolgte
  }


  def readNameConsoleThread(): String = {
    @volatile var input: String = ""

    model.dynamicGamedata.NameReaderThread = new Thread(() => {
      input = Try(scala.io.StdIn.readLine()) match
        case Success(value) => value
        case _ => ""
    })

      // Starte den Reader-Thread
    model.dynamicGamedata.NameReaderThread.start()

    // Warte, bis der Reader-Thread beendet ist
    model.dynamicGamedata.NameReaderThread.join()

    // Gebe das Ergebnis zurück (oder einen Standardwert, falls keine Eingabe erfolgt ist)
    input
  }
}
