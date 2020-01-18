package io;

public class DecoratorDemoII {

    public static void main(String[] args) {
        Drink coffee = new Coffee();
        System.out.println(coffee.info() + " --> " + coffee.cost());
        Drink suger = new Suger(coffee);
        System.out.println(suger.info() + " --> " + suger.cost());
        Drink milk = new Milk(coffee);
        System.out.println(milk.info() + " --> " + milk.cost());
        milk = new Milk(suger);
        System.out.println(milk.info() + " --> " + milk.cost());
    }

}

interface Drink {

    double cost();
    String info();

}

class Coffee implements Drink {

    private String name = "original coffee";

    public double cost() {
        return 10;
    }

    public String info() {
        return name;
    }

}

abstract class DrinkDecorator implements Drink {

    private Drink drink;

    public DrinkDecorator(Drink drink) {
        this.drink = drink;
    }

    public double cost() {
        return this.drink.cost();
    }

    public String info() {
        return this.drink.info();
    }

}

class Milk extends DrinkDecorator {

    public Milk(Drink drink) {
        super(drink);
    }

    @Override
    public double cost() {
        return super.cost() * 4;
    }

    @Override
    public String info() {
        return super.info() + " add milk";
    }

}

class Suger extends DrinkDecorator {

    public Suger(Drink drink) {
        super(drink);
    }

    @Override
    public double cost() {
        return super.cost() * 2;
    }

    @Override
    public String info() {
        return super.info() + " add suger";
    }

}
