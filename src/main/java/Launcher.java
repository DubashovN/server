import java.util.Arrays;

public class Launcher {
    public static void main(String[] args) {
        new Thread(() -> {
            Server.main(null);
        }).start();
        Arrays.asList(1, 2, 3).forEach(ignore -> new Thread(() -> {
            Main.main(null);
        }).start());
    }
}
