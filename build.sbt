organization := "org.daron"

name := "ponv-bot"

version := "0.0.1"

scalaVersion := "2.12.7"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-tagless-macros" % "0.1.0",
  "com.bot4s" %% "telegram-core" % "4.0.0-RC2",
  "org.typelevel" %% "cats-effect" % "1.0.0",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0"
)

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-language:experimental.macros",
  "-language:higherKinds",
  "-language:postfixOps",
  "-feature",
  "-unchecked",
  "-Xfuture",
  "-Yno-adapted-args",
  "-Ywarn-numeric-widen",
  "-Ypartial-unification"
)

addCompilerPlugin("org.scalameta" % "paradise" % "3.0.0-M11" cross CrossVersion.patch)
addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.8")
addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.2.4")