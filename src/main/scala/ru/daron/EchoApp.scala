package ru.daron

import cats.MonadError
import cats.data.EitherT
import cats.effect._
import cats.syntax.applicativeError._
import cats.syntax.flatMap._
import cats.effect.IO._
import cats.syntax.functor._
import com.bot4s.telegram.api.declarative.Commands
import com.bot4s.telegram.api.{Polling, TelegramBot}
import com.bot4s.telegram.clients.ScalajHttpClient
import ru.daron.store.{Config, Store}

import scala.io.StdIn

object EchoApp extends IOApp {

  class EchoBot[F[_], K, V](val token: String, store: Store[K, V, F]) extends TelegramBot with Polling with Commands {
    val client = new ScalajHttpClient(token)

    onMessage { implicit msg =>
      println(msg.chat)
      reply(msg.text.getOrElse(""))
    }
  }

  def app[F[_] : Concurrent, K, V] = for {
    store <- Store.createInMemory[F, K, V]
    console = Console.apply[F]
    config = Config.apply[F]
    token <- config.token
    _ <- console.print("Store created.")
    bot = new EchoBot[F, K, V](token, store)
    _ <- Sync[F].delay(bot.run())
    _ <- Sync[F].delay(println("Bot Started. Enter any key to stop."))
    _ <- Sync[F].delay(StdIn.readLine())
    _ <- Sync[F].delay(bot.shutdown())
  } yield bot

  override def run(args: List[String]): IO[ExitCode] = app[IO, Long, Long] as ExitCode.Success
}
