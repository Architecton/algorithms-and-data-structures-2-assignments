// Node: represents a node in a graph
class Node {	
	
	// attributes (node ID)
	private int id;
	// colour == -1 -> uncoloured
	private int colour;
	private SimpleList<Edge> edges;
	private SimpleList<Integer> neighborIds;
	public int setIndex;
	
	// constructor
	public Node(int id) {
		this.id = id;
		this.colour = 0;
		this.edges = new SimpleList<Edge>();
		this.neighborIds = new SimpleList<Integer>();
		this.setIndex = 0;
	}
	
	// addEdge: add edge to this node
	public void addEdge(Edge e) {
		edges.add(e);
		// Add found neighbors to list of neighbors.
		if(e.getid1() == this.id) {neighborIds.add(e.getid2());}
		if(e.getid2() == this.id) {neighborIds.add(e.getid1());}
	}
	
	// getters and setters
	public int getId() {
		return id;
	}

	public int getColour() {
		return colour;
	}
	
	public void setColour(int colour) {
		this.colour = colour;
	}

	public int[] getNeighborIds() {
		int[] ids = new int[this.neighborIds.arr.length];
		for(int i = 0; i < ids.length; i++) {
			ids[i] = (Integer)this.neighborIds.arr[i];
		}
		return ids;
	}
	
	@Override
	public String toString() {
		return String.format("id == %d, colour == %d, Neighbors: %s", this.id, this.colour, this.neighborIds.toString());
	}
	
}