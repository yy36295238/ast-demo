package com.ast.service;

/**
 * @author yangyu
 * @date 2021/4/6 16:18
 */
public class HelloService {

    private NameService nameService;

    private UserService userService;

    public HelloService(NameService nameService, UserService userService) {
        this.nameService = nameService;
        this.userService = userService;
    }

    /**
     * 我是sayHello方法
     *
     * @param name 名称
     */
    public void sayHello(String name) {
        String newName = toUpper(nameService.findName(name));
        System.err.println(newName);
    }

    private String toUpper(String name) {
        return name.toUpperCase();
    }
}
