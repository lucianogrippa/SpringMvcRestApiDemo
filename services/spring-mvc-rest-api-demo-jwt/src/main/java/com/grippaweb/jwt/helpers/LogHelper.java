package com.grippaweb.jwt.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

enum Loglevels {
	ERROR, INFO, WARN, DEBUG
}


public class LogHelper {
	private Logger _logger;
	private final static String DEFAULT_APPENDER="application";

	public LogHelper() {
		_logger = LoggerFactory.getLogger(DEFAULT_APPENDER);
	}
	
	public LogHelper(Class<?> classObj) {
		_logger = LoggerFactory.getLogger(classObj);
	}

	public LogHelper(String appender) {
		_logger = LoggerFactory.getLogger(appender);
	}

	public void logError(String message) {
		message = message.replaceAll("[^\\x20-\\x7e]", "");
		_logger.error(message);
	}

	public void logDebug(String message) {
		message = message.replaceAll("[^\\x20-\\x7e]", "");
		_logger.debug(message);
	}

	public void logInfo(String message) {
		message = message.replaceAll("[^\\x20-\\x7e]", "");
		_logger.info(message);
	}

	public void logMethod(String methodName, Object[] params) {
		methodName = methodName.replaceAll("[^\\x20-\\x7e]", "");
		logInfo("Call method: " + methodName);
		if (params != null && params.length > 0) {
			int i = 0;
			for (Object p : params) {
				logInfo("Param[" + i + "]=  " + p);
				i++;
			}
		}
	}
	public String getDefaultAppender(){
		return DEFAULT_APPENDER; 
	}
	/**
	 * Esegue il log dell'eccezione
	 * 
	 * @param ex
	 */
	public void logException(Exception ex) {
		_logger.error(ex.getMessage(), ex);
	}

	public void log(String message, Loglevels level) {
		message = message.replaceAll("[^\\x20-\\x7e]", "");
		switch (level) {
		case INFO:
			logInfo(message);
			break;
		case DEBUG:
			_logger.debug(message);
			break;
		case ERROR:
			_logger.error(message);
			break;
		case WARN:
			_logger.warn(message);
			break;
		default:
			break;
		}
	}
}