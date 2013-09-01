package org.qmik.qmikjson.token;

import org.qmik.qmikjson.Config;

/**
 * 先进后出队列 
 * @author leo
 *
 * @param <E>
 */
public class LIFO<E> {
	private E[]	list;
	private int	posi	= -1;
	
	@SuppressWarnings("unchecked")
	public LIFO() {
		list = (E[]) new Object[Config.MAX_LEVEL];
	}
	
	public E pop() {
		return posi < 0 ? null : list[posi--];
	}
	
	public E peek() {
		return posi < 0 ? null : list[posi];
	}
	
	public void add(E value) {
		list[++posi] = value;
	}
	
	public boolean isEmpty() {
		return posi < 0;
	}
	
	public void clear() {
		posi = -1;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i <= posi; i++) {
			if (i > 0) {
				sb.append(",");
			}
			sb.append(list[i]);
		}
		return sb.toString();
	}
}
