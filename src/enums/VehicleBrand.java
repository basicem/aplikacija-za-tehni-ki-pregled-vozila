package enums;

public enum VehicleBrand {
    Suzuki (1),
    Fiat (2),
    Volvo (3),
    Volkswagen(4);

    private final int brand;

    VehicleBrand(int brand) {
        this.brand = brand;
    }

    public int getVehicleBrand() {
        return brand;
    }
}