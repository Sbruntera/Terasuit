package logic;

public class Register {
public static void main(String[] args) {
	String hashed = BCrypt.hashpw("test", BCrypt.gensalt());
	if (BCrypt.checkpw("test1", hashed))
		System.out.println("It matches");
	else
		System.out.println("It does not match");
}
}
