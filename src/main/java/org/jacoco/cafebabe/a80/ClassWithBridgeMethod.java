package org.jacoco.cafebabe.a80;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.Callable;

/**
 * ASM can't parse this class when compiled by javac version less than 1.8.0_202
 *
 * @see <a href="https://bugs.openjdk.java.net/browse/JDK-8160928">JDK-8160928</a>
 */
// javac src/main/java/org/jacoco/cafebabe/a80/ClassWithBridgeMethod.java -d target/classes
public class ClassWithBridgeMethod implements Callable<String> {
	@Override
	public String call() {
		@TypeUseAnnotation
		Object x = null;
		return String.valueOf(x);
	}
}

@Target({ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@interface TypeUseAnnotation {
}
