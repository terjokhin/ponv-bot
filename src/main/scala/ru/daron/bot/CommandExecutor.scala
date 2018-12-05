package ru.daron.bot

import cats.Applicative
import ru.daron.model._

trait CommandExecutor[F[_]] {

  def exec(cmd: Command): F[MessageResponse]
}

object CommandExecutor {

  def apply[F[_]](implicit A: Applicative[F]): CommandExecutor[F] = {
    case ke: KamilExcited =>
      A.pure(KamilExcitedResponse(ke.howMuch, ke.username))
    case EmptyCommand =>
      A.pure(EmptyResponse)
  }

}
