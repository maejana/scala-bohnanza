package Bohnanza.model.modelBase

import Bohnanza.model.Builder


object fieldBuilder extends Builder {
  override def buildGrowingFieldStr(playingPlayer: Option[player]): String = {
    val growingFieldText: String =
      s"""
                                   ${playingPlayer.get.playerName}:
                                      Field 1:
                                   ${gameDataFunc.playerFieldToString(playingPlayer.get.plantfield1)}
                                      Field 2:
                                   ${gameDataFunc.playerFieldToString(playingPlayer.get.plantfield2)}
                                      Field 3:
                                   ${gameDataFunc.playerFieldToString(playingPlayer.get.plantfield3)}

                                   """
    growingFieldText
  }
}
