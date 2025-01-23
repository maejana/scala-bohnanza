package Bohnanza.model.modelBase

import Bohnanza.controller.ObserverTrait
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito._

class CardObserverTest extends AnyFlatSpec with Matchers with MockitoSugar {

  "CardObserver" should "print update message when update is called" in {
    val cardObserver = new CardObserver()
    val outputStream = new java.io.ByteArrayOutputStream()
    Console.withOut(outputStream) {
      cardObserver.update()
    }
    outputStream.toString should include ("Cards wurden geupdated!")
  }
}