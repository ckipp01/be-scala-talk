//> using scala "3.1.2"

package example

def greeting(who: String) = s"Hello there $who!"

@main def hello() =
  println(greeting("BE"))
