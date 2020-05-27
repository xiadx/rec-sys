package utils

import org.junit.Test
import utils.GLUtil

class TestGLUtil {

  @Test
  def testGetDistance(): Unit = {
    println(GLUtil.getDistance(0, 45, 0, 90))
    println(Math.PI * 6378.137 / 4)
  }

}
