import speaking.animals.*;
public class SpeakingAnimalsTest {
    public static void main(String[] args) {
        Voice dog = new Dog();
        Voice cat = new Cat();
        Voice cow = new Cow();
        dog.voice();
        cat.voice();
        cow.voice();
    }
}
