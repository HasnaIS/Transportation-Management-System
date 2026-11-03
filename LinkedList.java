public class LinkedList<T> {

	private Node<T> head;
	private Node<T> current;

	// Creates an empty list.
	public LinkedList() {
		head = current = null;
	}

	// Returns true if the list has no elements.
	public boolean empty() {
		return head == null;
	}

	// Returns true if current is the last element.
	public boolean last() {
		return current.next == null;
	}

	public boolean full() {
		return false;
	}

	// Makes the first element the current element.
	public void findFirst() {
		current = head;
	}

	// Moves current to the next element.
	public void findNext() {
		current = current.next;
	}

	// Returns the current element.
   	public T retrieve() {
		return current.data;
	}

	// Replaces the current element with val. 
   	public void update(T val) {
		current.data = val;
	}

	// Inserts val after the current element and makes it the current element and if the list is empty val becomes the head.
	public void insert(T val) {
		Node<T> tmp;
		if (empty()) {
			current = head = new Node<T>(val);
		}
		else {
			tmp = current.next;
			current.next = new Node<T>(val);
			current = current.next;
			current.next = tmp;
		}
	}

	// Removes the current element The next element becomes current and if there is no next element current goes back to the head.
	public void remove() {
		if (current == head) {
			head = head.next;
		}
		else {
			Node<T> tmp = head;
			while (tmp.next != current)
				tmp = tmp.next;
			tmp.next = current.next;
		}
		if (current.next == null)
			current = head;
		else
			current = current.next;
	}
}
