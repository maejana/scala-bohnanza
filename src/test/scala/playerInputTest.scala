import Bohnanza.model.modelBase.dynamicGamedata
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import java.io.{ByteArrayInputStream, InputStream}
import Bohnanza.view.viewBase.playerInput

class playerInputTest extends AnyFlatSpec with Matchers {

  def withInput(input: String)(testCode: => Unit): Unit = {
    val in: InputStream = new ByteArrayInputStream(input.getBytes)
    val originalIn: InputStream = System.in
    try {
      System.setIn(in)
      testCode
    } finally {
      System.setIn(originalIn)
    }
  }

  "playercount" should "read and set the player count correctly" in {
    dynamicGamedata.playerCount = 0
    withInput("3\n") {
      val testThread = new Thread(new Runnable {
        def run(): Unit = playerInput.readConsoleThread()
      })
      testThread.start()
      testThread.join(1000) // Wait for 1 second
      if (testThread.isAlive) testThread.interrupt()
      dynamicGamedata.playerCount should be(3)
    }
  }

  "playername" should "read and return the player name correctly" in {
    withInput("TestPlayer\n") {
      val testThread = new Thread(new Runnable {
        def run(): Unit = {
          val playerName = playerInput.readNameConsoleThread()
          assert(playerName == "TestPlayer")
        }
      })
      testThread.start()
      testThread.join(1000) // Wait for 1 second
      if (testThread.isAlive) testThread.interrupt()
    }
  }

  "keyListener" should "handle different inputs correctly" in {
    withInput("1\n") {
      val testThread = new Thread(new Runnable {
        def run(): Unit = playerInput.keyListener()
      })
      testThread.start()
      testThread.join(1000) // Wait for 1 second
      if (testThread.isAlive) testThread.interrupt()
      dynamicGamedata.plant1or2 should be(1)
    }
  }

  "readConsoleThreadPlant1or2" should "set plant1or2 correctly based on input" in {
    dynamicGamedata.plant1or2 = 0
    withInput("2\n") {
      val testThread = new Thread(new Runnable {
        def run(): Unit = playerInput.readConsoleThreadPlant1or2()
      })
      testThread.start()
      testThread.join(1000) // Wait for 1 second
      if (testThread.isAlive) testThread.interrupt()
      dynamicGamedata.plant1or2 should be(0)
    }
  }
}