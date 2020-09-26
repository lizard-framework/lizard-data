package io.lizardframework.data.enums;

/**
 * @author xueqi
 * @date 2020-09-26
 */
public enum MixedType {
	ORM("orm"),
	CACHE("cache");

	private String value;

	MixedType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
