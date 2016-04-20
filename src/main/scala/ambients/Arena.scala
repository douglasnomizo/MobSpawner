package ambients

import akka.actor.{Actor, ActorSystem, PoisonPill, Props}
import events.{SendWarrior, StopWarrior}
import heros.{Punch, Warrior}

import scala.util.Random

class Arena(system: ActorSystem, level: Integer) extends Actor {
  val mobsFactor: Integer = level * 5
  val spawner = system.actorOf(Props[Spawner])

  def receive = {
    case Start =>
      (1 to mobsFactor) foreach (x => {
        implicit val types = List(SpawnZombie, SpawnSpider, SpawnCreeper, SpawnBoss)
        implicit val mobType = Random.shuffle(types).head
        spawner ! mobType
      })
    case SendWarrior =>
      context.actorOf(Props(classOf[Warrior]), "warrior")
    case StopWarrior =>
      context.child("warrior").map(w => {
        println("All monsters were destroyed! The arena is safe now. Thanks!")
        w ! PoisonPill
      })
    case Punch =>
      spawner ! Punch
  }
}
