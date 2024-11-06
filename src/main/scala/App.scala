package Test

object App {
  @main def run: Unit = {
    view.TUI.setUpTUI()
    controller.UIlogic.gameUpdate()
  }
}