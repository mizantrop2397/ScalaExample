package ru.scala.example

import akka.actor.{ActorRef, ActorSystem, Props}
import ru.scala.example.Constants.{PINGER_ACTOR_NAME, PONGER_ACTOR_NAME}

import scala.concurrent.duration._

object PingPongGame {
  // val - переменная/поле с незименяемыми значением
  // var - соответственно с изменениями
  private val system = createActorSystem()

  // Импорт объекта из ОБЪЕКТА system. Это фишка скалы, позволяющая получить доступ ко внутренним полям/объектам какого-либо объекта.
  import system.dispatcher

  // Unit - аналог Java void
  def startGame(): Unit = startPinging(createPonger(createPinger))

  /**
    * Данный метод запускает процесс пинга (system.scheduler.scheduleOnce(0 nanos) означает "Через 0 наносекунд выполнить действие")
    * { println("Starting game...") ponger ! Ping } - это то самое действие. Java аналог - new Runnable () { public void run() {println("Starting game...") ponger ! Ping} }
    * @param ponger Ссылка на актера
    */
  private def startPinging(ponger: ActorRef): Unit = system.scheduler.scheduleOnce(0 nanos) {
    println("Starting game...")
    // "!" - в скале возможна перегрузка операторов. Вот в данном случае нехорошие ребята из Akka сделали перегрузку оператора "!", которая посылает актеру ponger сообщение Ping
    ponger ! DefaultPing
  }

  // Создание актера Ponger
  private def createPonger(pinger: ActorRef) = system.actorOf(Props[PongActor], PONGER_ACTOR_NAME)

  // Создание актера Pinger
  private def createPinger = system.actorOf(Props[PingActor], PINGER_ACTOR_NAME)

  // Создание системы актеров
  private def createActorSystem(): ActorSystem = {
    val system = ActorSystem()
    system.registerOnTermination {
      println("Terminating game...")
    }
    system
  }
}
