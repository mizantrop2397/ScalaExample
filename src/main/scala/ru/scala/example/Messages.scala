package ru.scala.example

case class Ping(text: String)

case class Pong(text: String)

object defaultPing extends Ping(text = "Default ping message")

object defaultPong extends Pong(text = "Default pong message")