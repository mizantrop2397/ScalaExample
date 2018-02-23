package ru.scala.example

import java.lang.Thread.sleep

import ru.scala.example.PingPongGame.startGame

object MainApp extends App {
  for (i <- 1 to 3) {
    println {
      mapStepToWord(i)
    }
    sleep(1000)
  }
  startGame()


  private def mapStepToWord(step: Int) = step match {
    case 1 => "One..."
    case 2 => "Two..."
    case 3 => "Three..."
  }
}
