package uk.ac.london.co3326;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Student {
	private transient String path;
	private String file;
	private String name;
	private String camelCase;
	private String srnFromFile;
	private String srnFromRun;
	private TestCase testCase;
	private String stdout;
	private String stderr;
	private String exception;

	public Student(String file) {
		this.file = file;
		this.path = file.substring(0, file.lastIndexOf(File.separatorChar));
		this.file = file.substring(file.lastIndexOf(File.separatorChar) + 1);
		this.testCase = new TestCase(path);
		String[] temp = this.file.split("_");
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
		String[] arg = new String[] { "java", "-jar", (path + File.separatorChar + file) , testCase.getFile()};
		StringBuilder stderror = new StringBuilder();
		List<String> stdout = new ArrayList<>();
		try {
			Process p = Runtime.getRuntime().exec(arg);
			p.waitFor(10, TimeUnit.SECONDS);
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null)
				stdout.add(line);
			reader.close();
			this.stdout = stdout.stream().collect(Collectors.joining("\n"));
			if (stdout.size() < 3) {
				testCase.setError("Unexpected output format", stdout.stream().collect(Collectors.joining("\n")));
			} else {
				camelCase = stdout.get(0);
				srnFromRun = stdout.get(1);
				testCase.evaluate(stdout.get(2));
			}

			reader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			while ((line = reader.readLine()) != null) {
				stderror.append(line);
				stderror.append(System.getProperty("line.separator"));
			}
			reader.close();
			this.stderr = stderr.toString();
		} catch (Exception e) {
			setError(e.getMessage(), stdout.stream().collect(Collectors.joining("\n")), stderr.toString());
		} 			
	}
	
	public void setError(String exception, String stdout, String stderr) {
		this.stderr = stderr;
		this.stdout = stdout;
		this.exception = exception;		
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

	public String getStdout() {
		return stdout;
	}

	public String getStderr() {
		return stderr;
	}

	public String getException() {
		return exception;
	}
}
