public class Node<T> {

	public T data;
	public Node<T> next;

	// Creates an empty node.
	public Node() {
		data = null;
		next = null;
	}

	// Creates a node that stores the given value.
	public Node(T val) {
		data = val;
		next = null;
	}

	// Setters and Getters
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Node<T> getNext() {
		return next;
	}

	public void setNext(Node<T> next) {
		this.next = next;
	}
}
