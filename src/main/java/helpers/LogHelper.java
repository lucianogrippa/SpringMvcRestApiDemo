package helpers;

import com.google.gson.Gson;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

enum Loglevels {
	ERROR, INFO, WARN, DEBUG
}


public class LogHelper {
	private static LogHelper instance;
	private Logger _logger;
	private final static String DEFAULT_APPENDER=LogHelper.class.getName();

	public LogHelper() {
		_logger = LogManager.getLogger(DEFAULT_APPENDER);
	}

	public LogHelper(String appender) {
		_logger = LogManager.getLogger(appender);
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

	public String jsonParamiter(Object T) {
		Gson json = new Gson();

		return T != null ? json.toJson(T) : "NULL";
	}

	public void logMethod(String methodName, Object[] params) {
		methodName = methodName.replaceAll("[^\\x20-\\x7e]", "");
		LogHelper.getLogger().logInfo("Call method: " + methodName);
		if (params != null && params.length > 0) {
			int i = 0;
			for (Object p : params) {
				LogHelper.getLogger().logInfo("Param[" + i + "]=  " + p);
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
			LogHelper.getLogger().logInfo(message);
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

	public void changeLogLevel(Level logLevel) {
		_logger.setLevel(logLevel);
	}

	/**
	 * Ottiene l'instanza del logger utilizzando l'applender di default
	 * 
	 * @return the instance
	 */
	public static LogHelper getLogger() {
		if (instance == null) {
			instance = new LogHelper();
		}
		else
		{
			instance._logger = LogManager.getLogger(DEFAULT_APPENDER);
		}
		return instance;
	}

	/**
	 * Ottieni il logger specificando l'appender
	 * 
	 * @return the instance
	 */
	public static LogHelper getLogger(String appender) {
		if (instance == null) {
			instance = new LogHelper(appender);
		} else {
			instance._logger = LogManager.getLogger(appender);
		}
		return instance;
	}
}