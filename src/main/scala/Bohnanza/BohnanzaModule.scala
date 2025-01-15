package Bohnanza

import Bohnanza.controller.UtilityComponent
import com.google.inject.{AbstractModule, Provides}
import com.google.inject.name.Names
import net.codingwell.scalaguice.ScalaModule
import Bohnanza.controller.controllerBase.{Utility, playerState}


class BohnanzaModule(playerstate: playerState) extends AbstractModule with ScalaModule {
  
  override def configure(): Unit = {
    bind[UtilityComponent].to[Utility]
  }

  @Provides
  def provideGameState(): playerState = playerstate

}
