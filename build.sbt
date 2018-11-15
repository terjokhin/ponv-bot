organization := "org.daron"

name := "ponv-bot"

version := "0.0.1"

scalaVersion := "2.12.7"

libraryDependencies += "com.bot4s" %% "telegram-core" % "4.0.0-RC2"

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