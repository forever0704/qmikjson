package test.org.qmik.qmikjson.test.bean;

import com.alibaba.fastjson.JSON;

import test.org.qmik.datamap.creataStrongClass.User;

public class TestFastJSON {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("test fastjson");
		User user = CoreBeanUnit.create();
		CoreBeanUnit.testFastJSON(user);
		System.out.println(JSON.toJSONString(user));
	}
	
}
