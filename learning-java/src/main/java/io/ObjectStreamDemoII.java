package io;

import java.util.Date;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class ObjectStreamDemoII {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String path = ObjectStreamDemoII.class.getClassLoader().getResource(".").getPath();
        ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(path + "/obj.ser")));
        oos.writeUTF("hello");
        oos.writeInt(18);
        oos.writeBoolean(false);
        oos.writeChar('a');
        oos.writeObject("world");
        oos.writeObject(new Date());
        Employee e = new Employee("abc", 2000);
        oos.writeObject(e);
        oos.flush();
        oos.close();
        ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(path + "/obj.ser")));
        String s = ois.readUTF();
        int a = ois.readInt();
        boolean b = ois.readBoolean();
        char c = ois.readChar();
        Object o = ois.readObject();
        Object d = ois.readObject();
        Object m = ois.readObject();
        if (o instanceof String) {
            System.out.println((String)o);
        }
        if (d instanceof Date) {
            System.out.println((Date)d);
        }
        if (m instanceof Employee) {
            Employee p = (Employee)m;
            System.out.println("name->" + p.getName() + ",salary->" + p.getSalary());
        }
        ois.close();
    }

}
