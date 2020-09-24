package io.lizardframework.data.orm.enums;

/**
 * Master Slave Type Enum
 *
 * @author xueqi
 * @date 2020-09-10
 */
public enum MasterSlaveType {
	MASTER("master"),
	SLAVE("slave"),
	;

	private String value;

	MasterSlaveType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
