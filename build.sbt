import com.github.retronym.SbtOneJar._
name := "ApkInfo"

oneJarSettings
version := "0.11"

scalaVersion := "2.12.1"
organization := "edu.colorado.plv.apkinfo"
resolvers += "Maven Central Repository" at "https://repo1.maven.org/maven2/"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"

libraryDependencies ++= Seq(
  "com.github.arguslab" % "saf-library_2.11" % "2.0.5",
  "com.github.scopt" %% "scopt" % "3.7.0",
  "org.scala-lang.modules" %% "scala-xml" % "1.2.0"
)