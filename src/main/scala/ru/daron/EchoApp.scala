package ru.daron

import com.bot4s.telegram.api.declarative.Commands
import com.bot4s.telegram.api.{Polling, TelegramBot}
import com.bot4s.telegram.clients.ScalajHttpClient

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.io.StdIn
import scala.util.Properties

object EchoApp {

  class EchoBot(val token: String) extends TelegramBot with Polling with Commands {
    val client = new ScalajHttpClient(token)

    onMessage { implicit msg =>
      reply(msg.text.getOrElse(""))
    }
  }

  def main(args: Array[String]): Unit = {
    Properties.envOrNone("TOKEN").fold(println("Cannot find token. Exit")) { token =>
      val bot = new EchoBot(token)
      println("Starting.")
      val runningFuture = bot.run()
      println("Press [ENTER] to stop bot.")
      StdIn.readLine()
      println("Stopping.")
      bot.shutdown()
      Await.result(runningFuture, Duration.Inf)
    }
  }
}
