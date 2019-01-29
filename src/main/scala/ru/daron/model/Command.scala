package ru.daron.model

import info.mukel.telegrambot4s.models.Message

sealed trait Command

object Command {

  def parse(msg: Message): Command = msg.text.getOrElse("") match {
    case s if KamilExcited.isKamilExcited(s) =>
      KamilExcited(s, msg.from.flatMap(_.username).getOrElse("unknown"))
    case _ =>
      EmptyCommand
  }
}

case class KamilExcited(msg: String, username: String) extends Command {

  val howMuch: Double = {
    val base = msg.filter(v => KamilExcited.code.contains(v)).length.toDouble / 4
    val bonus = msg.count(_ == 'ъ') * 5.0
    base + bonus
  }
}

object KamilExcited {
  val code = "амзхъ"

  def isKamilExcited(s: String): Boolean = {
    code
      .filterNot(v => s.contains(v))
      .isEmpty && s.filterNot(v => code.contains(v)).length <= 3 && s.nonEmpty
  }
}

case object EmptyCommand extends Command
