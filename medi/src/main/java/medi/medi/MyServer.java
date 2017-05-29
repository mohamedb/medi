package medi.medi;

public class MyServer {
	ITxtLogger logger;
	String appName;
    
	@Medi
	public MyServer(@Medi ITxtLogger log, String appName) {
		super();
		this.logger = log;
		this.appName= appName;
	}
	
	public void run(){
		 this.logger.log(" The SERVER: "+this.appName==null?"Null":this.appName+"  is starting"); 
	}
}
