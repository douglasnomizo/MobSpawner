import akka.actor._

import scala.concurrent.duration._
import scala.util.Random

case object Spawn
case object SpawnCreeper
case object SpawnZombie
case object SpawnSpider
case object SpawnBoss
case object Hit
case object Start
case object Tick

case class MonstersCount(value: String)

class ZombieSpawner extends Actor {
  def receive = {
    case Spawn =>
      println("Spawning Zombie! Run for your life!")
  }
  override def aroundPostStop = {
    println("Zombie Died!")
  }
}

class SpiderSpawner extends Actor {
  def receive = {
    case Spawn =>
      println("Spawning Spider! Nope! Nope! Nope!")
  }
  override def aroundPostStop = {
    println("Spider Died!")
  }
}

class CreeperSpawner extends Actor {
  def receive = {
    case Spawn =>
      println("Spawning Creeper! Boom!")
  }
  override def aroundPostStop = {
    println("Creeper Died!")
  }
}

class BossSpawner extends Actor {
  def receive = {
    case Spawn =>
      println("Spawning Boss!")
  }
  override def aroundPostStop = {
    println("Well done! You killed the Boss!")
  }
}

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
    case Hit =>
      val child:ActorRef = Random.shuffle(context.children).head
      child ! PoisonPill
      context.unwatch(child)
  }
}

class Arena(system: ActorSystem, level: Integer) extends Actor {
  val mobsFactor: Integer = level * 25
  val spawner = system.actorOf(Props[Spawner])

  def receive = {
    case Start =>
      (1 to mobsFactor) foreach (x => {
        implicit val types = List(SpawnZombie, SpawnSpider, SpawnCreeper, SpawnBoss)
        implicit val mobType = Random.shuffle(types).head
        spawner ! mobType
      })
    case Hit =>
      spawner ! Hit
  }
}

class Warrior(arena: ActorRef) extends Actor {
  import context.dispatcher
  
  val tick = context.system.scheduler.schedule(500 millis, 1000 millis, self, Tick)

  override def postStop() = tick.cancel()

  def receive = {
    case Tick =>
      arena ! Hit
  }
}

object Main extends App {
  val system = ActorSystem("MobSpawner")
  val level = 5
  val arena = system.actorOf(Props(classOf[Arena], system, level))
  arena ! Start
  val warrior = system.actorOf(Props(classOf[Warrior], arena))
}