package medi;

import medi.annot.Main;
import medi.annot.Medi;

public class MyMachine {
	ILogger logger;
	String appName;
	ITxtLogger txtLogger;
	
	@Medi
	public MyMachine(@Medi ILogger log, String appName,ITxtLogger txtLogger) {
		super();
		this.logger = log;
		this.txtLogger= txtLogger;
		this.appName= appName;
	}
	
	@Main
	public void run(){
		 this.logger.log(this.appName+" Machine is booting!!"); 
		 this.txtLogger.log("TXT-Logger is ready on machine");
	}

}
