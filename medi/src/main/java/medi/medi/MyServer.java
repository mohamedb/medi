package medi.medi;

public class MyServer {
	ILogger logger;
	String appName;

	public MyServer(ILogger log, String appName) {
		super();
		this.logger = log;
		this.appName= appName;
	}
	
	public void run(){
		 this.logger.log(" The SERVER: "+this.appName+"  is starting"); 
	}
}
