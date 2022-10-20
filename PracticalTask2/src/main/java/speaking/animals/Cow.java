package speaking.animals;

public class Cow implements Voice {
    @Override
    public void voice() {
        System.out.println("Moo!");
    }
}
