package exceptions;

public class WrongRegistrationNumber extends Throwable {
    public WrongRegistrationNumber(String s) {
        super(s);
    }
}
