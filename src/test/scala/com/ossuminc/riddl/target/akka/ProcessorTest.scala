package com.ossuminc.riddl.target.akka

import akka.actor.typed.scaladsl.*

class ProcessorTest extends AkkaTestCase {

  "Processor" should {
    import TestProcessor._

    "construct " in {
      val test = TestProcessor.apply()
      test != Behaviors.unhandled
    }

    "should have init and term handlers called" in {
      Behaviors.setup[TestMessage] { context =>
        val test = TestProcessorImpl(context)
        test.initCalled must be(true)
        test.close()
        test.termCalled must be(true)
        test.testMessageCalled must be(false)
        test
      }
    }
    "should dispatch to default (first) handler " in {
      Behaviors.setup[TestMessage] { context =>
        val test = TestProcessorImpl(context)
        test.open()
        val tm = FooMessage("foo")
        val actor = testKit.spawn(test)
        actor ! tm
        test.testMessageCalled must be(true)
        test
      }
    }
    "should be able to become a new version of itself" in {
      Behaviors.setup[TestMessage] { context =>
        val test = TestProcessorImpl(context)
        test.open()
        test.become(test.closedMode)
        intercept[IllegalArgumentException] {
          test.receive(context, FooMessage("foo"))
        }
        test
      }
    }
  }
}
