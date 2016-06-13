package uk.ac.london.co3326.harness;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.google.gson.Gson;

import uk.ac.london.co3326.Coursework;
import uk.ac.london.co3326.Cw1;

public class Harness<T extends Coursework> {
	
    public static final Class<? extends Coursework> CLASS = Cw1.class;

    private List<Student<? extends Coursework>> results = new ArrayList<>();
	private static transient Harness<? extends Coursework> instance = null;
	private transient T etalon = null;
	private transient Gson gson = new Gson(); 
	
	private Harness() {}
	
	public static <T> Harness<? extends Coursework> i() {
	    if (instance == null)
	        instance = new Harness<>();
	    return instance;
	}

	public T getEtalon() {
	    if (etalon != null) {
	        return etalon;	        
	    }
	    
        // set up the etalon
        try (BufferedReader in = new BufferedReader(new FileReader(TestSuite.getFile()))) {
            String line;
            while ((line = in.readLine()) != null) {
                try {
                    // convert the JSON string back to object
                    etalon = gson.fromJson(line, CLASS);
                    etalon.demonstrate();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return etalon;	    
	}
	
	public static void main(String[] args) throws IOException {		
		Options options = new Options();
		options.addOption("f", "file", true, "Folder, where the coursework JARs are located or coursework JAR, that is to be tested.");
        options.addOption("t", "test", true, "Test file (default: test.txt)");
		
        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine commandLine = parser.parse(options, args);
            
            if (args.length == 0)
                throw new ParseException("No arguments provided");
            if (commandLine.hasOption("t"))
                TestSuite.TEST_FILE = commandLine.getOptionValue("t");
            
            String path = commandLine.getOptionValue("f");
            File file = new File(path);
            if (!file.exists())
                throw new ParseException(String.format("File/folder [%s] doesn't exist.", path));
            
            if (file.isFile()) {
                if (!file.toString().toLowerCase().endsWith(".jar"))
                    throw new ParseException(String.format("[%s] is invalid. A JAR file is expected.", path));
                Harness.<Cw1>i().test(path);
            } else if(file.isDirectory()) {
                System.out.println(String.format("Testing all JARs in [%s]...", path));
                try (Stream<Path> stream = Files.list(Paths.get(path))) {
                    stream.map(String::valueOf).filter(f -> f.toLowerCase().endsWith(".jar")).forEach(f -> Harness.<Cw1>i().test(f));
                }                
            }
            
            Gson gson = new Gson();
            System.out.println(gson.toJson(Harness.<Cw1>i().getResults()));                           	    
            // this can be fed into http://json2table.com/ to get the results in a tabular form
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("uk.ac.london.co3326.Harness", options);            
        }        
	}
	
	private void test(String path) {
        Student<Cw1> student = new Student<>(path);
        student.evaluate();
        results.add(student);        
	}
	
	public List<Student<?>> getResults() {
		return this.results;
	}

}
