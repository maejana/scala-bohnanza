import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import Bohnanza.DiContainer

class DiContainerTest extends AnyFlatSpec with Matchers {

  trait TestInterface {
    def doSomething(): String
  }

  class TestImplementation extends TestInterface {
    override def doSomething(): String = "TestImplementation"
  }

  class AnotherTestImplementation extends TestInterface {
    override def doSomething(): String = "AnotherTestImplementation"
  }

  "DiContainer" should "bind and inject implementations correctly" in {
    DiContainer.bind(classOf[TestInterface])(new TestImplementation)
    DiContainer.bind(classOf[TestInterface])(new AnotherTestImplementation)

    val injector = DiContainer.inject(classOf[TestInterface])

    val instance1 = injector.getInstanceOf(classOf[TestImplementation])
    instance1.doSomething() shouldEqual "TestImplementation"

    val instance2 = injector.getInstanceOf(classOf[AnotherTestImplementation])
    instance2.doSomething() shouldEqual "AnotherTestImplementation"
  }

  it should "throw an exception if no bindings are found" in {
    assertThrows[RuntimeException] {
      DiContainer.inject(classOf[String])
    }
  }

  it should "throw an exception if no implementation of the specified type is found" in {
    DiContainer.bind(classOf[TestInterface])(new TestImplementation)

    val injector = DiContainer.inject(classOf[TestInterface])

    assertThrows[RuntimeException] {
      injector.getInstanceOf(classOf[AnotherTestImplementation])
    }
  }
}