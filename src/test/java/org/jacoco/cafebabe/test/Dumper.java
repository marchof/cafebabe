package org.jacoco.cafebabe.test;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.util.TraceClassVisitor;

/**
 * Utility to get readable dumps of class files.
 */
public class Dumper {

	public static String dump(byte[] definition) {
		var buffer = new StringWriter();
		var trace = new TraceClassVisitor(new PrintWriter(buffer, true));
		new ClassReader(definition).accept(trace, ClassReader.EXPAND_FRAMES);
		return buffer.toString();
	}

}
