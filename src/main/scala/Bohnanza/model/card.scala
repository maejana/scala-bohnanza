package Bohnanza.model

import scala.collection.mutable.ArrayBuffer

case class card(bean: String, weight: Int, price: Array[Int]) {
  val beanName = bean
  var weightCount = weight
  val priceStairs: Array[Int] = price
  // garten ab 2 = 1 sau 3 = 1 
}