package heros

import akka.actor.Actor
import scala.concurrent.duration._

case object Attack

class Warrior extends Actor {
  import context.dispatcher
  val tick = context.system.scheduler.schedule(200 millis, 500 millis, self, Attack)

  override def postStop() = {
    tick.cancel()
    context.system.terminate()
  }

  def receive = {
    case Attack =>
      context.parent ! Punch
  }
}
