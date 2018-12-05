package ru.daron.store

import cats.effect.concurrent.MVar
import cats.effect.syntax.bracket._
import cats.effect.{ExitCase, Sync}
import cats.syntax.functor._

class InMemoryStore[F[_], K, V](store: MVar[F, Map[K, V]])(implicit F: Sync[F]) extends Store[K, V, F] {
  override def get(id: K): F[Option[V]] = store.read.map(_.get(id))

  override def update(id: K, value: V): F[V] = store.take.bracketCase { map =>
    store.put(map.updated(id, value)).map(_ => value)
  } {
    case (_, ExitCase.Completed) => F.unit
    case (old, _) => store.put(old)
  }
}
