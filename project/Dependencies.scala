import sbt._

object Dependencies {
  object Version {
    val typesafeConfig = "1.2.1"
    val roshttp        = "2.0.1"
    val circe          = "0.7.0-M2"
    val pprint         = "0.4.3"
    val scalatest      = "2.2.4"
  }

  private val typesafeConfig = "com.typesafe"  % "config"         % Version.typesafeConfig
  private val roshttp        = "fr.hmil"       %% "roshttp"       % Version.roshttp
  private val circeCore      = "io.circe"      %% "circe-core"    % Version.circe
  private val circeParser    = "io.circe"      %% "circe-parser"  % Version.circe
  private val circeGeneric   = "io.circe"      %% "circe-generic" % Version.circe
  private val pprint         = "com.lihaoyi"   %% "pprint"        % Version.pprint
  private val scalatest      = "org.scalatest" %% "scalatest"     % Version.scalatest

  val compile = Seq(
    typesafeConfig,
    roshttp,
    circeCore,
    circeParser,
    circeGeneric,
    pprint
  )

  val test = Seq(
    scalatest % Test
  )
}
