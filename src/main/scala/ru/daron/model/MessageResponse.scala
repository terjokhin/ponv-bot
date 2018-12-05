package ru.daron.model

trait MessageResponse

case class KamilExcitedResponse(howMuch: Double, username: String) extends MessageResponse {
  override def toString: String =
    s"@$username Поздравляю! " + f"$howMuch%2.2f" + " Камилей по шкале амзхамзхамзхамзхам."
}

case object EmptyResponse extends MessageResponse
