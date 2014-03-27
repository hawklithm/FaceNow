package com.hawklithm.cerberus.protocol;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class ProtocolUtils {
	static public <T, V> void notNullSet(Map<T, V> map, T attribute, V object) {
		if (map == null) {
			System.out.println("map is null");
			return;
		}
		if (object != null) {
			map.put(attribute, object);
		}
	}

	static public <T, V> V notNullGet(Map<T, V> map, T attribute, Class clazz) {
		if (map == null) {
			System.out.println("map is null");
			return null;
		}
		if (map.containsKey(attribute)) {
			return map.get(attribute);
		}
		return null;
	}

	static public <T, V> V notNullGet(Map<T, V> map, T attribute) {
		if (map == null) {
			System.out.println("map is null");
			return null;
		}
		if (map.containsKey(attribute)) {
			return map.get(attribute);
		}
		return null;
	}

	static public <T, V, U> void notNullSet(Map<T, U> map, T attribute, V object, Map<V, U> trans) {
		if (map == null) {
			System.out.println("map is null");
			return;
		}
		if (!trans.containsKey(object)) {
			return;
		}

		if (object != null) {
			map.put(attribute, trans.get(object));
		}
	}

	/**
	 * 如果map是由json转过来的，可能类型出错
	 * @param map
	 * @param object
	 * @param clazz
	 * @throws Exception
	 */
	static public void assignPropertyFromMap(Map<String, Object> map, Object object,Class<?> clazz)
			throws Exception {
//		Class<?> clazz =object.getClass();
		Field[] fields = clazz.getDeclaredFields();
		// Method[] methods=clazz.getMethods();

		for (Field index : fields) {
			String property = index.getName();
			 String methodName="set"+Character.toUpperCase(property.charAt(0))+property.substring(1);
			 Method method=clazz.getMethod(methodName,index.getType());
//			MethodHandles.Lookup lookup = MethodHandles.lookup();
//			MethodHandle handle = lookup.findSetter(clazz, property, index.getType());
			if (map.containsKey(property)) {
				try {
					Object test=map.get(property);
					method.invoke(object, index.getType().cast(map.get(property)));
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
		// for (Method method:methods){
		// String methodName=method.getName();
		// if (methodName.matches("set[a-zA-Z]+")){
		// String
		// propertyName=Character.toLowerCase(methodName.charAt(3))+methodName.substring(4);
		// if (map.containsKey(propertyName)){
		// method.invoke(object,map.get(propertyName));
		// }
		// }
		// }
	}

	@SuppressWarnings("unchecked")
	static public <T> T GetProperty(Object object, String propertyName) throws Exception {
		Class<?> clazz = object.getClass();
		// Field[] fields=clazz.getFields();
		try {
			// Field field=clazz.getField(propertyName);
			String methodName = "get" + Character.toUpperCase(propertyName.charAt(0))
					+ propertyName.substring(1);
			Method method = clazz.getMethod(methodName);
			return (T) method.invoke(object);
			// return (T)field.get(object);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException e) {
			// System.out.println("can't get property ["+propertyName+"] from class ["+clazz.getName()+"]");
			return null;
			// throw new
			// Exception("can't get property ["+propertyName+"] from class ["+clazz.getName()+"]");
		}

	}
}
