package uk.ac.london.co3326.harness;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import uk.ac.london.co3326.Coursework;

public class Student<T extends Coursework> {
	private transient String[] arg;
	private String file;
	private String name;
	private String camelCase;
	private String srnFromFile;
	private String srnFromRun;
	private TestSuite<?> test1, test2;
	private String stdout;
	private String stderr;
	private String exception;

	public Student(String jarFile, String testFile) {
		arg = new String[] { "java", "-jar", jarFile, testFile};
		this.file = jarFile.substring(jarFile.lastIndexOf(File.separatorChar) + 1);
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
		StringBuilder stderr = new StringBuilder();
		List<String> stdout = new ArrayList<>();
		boolean result = false;
		try {
			Process p = Runtime.getRuntime().exec(arg);
			p.waitFor(10, TimeUnit.SECONDS);
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
			    if (!line.trim().isEmpty() && (stdout.size() < 2 || (stdout.size() >= 2 || line.startsWith("{")))) {
			        stdout.add(line);
			    }
			}
			reader.close();
			this.stdout = stdout.stream().collect(Collectors.joining("\n"));
			reader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			while ((line = reader.readLine()) != null) {
				stderr.append(line);
				stderr.append(System.getProperty("line.separator"));
			}
			reader.close();
			this.stderr = stderr.toString();
            this.camelCase = stdout.get(0);
            this.srnFromRun = stdout.get(1);
			if (stdout.size() < 3)
				throw new Exception("Unexpected output format");
			if (stdout.get(2) == null || stdout.isEmpty())
                throw new Exception("Line 3 is null or empty");
			//this.test1 = new FullySpecifiedTestSuite(stdout.get(2), Harness.getTestInput().get(0));
			//result = test1.evaluate();
			this.test2 = new EmptyTestSuite(stdout.get(2), null);
			result = test2.evaluate();
			if (result)
			    this.stdout = null;
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
