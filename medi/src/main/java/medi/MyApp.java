package medi;

import medi.annot.Main;
import medi.annot.Medi;

public class MyApp {
	ILogger logger;
	String appName;
	
	@Medi
	public MyApp(@Medi ILogger log, String appName) {
		super();
		this.logger = log;
		this.appName= appName;
	}
	
	@Main
	public void run(){
		 this.logger.log(this.appName+" App is starting"); 
	}

}
