package hello.controller

import database.controller.IndexController
import database.model.Store
import org.scalatest.FlatSpec

/**
 * Created by xiachen on 3/8/15.
 */
class IndexControllerTest extends FlatSpec {
  val indexController = new IndexController

  "Index Controller " should "parse a json string exactly" in {
    Store.clear()
    val json1 = "{\"name\": \"user\", \"data\": [{\"name\":\"jack\", \"age\":18}]}"

    assert(!indexController.create(json1).contains("JSON Parsed Failed: "))
  }

  "Index Controller " should "create a database by a json" in {
    Store.clear()
    val json1 = "{\"name\": \"user\", \"data\": [{\"name\":\"jack\", \"age\":18}]}"
    indexController.create(json1)

    val jsonObject = Store.pop()
    assert(jsonObject.getName == "user")
    assert(jsonObject.getData.get(0).get("name") == "jack")
    assert(jsonObject.getData.get(0).get("age").asInstanceOf[Integer] == 18)

    assert(jsonObject.getData.get(0).get("id").asInstanceOf[Integer] == 0)
    assert(jsonObject.getData.get(0).get("created_at").asInstanceOf[Long] <= System.currentTimeMillis())

    val json2 = "{\"name\": \"book\", \"data\": [{\"name\":\"hello\", \"price\":21}]}"
    indexController.create(json2)
    val jsonObject2 = Store.pop()
    assert(jsonObject2.getName == "book")
    assert(jsonObject2.getData.get(0).get("name") == "hello")
    assert(jsonObject2.getData.get(0).get("price").asInstanceOf[Integer] == 21)

    assert(jsonObject2.getData.get(0).get("id").asInstanceOf[Integer] == 0)
    assert(jsonObject2.getData.get(0).get("created_at").asInstanceOf[Long] <= System.currentTimeMillis())
  }

  "Index Controller" should "update existed database by a json" in {
    Store.clear()

    val json1 = "{\"name\": \"user\", \"data\": [{\"name\":\"jack\", \"age\":18}]}"
    indexController.create(json1)

    val json2 = "{\"name\": \"user\", \"data\": [{\"id\": 0, \"name\":\"rose\", \"age\":32}]}"
    indexController.create(json2)

    val jsonObject = Store.pop()
    assert(Store.stores.size() == 1)
    assert(jsonObject.getName == "user")
    assert(jsonObject.getData.get(0).get("name") == "rose")
    assert(jsonObject.getData.get(0).get("age").asInstanceOf[Integer] == 32)

    assert(jsonObject.getData.get(0).get("id").asInstanceOf[Integer] == 0)
    assert(jsonObject.getData.get(0).get("created_at").asInstanceOf[Long] <= System.currentTimeMillis())
  }

  "Index Controller" should "add existed database by a json without id" in {
    Store.clear()

    val json1 = "{\"name\": \"user\", \"data\": [{\"name\":\"jack\", \"age\":18}]}"
    indexController.create(json1)

    val json2 = "{\"id\":0, \"name\": \"user\", \"data\": [{\"name\":\"rose\", \"age\":32}]}"
    indexController.create(json2)

    assert(Store.stores.size() == 1)
    assert(Store.stores.get(0).getData.size() == 2)
  }

  "Index Controller " should "parse a no json string failed" in {
    val json1 = "Hello World"
    assert(indexController.create(json1).contains("JSON Parsed Failed: "))

    val json2 = "{'name':'jack', 'age':18}"
    assert(indexController.create(json2).contains("JSON Parsed Failed: "))
  }
}
