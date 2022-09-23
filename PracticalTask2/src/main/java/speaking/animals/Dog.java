package speaking.animals;

public class Dog implements Voice {
    @Override
    public void voice() {
        System.out.println("Bark!");
    }
}
