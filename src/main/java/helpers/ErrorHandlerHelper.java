package helpers;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ErrorHandlerHelper {
	public static String getStatTrace(Throwable exception) {
		String message = "";

		if (exception != null) {
			StringWriter sw = new StringWriter();
			sw.append(exception.getMessage());
			PrintWriter pw = new PrintWriter(sw);
			exception.printStackTrace(pw);
		}
		return message;
	}
}
