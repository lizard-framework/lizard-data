package io.lizardframework.data.admin.message;

/**
 * @author xueqi
 * @date 2020-09-25
 */
public enum RespMessage {

	SUCCESS("000000", "SUCCESS"),

	ORM_MIXED_DATASOURCE_NOT_EXIST("100001", "orm mixed datasource not exist"),

	UNKNOWN_ERROR("999999", "unknown system error"),
	;

	private String code;
	private String message;

	RespMessage(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
