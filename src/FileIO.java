import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileIO {

    public void saveData(ArrayList<String> data, String path, String header) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(path));

            writer.println(header);

            for (String line : data) {
                writer.println(line);
            }

            writer.close();

        } catch (IOException e) {
            System.out.println("Error saving file: " + path);
        }
    }

    public ArrayList<String> readData(String path) {
        ArrayList<String> data = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(path));

            while (scanner.hasNextLine()) {
                data.add(scanner.nextLine());
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + path);
        }

        return data;
    }

    public String[] readData(String path, int length) {
        String[] data = new String[length];

        try {
            Scanner scanner = new Scanner(new File(path));

            int i = 0;

            while (scanner.hasNextLine() && i < length) {
                data[i] = scanner.nextLine();
                i++;
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + path);
        }

        return data;
    }
}
