package dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorMessageResponse extends Content {
	private static final long serialVersionUID = 1L;
	private Throwable exeption;
	private boolean exceptionOccurred = true;

	public Throwable getExeption() {
		return exeption;
	}

	public void setExeption(Throwable exeption) {
		this.exeption = exeption;
	}

	public boolean isExceptionOccurred() {
		return exceptionOccurred;
	}

	public void setExceptionOccurred(boolean exceptionOccurred) {
		this.exceptionOccurred = exceptionOccurred;
	}

}
