package Bohnanza.view
import Bohnanza.model

import scala.util.{Failure, Success, Try}
import scala.language.postfixOps

object playerInput {
  def keyListener(): Unit = {
    model.dynamicGamedata.readerThreadPlant1or2 = new Thread(() => {
      val input = Try(scala.io.StdIn.readLine()) match
        case Success(value) => if (value == "2") {
          model.dynamicGamedata.plant1or2 = 2
        } else if(value == "1") {
          model.dynamicGamedata.plant1or2 = 1
        } else if(value == "0"){
          model.dynamicGamedata.plant1or2 = 0
        } else if(value == "M"){
          handleInput.handleInputF(model.dynamicGamedata.playingPlayer)
        }
        case _ => println(model.gamedata.keineKorrekteNR)
          keyListener()
    })
    model.dynamicGamedata.readerThreadPlant1or2.start()

    model.dynamicGamedata.readerThreadPlant1or2.join()
  }
  def playercount(): Unit={
    readConsoleThread()
    println(model.dynamicGamedata.playerCount)
  }
  def playername(): String = {
    val name = readNameConsoleThread()
    name
  }

  def readConsoleThreadPlant1or2(): Unit = {
    model.dynamicGamedata.readerThread = new Thread(() => {
      val input = Try(scala.io.StdIn.readInt()) match
        case Success(value) => if (value > 2) {
          model.dynamicGamedata.plant1or2 = 2
        } else {
          model.dynamicGamedata.plant1or2 = 1
        }
        case _ => -1
    })
    // Starte den Reader-Thread
    model.dynamicGamedata.readerThread.start()

    // Warte, bis der Reader-Thread beendet ist
    model.dynamicGamedata.readerThread.join()

    // Gebe das Ergebnis zurück (oder einen Standardwert, falls keine Eingabe erfolgt ist)
    // -1 als Default, falls keine Eingabe erfolgte
  }

  def readConsoleThread(): Unit = {
    model.dynamicGamedata.readerThread = new Thread(() => {
      val input = Try(scala.io.StdIn.readInt()) match
        case Success(value) => model.dynamicGamedata.playerCount = value
        case _ => -1
    })
      // Starte den Reader-Thread
    model.dynamicGamedata.readerThread.start()

    // Warte, bis der Reader-Thread beendet ist
    model.dynamicGamedata.readerThread.join()

    // Gebe das Ergebnis zurück (oder einen Standardwert, falls keine Eingabe erfolgt ist)
    // -1 als Default, falls keine Eingabe erfolgte
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
