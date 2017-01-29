import sbt.Configuration

name := """youtube_sync"""

version := "1.0"

Configuration.commonSettings

resourceDirectory in Compile := baseDirectory.value / "conf"

libraryDependencies ++= Dependencies.compile
libraryDependencies ++= Dependencies.test

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
