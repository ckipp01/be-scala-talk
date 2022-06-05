//> using lib "org.scalameta::munit::1.0.0-M4"

package example

class MainTest extends munit.FunSuite:
  test("greeting") {
    val greeting = example.greeting("BE")
    assertEquals(greeting, "Hello there BE!")

  }
