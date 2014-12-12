// *****************************
// Anji Zhao (az2324)
// COMS W3134 - Homework 6
// TooLargeNException.java
// ensure that no more than 7 vertices are used 
// *****************************

public class TooLargeNException extends Exception {
	
	public TooLargeNException(String message) {
		super(message); 
	}
	public TooLargeNException(String message, Throwable throwable) {
		super(message, throwable); 
	}

}