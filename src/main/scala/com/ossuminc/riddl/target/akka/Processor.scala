package com.ossuminc.riddl.target.akka

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}

import com.ossuminc.riddl.target.akka.Common.{OptionList, Handler}

abstract class Processor[MT <: MessageBase](
  val riddlName: String,
  val options: OptionList
)(implicit context: ActorContext[MT])
    extends AbstractBehavior[MT](context)
    with VitalDefinition
    with AutoCloseable {

  final def sameBehavior: Behavior[MT] = Behaviors.same
  final def unhandled: Behavior[MT] = Behaviors.unhandled[MT]

  private var currentHandler: Option[Handler[MT]] = handlers.headOption
  private var handlers: Seq[Handler[MT]] = Seq.empty[Handler[MT]]

  protected def addHandler(handler: Handler[MT]) = {
    handlers = this.handlers :+ handler
  }

  def onMessage(message: MT): Behavior[MT] = {
    currentHandler match {
      case Some(behave: Handler[MT]) => behave(message)
      case None                      => unhandled
    }
  }

  def initHandler(): Unit = ()
  def terminateHandler(): Unit = ()

  def open(): Unit = {
    initHandler()
  }

  def become(handler: Handler[MT]): Unit = {
    require(handlers.nonEmpty, s"No handlers in ${getClass.getSimpleName}")
    require(handlers.contains(handler))
    currentHandler = Some(handler)
  }

  @throws[Exception]
  def close(): Unit = {
    terminateHandler()
  }

}
