package Bohnanza.view

import Bohnanza.{controller, model, view}

import scala.swing.*
import scala.swing.event.ButtonClicked
import scala.swing.event.*
import javax.swing.BorderFactory
import scala.language.postfixOps

object GUI extends SimpleSwingApplication{
 def guistart(): Unit = {
   val guiThread = new Thread(new Runnable {
     override def run(): Unit = {
       top.visible = true
     }
   })
   guiThread.start()
 }

  var fields: BoxPanel = new BoxPanel(Orientation.Vertical){}
  var pa: BoxPanel = new BoxPanel(Orientation.Vertical) {}
  var Nr: Int = 0
  val mainFrame = new MainFrame {
    title = "Bohnanza"
    contents = Startseite()
    size = new Dimension(1920, 1000)
  }

  def top: MainFrame = mainFrame
  def Startseite(): Panel = {
    new BoxPanel(Orientation.Vertical) {
      minimumSize = new Dimension(1920, 1000)
      contents += new Label(model.gamedata.bohnanza)
      val button = new Button(model.gamedata.play)
      contents += button
      listenTo(button)
      reactions += {
        case ButtonClicked(`button`) => mainFrame.contents = NamenEingebenSeite()
          revalidate()
          top.repaint()
      }
    }
  }

  def SpieleranzahlEingeben(): Unit = {
    new BoxPanel(Orientation.Vertical){
      pa.contents += new Label("Wie viele Spieler spielen?")
      val dropdown =  new ComboBox(Seq("2", "3", "4", "5"))
      dropdown.maximumSize = new Dimension(100,30)
      pa.contents += dropdown
      val button = new Button("Hinzufügen")
      pa.contents += button
      listenTo(button)
      reactions += {
        case ButtonClicked(`button`) => model.dynamicGamedata.readerThread.interrupt()
          val selectedItem = dropdown.selection.item
          if (selectedItem == "2") NamenEingeben(2)
          else if (selectedItem == "3") NamenEingeben(3)
          else if (selectedItem == "4") NamenEingeben(4)
          else NamenEingeben(5)
          revalidate()
          mainFrame.repaint()
      }
    }
  }

  def NamenEingeben(x: Int): Unit = {
    pa.contents.clear()
    model.dynamicGamedata.playerCount = x
    for (i <- 0 to x-1) {
      pa.contents += addPlayer(i)
      model.dynamicGamedata.NameReaderThread.interrupt()
    }
    pa.revalidate()
    pa.repaint()
  }

  def NamenEingebenSeite(): Panel = {
    new BoxPanel(Orientation.Vertical) {
      preferredSize = new Dimension(1920, 1000)
      contents += new Label(model.gamedata.bohnanza)
      SpieleranzahlEingeben()
      contents += pa
      val button = new Button(model.gamedata.continue)
      contents += button
      listenTo(button)
      reactions += {
        case ButtonClicked(`button`) => mainFrame.contents = SpielerRunde()
          revalidate()
          pa.repaint()
          mainFrame.repaint()
      }
    }
  }

  def addPlayer(nr : Int): Panel = {
    Nr = nr+1
    new BoxPanel(Orientation.Vertical){
      contents += new Label(Nr + ".")
      val textField = new TextField()
      textField.columns = 1
      textField.maximumSize = new Dimension(1000,30)
      val buttonSave = new Button("speichern")
      contents += textField
      contents += buttonSave
      listenTo(buttonSave)
      reactions += {
        case ButtonClicked(`buttonSave`) => if (!textField.text.isEmpty) {
          model.gameDataFunc.initPlayer(textField.text)
          model.dynamicGamedata.NameReaderThread.interrupt()
          model.dynamicGamedata.playingPlayer = controller.Utility.selectPlayer(Nr - model.dynamicGamedata.playerCount)
          contents -= buttonSave
          textField.editable = false
          mainFrame.repaint()
        }
      }
    }
  }



  def addPlayerViaTUI(Pname: String, nr: Int): Unit = {
    pa.contents += new Label(nr + ".")
    Nr += 1
    val textField = new TextField()
    textField.columns = 1
    textField.maximumSize = new Dimension(1000, 30)
    textField.text = Pname
    textField.editable = false
    pa.contents += textField
    pa.repaint()
    mainFrame.repaint()
  }

  def SpielerRunde(): Panel = {
    var i = 0
    new BoxPanel(Orientation.Vertical) {
      preferredSize = new Dimension(1920, 1000)
      contents += new Label("Spieler Runde")

      val playerName: String = model.dynamicGamedata.playingPlayer.playerName
      val playingPlayer = new Label(s"Spieler: $playerName") {
        font = new Font("Arial", 1, 36)
      }
      contents += playingPlayer

      val coins = new Label(model.gamedata.coinsString + ": " + model.dynamicGamedata.playingPlayer.gold) {
        font = new Font("Arial", 1, 24)
      }
      contents += coins
      val Handkarten: Label = new Label(model.gamedata.handcards) {
        font = new Font("Arial", 1, 24)
      }
      contents += PlayerHand
      val question = new Label(model.gamedata.plantAmountQuestion) {
        font = new Font("Arial", 1, 24)
      }
      contents += question

      val button = new Button("1")
      contents += button
      val button2 = new Button("2")
      contents += button2
      contents += fields
      listenTo(button)
      listenTo(button2)
      reactions += {
        case ButtonClicked(`button`) => plantBean(1)
          revalidate()
          mainFrame.repaint()
        case ButtonClicked(`button2`) => plantBean(2)
          revalidate()
          mainFrame.repaint()
      }
    }
  }

  def plantBean(i: Int): Unit = {

    if (model.dynamicGamedata.playingPlayer.playerHand.size >= i) {
      i match {
        case 1 =>
          controller.Utility.plantAllSelectedCards(1)
          mainFrame.contents = plantInPlantfield(model.dynamicGamedata.playingPlayer.playerHand(0).toString)
        case 2 =>
          controller.Utility.plantAllSelectedCards(2)
          mainFrame.contents = new BoxPanel(Orientation.Vertical) {
            contents += plantInPlantfield(model.dynamicGamedata.playingPlayer.playerHand(0).toString)
            if (model.dynamicGamedata.playingPlayer.playerHand.size > 1) {
              contents += plantInPlantfield(model.dynamicGamedata.playingPlayer.playerHand(1).toString)
            }
            revalidate()
          }
      }
      mainFrame.repaint()
    }
  }

    
  def plantField(): Panel = {
    new BoxPanel(Orientation.Vertical) {
      border = BorderFactory.createLineBorder(java.awt.Color.BLACK)
      preferredSize = new Dimension(1920, 1000)
      var plantfield = new Label(model.gamedata.plantfield)
      contents += plantfield
      revalidate()
      mainFrame.repaint()
    }
  }

  def plantInPlantfield(bean: String): Panel = {
    val panel = new BoxPanel(Orientation.Vertical) {
      contents += plantField()
      var plantedBean = new Label(bean)
      revalidate()
      mainFrame.repaint()
    }
    panel
   
  }

  def PlayerHand: Panel = {
    new BoxPanel(Orientation.Vertical) {
      border = BorderFactory.createLineBorder(java.awt.Color.BLACK)
      preferredSize = new Dimension(1500, 500)
      model.dynamicGamedata.playingPlayer.playerHand.foreach { card =>
        contents += view.GUICards().getCardPanel(card)
      }
    }
  }
}
