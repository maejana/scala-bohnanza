package Bohnanza

object App {
  @main def run: Unit = {
    view.TUI.setUpTUI()
    print(controller.GameUpdate.gameUpdate())
  }
}