package basic

import java.awt.event._
import javax.swing._

object SAMConvertDemo extends App {

//  val f = new JFrame("Hello World")
//  val b = new JButton("Click Me")
//  b.setBounds(100, 100, 65, 30)
//  b.addActionListener(new ActionListener {
//    override def actionPerformed(event: ActionEvent): Unit = {
//      println("Click Me")
//    }
//  })
//  f.add(b)
//  f.setSize(300, 300)
//  f.setVisible(true)

  val f = new JFrame("Hello World")
  val b = new JButton("Click Me")
  b.setBounds(100, 100, 65, 30)
  implicit def getActionListener(actionProcessFunc: (ActionEvent) => Unit) = new ActionListener {
    override def actionPerformed(event: ActionEvent): Unit = {
      actionProcessFunc(event)
    }
  }
  b.addActionListener((event: ActionEvent) => println("Click Me"))
  f.add(b)
  f.setSize(300, 300)
  f.setVisible(true)

}
