package assam.common.reflection.practice1;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

/**
 * @Description 	用反射的方式呼叫某Class的方法
 * @author 			charlie
 * @CreateDate 		2016年1月25日 下午2:36:05
 * @LastModifier 	charlie
 * @LastModifyDate 	2016年1月25日 下午2:36:05
 * @Copyright 		Smart Catch International Co., Ltd
 */
public class InvokeClassMethodByClassName {

	@Test
	public void test() throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException{
		Class<?> c = Class.forName("assam.common.reflection.practice1.InvokedClass");
		
		//獲取該Class的方法
		Method method = c.getDeclaredMethod("callMe", null);
		
		//呼叫
		int test = (int) method.invoke(c.newInstance(), null);
		
		//回傳值
		System.out.println(test);
	}
}
