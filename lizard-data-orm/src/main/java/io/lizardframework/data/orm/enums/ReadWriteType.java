package io.lizardframework.data.orm.enums;

/**
 * Read Write Type Enum
 *
 * @author xueqi
 * @date 2020-09-10
 */
public enum ReadWriteType {
	READ("read"),
	WRITE("write");

	private String value;

	ReadWriteType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
