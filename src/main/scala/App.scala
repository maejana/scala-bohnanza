package Test

object App {
  @main def run: Unit = {
    view.TUI.setUpTUI()
    print(controller.UIlogic.gameUpdate())
  }
}