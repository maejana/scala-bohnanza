package Bohnanza.model.modelBase

import Bohnanza.controller.UtilityComponent
import Bohnanza.model.Builder

class fieldBuilder(utility: UtilityComponent) extends Builder {
  override def buildGrowingFieldStr(playingPlayer: Option[player]): String = {
    val growingFieldText: String =
      s"""
                                   ${playingPlayer.get.playerName}:
                                      Field 1:
                                   ${utility.playerFieldToString(playingPlayer.get.plantfield1)}
                                      Field 2:
                                   ${utility.playerFieldToString(playingPlayer.get.plantfield2)}
                                      Field 3:
                                   ${utility.playerFieldToString(playingPlayer.get.plantfield3)}

                                   """
    growingFieldText
  }
}
