package Bohnanza.view

object playerInput {
  def keyListener(): Int = {
    val key = scala.io.StdIn.readLine().strip()
    key match {
      case "0" => 0
      case "1" => 1
      case "2" => 2
      case _ => -1
    }
  }
  def playercount(): Int={
    val count = scala.io.StdIn.readInt()
    count
  }
  def playername(): String = {
    val name = scala.io.StdIn.readLine()
    name
  }
}
