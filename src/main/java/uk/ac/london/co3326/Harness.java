package uk.ac.london.co3326;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import com.google.gson.Gson;

public class Harness {

	public static void main(String[] args) throws IOException {
		String folder = args[0];
		System.out.println(folder);
		try (Stream<Path> stream = Files.list(Paths.get(folder))) {
			stream.map(String::valueOf).filter(f -> f.toLowerCase().endsWith(".jar")).forEach(f -> {
				Student student = new Student(f);
				student.evaluate();
				Gson gson = new Gson();
	            System.out.println(gson.toJson(student));			
			});
		}
	}

}
