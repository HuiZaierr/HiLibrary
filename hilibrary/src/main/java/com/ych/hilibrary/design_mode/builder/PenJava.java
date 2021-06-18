package com.ych.hilibrary.design_mode.builder;

/**
 * TODO：实现建造者模式，将负责的对象进行分离。
 */
public class PenJava {
    private Builder builder;

    public PenJava(Builder builder) {
        this.builder = builder;
    }

    public void write(){
        System.out.println("当前颜色："+builder.color);
        System.out.println("当前画笔宽度："+builder.width);
        System.out.println("当前画笔是否为圆角："+builder.round);
    }


    //Builder静态内部类
    public static class Builder{
        //定义Pen的属性
        private static String color = "white";
        private static Float width = 0.5f;
        private static Boolean round = true;

        public Builder color(String color){
            this.color = color;
            return this;
        }

        public Builder width(float width){
            this.width = width;
            return this;
        }

        public Builder round(boolean round){
            this.round = round;
            return this;
        }

        public PenJava build(){
            return new PenJava(this);
        }
    }
}
