package enums;

public enum StatusOfTechnicalInspection {
    Zakazan (1),
    Kompletiran (2),
    Otkazan (3);

    private final int statusOfTechnicalInspection;

    StatusOfTechnicalInspection(int statusOfTechnicalInspection) {
        this.statusOfTechnicalInspection = statusOfTechnicalInspection;
    }

    public int getStatusOfTechnicalInspection() {
        return statusOfTechnicalInspection;
    }
}
