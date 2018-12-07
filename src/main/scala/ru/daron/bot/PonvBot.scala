package ru.daron.bot

import cats.effect.Effect
import cats.syntax.flatMap._
import cats.syntax.functor._
import info.mukel.telegrambot4s.api.declarative.Commands
import info.mukel.telegrambot4s.api.{Polling, TelegramBot}
import info.mukel.telegrambot4s.models.Message
import ru.daron.Console
import ru.daron.model.{EmptyResponse, MessageResponse}
import ru.daron.store.Store

class PonvBot[F[_], K, V](override val token: String,
                          store: Store[K, V, F],
                          console: Console[F],
                          parser: Parser[F],
                          executor: CommandExecutor[F])
                         (implicit F: Effect[F]) extends TelegramBot
  with Polling
  with Commands {

  onMessage { implicit msg => F.toIO(replyEchoMessage).unsafeToFuture() }

  def replyEchoMessage(implicit msg: Message): F[Unit] = for {
    cmd <- parser.parse(msg)
    resp <- executor.exec(cmd)
    _ = resp match {
      case EmptyResponse =>
        ()
      case resp: MessageResponse =>
        reply(resp.toString)
    }
  } yield ()

}
