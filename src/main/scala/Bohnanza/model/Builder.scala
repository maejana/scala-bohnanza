package Bohnanza.model

import Bohnanza.model.modelBase.player

trait Builder {
  def buildGrowingFieldStr(playingplayer : Option[player]): String
}
