package Bohnanza.view

import Bohnanza.{model, view}

import scala.swing.*
import scala.swing.event.ButtonClicked

object GUI extends SimpleSwingApplication{
 def guistart(): Unit = {
   val guiThread = new Thread(new Runnable {
     override def run(): Unit = {
       top.visible = true
     }
   })
   guiThread.start()
 }

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
        case ButtonClicked(`button`) => mainFrame.contents = NamenEingeben()
          revalidate()
          top.repaint()
      }
    }
  }
  def NamenEingeben(): Panel = {
    new BoxPanel(Orientation.Vertical) {
      preferredSize = new Dimension(1920, 1000)
      contents += new Label(model.gamedata.bohnanza)
      contents += addPlayer(0)
      val button = new Button(model.gamedata.continue)
      contents += button
      listenTo(button)
      reactions += {
        case ButtonClicked(`button`) => mainFrame.contents = SpielerRunde()
          revalidate()
          top.repaint()
      }
    }
  }
  def addPlayer(nr : Int): Panel = {
    val Nr = nr+1
    new BoxPanel(Orientation.Vertical){
      contents += new Label(Nr + ".")
      val textField = new TextField()
      textField.columns = 1
      textField.maximumSize = new Dimension(1000,30)
      val buttonSave = new Button("speichern")
      val button = new Button("+")
      contents += textField
      contents += buttonSave
      contents += button
      listenTo(buttonSave)
      listenTo(button)
      reactions += {
        case ButtonClicked(`button`) => contents += addPlayer(Nr)
          contents -= button
          revalidate()
          mainFrame.repaint()
        case ButtonClicked(`buttonSave`) => if (!textField.text.isEmpty) {
          model.dynamicGamedata.playerNameBuffer += textField.text
          contents -= buttonSave
          textField.editable = false
          revalidate()
          mainFrame.repaint()
        }
      }
    }
  }
  def addPlayerViaTUI(Pname: String, nr: Int): Panel = {
    new BoxPanel(Orientation.Vertical) {
      contents += new Label(nr + ".")
      val textField = new TextField()
      textField.columns = 1
      textField.maximumSize = new Dimension(1000, 30)
      textField.text = Pname
      textField.editable = false
      contents += textField
      revalidate()
      mainFrame.repaint()
      }
  }
  def SpielerRunde(): Panel = {
    new BoxPanel(Orientation.Vertical) {
      preferredSize = new Dimension(1920, 1000)
      contents += new Label("Super")
    }
  }
}