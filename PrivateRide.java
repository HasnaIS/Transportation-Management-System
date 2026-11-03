public class PrivateRide extends Ride implements IPrivateRide {

	private IRider rider;

	// Creates a private ride for one rider with one driver.
	public PrivateRide(String pickupLocation, IDateTime pickupTime, IDateTime dropoffTime, String dropoffLocation, IRider rider, IDriver driver) {

		super(pickupLocation, pickupTime, dropoffTime, dropoffLocation, driver);
		this.rider = rider;
	}

	public IRider getRider() {
		return rider;
	}

	public void setRider(IRider rider) {
		this.rider = rider;
	}

	// Returns true if the rider of this ride has the given ID.
	public boolean hasRider(int riderId) {
		return rider != null && rider.getId() == riderId;
	}

	public String toString() {
		return "PrivateRide{" +
				super.toString() +
				", Rider='" + rider.getName() + '\'' +
				'}';
	}
}