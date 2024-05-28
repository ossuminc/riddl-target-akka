package com.ossuminc.riddl.target.akka

import akka.actor.typed.{Behavior, ExtensibleBehavior}
import akka.actor.typed.scaladsl.{ActorContext, Behaviors}
import com.ossuminc.riddl.target.akka.Common.Handler

object TestContext {
  sealed trait TestContextMessage extends MessageBase

  case class FooMessage(foo: String) extends TestContextMessage
  case class BarMessage(bar: Int) extends TestContextMessage

  def apply(): Behavior[TestContextMessage] =
    val result = Behaviors.setup { (context: ActorContext[TestContextMessage]) =>
      val tci = new TestContextImpl[TestContextMessage](context)
      tci.open()
      tci
    }
    result
}

abstract class TestContext[MT <: TestContext.TestContextMessage](
  context: ActorContext[MT]
) extends Context("test", Common.emptyOptions)(context) {

  import TestProcessor._

  addHandler(handleAMessage)

  def handleAMessage: Handler[MT]

}
