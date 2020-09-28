package io.lizardframework.data.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * LoadBalance Type Enum
 *
 * @author xueqi
 * @date 2020-09-07
 */
public enum LoadBalanceType {
	ROUND_ROBIN("roundRobin"),
	RANDOM("random");

	private String value;

	LoadBalanceType(String value) {
		this.value = value;
	}

	public static LoadBalanceType convert(String value) {
		for (LoadBalanceType loadBalanceType : LoadBalanceType.values()) {
			if (StringUtils.equalsIgnoreCase(loadBalanceType.value, value))
				return loadBalanceType;
		}

		return null;
	}

	public String getValue() {
		return value;
	}
}
