package com.ossuminc.riddl.target.akka

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.scaladsl.ActorContext
import com.ossuminc.riddl.target.akka.TestProcessor._
import com.ossuminc.riddl.target.akka.Common.Handler

class TestProcessorImpl[MT <: TestMessage](context: ActorContext[MT])
    extends TestProcessor[MT]()(context) {
  var initCalled: Boolean = false
  var termCalled: Boolean = false
  var testMessageCalled: Boolean = false

  override def initHandler(): Unit = {
    initCalled = true
  }

  override def terminateHandler(): Unit = {
    termCalled = true
  }

  def handleAMessage: Handler[MT] = (msg: MT) => {
    msg match {
      case tm: FooMessage => testMessageCalled = true
      case bm: BarMessage => testMessageCalled = true
    }
    sameBehavior
  }

  def closedMode: Handler[MT] = (msg: MessageBase) => {
    assert(true, "Processor is not open")
    sameBehavior
  }
}
