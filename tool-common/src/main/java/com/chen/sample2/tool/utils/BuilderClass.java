package com.chen.sample2.tool.utils;

/**
 * @desc
 * @Author Chentian
 * @date 2021/8/5
 */
public class BuilderClass {
    private final int id;
    private final String name;
    private final int age;
    private final String sex;

    public static class Builder {
        //必填参数
        private final int id;
        //可选参数
        private String name;
        private int age;
        private String sex;

        public Builder (int id) {
            this.id = id;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder sex(String sex) {
            this.sex = sex;
            return this;
        }

        public BuilderClass build() {
            return new BuilderClass(this);
        }
    }

    private BuilderClass(Builder builder) {
        id = builder.id;
        name = builder.name;
        age = builder.age;
        sex = builder.sex;
    }

    public static void main(String[] args) {
        //静态内部类可以直接创建对象,new A.B();
        BuilderClass builderClass = new BuilderClass
                .Builder(666)
                .name("Fisher").age(18).sex("male").build();
        System.out.println("builderClass:{"+builderClass.id+"-"+builderClass.name+"-"+builderClass.age+"-"+builderClass.sex+"}");
    }
}
