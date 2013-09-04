package org.qmik.qmikjson.out;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.qmik.qmikjson.util.MixUtil;

public class Array2Text extends Base2Text {
	private static ThreadLocal<SoftReference<CharWriter>>	gtl_writers	= new ThreadLocal<SoftReference<CharWriter>>() {
																								@SuppressWarnings({ "unchecked", "rawtypes" })
																								protected SoftReference<CharWriter> initialValue() {
																									return new SoftReference(new CharWriter(1024));
																								};
																							};
	private static Array2Text										instance		= new Array2Text();
	
	private Array2Text() {
	}
	
	public static Array2Text getInstance() {
		return instance;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String list2JSON(Collection<Object> list, DateFormat df) {
		CharWriter writer = gtl_writers.get().get();
		if (writer == null) {
			gtl_writers.set(new SoftReference(new CharWriter(1024)));
		}
		writer.clear();
		try {
			list2JSON(writer, list, df);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return writer.toString();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void list2JSON(CharWriter writer, Collection<Object> list, DateFormat df) throws IOException {
		writer.append('[');
		Iterator<Object> vals = list.iterator();
		Object value;
		while (vals.hasNext()) {
			value = vals.next();
			if (value == null) {
				continue;
			}
			if (value instanceof CharSequence) {
				writer.append('"').append(value.toString()).append('"');
			} else if (MixUtil.isPrimitive(value.getClass())) {
				writer.append(String.valueOf(value));
			} else if (value instanceof Map) {
				Data2Text.getInstance().map2JSON(writer, (Map) value, df);
			} else if (value instanceof Collection) {
				if (value == list) {
					continue;
				}
				list2JSON(writer, (Collection) value, df);
			} else {
				writer.append("\"").append(value.toString()).append('"');
			}
			if (vals.hasNext()) {
				writer.append(',');
			}
		}
		writer.append(']');
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void toStringWriter(CharWriter writer, Object value, DateFormat df) throws IOException {
		list2JSON(writer, (Collection) value, df);
	}
	
}
