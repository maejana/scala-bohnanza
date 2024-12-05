package Bohnanza

object App {
  @main def run: Unit = {
    view.TUI.setUpTUI()
    view.TUI.startGame()
  }
    view.GUI.guistart()
}