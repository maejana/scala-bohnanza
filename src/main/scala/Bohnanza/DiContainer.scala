package Bohnanza


import scala.collection.mutable

object DiContainer {
  // Map für Abhängigkeiten: Interface -> Liste von Implementierungs-Funktionen
  private val bindings = mutable.Map[Class[_], mutable.ListBuffer[() => Any]]()

  /**
   * Registrierung einer Implementierung für ein bestimmtes Interface.
   * @param interface Das Interface, für das eine Implementierung registriert wird.
   * @param implementation Die Implementierung, die registriert werden soll.
   */
  def bind[T](interface: Class[T])(implementation: => T): Unit = {
    val implementations = bindings.getOrElseUpdate(interface, mutable.ListBuffer())
    implementations += (() => implementation)
  }

  /**
   * Liefert einen Injector für ein bestimmtes Interface.
   * @param interface Das Interface, für das ein Injector erzeugt werden soll.
   * @return Ein Injector, der Zugriff auf die registrierten Implementierungen bietet.
   * @throws RuntimeException Wenn keine Implementierungen für das Interface registriert wurden.
   */
  def inject[T](interface: Class[T]): Injector[T] = {
    val implementations = bindings.getOrElse(interface, throw new RuntimeException(s"No bindings found for ${interface.getName}"))
    new Injector[T](implementations.toSeq)
  }
}

// Injector, der auf spezifische Implementierungen zugreifen kann
class Injector[T](implementations: Seq[() => Any]) {
  /**
   * Liefert eine Instanz einer spezifischen Implementierung des Interfaces.
   * @param clazz Die Klasse der gewünschten Implementierung.
   * @tparam I Der Typ der Implementierung, der von T abgeleitet sein muss.
   * @return Eine Instanz der gewünschten Implementierung.
   * @throws RuntimeException Wenn keine Implementierung des angegebenen Typs gefunden wird.
   */
  def getInstanceOf[I <: T](clazz: Class[I]): I = {
    implementations
      .map(_.apply()) // Instanzen erzeugen
      .collectFirst { case instance if clazz.isInstance(instance) => clazz.cast(instance) }
      .getOrElse(throw new RuntimeException(s"No implementation of type ${clazz.getName} found"))
  }
}
