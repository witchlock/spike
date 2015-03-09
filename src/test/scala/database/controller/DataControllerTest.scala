package database.controller

import org.scalatest.FlatSpec


/**
 * Created by xiachen on 3/8/15.
 */
class DataControllerTest extends FlatSpec {
  val dataController = new DataController
  val indexController = new IndexController
  val json1 = "{\"name\": \"user\", \"data\": [{\"name\":\"jack\", \"age\":18}]}"
  indexController.create(json1)

  "Data Controller" should "get user by id 0" in {
  }
}
