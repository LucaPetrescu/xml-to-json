package lucapetrescu;

import java.io.IOException;

public class App {
    public static int PRETTY_PRINT_INDENT_FACTOR = 4;

    public static void main(String[] args) throws IOException {
        try {
            XMLtoJSON xmltojson = new XMLtoJSON("info.xml");
            xmltojson.convertToJson();
        } catch (IOException e) {
            System.out.println("Something went wrong" + e.getMessage());
        }

    }
}
