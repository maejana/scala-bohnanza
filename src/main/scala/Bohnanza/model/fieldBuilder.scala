package Bohnanza.model

import Bohnanza.model.{Builder, gameDataFunc, player}


object fieldBuilder extends Builder {
  override def buildGrowingFieldStr(playingPlayer: player): String = {
    val growingFieldText: String =
      s"""
                                   ${playingPlayer.playerName}:
                                      Field 1:
                                   ${gameDataFunc.playerFieldToString(playingPlayer.plantfield1)}
                                      Field 2:
                                   ${gameDataFunc.playerFieldToString(playingPlayer.plantfield2)}
                                      Field 3:
                                   ${gameDataFunc.playerFieldToString(playingPlayer.plantfield3)}

                                   """
    growingFieldText
  }
}
