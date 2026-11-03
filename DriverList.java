public class DriverList implements IDriverList {

	private Node<IDriver> head;
	private int size;

	public DriverList() {
		head = null;
		size = 0;
	}

	// Inserts a driver in its correct position.
	public boolean add(IDriver driver) {
		if (driver == null) {
			return false;
		}

		// The vehicle plate must be unique across all drivers.
		if (findByVehiclePlate(driver.getVehiclePlate()) != null) {
			return false;
		}

		Node<IDriver> previous = null;
		Node<IDriver> current = head;

		// Skip every driver whose ID is smaller than the new driver's ID.
		while (current != null && current.data.compareTo(driver) < 0) {
			previous = current;
			current = current.next;
		}

		// We stopped on a driver with the same ID: no duplicates allowed.
		if (current != null && current.data.compareTo(driver) == 0) {
			return false;
		}

		// Link the new node between previous and current.
		Node<IDriver> newNode = new Node<IDriver>(driver);
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

	public IDriver findById(int driverId) {
		Node<IDriver> current = head;

		while (current != null) {
			if (current.data.getId() == driverId) {
				return current.data;
			}
			current = current.next;
		}

		return null;
	}

	public LinkedList<IDriver> findByName(String fullName) {
		LinkedList<IDriver> result = new LinkedList<IDriver>();
		Node<IDriver> current = head;

		while (current != null) {
			if (current.data.getName().equals(fullName)) {
				result.insert(current.data);
			}
			current = current.next;
		}

		return result;
	}

	// Returns the driver with the given vehicle plate.
	public IDriver findByVehiclePlate(String vehiclePlate) {
		Node<IDriver> current = head;

		while (current != null) {
			if (current.data.getVehiclePlate().equals(vehiclePlate)) {
				return current.data;
			}
			current = current.next;
		}

		return null;
	}

	// Returns every driver with the given vehicle type.
	public LinkedList<IDriver> findByVehicleType(VehicleType vehicleType) {
		LinkedList<IDriver> result = new LinkedList<IDriver>();
		Node<IDriver> current = head;

		while (current != null) {
			if (current.data.getVehicleType() == vehicleType) {
				result.insert(current.data);
			}
			current = current.next;
		}

		return result;
	}

	// Returns all drivers ordered by ID.
	public LinkedList<IDriver> getAll() {
		LinkedList<IDriver> result = new LinkedList<IDriver>();
		Node<IDriver> current = head;

		while (current != null) {
			result.insert(current.data);
			current = current.next;
		}

		return result;
	}

	// Removes the driver with the given ID.
   	public boolean removeById(int driverId) {
		Node<IDriver> previous = null;
		Node<IDriver> current = head;

		while (current != null) {
			if (current.data.getId() == driverId) {
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

	// Removes the driver with the given vehicle plate.
   	public boolean removeByVehiclePlate(String vehiclePlate) {
		Node<IDriver> previous = null;
		Node<IDriver> current = head;

		while (current != null) {
			if (current.data.getVehiclePlate().equals(vehiclePlate)) {
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

	// Removes every driver whose full name is exactly the given name.
	public int removeByName(String fullName) {
		int count = 0;
		Node<IDriver> previous = null;
		Node<IDriver> current = head;

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

	// Removes every driver with the given vehicle type.
	public int removeByVehicleType(VehicleType vehicleType) {
		int count = 0;
		Node<IDriver> previous = null;
		Node<IDriver> current = head;

		while (current != null) {
			if (current.data.getVehicleType() == vehicleType) {
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