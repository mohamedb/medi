package medi.medi;

public class MyApp {
	ILogger logger;
	String appName;

	public MyApp(ILogger log, String appName) {
		super();
		this.logger = log;
		this.appName= appName;
	}
	
	public void run(){
		 this.logger.log(this.appName+" App is starting"); 
	}

}
