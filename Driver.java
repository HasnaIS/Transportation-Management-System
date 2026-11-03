//represents a driver in the ride-sharing system
public class Driver
        extends Person
        implements IDriver {

    private String vehiclePlate;

    private VehicleType vehicleType;

    public Driver(
            int id,
            String name,
            String phoneNumber,
            String vehiclePlate,
            VehicleType vehicleType) {

        super(
                id,
                name,
                phoneNumber
        );

        setVehiclePlate(vehiclePlate);

        setVehicleType(vehicleType);
    }

    @Override
    public String getVehiclePlate() {
        return vehiclePlate;
    }
//validates and sets the vehicle plate (3 uppercase letters followed by 4 digits)
@Override
public void setVehiclePlate(String vehiclePlate) {

    if (vehiclePlate == null || vehiclePlate.length() != 7) {
        throw new IllegalArgumentException("Invalid vehicle plate.");
    }

    for (int i = 0; i < 3; i++) {
        if (vehiclePlate.charAt(i) < 'A' ||
            vehiclePlate.charAt(i) > 'Z') {

            throw new IllegalArgumentException(
                    "Vehicle plate must be 3 uppercase letters followed by 4 digits."
            );
        }
    }

    for (int i = 3; i < 7; i++) {
        if (vehiclePlate.charAt(i) < '0' ||
            vehiclePlate.charAt(i) > '9') {

            throw new IllegalArgumentException(
                    "Vehicle plate must be 3 uppercase letters followed by 4 digits."
            );
        }
    }

    this.vehiclePlate = vehiclePlate;
}

    @Override
    public VehicleType getVehicleType() {
        return vehicleType;
    }
//sets vehicle type with null check
    @Override
    public void setVehicleType(
            VehicleType vehicleType) {

        if (vehicleType == null) {

            throw new IllegalArgumentException(
                    "Vehicle type cannot be null."
            );
        }

        this.vehicleType = vehicleType;
    }
//compares drivers by ID
    @Override
public int compareTo(IDriver other) {

    if (getId() < other.getId()) {
        return -1;
    } else if (getId() > other.getId()) {
        return 1;
    } else {
        return 0;
    }
}

    @Override
    public String toString() {

        return "Driver{" +
                "ID=" + getId() +
                ", Name='" + getName() + '\'' +
                ", Phone='" + getPhoneNumber() + '\'' +
                ", VehiclePlate='" + vehiclePlate + '\'' +
                ", VehicleType=" + vehicleType +
                '}';
    }
}