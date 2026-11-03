public abstract class Ride implements IRide {

	private static int nextRideId = 1;

	private final int rideId;
	private String pickupLocation;
	private String dropoffLocation;
	private final IDateTime pickupTime;
	private final IDateTime dropoffTime;
	private IDriver driver;

   // Creates a ride. The pickup time must be before the drop off time.
	public Ride(String pickupLocation, IDateTime pickupTime, IDateTime dropoffTime, String dropoffLocation, IDriver driver) {
		if (pickupTime == null || dropoffTime == null) {
			throw new IllegalArgumentException("Pickup and drop off times are required ");
		}

		if (pickupTime.compareTo(dropoffTime) >= 0) {
			throw new IllegalArgumentException("Pickup time must be before drop off time ");
		}

		this.rideId = nextRideId;
		nextRideId++;

		this.pickupLocation = pickupLocation;
		this.pickupTime = pickupTime;
		this.dropoffTime = dropoffTime;
		this.dropoffLocation = dropoffLocation;
		this.driver = driver;
	}

	public int getRideId() {
		return rideId;
	}

	public String getPickupLocation() {
		return pickupLocation;
	}

	public void setPickupLocation(String pickupLocation) {
		this.pickupLocation = pickupLocation;
	}

	public IDateTime getPickupTime() {
		return pickupTime;
	}

	public IDateTime getDropoffTime() {
		return dropoffTime;
	}

	public String getDropoffLocation() {
		return dropoffLocation;
	}

	public void setDropoffLocation(String dropoffLocation) {
		this.dropoffLocation = dropoffLocation;
	}

	public IDriver getDriver() {
		return driver;
	}

	public void setDriver(IDriver driver) {
		this.driver = driver;
	}

	public abstract boolean hasRider(int riderId);
   
   // Compares rides alphabetically by pickup location.
	public int compareTo(IRide other) {
		return pickupLocation.compareToIgnoreCase(other.getPickupLocation());
	}

	public String toString() {
		return "ID=" + rideId +
				", Pickup='" + pickupLocation + '\'' +
				", PickupTime=" + pickupTime.format() +
				", Dropoff='" + dropoffLocation + '\'' +
				", DropoffTime=" + dropoffTime.format() +
				", Driver='" + driver.getName() + '\'';
	}
}