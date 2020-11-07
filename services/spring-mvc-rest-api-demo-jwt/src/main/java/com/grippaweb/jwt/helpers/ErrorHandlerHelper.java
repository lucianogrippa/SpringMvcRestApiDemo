package com.grippaweb.jwt.helpers;

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
	public static String getStackTrace(String message,StackTraceElement[] stackTrace) {
	    String description = "";

		if (description != null ) {
		    StringWriter sw = new StringWriter();
			sw.append(message);
			for(StackTraceElement element : stackTrace) {
			    sw.append(String.format(" [%s] ", element.getLineNumber()));
			    if(element.getFileName() != null)
				sw.append(String.format(" %s ", element.getFileName()));
			    if(element.getClassName() != null)
				sw.append(String.format(" %s ", element.getClassName()));
			    if(element.getMethodName() != null)
				sw.append(String.format(" %s ", element.getMethodName()));
			    if(element.getModuleName() != null)
			    sw.append(String.format(" %s ", element.getModuleName()));
			}
			description = sw.toString();
		}
		return description;
	}
}
