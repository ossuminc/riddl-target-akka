package com.ossuminc.riddl.target.akka

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.{ActorContext, Behaviors}
import com.ossuminc.riddl.target.akka.Common.Handler

object TestProcessor {
  sealed trait TestMessage extends MessageBase

  case class FooMessage(foo: String) extends TestMessage
  case class BarMessage(bar: Int) extends TestMessage

  def apply(): Behavior[TestMessage] =
    Behaviors.setup { (context: ActorContext[TestMessage]) =>
      {
        val tpi = new TestProcessorImpl[TestMessage](context)
        tpi.open()
        tpi
      }
    }
}

abstract class TestProcessor[MT <: TestProcessor.TestMessage]()(
  implicit context: ActorContext[MT]
) extends Processor("test", Common.emptyOptions)(context) {

  import TestProcessor._

  addHandler(handleAMessage)
  addHandler(closedMode)

  def handleAMessage: Handler[MT]

  def closedMode: Handler[MT]
}
