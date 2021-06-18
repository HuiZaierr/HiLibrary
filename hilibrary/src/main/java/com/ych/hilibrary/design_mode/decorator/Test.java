package com.ych.hilibrary.design_mode.decorator;

public class Test {
    public static void main(String[] args) {
        Panda panda = new Panda();
        BambooFood bambooFood = new BambooFood(panda);
        CarrotFood carrotFood = new CarrotFood(bambooFood);
        carrotFood.eat();
    }
}
