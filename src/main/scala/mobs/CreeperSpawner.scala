package mobs

import akka.actor.Actor

class CreeperSpawner extends Actor {
  def receive = {
    case Spawn =>
      println("Spawning Creeper! Boom!")
  }
  override def aroundPostStop = {
    println("Creeper Died!")
  }
}
