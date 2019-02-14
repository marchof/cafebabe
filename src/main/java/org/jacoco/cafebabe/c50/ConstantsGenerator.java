package org.jacoco.cafebabe.c50;

import static org.objectweb.asm.Opcodes.ACC_FINAL;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_STATIC;
import static org.objectweb.asm.Opcodes.V11;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

/**
 * Creates a class with constant fields with initial values.
 */
public class ConstantsGenerator {

	public static byte[] create() {
		ClassWriter writer = new ClassWriter(0);
		create(writer);
		return writer.toByteArray();
	}

	public static void create(ClassVisitor cv) {
		cv.visit(V11, ACC_PUBLIC, "Constants", null, "java/lang/Object", null);

		cv.visitField(ACC_PUBLIC | ACC_STATIC | ACC_FINAL, "CONST_INT", "I", null, 42);
		cv.visitField(ACC_PUBLIC | ACC_STATIC | ACC_FINAL, "CONST_LONG", "J", null, 1234L);
		cv.visitField(ACC_PUBLIC | ACC_STATIC | ACC_FINAL, "CONST_SHORT", "S", null, 15);
		cv.visitField(ACC_PUBLIC | ACC_STATIC | ACC_FINAL, "CONST_BYTE", "B", null, 7);
		cv.visitField(ACC_PUBLIC | ACC_STATIC | ACC_FINAL, "CONST_CHAR", "C", null, (int) 'c');
		cv.visitField(ACC_PUBLIC | ACC_STATIC | ACC_FINAL, "CONST_FLOAT", "F", null, 0.25f);
		cv.visitField(ACC_PUBLIC | ACC_STATIC | ACC_FINAL, "CONST_DOUBLE", "D", null, 0.5);
		cv.visitField(ACC_PUBLIC | ACC_STATIC | ACC_FINAL, "CONST_BOOLEAN", "Z", null, 1); // boolean maps to 0 and 1
		cv.visitField(ACC_PUBLIC | ACC_STATIC | ACC_FINAL, "CONST_STRING", "Ljava/lang/String;", null, "cafebabe");

		cv.visitEnd();
	}

}
