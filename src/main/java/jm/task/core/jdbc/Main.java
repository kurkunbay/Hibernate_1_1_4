package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SessionFactory;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Ivan", "Ivanov", (byte) 17);
        userService.saveUser("Petr", "Petrov", (byte) 26);
        userService.saveUser("Peter", "Pan", (byte) 35);
        userService.saveUser("Sam", "Angry", (byte) 126);

        System.out.println(userService.getAllUsers().toString());

        userService.removeUserById(1);

        userService.dropUsersTable();
    }
}
