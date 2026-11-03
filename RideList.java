public class RideList implements IRideList {

	private Node<IRide> head;
	private int size;

	public RideList() {
		head = null;
		size = 0;
	}

	// Inserts a ride in its correct position (alphabetical by pickup location).
	public boolean addRide(IRide ride) {
		if (ride == null) {
			return false;
		}

		Node<IRide> previous = null;
		Node<IRide> current = head;

		// Skip every ride whose pickup location comes before or equals the new one.
		while (current != null && current.data.compareTo(ride) <= 0) {
			previous = current;
			current = current.next;
		}

		// Link the new node between previous and current.
		Node<IRide> newNode = new Node<IRide>(ride);
		newNode.next = current;
		if (previous == null) {
			head = newNode;
		}
		else {
			previous.next = newNode;
		}

		size++;
		return true;
	}

	// Removes the ride with the given ID.
   	public boolean removeRideById(int rideId) {
		Node<IRide> previous = null;
		Node<IRide> current = head;

		while (current != null) {
			if (current.data.getRideId() == rideId) {
				if (previous == null) {
					head = current.next;
				}
				else {
					previous.next = current.next;
				}
				size--;
				return true;
			}
			previous = current;
			current = current.next;
		}

		return false;
	}

	// Returns all rides ordered alphabetically by pickup location.
	public LinkedList<IRide> getAllAlphabetically() {
		LinkedList<IRide> result = new LinkedList<IRide>();
		Node<IRide> current = head;

		while (current != null) {
			result.insert(current.data);
			current = current.next;
		}

		return result;
	}

	// Returns every ride whose pickup location matches the given location
	public LinkedList<IRide> findByPickupLocation(String pickupLocation) {
		LinkedList<IRide> result = new LinkedList<IRide>();
		Node<IRide> current = head;

		while (current != null) {
			if (current.data.getPickupLocation().equalsIgnoreCase(pickupLocation)) {
				result.insert(current.data);
			}
			current = current.next;
		}

		return result;
	}

	// Returns every ride that involves a rider with exactly the given full name.
	public LinkedList<IRide> findByRiderName(String riderFullName) {
		LinkedList<IRide> result = new LinkedList<IRide>();
		Node<IRide> current = head;

		while (current != null) {
			if (hasRiderNamed(current.data, riderFullName)) {
				result.insert(current.data);
			}
			current = current.next;
		}

		return result;
	}

	// Checks whether a ride involves a rider with the given full name.
	private boolean hasRiderNamed(IRide ride, String riderFullName) {
		if (ride instanceof IPrivateRide) {
			IRider rider = ((IPrivateRide) ride).getRider();
			return rider != null && rider.getName().equals(riderFullName);
		}

		if (ride instanceof ISharedRide) {
			LinkedList<IRider> participants = ((ISharedRide) ride).getParticipants();

			if (participants.empty()) {
				return false;
			}

			participants.findFirst();
			while (!participants.last()) {
				if (participants.retrieve().getName().equals(riderFullName)) {
					return true;
				}
				participants.findNext();
			}

			return participants.retrieve().getName().equals(riderFullName);
		}

		return false;
	}

	public int size() {
		return size;
	}
}