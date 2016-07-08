package uk.ac.london.co3326.harness;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import uk.ac.london.co3326.harness.cw1.EmptyTestSuiteCw1;
import uk.ac.london.co3326.harness.cw1.FullySpecifiedTestSuiteCw1;
import uk.ac.london.co3326.harness.cw2.EmptyTestSuiteCw2;
import uk.ac.london.co3326.harness.cw2.FullySpecifiedTestSuiteCw2;

public class Student {
	private transient String[] arg;
	private String file;
	private String name;
	private String camelCase;
	private String srnFromFile;
	private String srnFromRun;
	private int score = 0;
	private List<TestResult> tests;
	private List<String> out = new ArrayList<>();
	private String stdout;
	private String stderr;
	private String exception = "";

	public Student(String jarFile, String testFile) {
		arg = new String[] { "java", "-jar", jarFile, testFile};
		this.file = jarFile.substring(jarFile.lastIndexOf(File.separatorChar) + 1);
		this.tests = new ArrayList<>();
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
		try {
			Process p = Runtime.getRuntime().exec(arg);
			//p.waitFor(10, TimeUnit.SECONDS);
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
			    if (line != null && !line.trim().isEmpty() && (stdout.size() < 2 || (stdout.size() >= 2 || line.trim().startsWith("{")))) {
			        stdout.add(line);
			    }
			}
			reader.close();
			//this.stdout = stdout.stream().collect(Collectors.joining("\n"));
			reader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			while ((line = reader.readLine()) != null) {
			    if (line != null && !line.isEmpty()) {
    				stderr.append(line);
    				stderr.append(System.getProperty("line.separator"));
			    }
			}
			reader.close();
			this.stderr = stderr.toString();

			if (stdout.size() < 2)
				throw new Exception("Unexpected output format");
			
            this.camelCase = stdout.get(0);
            this.srnFromRun = stdout.get(1);
            			
            //evalCw1(stdout);
            evalCw2(stdout);
            
		} catch (Exception e) {
		    e.printStackTrace();
			setError(e.getMessage(), stdout.stream().collect(Collectors.joining("\n")), stderr.toString());
		} 			
	}
	
	protected int eval(String out, String test, int outIndex, Class<? extends TestSuite<?>> clazz) {
		int r = 0;
		try {
			Constructor<?> constructor = clazz.getConstructor(String.class, String.class);
			TestSuite<?> suite = (TestSuite<?>) constructor.newInstance(new Object[] { out, test });
			r = suite.evaluate();
			if (r == 0)
			    this.out.add(out);	
			else
				addScore(r);
	        this.tests.addAll(suite.getResult());
			System.out.println(suite.getResult());
		} catch(Exception e) {
		    e.printStackTrace();
            this.out.add(out);  
			this.exception += System.getProperty("line.separator") + "Line " + (outIndex + 3) + ": " + e.getMessage(); 			
		}		
		return r;		
	}
	
	protected void evalCw2(List<String> stdout) throws ClassNotFoundException {
		int r1 = 0, r2 = 0;

		if (stdout.size() > 2)
			r1 = eval(stdout.get(2), Harness.getTestInput().get(0), 0, FullySpecifiedTestSuiteCw2.class);
		if (stdout.size() > 3)
			r2 = eval(stdout.get(3), null, 1, EmptyTestSuiteCw2.class);
                        
        if (r1 + r2 == 0) {
            this.stdout = stdout.stream().collect(Collectors.joining("\n"));
        }
    }
	
	protected void evalCw1(List<String> stdout) throws ClassNotFoundException {
		int r1 = 0, r2 = 0, r3 = 0, r4 = 0;

		if (stdout.size() > 2)
			r1 = eval(stdout.get(2), Harness.getTestInput().get(0), 0, FullySpecifiedTestSuiteCw1.class);
		if (stdout.size() > 3)
			r2 = eval(stdout.get(3), Harness.getTestInput().get(1), 1, FullySpecifiedTestSuiteCw1.class);
		if (stdout.size() > 4)
			r2 = eval(stdout.get(4), null, 2, EmptyTestSuiteCw1.class);
		if (stdout.size() > 5)
			r2 = eval(stdout.get(5), null, 3, EmptyTestSuiteCw1.class);
        
        if (r1 * r2 * r3 * r4 == 0) {
            this.stdout = stdout.stream().collect(Collectors.joining("\n"));
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

    public int getScore() {
        return this.score;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
    
    public void addScore(int score) {
        this.score += score;
    }
}
