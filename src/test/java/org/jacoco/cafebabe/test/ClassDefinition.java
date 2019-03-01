package org.jacoco.cafebabe.test;

import java.io.IOException;

/**
 * Utility to load raw definitions of given classes.
 */
public class ClassDefinition {

	public static byte[] get(Class<?> clazz) throws IOException {
		final String resource = clazz.getName().replace('.', '/') + ".class";
		return clazz.getClassLoader().getResourceAsStream(resource).readAllBytes();
	}

}
