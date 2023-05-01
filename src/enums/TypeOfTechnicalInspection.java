package enums;

public enum TypeOfTechnicalInspection {
    Redovni (1),
    Preventivni (2),
    Vandredni (3);

    private final int typeOfTechnicalInspection;

    TypeOfTechnicalInspection(int typeOfTechnicalInspection) {
        this.typeOfTechnicalInspection = typeOfTechnicalInspection;
    }

    public int getTypeOfTechnicalInspection() {
        return typeOfTechnicalInspection;
    }
}
