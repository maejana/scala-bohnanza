package Test.controller

import Test.model

object gamelogic {
  def update(playerID: model.player): String = {
    var output: String =
      s"""
          ${playerID.playerName}:
             Field 1:
             ${playerID.plantfield1}
             Field 2:
             ${playerID.plantfield2}
             Field 3:
             ${playerID.plantfield3}

          """
    output += playerID.playerHand
    output
  }

  def plant(cards: String, fieldNr: Int, playerID: model.player): Unit ={
    fieldNr match {
      case 1 => playerID.plantfield1 += cards + "\n"
      case 2 => playerID.plantfield2 += cards + "\n"
      case 3 => playerID.plantfield3 += cards + "\n"
    }
  }
  /*
  def harvest(field: Int): String={

  }
  def trade(): String ={

  }*/
  /*
  def drawCards(): String ={
    
  }
  
   */
  


}