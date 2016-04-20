package mobs

import akka.actor.Actor

class BossSpawner extends Actor {
  def receive = {
    case Spawn =>
      println("Spawning Boss!")
  }
  override def aroundPostStop = {
    println("Well done! You killed the Boss!")
  }
}
