// Edge: represents edge in graph
class Edge {
	
	// IDs of start and end nodes
	private int id1;
	private int id2; 
	
	// constructor
	public Edge(int id1, int id2) {
		this.id1 = id1;
		this.id2 = id2;
	}

	// getters
	public int getid1() {
		return id1;
	}

	public int getid2() {
		return id2;
	}

}