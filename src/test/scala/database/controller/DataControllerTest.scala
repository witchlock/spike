package database.controller

import database.model.Store
import org.scalatest.FlatSpec
import org.springframework.mock.web.MockHttpServletRequest

/**
 * Created by xiachen on 3/8/15.
 */
class DataControllerTest extends FlatSpec {
  val dataController = new DataController
  val indexController = new IndexController
  val json1 = "{\"name\": \"user\", \"data\": [{\"name\":\"jack\", \"age\":18}]}"
  val mockHttpRequest = new MockHttpServletRequest
  indexController.create(json1)

  "Data Controller" should "get user by id 0" in {
    mockHttpRequest.setServletPath("/data/user/0")
    val user0 = Store.parseMap(dataController.get(mockHttpRequest))
    assert(user0 != null)
    assert(user0.get("name") == "jack")
    assert(user0.get("age") == 18)
  }
  "Data Controller" should "get all user by user path" in {
    mockHttpRequest.setServletPath("/data/user/")
    indexController.create(json1)
    indexController.create(json1)
    indexController.create(json1)

    val user = Store.parseList(dataController.getAll(mockHttpRequest))
    assert(user.size() == 4)
  }

  "Data Controller" should "delete user by user id" in {
    mockHttpRequest.setServletPath("/data/user/0")
    dataController.delete(mockHttpRequest)
    val user0 = Store.parseMap(dataController.get(mockHttpRequest))
    assert(user0 == null)
  }

  "Data Controller" should "add a user by post user data" in {
    mockHttpRequest.setServletPath("/data/user/")
    val json1 = "{\"name\":\"rose\", \"age\":32}"

    dataController.post(json1, mockHttpRequest)
    val user = Store.parseList(dataController.getAll(mockHttpRequest))

    val dataId = user.size() - 1
    assert(user.get(dataId).get("name") == "rose")
    assert(user.get(dataId).get("age") == 32)
  }
  "Data Controller" should "update a user by put user data" in {
    mockHttpRequest.setServletPath("/data/user/1")
    val json1 = "{\"name\":\"rose\", \"age\":32}"

    dataController.put(json1, mockHttpRequest)

    val user0 = Store.parseMap(dataController.get(mockHttpRequest))
    assert(user0.get("name") == "rose")
    assert(user0.get("age") == 32)
  }
}
