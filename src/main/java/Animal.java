public class Animal {
    private String name;
    protected int age;

    public void eat() {
        System.out.println("Eating...");

    }
    public void sleep_and_eat() {
        System.out.println("Sleeping...");
        eat();
    }
}
