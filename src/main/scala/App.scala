package Test

object App {
  @main def run: Unit = {
    view.TUI.setUpTUI()
    view.TUI.gameUpdate()
  }
}