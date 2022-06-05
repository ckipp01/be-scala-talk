package example

class HelloSuite extends munit.FunSuite {
  test("numbers") {
    val obtained = 42
    val expected = 43
    val expected2 = 44
    assertEquals(obtained, expected2)
  }
}
