import org.scoverage.coveralls.Imports.CoverallsKeys.*
import com.ossuminc.sbt.OssumIncPlugin

Global / onChangedBuildSource := ReloadOnSourceChanges

enablePlugins(OssumIncPlugin)

lazy val startYear: Int = 2024

def comptest(p: Project): ClasspathDependency =
  p % "compile->compile;test->test"

lazy val riddl: Project = Root("riddl-target-akka", startYr = startYear)
  .configure(
    With.typical,
    With.akka,
    With.build_info,
    With.coverage(90),
    With.publishing
  )
  .settings(
    coverageExcludedFiles := """<empty>;$anon""",
    scalaVersion := "3.4.1",
    buildInfoPackage := "com.ossuminc.riddl.target.akka",
    buildInfoObject := "RiddlTargetAkkaBuildInfo",
    description := "Various utilities used throughout riddl libraries"
  )
