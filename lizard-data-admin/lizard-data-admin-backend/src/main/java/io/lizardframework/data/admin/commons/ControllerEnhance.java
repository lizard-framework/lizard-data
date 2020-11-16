package io.lizardframework.data.admin.commons;

import io.lizardframework.data.admin.message.MessageEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author xueqi
 * @date 2020-09-27
 */
@RestControllerAdvice
@Slf4j
public class ControllerEnhance {

	@ExceptionHandler
	public Resp handlerException(Exception e) {
		if (e instanceof BizException) {
			log.warn("Controller exception BizException:", e);

			BizException ex = (BizException) e;
			return new Resp(ex);
		}

		log.error("Controller exception Unknown Exception:", e);
		return new Resp(MessageEnum.UNKNOWN_ERROR);
	}

}
