package org.jacoco.cafebabe.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

public class StatementExecutorTest {

	private Map<String, List<?>> invocations;

	@BeforeEach
	void setup() {
		invocations = new HashMap<String, List<?>>();
	}

	@Test
	void should_prefix_arguments() {
		StatementExecutor executor = new StatementExecutor(this, "Hello", "world");

		executor.visitInvocation("ctx", "target1", "!");

		assertEquals(Arrays.asList("Hello", "world", "!"), invocations.get("target1"));
	}

	@Test
	void should_call_method_with_int_argument() {
		StatementExecutor executor = new StatementExecutor(this);

		executor.visitInvocation("ctx", "target2", Integer.valueOf(42));

		assertEquals(Arrays.asList(Integer.valueOf(42)), invocations.get("target2"));
	}

	@Test
	void should_preserve_Errors() {
		StatementExecutor executor = new StatementExecutor(this);

		Throwable ex = assertThrows(AssertionFailedError.class, () -> executor.visitInvocation("ctx", "target3"));
		assertEquals("Original AssertionError.", ex.getMessage());
	}

	@Test
	void should_preserve_RuntimeExceptions() {
		StatementExecutor executor = new StatementExecutor(this);

		Throwable ex = assertThrows(NoSuchElementException.class, () -> executor.visitInvocation("ctx", "target4"));
		assertEquals("Original AssertionError.", ex.getMessage());
	}

	@Test
	void should_wrap_other_exceptions() {
		StatementExecutor executor = new StatementExecutor(this);

		Throwable ex = assertThrows(RuntimeException.class, () -> executor.visitInvocation("ctx", "target5"));
		assertEquals("Invocation error (ctx)", ex.getMessage());
		assertEquals(IOException.class, ex.getCause().getClass());
	}

	@Test
	void should_throw_RuntimeException_when_method_cannot_be_invoked() {
		StatementExecutor executor = new StatementExecutor(this);

		Throwable ex = assertThrows(RuntimeException.class, () -> executor.visitInvocation("ctx", "doesNotExist"));
		assertEquals("Invocation error (ctx)", ex.getMessage());
		assertEquals(NoSuchMethodException.class, ex.getCause().getClass());
	}

	public void target1(String a, String b, String c) {
		invocations.put("target1", Arrays.asList(a, b, c));
	}

	public void target2(int i) {
		invocations.put("target2", Arrays.asList(Integer.valueOf(i)));
	}

	public void target3() {
		throw new AssertionFailedError("Original AssertionError.");
	}

	public void target4() {
		throw new NoSuchElementException("Original AssertionError.");
	}

	public void target5() throws IOException {
		throw new IOException("Original IOException.");
	}

}
