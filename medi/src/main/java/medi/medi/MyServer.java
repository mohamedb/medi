package medi.medi;

public class MyServer {
	ITxtLogger logger;
	String appName;
    
	@Medi
	public MyServer( ITxtLogger log, String appName) {
		super();
		this.logger = log;
		this.appName= appName;
	}
	
	public void run(){
		 this.logger.log(" The SERVER: "+this.appName+"  is starting"); 
	}
}
