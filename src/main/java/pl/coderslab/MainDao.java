package pl.coderslab;

import pl.coderslab.entity.UserDao;

public class MainDao {
    public static void main(String[] args) {

        // ready to run test
        // unique emails provided

        User user = new User();
        user.setUserName("1");
        user.setEmail("boaAAb@pl.pl");
        user.setPassword("MAAyHiddenPasswird2");

        UserDao userDao = new UserDao();

        System.out.println("All users");
        User[] allUsers = userDao.findAll();
        for (User u : allUsers) {
            System.out.println(u);
        }

        System.out.println("I create user:" + user);
        userDao.create(user);
        System.out.println("All users");
        allUsers = userDao.findAll();
        for (User u : allUsers) {
            System.out.println(u);
        }
        System.out.println("I read user with id=6");
        System.out.println(userDao.read(6));

        user.setId(6);
        user.setEmail("BASQff@ww.fi");
        System.out.println("Updating with user" + user);
        System.out.println("I update user with id 6");
        System.out.println(user);
        userDao.update(user);
        System.out.println("Aftre update");
        System.out.println(userDao.read(6));

        System.out.println("All users");
        allUsers = userDao.findAll();
        for (User u : allUsers) {
            System.out.println(u);
        }
        
        System.out.println("I delete user with id =6");
        userDao.delete(6);
        System.out.println("All users");

        allUsers = userDao.findAll();
        for (User u : allUsers) {
            System.out.println(u);
        }

        userDao.read(6);
    }
}