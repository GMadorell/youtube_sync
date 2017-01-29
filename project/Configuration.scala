import sbt.{Tests, _}
import Keys.{excludeFilter, exportJars, _}
import org.scalafmt.sbt.ScalaFmtPlugin.autoImport._

object Configuration {
  val commonSettings = Seq(
    scalaVersion := "2.11.8",
    scalacOptions <<= version map { v: String =>
      val default = Seq(
        "-Xlint", // more warnings when compiling
        "-Xfatal-warnings", // warnings became errors
        "-unchecked", // more warnings. Strict
        "-deprecation", // warnings deprecation
        "-feature" // advise features
      )
      if (v.endsWith("SNAPSHOT")) default :+ "-Xcheckinit" else default // check against early initialization
    },
    javaOptions += "-Duser.timezone=UTC",
    fork in Test := true,
    parallelExecution in Test := true,
    testOptions in Test ++= Seq(
      Tests.Argument(TestFrameworks.ScalaTest, "-u", "target/test-reports"),
      Tests.Argument("-oDF")
    )
  )
}
