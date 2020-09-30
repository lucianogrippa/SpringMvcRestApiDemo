package com.grippaweb.usersmanager.helpers;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ErrorHandlerHelper {
	public static String getStackTrace(Throwable exception) {
		String message = "";

		if (exception != null) {
			StringWriter sw = new StringWriter();
			sw.append(exception.getMessage());
			PrintWriter pw = new PrintWriter(sw);
			exception.printStackTrace(pw);
			
			message = sw.toString();
		}
		return message;
	}
	public static String traceString(String traceMessage,StackTraceElement[] trace) {
		String message = "";

		if (trace != null) {
			StringWriter sw = new StringWriter();
			sw.append(traceMessage);
			for(StackTraceElement element : trace) {
				sw.append(element.getFileName()+" "+element.getLineNumber());
				sw.append(" -> ");
				sw.append(element.getClassName());
				sw.append(" ");
				sw.append(element.getMethodName());
				sw.append("\n");
			}
			
			message = sw.toString();
		}
		return message;
	}
}
