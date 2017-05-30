package medi;

import medi.annot.Main;
import medi.annot.Medi;

public class MyServer {
	ITxtLogger logger;
	String appName;
    
	@Medi
	public MyServer( ITxtLogger log, String appName) {
		super();
		this.logger = log;
		this.appName= appName;
	}
	
	@Main
	public void run(){
		 this.logger.log(" The SERVER: "+this.appName+"  is starting"); 
	}
}
