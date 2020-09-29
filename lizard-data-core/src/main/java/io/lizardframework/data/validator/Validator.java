package io.lizardframework.data.validator;

/**
 * @author xueqi
 * @date 2020-09-29
 */
public interface Validator<T> {

	void validate(T object);

}
