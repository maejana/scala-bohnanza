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
      button.peer.setLocation(900, 500)
      button.peer.setSize(10, 5)

      contents += new BorderPanel {
        add(button, BorderPanel.Position.North)
      }
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
        case ButtonClicked(`button`) => model.dynamicGamedata.NameReaderThread.interrupt()
          mainFrame.contents = SpielerRunde()
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
          model.dynamicGamedata.playingPlayer = controller.Utility.selectPlayer()
          contents -= buttonSave
          textField.editable = false
          mainFrame.repaint()
        }
      }
    }
  }



  def addPlayerViaTUI(Pname: String, nr: Int): Unit = {
    //pa.contents += new Label(nr + ".")
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
      border = BorderFactory.createEmptyBorder(10, 10, 10, 10)

      val combinedPanel = new BoxPanel(Orientation.Horizontal) {
        contents += playerOut() // Linker Bereich
        contents += Swing.HStrut(10) // Horizontaler Abstand
        contents += fields // Rechter Bereich
        xLayoutAlignment = 0.0
      }
      contents += combinedPanel

      contents += Swing.VStrut(10) // Vertikaler Abstand zum nächsten Element

      val question = new Label(model.gamedata.plantAmountQuestion) {
        font = new Font("Arial", 1, 24)
        yLayoutAlignment = 0.0
      }
      contents += question

      contents += Swing.VStrut(5) // Vertikaler Abstand zum nächsten Element

      val buttonPanel = new BoxPanel(Orientation.Horizontal) {
        val button1 = new Button("1")
        contents += button1
        val button2 = new Button("2")
        contents += button2
        listenTo(button1)
        listenTo(button2)
        reactions += {
          case ButtonClicked(`button1`) =>
            plantBean(1)
          case ButtonClicked(`button2`) =>
            plantBean(2)
        }
        xLayoutAlignment = 0.0
      }
      contents += buttonPanel

      mainFrame.validate()
      mainFrame.repaint()
    }
  }

  def plantBean(i: Int): Unit = {
    i match {
      case 1 =>
        model.dynamicGamedata.cardsToPlant += model.dynamicGamedata.playingPlayer.playerHand(0)
        controller.Utility.plantPreperation(model.dynamicGamedata.playingPlayer)
        val beanToplant = model.gameDataFunc.playerFieldToString(model.dynamicGamedata.cardsToPlant)
        mainFrame.contents = new BoxPanel(Orientation.Vertical) {
          contents += playerOut()
          contents += plantInPlantfield(beanToplant)
        }
      case 2 =>
        model.dynamicGamedata.cardsToPlant += model.dynamicGamedata.playingPlayer.playerHand(0)
        model.dynamicGamedata.cardsToPlant += model.dynamicGamedata.playingPlayer.playerHand(1)
        controller.Utility.plantPreperation(model.dynamicGamedata.playingPlayer)
        mainFrame.contents = new BoxPanel(Orientation.Vertical) {
          contents += playerOut()
          contents += plantInPlantfield(model.dynamicGamedata.cardsToPlant(0).toString)
          if (model.dynamicGamedata.playingPlayer.playerHand.size > 1) {
            contents += plantInPlantfield(model.dynamicGamedata.cardsToPlant(1).toString)
          }
        }
    }
    mainFrame.validate()
    mainFrame.repaint()
  }


  def plantInPlantfield(bean: String): Panel = {
    val panel = new BoxPanel(Orientation.Vertical) {
      border = BorderFactory.createLineBorder(java.awt.Color.BLACK)
      preferredSize = new Dimension(1000, 200)
      var plantfield = new Label(model.gamedata.plantfield)
      contents += plantfield
      var plantedBean = new Label(bean)
      contents += plantedBean
      var buttonNext = new Button(model.gamedata.continue)
      contents += buttonNext
      listenTo(buttonNext)
      reactions += {
        case ButtonClicked(buttonNext) => model.dynamicGamedata.playingPlayer = controller.Utility.selectPlayer()
          controller.playerState.handle(model.dynamicGamedata.playingPlayer)
          mainFrame.contents = SpielerRunde()
          repaint()
      }
      mainFrame.repaint()
      revalidate()
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

  def playerOut(): Panel = {
    new BoxPanel(Orientation.Vertical) {
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
      contents += Handkarten
      contents += PlayerHand
    }
  }



}
