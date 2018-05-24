package com.hjc.netty.test;

/**
 * @author : Administrator
 * @date : 2018/5/24 0024 17:36
 * @description : 多参数内部构建类--构造模式（待确认）
 */
public class BuilderModelTest {
    private String name;

    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public static final class Builder {
        private String name;
        private Integer age;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAge(Integer age) {
            this.age = age;
            return this;
        }

        public BuilderModelTest builder() {
            return new BuilderModelTest(this);

        }

    }

    public static Builder newBuilder() {
        return new Builder();
    }

    private BuilderModelTest(Builder builder) {
        age = builder.age;
        name = builder.name;
    }

    public static void main(String[] args) {
        boolean isSame = BuilderModelTest.newBuilder().equals(BuilderModelTest.newBuilder());
        System.out.println(isSame);
        System.out.println(BuilderModelTest.newBuilder().setName("asdff").builder().getName());
        System.out.println(BuilderModelTest.newBuilder().setName("12122").builder().getName());
    }
}
