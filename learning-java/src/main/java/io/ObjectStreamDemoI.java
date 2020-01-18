package io;

import java.util.Date;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class ObjectStreamDemoI {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(baos));
        oos.writeUTF("hello");
        oos.writeInt(18);
        oos.writeBoolean(false);
        oos.writeChar('a');
        oos.writeObject("world");
        oos.writeObject(new Date());
        Employee e = new Employee("abc", 2000);
        oos.writeObject(e);
        oos.flush();
        byte[] bs = baos.toByteArray();
        System.out.println(bs.length);
        ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new ByteArrayInputStream(bs)));
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
    }

}

class Employee implements java.io.Serializable {

    private transient String name;
    private double salary;

    public Employee() {

    }

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

}
