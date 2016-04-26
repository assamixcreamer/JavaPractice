package assam.strange.test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

public class ObjectToMapTest {

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("123", "456");
		Object objMap = map;
//		Map<String, Object> map2 = getMap(map);
		
		ObjectMapper m = new ObjectMapper();
		Map<String,Object> map2 = m.convertValue(objMap,Map.class);
		System.out.println(map2.get("123"));
	}

	public static Map<String, Object> getMap(Object o) throws IllegalArgumentException, IllegalAccessException {
	    Map<String, Object> result = new HashMap<String, Object>();
	    Field[] declaredFields = o.getClass().getDeclaredFields();
	    for (Field field : declaredFields) {
	        result.put(field.getName(), field.get(o));
	    }
	    return result;
	}
}
