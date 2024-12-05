package Bohnanza.view

import Bohnanza.{model, view, controller}

import scala.swing.*
import scala.swing.event.ButtonClicked
import scala.swing.event._
import javax.swing.BorderFactory

object GUI extends SimpleSwingApplication{
 def guistart(): Unit = {
   val guiThread = new Thread(new Runnable {
     override def run(): Unit = {
       top.visible = true
     }
   })
   guiThread.start()
 }

  var pa: BoxPanel = new BoxPanel(Orientation.Vertical) {
  }
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
        case ButtonClicked(`button`) => val selectedItem = dropdown.selection.item
          if (selectedItem == "1") NamenEingeben(1)
          else if (selectedItem == "2") NamenEingeben(2)
          else if (selectedItem == "3") NamenEingeben(3)
          else NamenEingeben(4)
          revalidate()
          mainFrame.repaint()
      }
    }
  }
  def NamenEingeben(x: Int): Unit = {
    pa.contents.clear()
    model.dynamicGamedata.playerCount = x
    for (i <- 0 to x - 1) {
      pa.contents += addPlayer(i)
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
    model.dynamicGamedata.playerNameBuffer.clear()
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
          model.dynamicGamedata.playerNameBuffer += textField.text
          model.gameDataFunc.initPlayer(model.dynamicGamedata.playerNameBuffer.toString())
          model.dynamicGamedata.playingPlayer = controller.Utility.selectPlayer(Nr)
          println(model.dynamicGamedata.playingPlayer)
          contents -= buttonSave
          textField.editable = false
          revalidate()
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

      val playingPlayer  = new Label("Spieler: " + model.dynamicGamedata.playingPlayer.playerName) {
        font = new Font("Arial", 1, 36)
      }
      contents += playingPlayer
      val coins = new Label(model.gamedata.coinsString + model.dynamicGamedata.playingPlayer.gold) {
        font = new Font("Arial", 1, 24)
      }
      contents += coins
      val Handkarten = new Label(model.gamedata.handcards) {
        font = new Font("Arial", 1, 24)
      }
      contents += PlayerHand
      contents  += new Label(model.gamedata.plantAmountQuestion) 
      val button = new Button("1")
      contents += button
      val button2 = new Button("2")
      contents += button2
      listenTo(button) 
      listenTo(button2)
      reactions += {
        case ButtonClicked(`button`) =>
        case ButtonClicked(`button2`) => 
      }
    }


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