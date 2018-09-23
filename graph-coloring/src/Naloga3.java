import java.util.Scanner;

public class Naloga3 {

	public static void main(String[] args) {
		
		// Define variables for storing arguments.
		// traceLimit == -1 -> no trace limit
		String algorithmSpec = "";
		int traceLimit = -1;
		
		// Define supported algorithm specifiers
		String[] supportedAlgs = {"2c", "gr", "ex", "bt", "dp"};
		
		// Try to parse arguments and handle errors appropriately.
		try {
			algorithmSpec = args[0];
			if(args.length > 1) {
				try {
					try {
						if(!args[1].equals("-n")) {
							throw new IllegalArgumentException(args[1]);
						}
					} catch (IllegalArgumentException e) {
						System.err.printf("Unknown option %s\n", e.getMessage());
						e.printStackTrace();
						System.exit(1);
					}
					traceLimit = Integer.parseInt(args[2]);
				} catch(IndexOutOfBoundsException e) {
					System.err.println("Missing argument for -n option");
					e.printStackTrace();
					System.exit(1);
				}
			}
		} catch (IndexOutOfBoundsException e) {
			System.err.println("Missing algorithm specifier");
			e.printStackTrace();
			System.exit(1);
		}

		// Check if specified algorithm is valid and handle errors appropriately
		try {
			boolean validAlgorithm = false;
			for(String s : supportedAlgs) {
				if(algorithmSpec.equals(s)) {
					validAlgorithm = true;
				}
			}
			
			if(!validAlgorithm) {
				throw new IllegalArgumentException(algorithmSpec);
			}
			
		} catch (IllegalArgumentException e) {
			System.err.printf("Unrecognize algorithm specifier %s\n", e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
		
		// Construct graph
		Scanner sc = new Scanner(System.in);
		Graph graph = null;
		
		try {
			int numNodes = sc.nextInt();
			int numEdges = sc.nextInt();
			graph = new Graph(numNodes);
			
			// Parse and initialize edges
			for(int i = 0; i < numEdges; i++) {
				graph.addConnection(sc.nextInt(), sc.nextInt());
			}
			
		} catch (IndexOutOfBoundsException e) {
			System.err.println("Invalid edge descriptions");
			e.printStackTrace();
		}
		
		
		// Perform specified algorithm
		try {
			switch(algorithmSpec) {
			case "2c":
				graph.colour_2c(traceLimit);
				graph.printTraceBuffer();
				break;
			case "gr":
				graph.colour_gr(traceLimit);
				graph.printTraceBuffer();
				break;
			case "ex":
				graph.colour_ex(traceLimit);
				graph.printTraceBuffer();
				break;
			case "bt":
				try {
				graph.colour_bt(traceLimit);
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
				graph.printTraceBuffer();
				break;
			case "dp":
				graph.colour_dynamic(traceLimit);
				graph.printTraceBuffer();
				break;
			}
		} catch (NullPointerException e) {
			System.err.println("The graph has not been properly initialized.");
			sc.close();
			System.exit(1);
		} finally {
			sc.close();
		}
	}
}