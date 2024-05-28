package com.ossuminc.riddl.target.akka

import akka.actor.typed.scaladsl.{ActorContext, Behaviors}
import com.ossuminc.riddl.target.akka.TestContext.FooMessage

class ContextTest extends AkkaTestCase {

  "Context" must {
    "handle message reception" in {
      val actor = TestContext.apply()
      Behaviors.setup {
        (context: ActorContext[TestContext.TestContextMessage]) =>
          val actorRef = context.spawn(actor, "test-case")
          actorRef.tell(FooMessage("foo"))
          actor
      }
    }
  }
}
