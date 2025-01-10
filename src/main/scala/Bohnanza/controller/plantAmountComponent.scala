package Bohnanza.controller
import Bohnanza.controller.Strategy

trait plantAmountComponent {
  def selectStrategy(): Strategy
}
