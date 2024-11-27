package Bohnanza.controller


trait SubjectTrait{
  private var observers: List[ObserverTrait] = Nil
  def addObserver(observer: ObserverTrait): Unit = observers ::= observer
  def removeObserver(observer: ObserverTrait): Unit = observers = observers.filter(_ == observer)
  def notifyObservers(): Unit = observers.foreach(_.update())
}
