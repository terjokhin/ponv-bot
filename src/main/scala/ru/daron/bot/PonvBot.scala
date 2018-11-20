package ru.daron.bot

import cats.effect.Effect
import cats.syntax.functor._
import com.bot4s.telegram.api.declarative.Commands
import com.bot4s.telegram.api.{Polling, TelegramBot}
import com.bot4s.telegram.clients.ScalajHttpClient
import com.bot4s.telegram.models.Message
import ru.daron.Console
import ru.daron.store.Store

class PonvBot[F[_], K, V](token: String, store: Store[K, V, F], console: Console[F])
                         (implicit F: Effect[F]) extends TelegramBot
  with Polling
  with Commands {

  val client = new ScalajHttpClient(token)

  onMessage { implicit msg => F.toIO(replyEchoMessage).unsafeToFuture() }

  def replyEchoMessage(implicit msg: Message): F[Unit] =  for {
    _ <- console.print(msg.chat.toString)
    _ = reply(msg.text.getOrElse(""))
  } yield ()
}
