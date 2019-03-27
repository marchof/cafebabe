package org.jacoco.cafebabe.test;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.objectweb.asm.ClassReader.SKIP_CODE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jacoco.cafebabe.test.StatementParser.IStatementVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

/**
 * Reader for test target sources.
 */
public class SourceReader {

	private static final Pattern COMMENT_PATTERN = Pattern.compile("(?<!https?:)//(.*)");

	private final String source;

	private final Path path;

	/**
	 * Tries to find the sources for the given class.
	 * 
	 * @throws IOException
	 */
	public SourceReader(Class<?> clazz) throws IOException {
		this("src/test/java", clazz);
	}

	public SourceReader(String sourcepath, Class<?> clazz) throws IOException {
		source = getSource(clazz);
		path = Path.of(sourcepath, clazz.getPackageName().replace('.', '/'), source);
	}

	public void executeComments(Object target) throws IOException {
		int nr = 1;
		for (String line : Files.readAllLines(path, UTF_8)) {
			executeLine(line, nr++, target);
		}
	}

	private void executeLine(String line, int nr, Object target) throws IOException {
		IStatementVisitor executor = new StatementExecutor(target, nr);
		StatementParser.parse(getComment(line), executor, source + ":" + nr);
	}

	private static String getComment(String line) {
		final Matcher matcher = COMMENT_PATTERN.matcher(line);
		return matcher.find() ? matcher.group(1) : "";
	}

	private static String getSource(Class<?> clazz) throws IOException {
		var node = new ClassNode();
		new ClassReader(ClassDefinition.get(clazz)).accept(node, SKIP_CODE);
		return node.sourceFile;
	}

}
