import java.util.Scanner;
import java.util.ArrayList;

public class Naloga1 {
	// This program should accept 3 arguments and an optional 4. argument
	// The first argument specifies whether to print the number of swaps performed or the trace of the sort
	// the second argument specifies which sorting algorithm to use (see table of abbreviations)
	// the third argument specifies whether to sort ascending or descending (up, down)
	// the optional fourth argument specifies the length of the array to be sorted
	
	// the array is to be read from the standard input using .nextInt() method of a Scanner instance
	// if no array length is given the array should be allocated dynamically.
	public static void main(String[] args) {
		// parse arguments /////////
		int arrLength = -1;
		String outputAction = args[0];
		boolean trace = false;
		if(outputAction.equals("trace")) {
			trace = true;
		}
		String algorithm = args[1];
		String sortDirection = args[2];
		boolean up = false;
		if(sortDirection.equals("up")) {
			up = true;
		}
		if(args.length == 4) {
			arrLength = Integer.parseInt(args[3]);
		}
		////////////////////////////
		
		// parse the array /////////
		Scanner sc = new Scanner(System.in);
		int[] arr;
		// if length is known in advance
		if(arrLength != -1) {
			arr = new int[arrLength];
			for(int i = 0; i < arr.length; i++) {
				arr[i] = sc.nextInt();
			}
		// else, read into ArrayList and then convert to array of primitive ints
		} else {
			ArrayList<Integer> temp = new ArrayList<Integer>();
			while(sc.hasNextInt()) {
				temp.add(sc.nextInt());
			}
			arr = temp.stream().mapToInt(i -> i).toArray();
		}
		
		// select algorithm //////////
		switch(algorithm) {
			// bubblesort
			case "bs":
				if(!trace) {
					SortingAlgorithms.compCounter = SortingAlgorithms.asnCounter = 0;
					SortingAlgorithms.bubblesort(arr, up, trace);
					System.out.printf("%d %d\n", SortingAlgorithms.compCounter, SortingAlgorithms.asnCounter);
					SortingAlgorithms.compCounter = SortingAlgorithms.asnCounter = 0;
					SortingAlgorithms.bubblesort(arr, up, trace);
					System.out.printf("%d %d\n", SortingAlgorithms.compCounter, SortingAlgorithms.asnCounter);
					SortingAlgorithms.compCounter = SortingAlgorithms.asnCounter = 0;
					SortingAlgorithms.bubblesort(arr, !up, trace);
					System.out.printf("%d %d\n", SortingAlgorithms.compCounter, SortingAlgorithms.asnCounter);
					SortingAlgorithms.compCounter = SortingAlgorithms.asnCounter = 0;
				} else {
					SortingAlgorithms.bubblesort(arr, up, trace);
				}
				break;
			// selectionsort
			case "ss":
				if(!trace) {
					SortingAlgorithms.compCounter = SortingAlgorithms.asnCounter = 0;
					SortingAlgorithms.selectionsort(arr, up, trace);
					System.out.printf("%d %d\n", SortingAlgorithms.compCounter, SortingAlgorithms.asnCounter);
					SortingAlgorithms.compCounter = SortingAlgorithms.asnCounter = 0;
					SortingAlgorithms.selectionsort(arr, up, trace);
					System.out.printf("%d %d\n", SortingAlgorithms.compCounter, SortingAlgorithms.asnCounter);
					SortingAlgorithms.compCounter = SortingAlgorithms.asnCounter = 0;
					SortingAlgorithms.selectionsort(arr, !up, trace);
					System.out.printf("%d %d\n", SortingAlgorithms.compCounter, SortingAlgorithms.asnCounter);
					SortingAlgorithms.compCounter = SortingAlgorithms.asnCounter = 0;
				} else {
					SortingAlgorithms.selectionsort(arr, up, trace);
				}
				break;
			// insertion sort
			case "is":
				if(!trace) {
					SortingAlgorithms.compCounter = SortingAlgorithms.asnCounter = 0;
					SortingAlgorithms.insertionsort(arr, up, trace);
					System.out.printf("%d %d\n", SortingAlgorithms.compCounter, SortingAlgorithms.asnCounter);
					SortingAlgorithms.compCounter = SortingAlgorithms.asnCounter = 0;
					SortingAlgorithms.insertionsort(arr, up, trace);
					System.out.printf("%d %d\n", SortingAlgorithms.compCounter, SortingAlgorithms.asnCounter);
					SortingAlgorithms.compCounter = SortingAlgorithms.asnCounter = 0;
					SortingAlgorithms.insertionsort(arr, !up, trace);
					System.out.printf("%d %d\n", SortingAlgorithms.compCounter, SortingAlgorithms.asnCounter);
					SortingAlgorithms.compCounter = SortingAlgorithms.asnCounter = 0;
				} else {
					SortingAlgorithms.insertionsort(arr, up, trace);
				}
				break;
			// heapsort
			case "hs":
				if(!trace) {
					SortingAlgorithms.compCounter = SortingAlgorithms.asnCounter = 0;
					SortingAlgorithms.heapsort(arr, up, trace);
					System.out.printf("%d %d\n", SortingAlgorithms.compCounter, SortingAlgorithms.asnCounter - 3);
					SortingAlgorithms.compCounter = SortingAlgorithms.asnCounter = 0;
					SortingAlgorithms.heapsort(arr, up, trace);
					System.out.printf("%d %d\n", SortingAlgorithms.compCounter, SortingAlgorithms.asnCounter - 3);
					SortingAlgorithms.compCounter = SortingAlgorithms.asnCounter = 0;
					SortingAlgorithms.heapsort(arr, !up, trace);
					System.out.printf("%d %d\n", SortingAlgorithms.compCounter, SortingAlgorithms.asnCounter - 3);
					SortingAlgorithms.compCounter = SortingAlgorithms.asnCounter = 0;
				} else {
					SortingAlgorithms.heapsort(arr, up, trace);
				}
				break;
			// quicksort
			case "qs":
				if(!trace) {
					SortingAlgorithms.compCounter = SortingAlgorithms.asnCounter = 0;
					SortingAlgorithms.quicksort(arr, 0, arr.length - 1,  up, trace);
					System.out.printf("%d %d\n", SortingAlgorithms.compCounter, SortingAlgorithms.asnCounter);
					SortingAlgorithms.compCounter = SortingAlgorithms.asnCounter = 0;
					SortingAlgorithms.quicksort(arr, 0, arr.length - 1, up, trace);
					System.out.printf("%d %d\n", SortingAlgorithms.compCounter, SortingAlgorithms.asnCounter);
					SortingAlgorithms.compCounter = SortingAlgorithms.asnCounter = 0;
					SortingAlgorithms.quicksort(arr, 0, arr.length - 1, !up, trace);
					System.out.printf("%d %d\n", SortingAlgorithms.compCounter, SortingAlgorithms.asnCounter);
					SortingAlgorithms.compCounter = SortingAlgorithms.asnCounter = 0;
				} else {
					SortingAlgorithms.quicksort(arr, 0, arr.length - 1, up, trace);
				}
				break;
			// mergesort
			case "ms":
				if(!trace) {
					SortingAlgorithms.compCounter = SortingAlgorithms.asnCounter = 0;
					SortingAlgorithms.mergesort(arr, 0, arr.length - 1, up, trace);
					System.out.printf("%d %d\n", SortingAlgorithms.compCounter, SortingAlgorithms.asnCounter + 1);
					SortingAlgorithms.compCounter = SortingAlgorithms.asnCounter = 0;
					SortingAlgorithms.mergesort(arr, 0, arr.length - 1, up, trace);
					System.out.printf("%d %d\n", SortingAlgorithms.compCounter, SortingAlgorithms.asnCounter + 1);
					SortingAlgorithms.compCounter = SortingAlgorithms.asnCounter = 0;
					SortingAlgorithms.mergesort(arr, 0, arr.length - 1, !up, trace);
					System.out.printf("%d %d\n", SortingAlgorithms.compCounter, SortingAlgorithms.asnCounter + 1);
					SortingAlgorithms.compCounter = SortingAlgorithms.asnCounter = 0;
				} else {
					SortingAlgorithms.mergesort(arr, 0, arr.length - 1, up, trace);
				}
				break;
			// countingsort
			case "cs":
				arr = SortingAlgorithms.countingsort(arr, up, trace);
				printArray(arr);
				break;
			// radixsort
			case "rs":
				arr = SortingAlgorithms.radixsort(arr, up, trace);
				break;
		}
		//////////////////////////////
		
	}

	// printArray: prints array of integers arr
	public static void printArray(int[] arr) {
		for(int i = 0; i < arr.length; i++) {
			System.out.printf((i == arr.length - 1) ? "%d%n" : "%d ", arr[i]);
		}
	}
}