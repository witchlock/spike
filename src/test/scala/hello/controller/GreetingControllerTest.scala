package hello.controller

import org.scalatest.FlatSpec

class GreetingControllerTest extends FlatSpec {
  val greetingController = new GreetingController
  "Greeting Controller greeting method" should " return `Hello World~`" in {
    assert(greetingController.greeting() == "Hello World~")
  }
}