public class RiderList implements IRiderList {

	private Node<IRider> head;
	private int size;

	public RiderList() {
		head = null;
		size = 0;
	}

	public boolean add(IRider rider) {
		if (rider == null) {
			return false;
		}

		// The email must be unique across all riders.
		if (findByEmail(rider.getEmail()) != null) {
			return false;
		}

		Node<IRider> previous = null;
		Node<IRider> current = head;

		// Skip every rider whose ID is smaller than the new rider's ID.
		while (current != null && current.data.compareTo(rider) < 0) {
			previous = current;
			current = current.next;
		}

		// no duplicates allowed.
		if (current != null && current.data.compareTo(rider) == 0) {
			return false;
		}

		// Link the new node between previous and current.
		Node<IRider> newNode = new Node<IRider>(rider);
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

	public IRider findById(int riderId) {
		Node<IRider> current = head;

		while (current != null) {
			if (current.data.getId() == riderId) {
				return current.data;
			}
			current = current.next;
		}

		return null;
	}

	// Returns every rider whose full name is exactly the given name.
	public LinkedList<IRider> findByName(String fullName) {
		LinkedList<IRider> result = new LinkedList<IRider>();
		Node<IRider> current = head;

		while (current != null) {
			if (current.data.getName().equals(fullName)) {
				result.insert(current.data);
			}
			current = current.next;
		}

		return result;
	}

	// Returns the rider with the given email or null if there is none.
	public IRider findByEmail(String email) {
		Node<IRider> current = head;

		while (current != null) {
			if (current.data.getEmail().equals(email)) {
				return current.data;
			}
			current = current.next;
		}

		return null;
	}

	// Returns every rider from the given home city.
	public LinkedList<IRider> findByHomeCity(String homeCity) {
		LinkedList<IRider> result = new LinkedList<IRider>();
		Node<IRider> current = head;

		while (current != null) {
			if (current.data.getHomeCity().equals(homeCity)) {
				result.insert(current.data);
			}
			current = current.next;
		}

		return result;
	}

	// Returns all riders ordered by ID.
	public LinkedList<IRider> getAll() {
		LinkedList<IRider> result = new LinkedList<IRider>();
		Node<IRider> current = head;

		while (current != null) {
			result.insert(current.data);
			current = current.next;
		}

		return result;
	}

	// Removes the rider with the given ID.
	public boolean removeById(int riderId) {
		Node<IRider> previous = null;
		Node<IRider> current = head;

		while (current != null) {
			if (current.data.getId() == riderId) {
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

	// Removes the rider with the given email.
	public boolean removeByEmail(String email) {
		Node<IRider> previous = null;
		Node<IRider> current = head;

		while (current != null) {
			if (current.data.getEmail().equals(email)) {
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

	// Removes every rider whose full name is exactly the given name.
	public int removeByName(String fullName) {
		int count = 0;
		Node<IRider> previous = null;
		Node<IRider> current = head;

		while (current != null) {
			if (current.data.getName().equals(fullName)) {
				if (previous == null) {
					head = current.next;
				}
				else {
					previous.next = current.next;
				}
				size--;
				count++;
			}
			else {
				previous = current;
			}
			current = current.next;
		}

		return count;
	}

	// Removes every rider from the given home city.
	public int removeByHomeCity(String homeCity) {
		int count = 0;
		Node<IRider> previous = null;
		Node<IRider> current = head;

		while (current != null) {
			if (current.data.getHomeCity().equals(homeCity)) {
				if (previous == null) {
					head = current.next;
				}
				else {
					previous.next = current.next;
				}
				size--;
				count++;
			}
			else {
				previous = current;
			}
			current = current.next;
		}

		return count;
	}

	public int size() {
		return size;
	}
}
