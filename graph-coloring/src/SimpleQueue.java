// SimpleQeuue: A simple implementation of a queue ADT using dynamically resized arrays
class SimpleQueue<E>{
	private Object[] arr;
	
	// constructor: initialize empty queue
	public SimpleQueue() {
		this.arr = new Object[0];
	}
	
	// Enqueue add object e to this queue.
	public void enqueue(E e) {
		Object[] newArr = new Object[this.arr.length + 1];
		System.arraycopy(this.arr, 0, newArr, 0, this.arr.length);
		this.arr = newArr;
		// upcast
		this.arr[this.arr.length - 1] = (Object)e;
	}
	
	// dequeue: dequeue next object from this queue.
	@SuppressWarnings("unchecked")
	public E dequeue() {
		// Check if queue is empty.
		if(this.arr.length == 0) {
			return null;
		}
		// Get element at head
		E res = (E)this.arr[0];
		
		// Resize array
		Object[] newArr = new Object[this.arr.length - 1];
		System.arraycopy(this.arr, 1, newArr, 0, this.arr.length - 1);
		this.arr = newArr;
		
		// Return head.
		return res;
	}
	
	// isEmpty: return true if this queue is empty and false otherwise
	public boolean isEmpty() {
		return this.arr.length == 0;
	}
	
	@Override
	public String toString() {
		return String.format("next: %s", this.arr[0].toString());
	}
}