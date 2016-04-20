package mobs

import akka.actor.Actor

class SpiderSpawner extends Actor {
  def receive = {
    case Spawn =>
      println("Spawning Spider! Nope! Nope! Nope!")
  }
  override def aroundPostStop = {
    println("Spider Died!")
  }
}
