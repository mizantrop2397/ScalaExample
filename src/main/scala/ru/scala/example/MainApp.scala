package ru.scala.example

import java.lang.Thread.sleep

import ru.scala.example.PingPongGame.startGame

/**
  * MainApp - объект. В скале объекты являются синглтонами (единственный экземпляр класса). В данном случае MainApp явялется единственным экземпляром класса App.
  * Загляните в App (ctrl + клик на App). Метод main(...), с которого у нас начинается всё веселье находится именно в нем.
  */
object MainApp extends App {
  // Цикл от 1 до трёх (1,2,3). 'to' создаст объект класса Range (диапазон), по которому и будет проходить наш "for"
  for (i <- 1 to 3) {
    // Старый добрый System.out.println(...). В скале методы можно вызывать как через такие скобочки '()', так и с помощью '{}'.
    // Но в некоторых случаях второй вариант не прокатит...
    println {
      mapStepToWord(i)
    }
    // Thread.sleep
    sleep(1000)
  }
  // PingPongGame.startGame()
  startGame()

  /** def - объявление методов в скале
    * step: Int - объявление входного параметра типа Int
    * : String - объявление типа возвращаемого значения (String)
    * В скале после объявления сигнатуры метода можно поставить "=" и сразу писать тело метода
    * Конструкция step match { case (что-то) => (что-то другое)} равносильна Java switch-у: switch(step) { case 1: return "One..." ...}
    * @param step Шаг цикла ожидания
    * @return Сообщение, соответствующее шагу
    */
  private def mapStepToWord(step: Int): String = step match {
    case 1 => "One..."
    case 2 => "Two..."
    case 3 => "Three..."
  }
}
