package basic

object ImplicitConversionDemoIII {

  def main(args: Array[String]): Unit = {
    class SpecialPerson(val name: String)
    class Student(val name: String)
    class Older(val name: String)
    class Teacher(val name: String)
    implicit def object2SpecialPerson(obj: Object): SpecialPerson = {
      if (obj.getClass == classOf[Student]) { val stu = obj.asInstanceOf[Student]; new SpecialPerson(stu.name) }
      else if (obj.getClass == classOf[Older]) { val older = obj.asInstanceOf[Older]; new SpecialPerson(older.name) }
      else null
    }
    class TicketHouse {
      var ticketNumber = 0
      def buySpecialTicket(p: SpecialPerson) = {
        ticketNumber += 1
        "T-" + ticketNumber
      }
    }
    val th = new TicketHouse
    val s = new Student("leo")
    println(th.buySpecialTicket(s))
    val o = new Older("jack")
    println(th.buySpecialTicket(o))
    val t = new Teacher("tom")
    println(th.buySpecialTicket(t))
  }

}
