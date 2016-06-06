package uk.ac.london.co3326;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.google.gson.Gson;

public class Harness {

	public static void main(String[] args) throws IOException {		
		Options options = new Options();
		options.addOption("f", "folder/file", true, "Folder, where the coursework JARs are located or coursework JAR, that is to be tested.");
        options.addOption("t", "test", false, "Test tile (default: test.txt)");
		
        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine commandLine = parser.parse(options, args);

            
            if (args.length == 0)
                throw new ParseException("No arguments provided");
            if (commandLine.hasOption("t"))
                TestCase.TEST_FILE = commandLine.getOptionValue("t");
            
            String path = commandLine.getOptionValue("f");
            File file = new File(path);
            if (!file.exists())
                throw new ParseException(String.format("File/folder [%s] doesn't exist.", path));
            if (file.isFile()) {
                if (!file.toString().toLowerCase().endsWith(".jar"))
                    throw new ParseException(String.format("[%s] is invalid. A JAR file is expected.", path));
                test(path);
            } else if(file.isDirectory()) {
                System.out.println(String.format("Testing all JARs in [%s]...", path));
                try (Stream<Path> stream = Files.list(Paths.get(path))) {
                    stream.map(String::valueOf).filter(f -> f.toLowerCase().endsWith(".jar")).forEach(f -> test(f));
                }                
            }
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("uk.ac.london.co3326.Harness", options);            
        }        
	}
	
	private static void test(String path) {
        Student student = new Student(path);
        student.evaluate();
        Gson gson = new Gson();
        System.out.println(gson.toJson(student));                           	    
	}

}
