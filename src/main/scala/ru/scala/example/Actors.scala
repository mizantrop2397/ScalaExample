package ru.scala.example

import akka.actor.{Actor, PoisonPill}
import ru.scala.example.Constants.{DEFAULT_COUNT_DOWN, PINGER_ACTOR_PATH}

class PingActor extends Actor {
  /**
    * Когда мы дергаем актера, у него вызывается метод receive
    * override def - перегрузка родительского метода receive
    * @return PartialFunction[Any, Unit]
    * Метод receive возвращает ФУНКЦИЮ типа PartialFunction.
    * В скале функция является ОБЪЕКТОМ, который можно создасть/передать/вернуть, ну и вызвать, как функциию. Аналог - function в JavaScript.
    * Можно сказать, что receive возвращает функцию, которая принимает объект типа Any (произвольный тип, аналог - java Object), а возвращает void
    * Телом этой функцией является конструкция "case Pong ⇒ handlePong(countDown)"
    * То есть мы вернули функцию - обработчик, которая будет вызвана в момент получения сообщения, и если сообщение будет иметь тип Pong, то мы вызовем метод handlePong(countDown)
    */
  override def receive: PartialFunction[Any, Unit] = onMessage(DEFAULT_COUNT_DOWN)

  private def onMessage(countDown: Int): Receive = {
    // Если пришел объект КЛАССА Pong (здесь идет именно проверка класса), то дергаем метод handlePong(countDown)
    case DefaultPong ⇒ handlePong(countDown, DefaultPong.text)
  }

  private def handlePong(countDown: Int, message: String): Unit = {
    println(s"Ponger received pong, count down $countDown")
    // Уменьшаем счётчк и "меняем" "поведение" нашего актера
    // Akka не любит изменяемых сущностей, поэтому "нельзя просто так взять и изменить значения текущего актера", а надо делать вот так (менять поведение, которое по сути представляет собой ФУНКЦИЮ-обработчик)
    // В скале можно вызывать методы даже без ".", достаточно пробела (но не всегда)
    context become onMessage(countDown - 1)
    if (countDown > 0) ping(message) else stopPinging()
  }

  private def stopPinging(): Unit = {
    println("Stopping pinging...")
    // sender() - даст нам ссылку на актера, который вызвал нашего текущего актера
    // PoisonPill - сообщение, которое останавливает работу актера
    sender() ! PoisonPill
    self ! PoisonPill
    context.system terminate()
  }

  private def ping(message: String): Unit = {
    println("Pinging...")
    println(message)
    sender() ! DefaultPing
  }
}

class PongActor() extends Actor {

  import context.system

  def receive: PartialFunction[Any, Unit] = {
    case DefaultPing ⇒ handlePing(DefaultPing.text)
  }

  private def handlePing(message: String): Unit = {
    println("Pinger received ping")
    println(message)
    // system actorSelection PINGER_ACTOR_PATH - а таким способом мы "ищмем" актера Ponger-а в нашей актерной системе, чтобы послать ему сигнал DefaultPong
    system actorSelection PINGER_ACTOR_PATH tell(DefaultPong, self)
  }
}