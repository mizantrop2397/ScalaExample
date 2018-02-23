package ru.scala.example

import akka.actor.{ActorRef, ActorSystem, Props}
import ru.scala.example.Constants.{PINGER_ACTOR_NAME, PONGER_ACTOR_NAME}

import scala.concurrent.duration._

object PingPongGame {
  private val system = createActorSystem()

  import system.dispatcher

  def startGame(): Unit = startPinging(createPonger(createPinger))

  private def startPinging(ponger: ActorRef) = system.scheduler.scheduleOnce(0 nanos) {
    println("Starting game...")
    ponger ! Ping
  }

  private def createPonger(pinger: ActorRef) = system.actorOf(Props[PongActor], PONGER_ACTOR_NAME)

  private def createPinger = system.actorOf(Props[PingActor], PINGER_ACTOR_NAME)

  private def createActorSystem(): ActorSystem = {
    val system = ActorSystem()
    system.registerOnTermination {
      println("Terminating game...")
    }
    system
  }
}
