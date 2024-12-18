package Bohnanza.model

trait Builder {
  def buildGrowingFieldStr(playingplayer : Option[player]): String
}
