import java.io.BufferedReader; 
import java.io.InputStreamReader;

public class Main {
public static void main(String[] args) {

    RideSharingSystem system =
            new RideSharingSystem();

    String ridersFile = "riders_100.csv";
    String driversFile = "drivers_30.csv";
    String ridesFile = "rides_40.csv";

    boolean ridersLoaded =
            system.loadRidersFromCSV(ridersFile);

    boolean driversLoaded =
            system.loadDriversFromCSV(driversFile);

    boolean ridesLoaded =
            system.loadRidesFromCSV(ridesFile);

    if (!ridersLoaded ||
            !driversLoaded ||
            !ridesLoaded) {

        System.out.println(
                "Error: Could not load the project data files."
        );

        return;
    }

    BufferedReader input =
            new BufferedReader(
                    new InputStreamReader(System.in)
            );

    boolean running = true;
// Repeates the menu until the user exits.
    while (running) {

        printMenu();

        try {

            System.out.print(
                    "Enter your choice: "
            );

            int choice =
                    Integer.parseInt(
                            input.readLine().trim()
                    );
// Executes the operation selected by the user.
            switch (choice) {

                case 1:

                    printList(
                            system.getAllRiders()
                    );

                    break;

                case 2:

                    System.out.print(
                            "Enter home city: "
                    );

                    printList(
                            system.searchRidersByHomeCity(
                                    input.readLine()
                            )
                    );

                    break;

                case 3:

                    System.out.print(
                            "Enter full name: "
                    );

                    printList(
                            system.searchRidersByName(
                                    input.readLine()
                            )
                    );

                    break;

                case 4:

                    addRider(
                            system,
                            input
                    );

                    break;

                case 5:

                    printList(
                            system.getAllDrivers()
                    );

                    break;

                case 6:

                    System.out.print(
                            "Enter vehicle type " +
                            "(SEDAN, LUXURY_SEDAN, SUV, VAN): "
                    );

                    VehicleType vehicleType =
                            parseVehicleType(
                                    input.readLine()
                            );

                    if (vehicleType == null) {

                        System.out.println(
                                "Invalid vehicle type."
                        );

                    } else {

                        printList(
                                system.searchDriversByVehicleType(
                                        vehicleType
                                )
                        );
                    }

                    break;

                case 7:

                    addDriver(
                            system,
                            input
                    );

                    break;

                case 8:

                    printList(
                            system.getAllRidesAlphabetically()
                    );

                    break;

                case 9:

                    System.out.print(
                            "Enter pickup location: "
                    );

                    printList(
                            system.searchRidesByPickupLocation(
                                    input.readLine()
                            )
                    );

                    break;

                case 10:

                    System.out.print("Enter rider full name: "
                    );

                    printList(
                            system.searchRidesByRiderName(
                                    input.readLine()
                            )
                    );

                    break;

                case 11:

                    System.out.print(
                            "Enter pickup location: "
                    );

                    LinkedList<IRider> participants =
                            system.getSharedRideParticipants(
                                    input.readLine()
                            );

                    printList(participants);

                    break;

                case 12:

                    addRide(
                            system,
                            input
                    );

                    break;

                case 0:

                    running = false;

                    System.out.println(
                            "Program ended."
                    );

                    break;

                default:

                    System.out.println(
                            "Invalid choice."
                    );
            }

        } catch (Exception e) {

            System.out.println(
                    "Invalid input."
            );
        }

        System.out.println();
    }
}
// Displays the available system operations.
private static void printMenu() {

    System.out.println(
            "===== Ride-Sharing System ====="
    );

    System.out.println(
            "1. List all riders."
    );

    System.out.println(
            "2. Search for riders by home city."
    );

    System.out.println(
            "3. Search for riders by name."
    );

    System.out.println(
            "4. Add a rider."
    );

    System.out.println(
            "5. List all drivers."
    );

    System.out.println(
            "6. Search for drivers by vehicle type."
    );

    System.out.println(
            "7. Add a driver."
    );

    System.out.println(
            "8. List all rides alphabetically by pickup location."
    );

    System.out.println(
            "9. Search for rides by pickup location."
    );

    System.out.println(
            "10. Search for rides by rider name."
    );

    System.out.println(
            "11. List the participants of a shared ride by pickup location."
    );

    System.out.println(
            "12. Add a ride."
    );

    System.out.println(
            "0. Exit."
    );
}
// Reads rider information and adds a new rider. 
private static void addRider(
        RideSharingSystem system,
        BufferedReader input)
        throws Exception {

    System.out.print(
            "Enter rider ID: "
    );

    int id =
            Integer.parseInt(
                    input.readLine().trim()
            );

    System.out.print(
            "Enter full name: "
    );

    String name =
            input.readLine();

    System.out.print(
            "Enter email: "
    );

    String email =
            input.readLine();

    System.out.print(
            "Enter phone number: "
    );

    String phone =
            input.readLine();

    System.out.print(
            "Enter home city: "
    );

    String city =
            input.readLine();

    try {

        IRider rider =
                new Rider(
                        id,
                        name,
                        email,
                        phone,
                        city
                );

        if (system.addRider(rider)) {

            System.out.println(
                    "Rider added successfully."
            );

        } else {

            System.out.println(
                    "Failed to add rider."
            );
        }

    } catch (IllegalArgumentException e) {

        System.out.println(
                "Failed to add rider: "
                + e.getMessage()
        );
    }
}
// Reads driver information and adds a new driver.
private static void addDriver(
        RideSharingSystem system,
        BufferedReader input)
        throws Exception {

    System.out.print(
            "Enter driver ID: "
    );
    int id =
            Integer.parseInt(
                    input.readLine().trim()
            );

    System.out.print(
            "Enter full name: "
    );

    String name =
            input.readLine();

    System.out.print(
            "Enter phone number: "
    );

    String phone =
            input.readLine();

    System.out.print(
            "Enter vehicle plate: "
    );

    String plate =
            input.readLine();

    System.out.print(
            "Enter vehicle type " +
            "(SEDAN, LUXURY_SEDAN, SUV, VAN): "
    );

    VehicleType vehicleType =
            parseVehicleType(
                    input.readLine()
            );

    if (vehicleType == null) {

        System.out.println(
                "Failed to add driver: invalid vehicle type."
        );

        return;
    }

    try {

        IDriver driver =
                new Driver(
                        id,
                        name,
                        phone,
                        plate,
                        vehicleType
                );

        if (system.addDriver(driver)) {

            System.out.println(
                    "Driver added successfully."
            );

        } else {

            System.out.println(
                    "Failed to add driver."
            );
        }

    } catch (IllegalArgumentException e) {

        System.out.println(
                "Failed to add driver: "
                + e.getMessage()
        );
    }
}
// Reads ride information and scheduals a private or shared ride.
private static void addRide(
        RideSharingSystem system,
        BufferedReader input)
        throws Exception {

    System.out.print(
            "Enter ride type (PRIVATE or SHARED): "
    );

    String rideType =
            input.readLine().trim();

    System.out.print(
            "Enter pickup location: "
    );

    String pickupLocation =
            input.readLine();

    System.out.print(
            "Enter pickup date/time " +
            "(MM/DD/YYYY HH:MM): "
    );

    IDateTime pickupTime =
            parseDateTime(
                    input.readLine()
            );

    System.out.print(
            "Enter drop-off date/time " +
            "(MM/DD/YYYY HH:MM): "
    );

    IDateTime dropoffTime =
            parseDateTime(
                    input.readLine()
            );

    System.out.print(
            "Enter drop-off location: "
    );

    String dropoffLocation =
            input.readLine();

    if (pickupTime == null ||
            dropoffTime == null) {

        System.out.println(
                "Invalid date/time."
        );

        return;
    }

    System.out.print(
            "Enter driver ID: "
    );

    int driverId =
            Integer.parseInt(
                    input.readLine().trim()
            );

    boolean success;

    if (rideType.equalsIgnoreCase(
            "PRIVATE")) {

        System.out.print(
                "Enter rider ID: "
        );

        int riderId =
                Integer.parseInt(
                        input.readLine().trim()
                );

        success =
                system.schedulePrivateRide(
                        pickupLocation,
                        pickupTime,
                        dropoffTime,
                        dropoffLocation,
                        riderId,
                        driverId
                );

    } else if (rideType.equalsIgnoreCase(
            "SHARED")) {

        System.out.print(
                "Enter number of riders: "
        );

        int count =
                Integer.parseInt(
                        input.readLine().trim()
                );

        if (count <= 0) {

            System.out.println(
                    "Invalid number of riders."
            );

            return;
        }

        int[] riderIds =
                new int[count];

        for (int i = 0;
             i < count;
             i++) {

            System.out.print(
                    "Enter rider ID "
                    + (i + 1)
                    + ": "
            );

            riderIds[i] =Integer.parseInt(
                            input.readLine().trim()
                    );
        }

        success =
                system.scheduleSharedRide(
                        pickupLocation,
                        pickupTime,
                        dropoffTime,
                        dropoffLocation,
                        riderIds,
                        driverId
                );

    } else {

        System.out.println(
                "Invalid ride type."
        );

        return;
    }

    if (success) {

        System.out.println(
                "Ride added successfully."
        );

    } else {

        System.out.println(
                "Failed to add ride."
        );
    }
}
// Converts the entered vehicle type into a VehicleType value.
private static VehicleType parseVehicleType(
        String value) {

    if (value == null) {
        return null;
    }

    if (value.equalsIgnoreCase(
            "SEDAN")) {

        return VehicleType.SEDAN;
    }

    if (value.equalsIgnoreCase(
            "LUXURY_SEDAN")
            ||
            value.equalsIgnoreCase(
                    "LUXURY SEDAN")) {

        return VehicleType.LUXURY_SEDAN;
    }

    if (value.equalsIgnoreCase(
            "SUV")) {

        return VehicleType.SUV;
    }

    if (value.equalsIgnoreCase(
            "VAN")) {

        return VehicleType.VAN;
    }

    return null;
}
// Converts the entered date and time into a DateTime object.
private static IDateTime parseDateTime(
        String value) {

    try {

        String[] parts =
                value.trim().split(" ");

        if (parts.length != 2) {
            return null;
        }

        String[] date =
                parts[0].split("/");

        String[] time =
                parts[1].split(":");

        if (date.length != 3 ||
                time.length != 2) {

            return null;
        }

        int month =
                Integer.parseInt(date[0]);

        int day =
                Integer.parseInt(date[1]);

        int year =
                Integer.parseInt(date[2]);

        int hour =
                Integer.parseInt(time[0]);

        int minute =
                Integer.parseInt(time[1]);

        return new DateTime(
                year,
                month,
                day,
                hour,
                minute
        );

    } catch (Exception e) {

        return null;
    }
}
// Prints all elements in a linked list.
private static <T> void printList(
        LinkedList<T> list) {

    if (list == null ||
            list.empty()) {

        System.out.println(
                "No results found."
        );

        return;
    }

    list.findFirst();

    while (true) {

        System.out.println(
                list.retrieve()
        );

        if (list.last()) {
            break;
        }

        list.findNext();
    }
}
}