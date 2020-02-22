import feature.scala.utils.ConfigUtil
import org.junit.Test

class TestConfigUtil {

  @Test
  def testGetFeatureMap(): Unit = {
    println(ConfigUtil.getFeatureMap("w1"))
  }

}
