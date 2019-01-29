package ru.daron.store

import cats.MonadError
import cats.syntax.either._

import scala.util.Properties

trait Config[F[_]] {
  def token: F[String]
}

object Config {

  def apply[F[_]: MonadError[?[_], Throwable]]: Config[F] = new Config[F] {
    override def token: F[String] = {
      (Properties.envOrNone("TOKEN") match {
        case Some(t) => t.asRight[Throwable]
        case None    => new IllegalStateException("Token not found").asLeft[String]
      }).raiseOrPure[F]
    }
  }
}
