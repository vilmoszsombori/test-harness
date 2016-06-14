package uk.ac.london.co3326.harness;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
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

public class Harness {
	
	public static String TEST_FILE = "test.txt";

    private transient String path;
    private transient String testFile;
    private static transient List<String> testInput;

    private List<Student<? extends Coursework>> results = new ArrayList<>();
	
	public static void main(String[] args) throws IOException {		
		Options options = new Options();
		options.addOption("f", "file", true, "Folder, where the coursework JARs are located or coursework JAR, that is to be tested.");
        options.addOption("t", "test", true, "Test file (default: test.txt)");
		
        Harness harness = new Harness();
        
        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine commandLine = parser.parse(options, args);
            
            if (args.length == 0)
                throw new ParseException("No arguments provided");
            harness.setTestFile((commandLine.hasOption("t") ? commandLine.getOptionValue("t") : TEST_FILE));
            
            String path = commandLine.getOptionValue("f");
            File file = new File(path);
            if (!file.exists())
                throw new ParseException(String.format("File/folder [%s] doesn't exist.", path));
            
            if (file.isFile()) {
                if (!file.toString().toLowerCase().endsWith(".jar"))
                    throw new ParseException(String.format("[%s] is invalid. A JAR file is expected.", path));
                harness.setPath(file.getAbsolutePath().toString());
                harness.initTestInput();
                harness.test(path);
            } else if(file.isDirectory()) {
                System.out.println(String.format("Testing all JARs in [%s]...", path));
                harness.setPath(path);
                harness.initTestInput();
                try (Stream<Path> stream = Files.list(Paths.get(path))) {
                    stream.map(String::valueOf).filter(f -> f.toLowerCase().endsWith(".jar")).forEach(f -> harness.test(f));
                }                
            }
            
            Gson gson = new Gson();
            System.out.println(gson.toJson(harness.getResults()));                           	    
            // this can be fed into http://json2table.com/ to get the results in a tabular form
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("uk.ac.london.co3326.harness.Harness", options);            
        }        
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public void setTestFile(String testFile) {
		this.testFile = testFile;
	}
	
	public String getTestFile() {
		return path + File.separatorChar + testFile;
	}
	
	private void test(String path) {
        Student<Cw1> student = new Student<>(path, getTestFile());
        student.evaluate();
        results.add(student);        
	}
	
	public List<Student<?>> getResults() {
		return this.results;
	}
	
	private void initTestInput() throws FileNotFoundException, IOException {
		testInput = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(getTestFile()))) {
            String line;
            while ((line = in.readLine()) != null) {
            	testInput.add(line);
            }
        }		
	}
	
	public static List<String> getTestInput() {
		return testInput;
	}
	
}
