package uk.ac.london.co3326;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Student {
	private String file;
	private String name;
	private String camelCase;
	private String srnFromFile;
	private String srnFromRun;
	private TestCase testCase;

	public Student(String file) {
		this.file = file;
		String path = file.substring(0, file.lastIndexOf('/'));
		testCase = new TestCase(path);
		String[] temp = file.substring(file.lastIndexOf('/') + 1).split("_");
		if (temp.length > 0)
			name = temp[0];
		if (temp.length > 1) {
			try {
				Long.parseLong(temp[1]);
				srnFromFile = temp[1];
			} catch(NumberFormatException e) {
				
			}
		}
	}
		
	public void evaluate() {
		String[] arg = new String[] { "java", "-jar", file , testCase.getFile()};
		try {
			Process p = Runtime.getRuntime().exec(arg);
			p.waitFor(10, TimeUnit.SECONDS);
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			List<String> stdout = new ArrayList<>();
			while ((line = reader.readLine()) != null)
				stdout.add(line);
			reader.close();
			if (stdout.size() < 3) {
				testCase.setError("Unexpected output format", stdout.stream().collect(Collectors.joining("\n")));
			} else {
				camelCase = stdout.get(0);
				srnFromRun = stdout.get(1);
				testCase.evaluate(stdout.get(2));
			}

			reader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			// Save the output in a StringBuffer for further processing
			StringBuilder stderror = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				stderror.append(line);
				stderror.append(System.getProperty("line.separator"));
			}
			reader.close();
			testCase.setError(stderror.toString());			
		} catch (IOException e) {
			testCase.setError("IOException", e.getMessage());
		} catch (InterruptedException e) {
			testCase.setError("InterruptedException", e.getMessage());
		}				
	}

	public String getName() {
		return name;
	}

	public String getCamelCase() {
		return camelCase;
	}

	public String getSrnFromFile() {
		return srnFromFile;
	}

	public String getSrnFromRun() {
		return srnFromRun;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCamelCase(String camelCase) {
		this.camelCase = camelCase;
	}

	public void setSrnFromFile(String srnFromFile) {
		this.srnFromFile = srnFromFile;
	}

	public void setSrnFromRun(String srnFromRun) {
		this.srnFromRun = srnFromRun;
	}
}
