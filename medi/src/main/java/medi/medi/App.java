package medi.medi;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws Exception {

		DiContainer.bind(ILogger.class, new JsonLogger());
		DiContainer.bind(ITxtLogger.class, new TextLogger());
		
		appLuncher(MyApp.class,"Zuper Zonic app");
		appLuncher(MyApp.class,"Hunter bPP");
		
		appLuncher(MyServer.class,"Apatz Zerver");

	}

	@SuppressWarnings("unchecked")
	public static void appLuncher(Class<?> cl,String appName) throws Exception {
//		Class<?> cl = Class.forName(pckg);
		Class<?>[] ptypes= getConstructors(cl);
		Constructor<?> cons = cl.getDeclaredConstructor(ptypes);
		System.out.println("PC= "+cons.getParameterCount());
		Object o =   cons.newInstance(DiContainer.resolve(ptypes[0]), appName);
		try {
			Method method = o.getClass().getMethod("run");
			method.invoke(o);
		} catch (Exception e) {
			throw new Exception(" x_x Method run() is not found in "+ o.getClass().getSimpleName()+" x_x");
		}
	}

	@SuppressWarnings("rawtypes")
	public static Class[] getConstructors(Class<?> aClass) {
		Constructor[] Constructors = aClass.getConstructors();
		for (Constructor c : Constructors) {
			Class[] parameterTypes = c.getParameterTypes();
			if(parameterTypes.length>0);
			for(Class p : parameterTypes){
				System.out.println(" "+ p.getName()+" ");
			}
			return parameterTypes;
		}
		System.out.println(" A null is reached! problem ther!");
		return null;
	}
}
