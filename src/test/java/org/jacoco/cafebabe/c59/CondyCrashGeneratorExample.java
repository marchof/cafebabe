package org.jacoco.cafebabe.c59;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import org.jacoco.cafebabe.test.MemoryClassLoader;

public class CondyCrashGeneratorExample {

	public static void main(String[] args) throws Throwable {
		var classLoader = new MemoryClassLoader();
		classLoader.add(CondyCrashGenerator.create());
		var cls = classLoader.loadClass("CondyCrash");

		for (int i = 0; i < 1000; i++) {
			try {
				MethodHandles.lookup()
					.findStatic(cls, "main", MethodType.methodType(void.class, String[].class))
					.invoke((Object) null);
			} catch (StackOverflowError ignored) {
			}
		}
	}

}
