import java.util.logging.Filter;

public class Main {

    public void main() {

        TextUI ui = new TextUI();
        FileIO io = new FileIO();

        StreamingService s = new StreamingService(ui, io);
        s.startSession();

    }
}
