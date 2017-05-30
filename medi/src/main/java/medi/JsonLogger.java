package medi;

public class JsonLogger implements ILogger {

	public void log(String msg) {
		System.out.println(" JSON logger :: "+msg);
	}

}
