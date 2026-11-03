public class SharedRide extends Ride implements ISharedRide {

	private LinkedList<IRider> participants;

	// Creates a shared ride . 
   	public SharedRide(String pickupLocation, IDateTime pickupTime, IDateTime dropoffTime, String dropoffLocation, IDriver driver) {

		super(pickupLocation, pickupTime, dropoffTime, dropoffLocation, driver);

		participants = new LinkedList<IRider>();
	}

	// Searches the participants for the rider with the given ID.
	private boolean findParticipant(int riderId) {
		if (participants.empty()) {
			return false;
		}

		participants.findFirst();
		while (!participants.last()) {
			if (participants.retrieve().getId() == riderId) {
				return true;
			}
			participants.findNext();
		}

		// Check the last participant.
		return participants.retrieve().getId() == riderId;
	}

	public LinkedList<IRider> getParticipants() {
		return participants;
	}

	// Adds a rider at the end of the participants.
	public boolean addParticipant(IRider rider) {
		if (rider == null || findParticipant(rider.getId())) {
			return false;
		}
		participants.insert(rider);
		return true;
	}

	// Removes the rider with the given ID.
	public boolean removeParticipantById(int riderId) {
		if (!findParticipant(riderId)) {
			return false;
		}
		participants.remove();
		return true;
	}

	public boolean isEmpty() {
		return participants.empty();
	}

	public boolean hasRider(int riderId) {
		return findParticipant(riderId);
	}

	public String toString() {
		String names = "";

		if (!participants.empty()) {
			participants.findFirst();
			while (!participants.last()) {
				names += participants.retrieve().getName() + ", ";
				participants.findNext();
			}
			names += participants.retrieve().getName();
		}

		return "SharedRide{" +
				super.toString() +
				", Riders=[" + names + "]" +
				'}';
	}
}