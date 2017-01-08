name := """youtube_sync"""

version := "1.0"

scalaVersion := "2.11.8"

resourceDirectory in Compile := baseDirectory.value / "conf"

libraryDependencies += "com.typesafe" % "config" % "1.2.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"

// Scalafmt
formatSbtFiles in Compile := false
formatSbtFiles in Test := false

// Command aliases
addCommandAlias("f", "scalafmt")
addCommandAlias("ft", "scalafmtTest")
addCommandAlias("tf", "test:scalafmt")
addCommandAlias("tft", "test:scalafmtTest")

addCommandAlias("format", ";f;tf")

addCommandAlias("prep", ";ft;tft")
