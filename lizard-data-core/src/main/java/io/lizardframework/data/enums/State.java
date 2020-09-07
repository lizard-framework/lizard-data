package io.lizardframework.data.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * State Enum
 *
 * @author xueqi
 * @date 2020-09-07
 */
public enum State {

	ONLINE("online"),
	OFFLINE("offline");

	private String value;

	State(String value) {
		this.value = value;
	}

	public static State convert(String value) {
		for (State state : State.values()) {
			if (StringUtils.equalsIgnoreCase(state.value, value))
				return state;
		}

		return null;
	}

	public String getValue() {
		return value;
	}
}
