package Test.model

import scala.collection.mutable.ArrayBuffer

case class card(bean: String, weight: Int, price: ArrayBuffer[Int]) {
  val beanName = bean
  var weightCount = weight
  val priceStairs: ArrayBuffer[Int] = price
  // garten ab 2 = 1 sau 3 = 1 
}
 