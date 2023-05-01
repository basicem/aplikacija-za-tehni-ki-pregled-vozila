package enums;

public enum VehicleType {
    Putnički (1),
    Autobus (2),
    Teretno (3);

    private final int type;

    VehicleType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

}
