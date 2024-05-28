package com.ossuminc.riddl.target.akka

import akka.actor.typed.{Behavior, ExtensibleBehavior}

object Common {
  type OptionList = Map[String, List[String]]
  val emptyOptions = Map.empty[String, List[String]]

  type Handler[MT <: MessageBase] = (msg: MT) => Behavior[MT]

}
