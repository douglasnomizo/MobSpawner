package ambients

import java.util.NoSuchElementException

import akka.actor.{Actor, ActorRef, PoisonPill, Props}
import events.StopWarrior
import heros.Punch
import mobs._

import scala.util.Random

class Spawner extends Actor {
  def receive = {
    case SpawnZombie =>
      context.actorOf(Props[ZombieSpawner]) ! Spawn
    case SpawnSpider =>
      context.actorOf(Props[SpiderSpawner]) ! Spawn
    case SpawnCreeper =>
      context.actorOf(Props[CreeperSpawner]) ! Spawn
    case SpawnBoss =>
      context.actorOf(Props[BossSpawner]) ! Spawn
    case Punch =>
      try {
        val child: ActorRef = Random.shuffle(context.children).head
        child ! PoisonPill
        context.unwatch(child)
      } catch {
        case e:NoSuchElementException => sender ! StopWarrior
      }
  }
}
