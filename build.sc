import mill._, scalalib._

object example extends ScalaModule {
  def scalaVersion = "3.1.2"

  object test extends Tests with TestModule.Munit {
    def ivyDeps = Agg(ivy"org.scalameta::munit:0.7.29")
  }
}
