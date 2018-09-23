// Graph: represents a graph
class Graph implements Cloneable {
	private Node[] nodes;
	private int traceLimit;
	private String[] traceBuffer;
	private int traceIndex;
	boolean end = false;
	
	// constructor
	public Graph(int numNodes){
		nodes = new Node[numNodes];
		// Initialize nodes with IDs from [0, numNode - 1].
		for (int i = 0; i < nodes.length; i++) {
			nodes[i] = new Node(i);
			// colour == -1 -> uncoloured
			nodes[i].setColour(-1);
		}
		this.traceBuffer = new String[95000];
		for(int i = 0; i < this.traceBuffer.length; i++) {
			this.traceBuffer[i] = "";
		}
		this.traceIndex = 0;
	}
	
	// AddConnection: Adds a connection between nodes specified by IDs fromNode and toNode
	public void addConnection(int id1, int id2){
		Edge e = new Edge(id1, id2);
		nodes[id1].addEdge(e);
		nodes[id2].addEdge(e);
	}
	
	// clear: remove all set colors from this graph (set signal value -1).
	public void clear() {
		for(Node node : this.nodes) {
			node.setColour(-1);
		}
	}
	
	/*
	 * BFS(s):
	 * 		mark s as visited and insert s into queue.  level(s)=0.
   	 * 		while (queue not empty):
     * 			v <- queue.remove()
     * 			for all unvisited neighbors w of v:
     * 				put edge (v,w) into BFS tree T
	 * 				mark w as visited, level(w)=level(v)+1
	 * 				queue.insert(w) 
	 */
	
	// colour_2c: Try to colour this graph using two colours (breadth first).
	// Returns true if this graph is colourable with two colours (bipartite)
	// and false otherwise.
	public boolean colour_2c(int traceLimit) {
		// Get starting node (0 by assumption)
		Node root = this.nodes[0]; // OK
		root.setColour(0);
		// Create a new instance of SimpleQeueue and enqueue the starting node.
		SimpleQueue<Node> queue = new SimpleQueue<Node>();
		queue.enqueue(root);
		// Try to colour the graph.
		boolean bipartite = colour_2c_aux(root, queue, traceLimit);
	
		// TRACE functionality ////////////////////////
		this.traceIndex++;
		this.traceBuffer[traceIndex] += (bipartite ? "OK" : "NOK");
		///////////////////////////////////////////////
		
		return bipartite;
	}
	
	// colour_2c_aux: auxilliary private method for the colour_2c method.
	private boolean colour_2c_aux(Node root, SimpleQueue<Node> queue, int traceLimit) {
		// Assume it is bipartite.
		boolean bipartite = true;
		int currentSetIndex = -1;
		SimpleList<Integer> set = new SimpleList<Integer>();
		// While the queue is not empty...
		search: {
			while(!queue.isEmpty()) {
				Node next = queue.dequeue();
				
				// TRACE functionality ////////////////////////
				// If starting next set...
				if(next.setIndex != currentSetIndex) {
					// print set
					set.sort();
					for(int i = 0; i < set.size(); i++) {
						this.traceBuffer[traceIndex] += String.format((i == set.size() - 1) ? "%d" : "%d ", set.get(i));
					}
					set.clear();
					
					
					if(currentSetIndex == -1) {
						this.traceBuffer[traceIndex] += String.format("%d : ", next.setIndex);
					} else {
						this.traceIndex++;
						this.traceBuffer[traceIndex] += String.format("%d : ", next.setIndex);
					}

					currentSetIndex = next.setIndex;

					set.add(next.getId());
				} else {
					set.add(next.getId());
				}
				/*if(this.traceLineCounter >= this.traceLimit) {
					if(next.setIndex != currentSetIndex) {
						System.out.printf((currentSetIndex == -1) ? "%d : " : "%n%d : " , next.setIndex);
						this.traceLineCounter++;
						currentSetIndex = next.setIndex;
						System.out.printf("%d ", next.getId());
					} else {
						System.out.printf("%d ", next.getId());
					}
				}*/
				///////////////////////////////////////////////
				
				// go over neighbors
				for(Integer neighborId : next.getNeighborIds()) {
					// if neighbor same colour -> NOT BIPARTITE (end colouring)
					if(this.nodes[neighborId].getColour() == next.getColour()) {
						bipartite = false;
						
						// TRACE functionality /////////////////////////////////////////
						// Purge and print remaining nodes with current set index
						while(!queue.isEmpty()) {
							Node n = queue.dequeue();
							if(n.setIndex == currentSetIndex) {
								set.add(n.getId());
							} else {
								break;
							}
						}
						
						set.sort();
						for(int i = 0; i < set.size(); i++) {
							this.traceBuffer[traceIndex] += String.format((i == set.size() - 1) ? "%d" : "%d ", set.get(i));
						}
						
						////////////////////////////////////////////////////////////////
						
						break search;
					}
					
					// if neighbor unvisited then color and add to queue (neighbor.setIndex == parent.setIndex + 1)
					if(this.nodes[neighborId].getColour() == -1) {
						this.nodes[neighborId].setColour(Math.abs(next.getColour() - 1));
						this.nodes[neighborId].setIndex = next.setIndex + 1;
						queue.enqueue(this.nodes[neighborId]);
					}
				}

			}
		}
		
		if(bipartite) {
			set.sort();
			for(int i = 0; i < set.size(); i++) {
				this.traceBuffer[traceIndex] += String.format((i == set.size() - 1) ? "%d" : "%d ", set.get(i));
			}
		}
		
		return bipartite;
	}
	
	
	// Depth first search - sketch
	/*
	// Assume this graph is bipartite (can be coloured with two colours)
	private boolean bipartite = true;
	
	// colout_2c: check if this graph can be colored using two colors
	public void colour_2c(int traceLimit) {
		// Assume root is Node with id == 0
		Node root = this.nodes[0];
		// Start colouring with colour 0.
		root.setColour(0);
		
		for(Integer neighbor : root.getNeighborIds()) {
			colour_2c_aux(this.nodes[neighbor], root);
			if(!bipartite) {
				break;
			}
		}
	}
	
	private void colour_2c_aux(Node current, Node parent) {
		
		if(!bipartite) {
			return ;
		}
		
		if(current.getColour() == -1) {
			current.setColour(Math.abs(parent.getColour() - 1));
		}
		
		for(Integer neighbor : current.getNeighborIds()) {
			if(this.nodes[neighbor].getColour() == current.getColour()) {
				bipartite = false;
				return;
			}
			
			if(this.nodes[neighbor].getColour() == -1) {
				colour_2c_aux(this.nodes[neighbor], current);
			}
		}
	} */
	
	// colour_gr: Colour this graph using the greedy colouring algorithm.
	public void colour_gr(int traceLimit) {
		// Set maximum number of lines to trace
		if(traceLimit == -1) {
			this.traceLimit = Integer.MAX_VALUE;
		} else {
			this.traceLimit = traceLimit;
		}
		
		// Make list of available colours [0, num_nodes].
		int[] colours = new int[this.nodes.length];
		for(int i = 0; i < colours.length; i++) {
			colours[i] = i;
		}
		
		// Go over vertices in ascending ids and colour with least colour that does not violate the rule.
		for(Node next : this.nodes) {
			for(Integer colour : colours) {
				if(colourable(next, colour)) {
					next.setColour(colour);
					// TRACE functionality ////////////////////////
					this.traceBuffer[traceIndex] += String.format("%d : %d", next.getId(), next.getColour());
					this.traceIndex++;
					///////////////////////////////////////////////
					break;
				}
			}
		}
	}
	
	// colourable: Check if the Node instance node is colourable with colout colour.
	private boolean colourable(Node node, int colour) {
		// Go over neighbors and check if any have this colour.
		for(Integer neighborId : node.getNeighborIds()) {
			if(this.nodes[neighborId].getColour() == colour) {
				return false;
			}
		}
		return true;
	}
	
	// colour_ex: Find optimal colouring of this graph using exhaustive search.
	public int colour_ex(int traceLimit) {
		
		// Set maximum number of lines to trace
		if(traceLimit == -1) {
			this.traceLimit = Integer.MAX_VALUE;
		} else {
			this.traceLimit = traceLimit;
		}
		
		// Define and initialize an array for holding the current colouring configuration.
		char[] colourMap = new char[this.nodes.length];
		// Define initial number of colours.
		int k = 2;
		
		// TRACE functionality ///////////////////////
		this.traceBuffer[traceIndex] += String.format("k = %d", k);
		this.traceIndex++;
		//////////////////////////////////////////////
		
		while(true) {
			
			// Generate all representations of digits in k-nary from that are not longer than number of nodes
			// Get maximum number in k-nary number system that can be expressed with number of places equal to number of nodes (in decimal).
			int upperLimit = getUpperLimit(k, colourMap.length);
			// Go over all number on [0, upperLimit]
			for(int i = 0; i <= upperLimit; i++) {
				
				// Getting the colour map /////////////////////
				String configuration = Integer.toString(i, k);
				
				// Pad with zeros to achieve desired length.
				int paddingLength = colourMap.length - configuration.length();
				String padStr = "";
				for(int j = 0; j < colourMap.length; j++) {
					padStr += "0";
				}
				// Convert to array. 
				configuration = padStr.substring(0, paddingLength) + configuration;
				colourMap = configuration.toCharArray();
				
				// TRACE functionality ///////////////////////
				// Print configuration.
				for(int j = 0; j < colourMap.length; j++) {
					this.traceBuffer[traceIndex] += String.format("%s ", colourMap[j]);
				}
				//////////////////////////////////////////////
				
				//////////////////////////////////////////////
				
				// Assign computed colour permutation to nodes in this graph.
				for(int j = 0; j < this.nodes.length; j++) {
					this.nodes[j].setColour(charToColour(colourMap[j]));
				}
				
				// Check if colour permutation if valid.
				boolean valid = isValidColouring();
				if(valid) {
					// TRACE functionality ///////////////////
					this.traceBuffer[traceIndex] += String.format("OK");
					this.traceIndex++;
					//////////////////////////////////////////
					return k;
				} else {
					// TRACE functionality ///////////////////
					this.traceBuffer[traceIndex] += String.format("NOK");
					this.traceIndex++;
					//////////////////////////////////////////
				}
			}
			// If valid configuration not found, increment k.
			k++;
			
			// TRACE functionality ///////////////////
			this.traceBuffer[traceIndex] += String.format("k = %d", k);
			this.traceIndex++;
			//////////////////////////////////////////
		}
	}
	
	// getUpperLimit: Get the maximum number in numSys-ary number system than can be represented with length places.
	private int getUpperLimit(int numSys, int length) {
		String num = "";
		for(int i = 0; i < length; i++) {
			num += numSys - 1;
		}
		return Integer.valueOf(num, numSys);
	}
	
	// ColorBacktracking: Color graph using the backtracking algorithm. Returns the number of colors it took to color the graph.
	public int colour_bt(int traceLimit) throws CloneNotSupportedException {
		
		// Set maximum number of lines to trace
		if(traceLimit == -1) {
			this.traceLimit = Integer.MAX_VALUE;
		} else {
			this.traceLimit = traceLimit;
		}
		
		// Start by trying to color graph with two colors.
		int k = 2;
		
		// While coloring with k colors is not successful, try again with k+1 colors.
		while(!isValidColouring()) {
			// TRACE functionality //////////////////
			this.traceBuffer[traceIndex] += String.format("k = %d", k);
			this.traceIndex++;
			
			/////////////////////////////////////////
			this.clear();
			colorGraphWithK(k, 0, this.cloneThis());
			// Clear all nodes.
			
			// increment maximum number of colors allowed.
			k++;
			
			
		}
		
		// Return number of colors it took to color the graph.
		return k;
	}
	
	// colorGraph: Try to color this graph with k colors (auxiliary function for backtracking algorithm). 
	private void colorGraphWithK(int k, int startNodeIndex, Graph graph) throws CloneNotSupportedException {
		
		Node currentNode = graph.nodes[startNodeIndex];
		
		for(int color = 0; color < k; color++) {
				// Color node with index startNodeIndex and 
				currentNode.setColour(color);
				// Check if coloring is valid.
				if(!graph.validColour(currentNode, color)) {
					// TRACE functionality /////////////////////////
					for(int i = 0; i <= startNodeIndex; i++) {
						this.traceBuffer[traceIndex] += String.format("%d ", graph.nodes[i].getColour());
					}
					this.traceBuffer[traceIndex] += String.format("NOK");
					this.traceIndex++;
					////////////////////////////////////////////////
				} else if(graph.validColour(currentNode, color)) {
					
					// TRACE functionality ///////////////
					for(int i = 0; i <= startNodeIndex; i++) {
						this.traceBuffer[traceIndex] += String.format("%d ", graph.nodes[i].getColour());
					}
					this.traceBuffer[traceIndex] += String.format("OK");
					this.traceIndex++;
					//////////////////////////////////////
					
					// If colored last node, return true as the graph has been successfully colored.
					if(startNodeIndex == graph.nodes.length - 1) {
						this.end = true;
						return;
					}
					color++;
					// else move to next Node.
					colorGraphWithK(k, ++startNodeIndex, graph.cloneThis());
					if(this.end) {
						return;
					}
				}
		}
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////
	
	// colour_dynamic: Use dynamic programming to colour this graph.
	public void colour_dynamic(int traceLimit) {

		// Set maximum number of lines to trace
		if(traceLimit == -1) {
			this.traceLimit = Integer.MAX_VALUE;
		} else {
			this.traceLimit = traceLimit;
		}
		
		// Generate all subsets of increasing size
		// Allocate extra array cell for storing the size of the subset
		int[][] subsets = new int[1 << this.nodes.length][this.nodes.length + 1];
		
		// Allocate memory for matrix of sorted subsets (sorted based on number of elements)
		int[][] subsetsSorted = new int[subsets.length][subsets[0].length];
		
		// store index of cell for storing the subset size.
		int setBitFieldIndex = this.nodes.length;
		// counts number of elements in subset.
		int setBitCounter;
		
		// Go over all bit combinations
		for(int i = 0; i < (1 << this.nodes.length); i++) {
			// Initialize indices and counters.
			int nodeIndex = 0;
			setBitCounter = 0;
			// Go over all elements and add elements not masked to subset.
			for(int j = 0; j < this.nodes.length; j++) {
				// If element is not masked
				if((i & (1 << j)) > 0) {
					// Add element to subset
					subsets[i][nodeIndex] = j;
					// Increment indices and counters.
					nodeIndex++;
					setBitCounter++;
				}
			}
			// Set size of subset.
			subsets[i][setBitFieldIndex] = setBitCounter;
			
			// Reset indices and counters.
			setBitCounter = 0;
			nodeIndex = 0;
		}
		
		// Go over subsets (increasing in size) (TESTED - WORKS).
		int subsetIndex = 1;
		for(int subsetSize = 1; subsetSize <= this.nodes.length; subsetSize++) {
			for(int i = 0; i < subsets.length; i++) {
				if(subsets[i][setBitFieldIndex] == subsetSize) {
					copySubset(subsets[i], subsetsSorted[subsetIndex]);
					subsetIndex++;
				}
			}
		}
		
		// Initialize array for keeping minimum numbers of colors needed to color each set.
		int[] minColors = new int[1 << this.nodes.length];
		// No colors needed to color empty set
		minColors[0] = 0;
		
		// TRACE FUNCTIONALITY ///////////////////
		this.traceBuffer[traceIndex] += String.format("{} : 0");
		this.traceIndex++;
		/////////////////////////////////////////
		
		// Go over sorted subsets.
		for(int i = 1; i < subsetsSorted.length; i++) {
			
			// Get minimum number of colors needed to color next subset.
			minColors[i] = 1 + getMinDyn(subsetsSorted, subsetsSorted[i], i, minColors);
			
			// TRACE FUNCTIONALITY //////////////////////////////////////////////////
			this.traceBuffer[traceIndex] += String.format("{");
			int subsetLength = subsetsSorted[i][subsetsSorted[i].length - 1];
			for(int j = 0; j < subsetLength; j++) {
				this.traceBuffer[traceIndex] += String.format((j == subsetLength - 1) ? "%d}" : "%d,", subsetsSorted[i][j]);
			}
			this.traceBuffer[traceIndex] += String.format(" : %d", minColors[i]);
			this.traceIndex++;
			/////////////////////////////////////////////////////////////////////////
		}
		
	}
	
	// copySubset: Copy subset src into subset dest
	private void copySubset(int[] src, int[] dest) {
		for(int i = 0; i < src.length; i++) {
			dest[i] = src[i];
		}
	}
	
	// getMinDyn: Compute the minimum number of colors needed to color a set of form S\I where I is a subset of S
	// and elements of I are independent.
	private int getMinDyn(int[][] subsets, int[] currentSet, int currentSetIndex, int[] minColors) {
		
		// Initialize minimum value;
		int min = Integer.MAX_VALUE;
		
		// Go over all I's
		for(int i = 1; i <= currentSetIndex; i++) {
			int[] nextSet = subsets[i];
			// If subset satisfies conditions for I...
			if(isSubset(nextSet, currentSet) && isIndependent(nextSet)) {
				
				// Compute next T[S\I].
				int nextVal = SminusIval(subsets, currentSet, nextSet, currentSetIndex, minColors);
				
				// If found new minimum
				if(nextVal < min) {
					min = nextVal;
				}
			}
		}
		
		return min;
	}
	
	// SminusIval: Get min number of colors needed to color set S\i
	private int SminusIval(int[][] subsets, int[] S, int[] I, int currentSetIndex, int[] minColors) {
		
		// Compute set S\I //////////////////////
		// index of element in diff
		int elIndex = 0;
		int[] diff = new int[S.length];
		
		// Go over all elements in S.
		for(int i = 0; i < S[S.length - 1]; i++) {
			// Assume next element in S is element in diff.
			boolean isEl = true;
			// Go over all elements in I
			for(int j = 0; j < I[I.length - 1]; j++) {
				// If found next element in I, then this element is not in the difference
				if(I[j] == S[i]) {
					isEl = false;
					break;
				}
			}
			// If element was not found in I add to diff.
			if(isEl) {
				diff[elIndex] = S[i];
				elIndex++;
			}
		}
		
		// Set number of elements in diff.
		diff[diff.length - 1] = elIndex;
		
		//////////////////////////////////////
		
		// Find index of set S\I in the subsets matrix.
		// Go over all subsets up to S.
		for(int i = 0; i <= currentSetIndex; i++) {
			// if diff == subsets[i] (has same elements)
			if(elementsMatch(diff, subsets[i])) {
				return minColors[i];
			}
		}
		return -1;
	}
	
	// elementsMatch: Check if set1 and set2 are the same (have same elements)
	private boolean elementsMatch(int[] set1, int[] set2) {
		
		// If sets are not same length, return false.
		if(set1[set1.length - 1] != set2[set2.length - 1]) {
			return false;
		}
		
		// Go over all elements in set1 and try to find them in set2
		for(int i = 0; i < set1[set1.length - 1]; i++) {
			// If element in set1 is not in set 2
			if(!hasElement(set2, set1[i])) {
				return false;
			}
		}
		
		return true;
	}
	
	// isSubset: Check if subsetCandidate is a subset of set
	private boolean isSubset(int[] subsetCandidate, int[] set) {
		
		// if subsetCandidate is an ampty set, it is a subset
		if(subsetCandidate[subsetCandidate.length - 1] == 0) {
			return true;
		}
		
		boolean containsEl;
		// Check if all elements of subsetCandidate are contained in set.
		for(int i = 0; i < subsetCandidate[subsetCandidate.length - 1]; i++) {
			containsEl = hasElement(set, subsetCandidate[i]);
			if(!containsEl) {
				return false;
			}
		}
		
		return true;
	}
	
	// hasElement: check if set contains element el.
	private boolean hasElement(int[] set, int el) {
		// Linear search
		for(int i = 0; i < set[set.length - 1]; i++) {
			if(set[i] == el) {
				return true;
			}
		}
		return false;
	}
	
	// isIndependet: Check if set represents an independent set (no direct connections between nodes)
	private boolean isIndependent(int[] set) {
		// Go over every element in set (each element represents a node index)
		for(int i = 0; i < set[set.length - 1]; i++) {
			int nodeIndex = set[i];
			// Go over all neighbors of next node.
			for(Integer neighborID : this.nodes[nodeIndex].getNeighborIds()) {
				// If neighborId is in this set, the set is not independent - return false.
				if(hasElement(set, neighborID)) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	
	////////////////////////////////////////////////////////////////
	
	
	
	// is validColouring: check if the current coloring of this graph is valid.
	private boolean isValidColouring() {
		// Go over all nodes.
		for(Node n : this.nodes) {
			// Go over all neighbors of current node.
			for(Integer neighborId : n.getNeighborIds()) {
				// If any neighbor has the same colour, the configuration is not valid.
				if(n.getColour() == this.nodes[neighborId].getColour()) {
					return false;
				}
			}
		}
		return true;
	}
	
	// validColour: Check if any neighbors of node have the same color in this graph.
	private boolean validColour(Node node, int color) {
		// Go over all neighbors of node
		for(Integer neighborId : node.getNeighborIds()) {
			// if neighbor has same color, return false (the coloring is not valid).
			if(this.nodes[neighborId].getColour() == color) {
				return false;
			}
		}
		return true;
	}
	
	// charToColour: Convert number represented by character c to its decimal numeric value.
	// Supports standard numeric values 0-9 and alphabetical extensions (a == 10, b == 11, and so on.)
	private int charToColour(char c) {
		if(Character.isDigit(c)) {
			return Character.getNumericValue(c);
		}
		return (int)c - 87;
	}
	
	// printTraceBuffer: Print last traceLimit contents of the traceBuffer.
	public void printTraceBuffer() {
		int startIndex = this.traceIndex - this.traceLimit;
		if(startIndex < 0 || this.traceLimit == 0) {
			startIndex = 0;
		}
		for(int i = startIndex; i <= this.traceIndex; i++) {
			System.out.println(this.traceBuffer[i]);
		}
	}
	
	public Graph cloneThis() throws CloneNotSupportedException {
		Graph newGraph = (Graph) this.clone();
		newGraph.nodes = this.nodes.clone();
		return newGraph;
	}
}