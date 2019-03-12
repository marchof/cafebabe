package org.jacoco.cafebabe.test;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;

/**
 * Executes statements against a given Java object instance.
 */
class StatementExecutor implements StatementParser.IStatementVisitor {

	private final Object target;
	private final Object[] prefixArgs;

	StatementExecutor(Object target, Object... prefixArgs) {
		this.target = target;
		this.prefixArgs = prefixArgs;
	}

	@Override
	public void visitInvocation(String ctx, String name, Object... args) {
		args = concat(prefixArgs, args);
		try {
			target.getClass().getMethod(name, getArgumentTypes(args)).invoke(target, args);
		} catch (InvocationTargetException e) {
			Throwable te = e.getTargetException();
			if (te instanceof Error) {
				throw (Error) te;
			}
			if (te instanceof RuntimeException) {
				throw (RuntimeException) te;
			}
			throw new RuntimeException("Invocation error (" + ctx + ")", te);
		} catch (Exception e) {
			throw new RuntimeException("Invocation error (" + ctx + ")", e);
		}
	}

	private static Object[] concat(Object[] a, Object[] b) {
		final Object[] result = Arrays.copyOf(a, a.length + b.length);
		System.arraycopy(b, 0, result, a.length, b.length);
		return result;
	}

	// We always use primitive int parameters:
	private static final Map<Class<?>, Class<?>> REPLACEMENTS = Map.of(Integer.class, Integer.TYPE);

	private static Class<?>[] getArgumentTypes(Object[] instances) {
		return Arrays.stream(instances) //
				.map(Object::getClass) //
				.map(c -> REPLACEMENTS.getOrDefault(c, c)) //
				.toArray(Class[]::new);
	}

}
