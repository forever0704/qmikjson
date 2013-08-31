package org.qmik.qmikjson.token.asm;

import java.lang.reflect.Field;
import java.util.HashMap;
import org.qmik.qmikjson.token.IBean;

/**
 * 增强bean类
 * @author leo
 *
 */
public class StrongBeanFactory {
	
	private final static StrongBeanClump			clump	= new StrongBeanClump();
	private final static StrongBean					bean	= new StrongBean();
	private final static HashMap<String, String>	names	= new HashMap<String, String>();
	
	@SuppressWarnings("unchecked")
	public static <T> T get(Class<?> superClazz) {
		T value = null;
		try {
			String key = (String) names.get(superClazz.getName());
			if (key == null) {
				key = superClazz.getName() + StrongBean.suffix;
				names.put(superClazz.getName(), key);
			}
			Class<?> strongClass = null;
			
			if (clump.get(key) != null) {
				strongClass = clump.get(key);
			} else {
				if (clump.get(key) == null) {
					strongClass = bean.makeClass(superClazz, IBean.class);
					clump.add(key, strongClass);
					value = (T) strongClass.newInstance();
					IBean bean = (IBean) value;
					Field[] fields = superClazz.getDeclaredFields();
					for (Field field : fields) {
						bean.$$$___keys().put(field.getName(), field.getName().toCharArray());
					}
					return value;
				}
			}
			value = (T) strongClass.newInstance();
		} catch (Exception e) {
			throw new NewInstanceException(e);
		}
		return value;
	}
}
