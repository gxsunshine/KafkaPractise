package com.gx.test;

public class StringTest {
    public static void main(String[] args) {
//        String s = "https://www.youtube.com/watch?v=OrGW9XpqCrA&pbjreload=101";
        String s = "com/watch?v=OrGW9XpqCrA&pbjreload=101";
        String s2 = "watch";
        System.out.println(s.indexOf(s2));
        System.out.println(s.split("watch")[1]);
    }
}
