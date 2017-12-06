import sbt._

object Dependencies {
  object Version {
    val betterFiles    = "2.17.1"
    val circe          = "0.9.0-M2"
    val pprint         = "0.4.3"
    val rosHttp        = "2.0.1"
    val scalaMock      = "3.4.2"
    val scalaTest      = "2.2.4"
    val typesafeConfig = "1.2.1"
    val cats           = "1.0.0-RC1"
  }

  private val betterFiles    = "com.github.pathikrit" %% "better-files"                % Version.betterFiles
  private val circeCore      = "io.circe"             %% "circe-core"                  % Version.circe
  private val circeGeneric   = "io.circe"             %% "circe-generic"               % Version.circe
  private val circeParser    = "io.circe"             %% "circe-parser"                % Version.circe
  private val pprint         = "com.lihaoyi"          %% "pprint"                      % Version.pprint
  private val roshttp        = "fr.hmil"              %% "roshttp"                     % Version.rosHttp
  private val scalaMock      = "org.scalamock"        %% "scalamock-scalatest-support" % Version.scalaMock
  private val scalaTest      = "org.scalatest"        %% "scalatest"                   % Version.scalaTest
  private val typesafeConfig = "com.typesafe"         % "config"                       % Version.typesafeConfig
  private val catsCore       = "org.typelevel"        %% "cats-core"                   % Version.cats

  val prod = Seq(
    betterFiles,
    circeCore,
    circeParser,
    circeGeneric,
    pprint,
    roshttp,
    typesafeConfig,
    catsCore
  )

  val test = Seq(
    scalaTest % Test,
    scalaMock % Test
  )
}
