package pl.coderslab;

import pl.coderslab.entity.User;

public class MainDao {
    public static void main(String[] args) {

        User user1 = new User();
        user1.setEmail("fdffdfd@.pl");
        user1.setUserName("jan");
        user1.setPassword("pass");

        User newUser = new User();
        newUser = newUser.read(4);
//        System.out.println("user1 = " + user1.create(user1));

        newUser.setUserName("Pan");
        newUser.setEmail("fdddAAfmsf@.pl");
        newUser.setPassword("pppdsapaa");
        System.out.println(newUser);
        //    newUser.update(newUser);
        newUser.create(newUser);
        User[] users = newUser.findAll();
        for (User user : users) {
            System.out.println("user = " + user);
        }
     //   System.out.println(user1.read(2));
    }
}