package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserServiceImpl userService = new UserServiceImpl();
        userService.dropUsersTable();
        userService.createUsersTable();
        userService.saveUser("Vitaliy", "Rud", (byte) 228);
        userService.saveUser("Daria", "Rud", (byte) 29);
        userService.saveUser("Tamara", "Rud", (byte) 80);
        userService.saveUser("Ekaterina", "Rud", (byte) 16);
        System.out.println(userService.getAllUsers());

    }
}
