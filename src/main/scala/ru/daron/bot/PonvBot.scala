package ru.daron.bot

import com.bot4s.telegram.api.declarative.Commands
import com.bot4s.telegram.api.{Polling, TelegramBot}
import com.bot4s.telegram.clients.ScalajHttpClient
import ru.daron.store.Store

class PonvBot[F[_], K, V](token: String, store: Store[K, V, F]) extends TelegramBot
  with Polling
  with Commands {

  val client = new ScalajHttpClient(token)

  onMessage { implicit msg =>
      println(msg.chat.toString)
      reply(msg.text.getOrElse(""))
  }
}
