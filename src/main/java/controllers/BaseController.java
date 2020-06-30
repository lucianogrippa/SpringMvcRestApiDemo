package controllers;

import helpers.LogHelper;

public abstract class BaseController {
	protected LogHelper logger = LogHelper.getLogger();

	public LogHelper getLogger() {
		return logger;
	}

	public void setLogger(LogHelper logger) {
		this.logger = logger;
	}

}
