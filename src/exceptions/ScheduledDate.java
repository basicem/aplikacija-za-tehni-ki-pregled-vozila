package exceptions;

public class ScheduledDate extends Throwable {
    public ScheduledDate(String vec_zakazan_termin) {
        super(vec_zakazan_termin);
    }
}
