package peaksoft;

import peaksoft.service.UserService;
import peaksoft.service.UserServiceImpl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService service = new UserServiceImpl();
        Scanner scanner = new Scanner(System.in);
        loop:
        while (true) {
            System.out.println("""
                    0. exit
                    1. create table
                    2. drop table
                    3. save user
                    4. delete user
                    5. get all
                    6. clean table
                    """);
            switch (scanner.nextInt()) {
                case 0 -> {break loop;}
                case 1 -> service.createUsersTable();
                case 2 -> service.dropUsersTable();
                case 3 -> service.saveUser("Nurislam", "Toigonbaev", (byte) 25);
                case 4 -> service.removeUserById(3);
                case 5 -> System.out.println(service.getAllUsers());
                case 6 -> service.cleanUsersTable();
            }
        }
    }
}
