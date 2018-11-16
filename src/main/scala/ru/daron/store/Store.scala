package ru.daron.store

import cats.effect.Concurrent
import cats.effect.concurrent.MVar
import cats.syntax.functor._
import cats.tagless.autoFunctorK

@autoFunctorK(true)
trait Store[K, V, F[_]] {
  def get(id: K): F[Option[V]]

  def update(id: K, value: V): F[V]
}

object Store {
  def createInMemory[F[_] : Concurrent, K, V]: F[Store[K, V, F]] =
    MVar.of[F, Map[K, V]](Map.empty[K, V]).map {
      new InMemoryStore[F, K, V](_)
    }
}