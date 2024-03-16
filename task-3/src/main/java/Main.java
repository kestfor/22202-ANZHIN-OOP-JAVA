public class Main {
    public static void main(String[] args) {
        Settings settings = new Settings(10, 20, 40, 720, 1280);
        Game game = new Game(settings);
        game.run();
    }
}
