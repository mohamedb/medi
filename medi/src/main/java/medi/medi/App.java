package medi.medi;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws Exception {

		DiContainer.bind(ILogger.class, new JsonLogger());
		DiContainer.bind(ITxtLogger.class, new TextLogger());

		appLuncher(MyApp.class, new String[] { "Zuper Zonic app" });
		appLuncher(MyApp.class, new String[] { "Hunter bPP" });

		appLuncher(MyServer.class, new String[] { "Apatz Zerver" });

	}

	public static void appLuncher(Class<?> clazz, Object[] args) throws Exception {
		Constructor<?> ctor = annotatedCtor(clazz);
		Object[] ctorAgs = buildParams(ctor, args);
		Object o = ctor.newInstance(ctorAgs);
		Method method = o.getClass().getMethod("run");
		method.invoke(o);

	}
	
	/**
	 * Get only the annotated constructor
	 * @param aClass
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Constructor annotatedCtor(Class<?> aClass) {
		Constructor[] Constructors = aClass.getConstructors();
		for (Constructor c : Constructors) {
			if (c.isAnnotationPresent(Medi.class)) {
				return c;
			}
		}
		return null;
	}
	
	/**
	 * Build params by getting the right instance from the container and skip 'normal' type
	 * Interface passed as param to constructor should be annotated and have a valid implementation registred to container
	 * @param ctor
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static Object[] buildParams(Constructor<?> ctor, Object[] args) throws Exception {
		Parameter[] params = ctor.getParameters();
		int i = 0, j = 0;
		Object[] ctorAgs = new Object[ctor.getParameterCount()];
		for (Parameter an : params) {
			if (an.isAnnotationPresent(Medi.class)) {
				Object impl = getTypeImpl(an.getType());
			    ctorAgs[i]= impl;
			} else if(j<args.length) {
				ctorAgs[i] = args[j];
				j++;
			}
			i++;
		}
		return ctorAgs;
	}

	public static Object getTypeImpl(Class<?> type) throws Exception {
		return DiContainer.resolve(type);
	}
}
