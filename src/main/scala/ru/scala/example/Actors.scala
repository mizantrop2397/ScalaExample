package ru.scala.example

import akka.actor.{Actor, PoisonPill}
import ru.scala.example.Constants.{DEFAULT_COUNT_DOWN, PINGER_ACTOR_PATH}

class PingActor extends Actor {
  private val countDown = DEFAULT_COUNT_DOWN

  override def receive: PartialFunction[Any, Unit] = onMessage(countDown)

  private def onMessage(countDown: Int): Receive = {
    case Pong ⇒ handlePong(countDown)
  }

  private def handlePong(countDown: Int): Unit = {
    println(s"Ponger received pong, count down $countDown")
    context become onMessage(countDown - 1)
    if (countDown > 0) ping() else stopPinging()
  }

  private def stopPinging(): Unit = {
    println("Stopping pinging...")
    sender() ! PoisonPill
    self ! PoisonPill
    context.system terminate()
  }

  private def ping(): Unit = {
    println("Pinging...")
    sender() ! Ping
  }
}

class PongActor() extends Actor {
  import context.system

  def receive: PartialFunction[Any, Unit] = {
    case Ping ⇒ handlePing()
  }

  private def handlePing(): Unit = {
    println("Pinger received ping")
    system actorSelection PINGER_ACTOR_PATH tell(Pong, self)
  }
}