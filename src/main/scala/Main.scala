import akka.actor._
import ambients.{Arena, Start}
import events.SendWarrior

object Main extends App {
  val system = ActorSystem("MobSpawner")
  val level = 1
  val arena = system.actorOf(Props(classOf[Arena], system, level))
  arena ! Start
  arena ! SendWarrior
}
