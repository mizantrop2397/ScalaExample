package ru.scala.example

object Constants {
  val DEFAULT_COUNT_DOWN = 100
  val PONGER_ACTOR_NAME = "Ponger"
  val PINGER_ACTOR_NAME = "Pinger"
  // Конструкции вида s"" позволяют "вставлять" значения/переменные прямо в текст строки, используя '$'
  val PINGER_ACTOR_PATH = s"/user/$PINGER_ACTOR_NAME"
  val PING_PONG_ACTOR_SYSTEM_NAME = "PingPongGame"
}
