package hello.controller

import hello.model.Store
import org.scalatest.FlatSpec

/**
 * Created by xiachen on 3/8/15.
 */
class IndexControllerTest extends FlatSpec{
  val indexController = new IndexController

  "Index Controller " should "parse a json string exactly" in {
    val json = "{\"name\":\"jack\", \"age\":18}"

    assert(indexController.index(json) == "OK")
    val jsonObject = Store.pop()
    assert(jsonObject.get("name") == "jack")
    assert(jsonObject.get("age") == 18)

    assert(jsonObject.get("id") == 0)
    assert(jsonObject.get("created_at").asInstanceOf[Long] <= System.currentTimeMillis())

    indexController.index(json)
    val jsonObject1 = Store.pop()
    assert(jsonObject1.get("id") == 1)
  }

  "Index Controller " should "parse a no json string failed" in {
    val json1 = "Hello World"
    assert(indexController.index(json1) != "OK")

    val json2 = "{'name':'jack', 'age':18}"
    assert(indexController.index(json2) != "OK")
  }
}
