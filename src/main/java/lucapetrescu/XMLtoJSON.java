package lucapetrescu;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

public class XMLtoJSON {

    private String xmlFileName;
    public static int PRETTY_PRINT_INDENT_FACTOR = 4;

    public XMLtoJSON(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    public void convertToJson() throws IOException {
        try {
            File xmlFile = new File(this.xmlFileName);
            String xml = new String(Files.readAllBytes(xmlFile.toPath()), StandardCharsets.UTF_8);
            JSONObject xmlJSONObject = XML.toJSONObject(xml);

            String jsonPrettyPrintString = xmlJSONObject.toString(PRETTY_PRINT_INDENT_FACTOR);
            JSONObject newJsonObject = new JSONObject(jsonPrettyPrintString);
            JSONObject JSONPurchaseOrder = new JSONObject(newJsonObject.get("PurchaseOrder").toString());
            JSONObject JSONItems = new JSONObject(JSONPurchaseOrder.get("Items").toString());
            JSONArray arr = (JSONArray) JSONItems.get("Item");
            JSONArray newArr = new JSONArray();
            for (int i = 0; i < arr.length(); i++) {
                JSONObject object = (JSONObject) arr.get(i);
                newArr.put(object);
            }
            JSONPurchaseOrder.remove("Items");
            JSONPurchaseOrder.put("Items", newArr);
            newJsonObject.remove("PurchaseOrder");
            newJsonObject.put("PurchaseOrder", JSONPurchaseOrder);
            FileWriter file = new FileWriter("info.json");
            file.write(newJsonObject.toString(PRETTY_PRINT_INDENT_FACTOR));
            file.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
