package com.example.controller.error;

import com.example.utils.ExceptionEnum;
import com.example.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ErrorProperties.IncludeStacktrace;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 
 * @项目名称：wyait-manage
 * @类名称：ErrorHandlerController
 * @类描述：
 * @创建人：wyait
 * @创建时间：2018年6月6日 下午5:29:20 
 * @version：
 */
@Controller
@RequestMapping("/error")
public class ErrorHandlerController extends AbstractErrorController {

	private ErrorProperties errorProperties;

	private static final Logger logger = LoggerFactory
			.getLogger(ErrorHandlerController.class);

	private static final String ERROR_PATH = "error";

	// 初始化errorAttributes
	public ErrorHandlerController(ErrorAttributes errorAttributes) {
		super(errorAttributes);
	}

	/*public ErrorHandlerController(ErrorAttributes errorAttributes,
			ErrorProperties errorProperties) {
		super(errorAttributes);
		Assert.notNull(errorProperties, "ErrorProperties must not be null");
		this.errorProperties = errorProperties;
	}*/

	@RequestMapping(produces = "text/html")
	public ModelAndView errorHtml(HttpServletRequest request,
								  HttpServletResponse response) {
		logger.debug("统一异常处理【" + getClass().getName()
				+ ".errorHtml】text/html=普通请求：request=" + request);
		ModelAndView mv = new ModelAndView(ERROR_PATH);
		// Collections.unmodifiableMap生成一个不可变的map集合，key不可修改，value对象内的数据可更新；Map中的大小、对象地址不可修改
		// [官方翻译]该方法返回了一个map的不可修改的视图umap， 为用户提供了一种生成只读容器的方法。如果尝试修改该容器umap,
		// 将会抛出UnsupportedOperationException异常
		/** model对象包含了异常信息 */
		Map<String, Object> model = getErrorAttributes(request,
						isIncludeStackTrace(request, MediaType.TEXT_HTML));
		logger.info("统一异常处理【" + getClass().getName()
				+ ".errorHtml】统一异常处理：model=" + model);
		// 1 获取错误状态码（也可以根据异常对象返回对应的错误信息）
		HttpStatus httpStatus = getStatus(request);
		logger.debug("统一异常处理【" + getClass().getName()
				+ ".errorHtml】统一异常处理!错误状态码httpStatus：" + httpStatus);
		// 2 返回错误提示
		ExceptionEnum ee = getMessage(httpStatus);
		Result<String> result = new Result<String>(
				String.valueOf(ee.getType()), ee.getCode(), ee.getMsg());
		// 3 将错误信息放入mv中
		mv.addObject("result", result);
		logger.info("统一异常处理【" + getClass().getName()
				+ ".errorHtml】统一异常处理!错误信息mv：" + mv);
		return mv;
	}

	@RequestMapping
	@ResponseBody
	//设置响应状态码为：200，结合前端约定的规范处理。也可不设置状态码，前端ajax调用使用error函数进行控制处理
	@ResponseStatus(value= HttpStatus.OK)
	public Result<String> error(HttpServletRequest request, Exception e) {
		logger.info("统一异常处理【" + getClass().getName()
				+ ".error】text/html=普通请求：request=" + request );
		/** model对象包含了异常信息 */
		Map<String, Object> model = getErrorAttributes(request,
						isIncludeStackTrace(request, MediaType.TEXT_HTML));
		logger.info("统一异常处理【" + getClass().getName()
				+ ".error】统一异常处理：model=" + model);
		// 1 获取错误状态码（也可以根据异常对象返回对应的错误信息）
		HttpStatus httpStatus = getStatus(request);
		logger.debug("统一异常处理【" + getClass().getName()
				+ ".error】统一异常处理!错误状态码httpStatus：" + httpStatus);
		// 2 返回错误提示
		ExceptionEnum ee = getMessage(httpStatus);
		Result<String> result = new Result<String>(
				String.valueOf(ee.getType()), ee.getCode(), ee.getMsg());
		// 3 将错误信息返回
//		ResponseEntity
		logger.info("统一异常处理【" + getClass().getName()
				+ ".error】统一异常处理!错误信息result：" + result);
		return result;
	}

	@Override
	public String getErrorPath() {
		return this.errorProperties.getPath();
	}
	
	/**
	 * 
	 * @描述：根据error状态码，返回不同的错误提示信息
	 * @创建人：wyait
	 * @创建时间：2018年5月31日 下午2:52:50
	 * @param httpStatus
	 * @return
	 */
	@SuppressWarnings("static-access")
	private ExceptionEnum getMessage(HttpStatus httpStatus) {
		if (httpStatus.is4xxClientError()) {
			// 4开头的错误状态码
			if (400==httpStatus.BAD_REQUEST.value()) {
				return ExceptionEnum.BAD_REQUEST;
			} else if (403==httpStatus.FORBIDDEN.value()) {
				return ExceptionEnum.BAD_REQUEST;
			} else if (404==httpStatus.NOT_FOUND.value()) {
				return ExceptionEnum.NOT_FOUND;
			}
		} else if (httpStatus.is5xxServerError()) {
			// 5开头的错误状态码
			if (500==httpStatus.INTERNAL_SERVER_ERROR.value()) {
				return ExceptionEnum.SERVER_EPT;
			}
		}
		// 统一返回：未知错误
		return ExceptionEnum.UNKNOW_ERROR;
	}

	protected boolean isIncludeStackTrace(HttpServletRequest request,
			MediaType produces) {
		IncludeStacktrace include = getErrorProperties().getIncludeStacktrace();
		if (include == IncludeStacktrace.ALWAYS) {
			return true;
		}
		if (include == IncludeStacktrace.ON_TRACE_PARAM) {
			return getTraceParameter(request);
		}
		return false;
	}

	/**
	 * Provide access to the error properties.
	 * @return the error properties
	 */
	protected ErrorProperties getErrorProperties() {
		//此处也可以通过注入ServerProperties获取ErrorProperties
		return new ErrorProperties();
	}
}