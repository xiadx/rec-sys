package io;

public class DecoratorDemoI {

    public static void main(String[] args) {
        Person p = new Person();
        p.say();
        Amplifier am = new Amplifier(p);
        am.say();
    }

}

interface Say {

    void say();

}

class Person implements Say {

    private int voice = 10;

    public void say() {
        System.out.println("person voice " + this.getVoice() + "db");
    }

    public int getVoice() {
        return voice;
    }

    public void setVoice(int voice) {
        this.voice = voice;
    }

}

class Amplifier implements Say {

    private Person p;

    Amplifier (Person p) {
        this.p = p;
    }

    public void say() {
        System.out.println("amplifier voice " + p.getVoice() * 100 + "db");
    }

}
