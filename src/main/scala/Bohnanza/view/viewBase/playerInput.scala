package Bohnanza.view.viewBase

import Bohnanza.model
import Bohnanza.model.modelBase
import Bohnanza.model.modelBase.{dynamicGamedata, gamedata}
import Bohnanza.view.viewBase.handleInput

import scala.language.postfixOps
import scala.util.{Failure, Success, Try}

object playerInput {
  def keyListener(): Unit = {
    dynamicGamedata.readerThreadPlant1or2 = new Thread(() => {
      val input = Try(scala.io.StdIn.readLine()) match
        case Success(value) => if (value == "2") {
          modelBase.dynamicGamedata.plant1or2 = 2
        } else if(value == "1") {
          modelBase.dynamicGamedata.plant1or2 = 1
        } else if(value == "0"){
          modelBase.dynamicGamedata.plant1or2 = 0
        } else if(value == "M"){
          handleInput.handleInputF(modelBase.dynamicGamedata.playingPlayer)
        }
        case _ => println(gamedata.keineKorrekteNR)
          keyListener()
    })
    modelBase.dynamicGamedata.readerThreadPlant1or2.start()

    modelBase.dynamicGamedata.readerThreadPlant1or2.join()
  }
  def playercount(): Unit={
    readConsoleThread()
    println(modelBase.dynamicGamedata.playerCount)
  }
  def playername(): String = {
    val name = readNameConsoleThread()
    name
  }

  def readConsoleThreadPlant1or2(): Unit = {
    modelBase.dynamicGamedata.readerThread = new Thread(() => {
      val input = Try(scala.io.StdIn.readInt()) match
        case Success(value) => if (value > 2) {
          modelBase.dynamicGamedata.plant1or2 = 2
        } else {
          modelBase.dynamicGamedata.plant1or2 = 1
        }
        case _ => -1
    })
    // Starte den Reader-Thread
    modelBase.dynamicGamedata.readerThread.start()

    // Warte, bis der Reader-Thread beendet ist
    modelBase.dynamicGamedata.readerThread.join()

    // Gebe das Ergebnis zurück (oder einen Standardwert, falls keine Eingabe erfolgt ist)
    // -1 als Default, falls keine Eingabe erfolgte
  }

  def readConsoleThread(): Unit = {
    modelBase.dynamicGamedata.readerThread = new Thread(() => {
      val input = Try(scala.io.StdIn.readInt()) match
        case Success(value) => modelBase.dynamicGamedata.playerCount = value
        case _ => -1
    })
      // Starte den Reader-Thread
    modelBase.dynamicGamedata.readerThread.start()

    // Warte, bis der Reader-Thread beendet ist
    modelBase.dynamicGamedata.readerThread.join()

    // Gebe das Ergebnis zurück (oder einen Standardwert, falls keine Eingabe erfolgt ist)
    // -1 als Default, falls keine Eingabe erfolgte
  }


  def readNameConsoleThread(): String = {
    @volatile var input: String = ""

    modelBase.dynamicGamedata.NameReaderThread = new Thread(() => {
      input = Try(scala.io.StdIn.readLine()) match
        case Success(value) => value
        case _ => ""
    })

      // Starte den Reader-Thread
    modelBase.dynamicGamedata.NameReaderThread.start()

    // Warte, bis der Reader-Thread beendet ist
    modelBase.dynamicGamedata.NameReaderThread.join()

    // Gebe das Ergebnis zurück (oder einen Standardwert, falls keine Eingabe erfolgt ist)
    input
  }
}
