import java.io.BufferedReader; 
import java.io.FileReader;
// Manages riders, drivers, and rides in the system.
public class RideSharingSystem implements IRideSharingSystem {

private RiderList riders;
private DriverList drivers;
private RideList rides;

public RideSharingSystem() {
    riders = new RiderList();
    drivers = new DriverList();
    rides = new RideList();
}
// loads rider data from a CSV file.
@Override
public boolean loadRidersFromCSV(String ridersFilePath) {

    try {
        BufferedReader reader =
                new BufferedReader(new FileReader(ridersFilePath));

        String line;

        while ((line = reader.readLine()) != null) {

            if (line.trim().isEmpty()) {
                continue;
            }

            String[] parts = line.split(",", -1);

            if (parts.length != 5) {
                reader.close();
                return false;
            }

            int id = Integer.parseInt(parts[0].trim());

            IRider rider = new Rider(
                    id,
                    parts[1].trim(),
                    parts[2].trim(),
                    parts[3].trim(),
                    parts[4].trim()
            );

            if (!addRider(rider)) {
                reader.close();
                return false;
            }
        }

        reader.close();
        return true;

    } catch (Exception e) {
        return false;
    }
}
// load driver data from a CSV file.
@Override
public boolean loadDriversFromCSV(String driversFilePath) {

    try {
        BufferedReader reader =
                new BufferedReader(new FileReader(driversFilePath));

        String line;

        while ((line = reader.readLine()) != null) {

            if (line.trim().isEmpty()) {
                continue;
            }

            String[] parts = line.split(",", -1);

            if (parts.length != 5) {
                reader.close();
                return false;
            }

            int id = Integer.parseInt(parts[0].trim());

            VehicleType vehicleType =
                    parseVehicleType(parts[4].trim());

            if (vehicleType == null) {
                reader.close();
                return false;
            }

            IDriver driver = new Driver(
                    id,
                    parts[1].trim(),
                    parts[2].trim(),
                    parts[3].trim(),
                    vehicleType
            );

            if (!addDriver(driver)) {
                reader.close();
                return false;
            }
        }

        reader.close();
        return true;

    } catch (Exception e) {
        return false;
    }
}
// load and schedules riders from a CSV file.
@Override
public boolean loadRidesFromCSV(String ridesFilePath) {

    try {
        BufferedReader reader =
                new BufferedReader(new FileReader(ridesFilePath));

        String line;

        while ((line = reader.readLine()) != null) {

            if (line.trim().isEmpty()) {
                continue;
            }

            String[] parts = line.split(",", -1);

            if (parts.length != 7) {
                reader.close();
                return false;
            }

            String rideType = parts[0].trim();

            String pickupLocation = parts[1].trim();

            IDateTime pickupTime =
                    parseDateTime(parts[2].trim());

            IDateTime dropoffTime =
                    parseDateTime(parts[3].trim());

            String dropoffLocation = parts[4].trim();

            int driverId =
                    Integer.parseInt(parts[5].trim());

            if (pickupTime == null || dropoffTime == null) {
                reader.close();
                return false;
            }

            boolean success;

            if (rideType.equalsIgnoreCase("PRIVATE")) {

                int riderId =
                        Integer.parseInt(parts[6].trim());

                success = schedulePrivateRide(
                        pickupLocation,
                        pickupTime,
                        dropoffTime,
                        dropoffLocation,
                        riderId,
                        driverId
                );
                } else if (rideType.equalsIgnoreCase("SHARED")) {

                String[] riderParts =
                        parts[6].trim().split(";");

                if (riderParts.length == 0) {
                    reader.close();
                    return false;
                }

                int[] riderIds =
                        new int[riderParts.length];

                for (int i = 0; i < riderParts.length; i++) {
                    riderIds[i] =
                            Integer.parseInt(
                                    riderParts[i].trim()
                            );
                }

                success = scheduleSharedRide(
                        pickupLocation,
                        pickupTime,
                        dropoffTime,
                        dropoffLocation,
                        riderIds,
                        driverId
                );

            } else {
                reader.close();
                return false;
            }

            if (!success) {
                reader.close();
                return false;
            }
        }

        reader.close();
        return true;

    } catch (Exception e) {
        return false;
    }
}
// Adds a rider if the ID is unique.
@Override
public boolean addRider(IRider rider) {

    if (rider == null) {
        return false;
    }

    if (riders.findById(rider.getId()) != null) {
        return false;
    }

    return riders.add(rider);
}
// Adds a driver if the ID and vehicle plate are unique.
@Override
public boolean addDriver(IDriver driver) {

    if (driver == null) {
        return false;
    }

    if (drivers.findById(driver.getId()) != null) {
        return false;
    }

    if (drivers.findByVehiclePlate(
            driver.getVehiclePlate()) != null) {
        return false;
    }

    return drivers.add(driver);
}
// Searches for a rider by ID.
@Override
public IRider searchRiderById(int riderId) {
    return riders.findById(riderId);
}

@Override
public IRider searchRiderByEmail(String email) {
    return riders.findByEmail(email);
}
// Searches for riders by name.
@Override
public LinkedList<IRider> searchRidersByName(
        String fullName) {

    return riders.findByName(fullName);
}
// searches for riders by home city.
@Override
public LinkedList<IRider> searchRidersByHomeCity(
        String homeCity) {

    return riders.findByHomeCity(homeCity);
}

@Override
public LinkedList<IRider> getAllRiders() {
    return riders.getAll();
}
// Searches for a driver by ID.
@Override
public IDriver searchDriverById(int driverId) {
    return drivers.findById(driverId);
}

@Override
public IDriver searchDriverByVehiclePlate(
        String vehiclePlate) {

    return drivers.findByVehiclePlate(vehiclePlate);
}
// Searches for drivers by vehicle type.
@Override
public LinkedList<IDriver> searchDriversByVehicleType(
        VehicleType vehicleType) {

    return drivers.findByVehicleType(vehicleType);
}

@Override
public LinkedList<IDriver> getAllDrivers() {
    return drivers.getAll();
}
// Removes a rider and updates the related rides.
@Override
public boolean removeRider(int riderId) {

    IRider rider = riders.findById(riderId);

    if (rider == null) {
        return false;
    }

    removeRiderFromRides(riderId);

    riders.removeById(riderId);

    return true;
}
// Removes a driver and the rides assigned to that driver.
@Override
public boolean removeDriver(int driverId) {

    IDriver driver = drivers.findById(driverId);

    if (driver == null) {
        return false;
    }

    removeDriverRides(driverId);

    drivers.removeById(driverId);

    return true;
}
// Schedules a private ride after checking for conflicts.
@Override
public boolean schedulePrivateRide(
        String pickupLocation,
        IDateTime pickupTime,
        IDateTime dropoffTime,
        String dropoffLocation,
        int riderId,
        int driverId) {

    IRider rider =
            riders.findById(riderId);

    IDriver driver =
            drivers.findById(driverId);

    if (rider == null || driver == null) {
        return false;
    }

    if (!validTimeRange(
            pickupTime,
            dropoffTime)) {

        return false;
    }

    if (hasConflict(
            rider.getRideHistory(),
            pickupTime,
            dropoffTime)) {

        return false;
    }

    if (hasConflict(
            driver.getRideHistory(),
            pickupTime,
            dropoffTime)) {

        return false;
    }
    PrivateRide ride =
            new PrivateRide(
                    pickupLocation,
                    pickupTime,
                    dropoffTime,
                    dropoffLocation,
                    rider,
                    driver
            );

    if (!rides.addRide(ride)) {
        return false;
    }

    rider.getRideHistory().insert(ride);

    driver.getRideHistory().insert(ride);

    return true;
}
// Schedules a shared ride after validating riders and driver availability.
@Override
public boolean scheduleSharedRide(
        String pickupLocation,
        IDateTime pickupTime,
        IDateTime dropoffTime,
        String dropoffLocation,
        int[] riderIds,
        int driverId) {

    IDriver driver =
            drivers.findById(driverId);

    if (driver == null ||
            riderIds == null ||
            riderIds.length == 0) {

        return false;
    }

    if (!validTimeRange(
            pickupTime,
            dropoffTime)) {

        return false;
    }

    IRider[] selectedRiders =
            new IRider[riderIds.length];

    for (int i = 0;
         i < riderIds.length;
         i++) {

        IRider rider =
                riders.findById(riderIds[i]);

        if (rider == null) {
            return false;
        }

        // Prevent the same rider from being added twice.
        for (int j = 0; j < i; j++) {

            if (riderIds[j] == riderIds[i]) {
                return false;
            }
        }

        if (hasConflict(
                rider.getRideHistory(),
                pickupTime,
                dropoffTime)) {

            return false;
        }

        selectedRiders[i] = rider;
    }

    if (hasConflict(
            driver.getRideHistory(),
            pickupTime,
            dropoffTime)) {

        return false;
    }

    SharedRide ride =
            new SharedRide(
                    pickupLocation,
                    pickupTime,
                    dropoffTime,
                    dropoffLocation,
                    driver
            );

    for (int i = 0;
         i < selectedRiders.length;
         i++) {

        if (!ride.addParticipant(
                selectedRiders[i])) {

            return false;
        }
    }

    if (!rides.addRide(ride)) {
        return false;
    }

    for (int i = 0;
         i < selectedRiders.length;
         i++) {

        selectedRiders[i]
                .getRideHistory()
                .insert(ride);
    }

    driver.getRideHistory().insert(ride);

    return true;
}
// Finds riders by pickup location.
@Override
public LinkedList<IRide> searchRidesByPickupLocation(
        String pickupLocation) {

    return rides.findByPickupLocation(
            pickupLocation);
}
// Finds riders by rider name.
@Override
public LinkedList<IRide> searchRidesByRiderName(
        String riderName) {

    return rides.findByRiderName(riderName);
}
// Returns participants of shared rides at a pickup location.
@Override
public LinkedList<IRider> getSharedRideParticipants(
        String pickupLocation) {

    LinkedList<IRide> matchingRides =
            rides.findByPickupLocation(
                    pickupLocation);

    LinkedList<IRider> result =
            new LinkedList<IRider>();

    if (matchingRides.empty()) {
        return result;
    }

    matchingRides.findFirst();

    while (true) {

        IRide ride =
                matchingRides.retrieve();

        if (ride instanceof ISharedRide) {

            LinkedList<IRider> participants =
                    ((ISharedRide) ride)
                            .getParticipants();

            if (!participants.empty()) {

                participants.findFirst();

                while (true) {

                    result.insert(
                            participants.retrieve());

                    if (participants.last()) {
                        break;
                    }

                    participants.findNext();
                }
            }
        }

        if (matchingRides.last()) {
            break;
        }

        matchingRides.findNext();
    }

    return result;
}
// Return all rides sorted by pickup location.
@Override
public LinkedList<IRide> getAllRidesAlphabetically() {

    return rides.getAllAlphabetically();
}
// Checks that the pickup time is before the drop-off time.
private boolean validTimeRange(IDateTime pickupTime,
        IDateTime dropoffTime) {

    if (pickupTime == null ||
            dropoffTime == null) {

        return false;
    }

    return pickupTime.compareTo(
            dropoffTime) < 0;
}
// checks whether a ride overlaps with an existing ride.
private boolean hasConflict(
        LinkedList<IRide> history,
        IDateTime pickupTime,
        IDateTime dropoffTime) {

    if (history == null ||
            history.empty()) {

        return false;
    }

    history.findFirst();

    while (true) {

        IRide existingRide =
                history.retrieve();

        boolean overlap =
                pickupTime.compareTo(
                        existingRide.getDropoffTime()) < 0
                &&
                existingRide.getPickupTime()
                        .compareTo(dropoffTime) < 0;

        if (overlap) {
            return true;
        }

        if (history.last()) {
            break;
        }

        history.findNext();
    }

    return false;
}
// Removes a rider from private and shared rides.
private void removeRiderFromRides(
        int riderId) {

    LinkedList<IRide> allRides =
            rides.getAllAlphabetically();

    if (allRides.empty()) {
        return;
    }

    allRides.findFirst();

    while (true) {

        IRide ride =
                allRides.retrieve();

        if (ride instanceof IPrivateRide) {

            if (ride.hasRider(riderId)) {
                removeRideAndHistories(ride);
            }

        } else if (ride instanceof ISharedRide) {

            ISharedRide sharedRide =
                    (ISharedRide) ride;

            if (sharedRide.hasRider(riderId)) {

                IRider rider =
                        riders.findById(riderId);

                removeRideFromHistory(
                        rider,
                        ride);

                sharedRide.removeParticipantById(
                        riderId);

                if (sharedRide.isEmpty()) {
                    removeRideAndHistories(ride);
                }
            }
        }

        if (allRides.last()) {
            break;
        }

        allRides.findNext();
    }
}
// Removes all rides assigned to a driver.
private void removeDriverRides(
        int driverId) {

    LinkedList<IRide> allRides =
            rides.getAllAlphabetically();

    if (allRides.empty()) {
        return;
    }

    allRides.findFirst();

    while (true) {

        IRide ride =
                allRides.retrieve();

        if (ride.getDriver() != null &&
                ride.getDriver().getId()
                        == driverId) {

            removeRideAndHistories(ride);
        }

        if (allRides.last()) {
            break;
        }

        allRides.findNext();
    }
}
// Removes a ride from the system and all related histories.
private void removeRideAndHistories(
        IRide ride) {

    removeRideFromHistory(
            ride.getDriver(),
            ride);

    if (ride instanceof IPrivateRide) {

        removeRideFromHistory(
                ((IPrivateRide) ride).getRider(),
                ride);

    } else if (ride instanceof ISharedRide) {

        LinkedList<IRider> participants =
                ((ISharedRide) ride)
                        .getParticipants();

        if (!participants.empty()) {

            participants.findFirst();

            while (true) {

                removeRideFromHistory(
                        participants.retrieve(),
                        ride);

                if (participants.last()) {
                    break;
                }

                participants.findNext();
            }
        }
    }

    rides.removeRideById(
            ride.getRideId());
}
// Removes a ride from a person's ride history.
private void removeRideFromHistory(
        IPerson person,
        IRide ride) {

    if (person == null) {
        return;
    }

    LinkedList<IRide> history =
            person.getRideHistory();

    if (history.empty()) {
        return;
    }

    history.findFirst();

    while (true) {

        if (history.retrieve() == ride) {

            history.remove();
            return;
        }

        if (history.last()) {
            break;
        }

        history.findNext();
    }
}
// Converts a vehicle type string into a VehicleType value.
private static VehicleType parseVehicleType(
        String value) {

    if (value.equalsIgnoreCase("SEDAN")) {
        return VehicleType.SEDAN;
    }

    if (value.equalsIgnoreCase("LUXURY_SEDAN")
            || value.equalsIgnoreCase(
                    "LUXURY SEDAN")) {

        return VehicleType.LUXURY_SEDAN;
    }

    if (value.equalsIgnoreCase("SUV")) {
        return VehicleType.SUV;
    }

    if (value.equalsIgnoreCase("VAN")) {
        return VehicleType.VAN;
    }

    return null;
}
// Converts a date and time string into a DateTime object.
private static IDateTime parseDateTime(
        String value) {

    try {

        String[] parts =
                value.split(" ");

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
}