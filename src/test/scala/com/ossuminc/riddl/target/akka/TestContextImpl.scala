package com.ossuminc.riddl.target.akka

import akka.actor.typed.scaladsl.ActorContext
import com.ossuminc.riddl.target.akka.Common.Handler

class TestContextImpl[MT <: TestContext.TestContextMessage](
  context: ActorContext[MT]
) extends TestContext[MT](context) {

  override def handleAMessage: Handler[MT] = { (msg: MT) =>
    sameBehavior
  }
}
