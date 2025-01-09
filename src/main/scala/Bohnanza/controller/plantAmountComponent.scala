package Bohnanza.controller
import Bohnanza.controller.controllerBase.plantAmount.Strategy

trait plantAmountComponent {
  def selectStrategy(): Strategy
}
