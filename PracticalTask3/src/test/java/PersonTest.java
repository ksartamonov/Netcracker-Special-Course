import name.middlename.surname.Person;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

public class PersonTest {
    public static void main(String[] args) {
        Person p1 = new Person("Ivanov", "Ivan", "Ivanovich");
        Person p2 = new Person("Justin", "Bieber");
        Person p3 = new Person("Viktorov");

        long startTime = System.nanoTime();
        System.out.println(p1.toString());
        System.out.println(p2.toString());
        System.out.println(p3.toString());
        long time = System.nanoTime() - startTime;
        System.out.println("Running time[using StringBuilder]: " + time + " ns.");

        startTime = System.nanoTime();
        System.out.println(p1.slowToString());
        System.out.println(p2.slowToString());
        System.out.println(p3.slowToString());
        time = System.nanoTime() - startTime;
        System.out.println("Running time[without StringBuilder]: " + time + " ns.");
    }
}
