package medi.medi;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Set;

import org.reflections.Reflections;

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
		appLuncher(MyMachine.class, new String[] { "POWER_MACHINE" });

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
	 * 
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
	 * Build params by getting the right instance from the container and skip
	 * 'normal' type Interface passed as param to constructor should be
	 * annotated and have a valid implementation registered to container
	 * otherArgs are additional params passed to luncher and are passed to
	 * constructor!
	 * 
	 * @param ctor
	 * @param otherArgs
	 * @return
	 * @throws Exception
	 */
	public static Object[] buildParams(Constructor<?> ctor, Object[] otherArgs) throws Exception {
		Parameter[] params = ctor.getParameters();
		int i = 0, j = 0;
		Object[] ctorAgs = new Object[ctor.getParameterCount()];
		for (Parameter an : params) {
			if (an.isAnnotationPresent(Medi.class)) {
				Object impl = getTypeImpl(an.getType());
				ctorAgs[i] = impl;
			} else if (j < otherArgs.length) {
				Object normalImp = findImplementation(an.getType()); 
				if (normalImp == null) {
					ctorAgs[i] = otherArgs[j];
				} else {
					ctorAgs[i] = normalImp;
				}

				j++;
			}
			i++;
		}
		return ctorAgs;
	}

	public static Object getTypeImpl(Class<?> type) throws Exception {
		return DiContainer.resolve(type);
	}

	public static Object findImplementation(Class<?> type) throws InstantiationException, IllegalAccessException {
		Reflections reflections = new Reflections("medi.medi");
		Set<?> subTypes = reflections.getSubTypesOf(type);
		System.out.println("Sub types of: "+ type+ " count: "+subTypes.size()+ " "+subTypes);
		for (Object obj : subTypes) {
			if (!obj.getClass().isInterface()) {
				Object ins= ((Class<?>) obj).newInstance();
				System.out.println("...... Found impl.....: "+ins);
				return ins;
			}
			else{
				System.out.println("---Scanning--: "+obj);
			}
		}
		return null;
	}
}
