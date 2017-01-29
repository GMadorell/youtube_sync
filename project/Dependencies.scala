import sbt._

object Dependencies {
  object Version {
    val typesafeConfig = "1.2.1"
    val rosHttp        = "2.0.1"
    val circe          = "0.7.0-M2"
    val pprint         = "0.4.3"
    val betterFiles    = "2.17.1"
    val scalaTest      = "2.2.4"
    val scalaMock      = "3.4.2"
  }

  private val typesafeConfig = "com.typesafe"         % "config"         % Version.typesafeConfig
  private val roshttp        = "fr.hmil"              %% "roshttp"       % Version.rosHttp
  private val circeCore      = "io.circe"             %% "circe-core"    % Version.circe
  private val circeParser    = "io.circe"             %% "circe-parser"  % Version.circe
  private val circeGeneric   = "io.circe"             %% "circe-generic" % Version.circe
  private val pprint         = "com.lihaoyi"          %% "pprint"        % Version.pprint
  private val betterFiles    = "com.github.pathikrit" %% "better-files"  % Version.betterFiles

  private val scalaTest = "org.scalatest" %% "scalatest"                   % Version.scalaTest
  private val scalaMock = "org.scalamock" %% "scalamock-scalatest-support" % Version.scalaMock

  val compile = Seq(
    typesafeConfig,
    roshttp,
    circeCore,
    circeParser,
    circeGeneric,
    pprint,
    betterFiles
  )

  val test = Seq(
    scalaTest % Test,
    scalaMock % Test
  )
}
