package Bohnanza.view.viewBase

import Bohnanza.model.modelBase.card
import scalafx.scene.image.{Image, ImageView}

class GUICards {
  def getCardPanel(card: card): ImageView = {
    card.beanName match {
      case "Blaue" => BlueCard
      case "Feuer" => FireCard
      case "Sau" => PigCard
      case "Brech" => VomitCard
      case "Soja" => SoyesCard
      case "Augen" => EyesCard
      case "Rote" => RedCard
      case "Garten" => GartenCard
      case "NoCard" => NoCard
    }
  }

  def BlueCard: ImageView = {
    val imageView = new ImageView(new Image("C:\\Users\\euule\\Documents\\GitHub\\scala-bohnanza\\src\\resources\\Images\\Blaue_Bohne.jpg"))
    imageView.setFitWidth(166)
    imageView.setFitHeight(250)
    imageView
  }

  def FireCard: ImageView = {
    val imageView = new ImageView(new Image("C:\\Users\\euule\\Documents\\GitHub\\scala-bohnanza\\src\\resources\\Images\\Feuerbohne.jpg"))
    imageView.setFitWidth(166)
    imageView.setFitHeight(250)
    imageView
  }

  def PigCard: ImageView = {
    val imageView = new ImageView(new Image("C:\\Users\\euule\\Documents\\GitHub\\scala-bohnanza\\src\\resources\\Images\\Saubohne.jpg"))
    imageView.setFitWidth(166)
    imageView.setFitHeight(250)
    imageView
  }

  def VomitCard: ImageView = {
    val imageView = new ImageView(new Image("C:\\Users\\euule\\Documents\\GitHub\\scala-bohnanza\\src\\resources\\Images\\Brechbohne.jpg"))
    imageView.setFitWidth(166)
    imageView.setFitHeight(250)
    imageView
  }

  def SoyesCard: ImageView = {
    val imageView = new ImageView(new Image("C:\\Users\\euule\\Documents\\GitHub\\scala-bohnanza\\src\\resources\\Images\\Sojabohne.jpg"))
    imageView.setFitWidth(166)
    imageView.setFitHeight(250)
    imageView
  }

  def EyesCard: ImageView = {
    val imageView = new ImageView(new Image("C:\\Users\\euule\\Documents\\GitHub\\scala-bohnanza\\src\\resources\\Images\\Augenbohne.jpg"))
    imageView.setFitWidth(166)
    imageView.setFitHeight(250)
    imageView
  }

  def RedCard: ImageView = {
    val imageView = new ImageView(new Image("C:\\Users\\euule\\Documents\\GitHub\\scala-bohnanza\\src\\resources\\Images\\Rote_Bohne.jpg"))
    imageView.setFitWidth(166)
    imageView.setFitHeight(250)
    imageView
  }

  def GartenCard: ImageView = {
    val imageView = new ImageView(new Image("C:\\Users\\euule\\Documents\\GitHub\\scala-bohnanza\\src\\resources\\Images\\Gartenbohne.jpg"))
    imageView.setFitWidth(166)
    imageView.setFitHeight(250)
    imageView
  }

  def NoCard: ImageView = {
    val imageView = new ImageView()
    imageView.setFitWidth(166)
    imageView.setFitHeight(250)
    imageView
  }
}