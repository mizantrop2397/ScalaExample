package ru.scala.example

/**
  * case классы в скале - это классы, которые имеют по умолчанию ряд полезных "плюшек"
  * Все плюшки лучше почитать в доках, основная для скалы: возможность использовать объекты case классов в констукциях match, case
  *  @see ru.scala.example.PongActor#receive (тык ctrl+N на ссылку)
  * @param text Текст сообщения пинга
  */
case class Ping(text: String)

case class Pong(text: String)

object DefaultPing extends Ping("Default ping message")

object DefaultPong extends Pong("Default pong message")