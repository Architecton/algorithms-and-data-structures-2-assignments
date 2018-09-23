class SimpleList <E>{
	Object[] arr;
	private int size;

	public SimpleList() {
		this.size = 0;
		this.arr = new Object[0];
	}

	public void add(E el) {
		Object[] newArr = new Object[this.arr.length + 1];
		System.arraycopy(this.arr, 0, newArr, 1, this.arr.length);
		this.arr = newArr;
		// upcast
		this.arr[0] = (Object)el;
		this.size++;
	}
	
	@SuppressWarnings("unchecked")
	public E get(int index) {
		E res = (E)this.arr[index];
		return res;
	}
	
	public int size() {
		return this.size;
	}

	public void clear() {
		this.arr = new Object[0];
		this.size = 0;
	}
	
	public void sort() {
		for(int i = 0; i < this.arr.length - 1; i++) {
			int minIndex = i;
			for(int j = i + 1; j < this.arr.length; j++) {
				if((Integer)this.arr[j] < (Integer)this.arr[minIndex]) {
					minIndex = j;
				}
			}
			if(minIndex != i) {
				Object temp = this.arr[i];
				this.arr[i] = this.arr[minIndex];
				this.arr[minIndex] = temp;
			}
		}
	}
}
