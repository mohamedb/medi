package medi.tools;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Set;

import org.reflections.Reflections;

import javassist.NotFoundException;
import medi.App;
import medi.DiContainer;
import medi.annot.Main;
import medi.annot.Medi;

public class AppLuncher {

	public static void lunch(Class<?> clazz, Object[] args) throws Exception {
		Constructor<?> ctor = annotatedCtor(clazz);
		Object[] ctorAgs = buildParams(ctor, args);
		Object o = ctor.newInstance(ctorAgs);
		Method method = findMain(clazz);
		method.invoke(o);
	}
	
	public static Method findMain(Class<?> clazz) throws NotFoundException{
		for (Method m : clazz.getMethods()){
			if(m.isAnnotationPresent(Main.class)){
				return m;
			}
		}
		throw new NotFoundException(" Class: "+clazz.getName()+ " doesn't contains an annotated method with @Main");
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
				Object impl = getTypeImplementation(an.getType());
				ctorAgs[i] = impl;
			} else {
				Object normalImp = findImplementation(an.getType()); 
				if (normalImp == null && j < otherArgs.length) {
					ctorAgs[i] = otherArgs[j];
				    j++;
				} else {
					ctorAgs[i] = normalImp;
				}
			}
			i++;
		}
		return ctorAgs;
	}

	public static Object getTypeImplementation(Class<?> type) throws Exception {
		return DiContainer.resolve(type);
	}
	
	/**
	 * Try to find an implementation 
	 * @param type
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static Object findImplementation(Class<?> type) throws InstantiationException, IllegalAccessException {
		Reflections reflections = new Reflections(App.BASE_PKG);
		Set<?> subTypes = reflections.getSubTypesOf(type);
		for (Object obj : subTypes) {
			if (!obj.getClass().isInterface()) {
				Object ins= ((Class<?>) obj).newInstance();
				return ins;
			}
		}
		return null;
	}
}
