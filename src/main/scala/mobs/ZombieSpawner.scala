package mobs

import akka.actor.Actor

class ZombieSpawner extends Actor {
  def receive = {
    case Spawn =>
      println("Spawning Zombie! Run for your life!")
  }
  override def aroundPostStop = {
    println("Zombie Died!")
  }
}
