package springbook.user.dao.v2;

public class DuplicateUserIdException extends RuntimeException{

	public DuplicateUserIdException(Throwable cause) {
		super(cause);
	}
}
