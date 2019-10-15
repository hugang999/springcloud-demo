package com.hugang;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZuulApplicationTests {


	public static void main(String[] args) {

		String[] array = new String[]{"1", "2", "3", "4"};
		//fori
		for (int i = 0; i < array.length; i++) {
			
		}
		//iter
		for (String s : array) {
			
		}
		//itar
		for (int i = 0; i < array.length; i++) {
			String s = array[i];
			
		}

		List list = null;
		//list.for
		for (Object o : list) {
			
		}
		//list.fori
		for (int i = 0; i < list.size(); i++) {
			
		}
		//list.forr
		for (int i = list.size() - 1; i >= 0; i--) {
			
		}

		//ifn
		if (list == null) {

		}

		//ifnn
		if (list != null) {

		}

		//list.nn
		if (list != null) {

		}
		//list.null
		if (list == null) {

		}
		//prsf

	}


}
