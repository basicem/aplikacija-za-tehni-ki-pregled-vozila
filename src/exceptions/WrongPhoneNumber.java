package exceptions;

public class WrongPhoneNumber extends Throwable {
    public WrongPhoneNumber(String s) {
        super(s);
    }
}
