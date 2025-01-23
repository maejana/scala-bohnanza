package test

import Bohnanza.view.viewBase.FieldsCase
import Bohnanza.model.modelBase.dynamicGamedata
import Bohnanza.model.modelBase.player
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito._
import scala.collection.mutable.ArrayBuffer

class FieldsCaseTest extends AnyFlatSpec with Matchers with MockitoSugar {

  "FieldsCase" should "create a HBox with one field" in {
    val mockPlayer = mock[player]
    when(mockPlayer.plantfield1).thenReturn(ArrayBuffer.empty)
    dynamicGamedata.playingPlayer = Some(mockPlayer)

    val oneField = FieldsCase.OneField().plantField()
    oneField.children.size should be(1)
  }

  it should "create a HBox with two fields" in {
    val mockPlayer = mock[player]
    when(mockPlayer.plantfield1).thenReturn(ArrayBuffer.empty)
    when(mockPlayer.plantfield2).thenReturn(ArrayBuffer.empty)
    dynamicGamedata.playingPlayer = Some(mockPlayer)

    val twoFields = FieldsCase.TwoFields().plantField()
    twoFields.children.size should be(2)
  }

  it should "create a HBox with three fields" in {
    val mockPlayer = mock[player]
    when(mockPlayer.plantfield1).thenReturn(ArrayBuffer.empty)
    when(mockPlayer.plantfield2).thenReturn(ArrayBuffer.empty)
    when(mockPlayer.plantfield3).thenReturn(ArrayBuffer.empty)
    dynamicGamedata.playingPlayer = Some(mockPlayer)

    val threeFields = FieldsCase.ThreeFields().plantField()
    threeFields.children.size should be(3)
  }


  "FieldsCase" should "create a HBox with one field when i is 1" in {
    val oneField = FieldsCase.plantField(1)
    oneField.children.size should be(1)
  }

  it should "create a HBox with two fields when i is 2" in {
    val twoFields = FieldsCase.plantField(2)
    twoFields.children.size should be(2)
  }

  it should "create a HBox with three fields when i is 3" in {
    val threeFields = FieldsCase.plantField(3)
    threeFields.children.size should be(3)
  }
}