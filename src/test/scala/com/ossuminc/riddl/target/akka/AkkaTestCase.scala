package com.ossuminc.riddl.target.akka

import akka.actor.testkit.typed.scaladsl.ActorTestKit
import akka.actor.typed.scaladsl.ActorContext
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.concurrent.ExecutionContext

class AkkaTestCase[MT] extends AnyWordSpec with BeforeAndAfterAll with Matchers {
  val testKit: ActorTestKit = ActorTestKit("AkkaTestCase")
  implicit val ec: ExecutionContext = testKit.internalSystem.executionContext
  override def afterAll(): Unit = testKit.shutdownTestKit()
}
