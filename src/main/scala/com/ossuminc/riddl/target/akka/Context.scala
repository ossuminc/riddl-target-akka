package com.ossuminc.riddl.target.akka

import Common.*
import akka.actor.typed.scaladsl.ActorContext

abstract class Context[MT <: MessageBase](
  riddlName: String,
  options: OptionList
)(context: ActorContext[MT])
    extends Processor(riddlName, options)(context)
    with AutoCloseable {

  override def initHandler(): Unit = ()

  override def terminateHandler(): Unit = ()

}
