package com.ast.service;

/**
 * @author yangyu
 * @date 2021/4/6 16:19
 */
public class Main {
    public static void main(String[] args) {
        new HelloService(new NameService(), new UserService()).sayHello("张三");
    }

    /**
     * 我是静态方法 myMain()
     *
     * @param args
     */
    public static void myMain(String[] args) {
        new HelloService(new NameService(), new UserService()).sayHello("张三");
    }
}
