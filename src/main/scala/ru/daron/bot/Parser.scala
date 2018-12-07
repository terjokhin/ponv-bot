package ru.daron.bot

import cats.Applicative
import info.mukel.telegrambot4s.models.Message
import ru.daron.model.Command

trait Parser[F[_]] {
  def parse(msg: Message): F[Command]
}

object Parser {

  def apply[F[_]](implicit A: Applicative[F]): Parser[F] = msg => A.pure(Command.parse(msg))
}
