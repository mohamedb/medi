package medi.medi;

import java.util.HashMap;
import java.util.Map;

public class DiContainer<T> {
    static Map<String, Object> elements = new HashMap<>();

	public DiContainer() {
		super();
	}
	
	public static <T> void bind(Class<?> type, T impl){
		elements.put(type.getSimpleName(), impl);
	}
    
	public static <T> Object resolve(Class<T> type) throws Exception {
		@SuppressWarnings("unchecked")
		T obj= (T) elements.get(type.getSimpleName());
		if(obj==null){
			throw new Exception("  x_x "+type.getSimpleName()+ " Is not bound to any impl!  x_x");
		}
		return (T) obj;
	}
   
}
