package ch.hszt.groupf.fallstudie.server.socket;


public class ExitException extends SecurityException {
	public final int status;

	public ExitException(int status) {

		this.status = status;
	}
}
