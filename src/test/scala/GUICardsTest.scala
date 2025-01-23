package test
import Bohnanza.model.modelBase.card
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalafx.scene.image.ImageView
import Bohnanza.view.viewBase.GUICards

class GUICardsTest extends AnyFlatSpec with Matchers {

  "GUICards" should "return the correct ImageView for each card type" in {
    val guiCards = new GUICards()

    val blueCard = new card("Blaue", 1, Array(1, 2, 3))
    val fireCard = new card("Feuer", 2, Array(1, 2, 3))
    val pigCard = new card("Sau", 3, Array(1, 2, 3))
    val vomitCard = new card("Brech", 4, Array(1, 2, 3))
    val soyesCard = new card("Soja", 5, Array(1, 2, 3))
    val eyesCard = new card("Augen", 6, Array(1, 2, 3))
    val redCard = new card("Rote", 7, Array(1, 2, 3))
    val gartenCard = new card("Garten", 8, Array(1, 2, 3))
    val noCard = new card("NoCard", 9, Array(1, 2, 3))

    guiCards.getCardPanel(blueCard).getImage.getUrl should include("Blaue_Bohne.jpg")
    guiCards.getCardPanel(fireCard).getImage.getUrl should include("Feuerbohne.jpg")
    guiCards.getCardPanel(pigCard).getImage.getUrl should include("Saubohne.jpg")
    guiCards.getCardPanel(vomitCard).getImage.getUrl should include("Brechbohne.jpg")
    guiCards.getCardPanel(soyesCard).getImage.getUrl should include("Sojabohne.jpg")
    guiCards.getCardPanel(eyesCard).getImage.getUrl should include("Augenbohne.jpg")
    guiCards.getCardPanel(redCard).getImage.getUrl should include("Rote_Bohne.jpg")
    guiCards.getCardPanel(gartenCard).getImage.getUrl should include("Gartenbohne.jpg")

    val noCardImageView = guiCards.getCardPanel(noCard)
    noCardImageView.getImage should be(null) // `NoCard` has no image set
    noCardImageView.getFitWidth shouldBe 166
    noCardImageView.getFitHeight shouldBe 250
  }

  it should "set the correct dimensions for each ImageView" in {
    val guiCards = new GUICards()
    val cardTypes = Seq(
      new card("Blaue", 1, Array(1, 2, 3)),
      new card("Feuer", 2, Array(1, 2, 3)),
      new card("Sau", 3, Array(1, 2, 3)),
      new card("Brech", 4, Array(1, 2, 3)),
      new card("Soja", 5, Array(1, 2, 3)),
      new card("Augen", 6, Array(1, 2, 3)),
      new card("Rote", 7, Array(1, 2, 3)),
      new card("Garten", 8, Array(1, 2, 3)),
      new card("NoCard", 9, Array(1, 2, 3))
    )

    cardTypes.foreach { card =>
      val imageView = guiCards.getCardPanel(card)
      imageView.getFitWidth shouldBe 166
      imageView.getFitHeight shouldBe 250
    }
  }
}