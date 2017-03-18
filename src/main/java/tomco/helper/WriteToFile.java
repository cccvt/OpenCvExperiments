package tomco.helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * Created by tomco on 20/01/2017.
 */
public class WriteToFile {

    public static void main(String[] args) {
        StringBuilder builder = new StringBuilder();

        for(int i = 0 ; i < 977; i++) {
            builder.append("ngs/");
            builder.append(i);
            builder.append(".jpg");
            builder.append("\n");
        }

        File test = new File("bg.txt");
        writeToFile(test, builder.toString());
    }

    private static void writeToFile(File filename, String json) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filename));
            writer.write(json);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the writer regardless of what happens...
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
