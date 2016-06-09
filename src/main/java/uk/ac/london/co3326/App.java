package uk.ac.london.co3326;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.google.gson.Gson;

public class App {
    
    public static Properties prop = new Properties();

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Test file expected");
            return;
        }
        
        //load the properties file to print the student's details
        prop.load(App.class.getClassLoader().getResourceAsStream("config.properties"));
        System.out.println(prop.getProperty("student.name"));
        System.out.println(prop.getProperty("student.srn"));
        
        Gson gson = new Gson();
        // read the test file line by line
        try (BufferedReader in = new BufferedReader(new FileReader(args[0]))) {
            String line;
            while ((line = in.readLine()) != null) {
                try {
                    // convert the JSON string back to object
                    Cw1 cw = gson.fromJson(line, Cw1.class);
                    cw.demonstrate();
                    System.out.println(gson.toJson(cw));
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        
    }

}
