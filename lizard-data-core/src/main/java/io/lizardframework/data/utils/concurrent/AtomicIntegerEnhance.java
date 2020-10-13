package io.lizardframework.data.utils.concurrent;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

/**
 * Non overflow AtomicInteger
 *
 * @author xueqi
 * @date 2020-10-13
 */
public class AtomicIntegerEnhance extends AtomicInteger {

	private static final IntUnaryOperator INT_UNARY_OPERATOR = new IntUnaryOperatorImpl();

	public AtomicIntegerEnhance(int initValue) {
		super(initValue);
	}

	public int getAndUpdate() {
		return getAndUpdate(INT_UNARY_OPERATOR);
	}

	private static class IntUnaryOperatorImpl implements IntUnaryOperator {

		@Override
		public int applyAsInt(int operand) {

			if (operand >= Integer.MAX_VALUE) {
				return 1;
			}

			return operand + 1;
		}
	}
}
