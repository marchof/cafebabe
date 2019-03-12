package org.jacoco.cafebabe.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jacoco.cafebabe.test.StatementParser.IStatementVisitor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StatementParserTest {

	private IStatementVisitor visitor;

	private List<String> actualInvocations;
	private List<String> expectedInvocations;

	@BeforeEach
	void setup() {
		actualInvocations = new ArrayList<>();
		expectedInvocations = new ArrayList<>();
		visitor = (String ctx, String name, Object... args) -> actualInvocations.add(invocationStr(ctx, name, args));
	}

	@AfterEach
	void teardown() {
		assertEquals(expectedInvocations, actualInvocations);
	}

	@Test
	void should_parse_empty_string() throws IOException {
		StatementParser.parse("", visitor, "Foo.java");
	}

	@Test
	void should_parse_invocation_without_params() throws IOException {
		StatementParser.parse("run()", visitor, "Foo.java");
		expectInvocation("Foo.java", "run");
	}

	@Test
	void should_parse_invocation_with_one_int_parameter() throws IOException {
		StatementParser.parse("ask(42)", visitor, "Foo.java");
		expectInvocation("Foo.java", "ask", Integer.valueOf(42));
	}

	@Test
	void should_parse_invocation_with_one_string_parameter() throws IOException {
		StatementParser.parse("say(\"hello\")", visitor, "Foo.java");
		expectInvocation("Foo.java", "say", "hello");
	}

	@Test
	void should_parse_invocation_with_two_parameters() throws IOException {
		StatementParser.parse("add(1000, 234)", visitor, "Foo.java");
		expectInvocation("Foo.java", "add", Integer.valueOf(1000), Integer.valueOf(234));
	}

	@Test
	public void should_parse_invocation_with_mixed_parameter_types() throws IOException {
		StatementParser.parse("mix(1, \"two\", 3)", visitor, "Foo.java");
		expectInvocation("Foo.java", "mix", Integer.valueOf(1), "two", Integer.valueOf(3));
	}

	@Test
	void should_parse_multiple_invocations() throws IOException {
		StatementParser.parse("start() stop()", visitor, "Foo.java");
		expectInvocation("Foo.java", "start");
		expectInvocation("Foo.java", "stop");
	}

	@Test
	void should_fail_when_parenthesis_is_missing() throws IOException {
		assertThrows(IOException.class, () -> StatementParser.parse("bad(", visitor, "Foo.java"));
	}

	@Test
	void should_fail_when_argument1_is_missing() throws IOException {
		assertThrows(IOException.class, () -> StatementParser.parse("bad(,2)", visitor, "Foo.java"));
	}

	@Test
	void should_fail_when_argument2_is_missing() throws IOException {
		assertThrows(IOException.class, () -> StatementParser.parse("bad(1,)", visitor, "Foo.java"));
	}

	@Test
	void should_give_context_info_when_parsing_fails() throws IOException {
		IOException ex = assertThrows(IOException.class, () -> StatementParser.parse("bad", visitor, "Foo.java:32"));
		assertEquals("Invalid syntax (Foo.java:32)", ex.getMessage());
	}

	private void expectInvocation(String ctx, String name, Object... args) {
		expectedInvocations.add(invocationStr(ctx, name, args));
	}

	private String invocationStr(String ctx, String name, Object... args) {
		return String.format("%s:%s%s", ctx, name, Arrays.asList(args));
	}

}
