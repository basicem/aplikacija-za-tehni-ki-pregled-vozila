package exceptions;

public class WrongVINNumber extends Throwable {
    public WrongVINNumber(String s) {
        super(s);
    }
}
