package com.ppfuns;

public class Test3 {
    static int i = 0;
    private static void t() throws InterruptedException {
        System.out.println(i++);
         t();
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(1.0/0.0);
        System.out.println(0.0/0.0);
    }
}
