package medi.medi;

public class TextLogger implements ILogger {

	public void log(String msg) {
		 System.out.println(" Text logging:: "+msg);
	}

}
