package basic

object BoundsDemoV {

  def main(args: Array[String]): Unit = {
    class Meat(val name: String)
    class Vegetable(val name: String)
    def packageFood[T: Manifest](food: T*) = {
      val foodPackage = new Array[T](food.length)
      for (i <- 0 until food.length) foodPackage(i) = food(i)
      foodPackage
    }
    val gongbaojiding = new Meat("gongbaojiding")
    val yuxiangrousi = new Meat("yuxiangrousi")
    val shousiyangpai = new Meat("shousiyangpai")
    val meatPackage = packageFood(gongbaojiding, yuxiangrousi, shousiyangpai)
    val qingcai = new Vegetable("qingcai")
    val baicai = new Vegetable("baicai")
    val huanggua = new Vegetable("huanggua")
    val vegetablePackage = packageFood(qingcai, baicai, huanggua)
    println(meatPackage)
    println(vegetablePackage)
  }

}
