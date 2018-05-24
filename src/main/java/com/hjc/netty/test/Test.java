package com.hjc.netty.test;

public class Test {

    private static int MAXIMUM_CAPACITY = 1<<30;

    public static void main1(String[] args) {
        int n = 0;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        int result = (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
        System.out.println(result);
        System.out.println(-1>>>1);
        System.out.println(1|0);
        System.out.println("msg".hashCode());
        System.out.println("result".hashCode());

        System.out.println((int)1.5);

        Integer a = 121;//new Integer(3);
        Integer b = 121;//new Integer(3);
        System.out.println(a == b);
    }

    public static void main(String[] args) {
        int a = 700;
            StringBuilder stringBuilder = new StringBuilder();
//            stringBuilder.append("1212中文12");
            System.out.println(stringBuilder.toString());
        System.out.println(a*12*20);
    }
}
