name := "MobSpawner"

version := "1.0"

scalaVersion := "2.11.8"

resolvers += "typesafe" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
"com.typesafe.akka" %% "akka-actor" % "2.4.4",
"com.typesafe.slick" %% "slick" % "3.1.1",
"org.slf4j" % "slf4j-nop" % "1.6.4"
)



    