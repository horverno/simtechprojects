package hu.puff.diffsolver.output;

import com.google.gson.Gson;
import hu.puff.diffsolver.input.InputData;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JSONParser {

    private static JSONParser mInstance;

    private JSONParser() {
    }

    public static JSONParser getInstance() {
        if (null == mInstance) {
            mInstance = new JSONParser();
        }
        return mInstance;
    }

    public SaveFile getDataFromJSON(File file) {
        SaveFile result = new SaveFile();

        Gson gson = new Gson();

        try {

            BufferedReader br = new BufferedReader(new FileReader(file));

            // convert the json string back to object
            result = gson.fromJson(br, SaveFile.class);

            System.out.println(result.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void dataToJSON(InputData data, List<MeasurementItem> list, File file) {
        Gson gson = new Gson();

        SaveFile saveFile = new SaveFile(data, list);
        // convert java object to JSON format,
        // and returned as JSON formatted string
        String json = gson.toJson(saveFile);

        try {
            // write converted json data to a file named "file.json"
            FileWriter writer = new FileWriter(file);
            // FileWriter writer = new FileWriter(path);
            writer.write(json);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(json);

        //
    }
}
