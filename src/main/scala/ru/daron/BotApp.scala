package ru.daron

import cats.effect.IO._
import cats.effect._
import cats.syntax.flatMap._
import cats.syntax.functor._
import ru.daron.bot.PonvBot
import ru.daron.store.{Config, Store}

import scala.io.StdIn

object BotApp extends IOApp {

  def app[F[_] : ConcurrentEffect, K, V] = for {
    store <- Store.createInMemory[F, K, V]
    console = Console.apply[F]
    config = Config.apply[F]
    token <- config.token
    _ <- console.print("Store created.")
    bot = new PonvBot[F, K, V](token, store, console)
    _ <- Sync[F].delay(bot.run())
    _ <- console.print("Bot Started. Enter any key to stop.")
    _ <- Sync[F].delay(StdIn.readLine())
    _ <- Sync[F].delay(bot.shutdown())
  } yield bot

  override def run(args: List[String]): IO[ExitCode] = app[IO, Long, Long] as ExitCode.Success
}
