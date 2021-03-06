class SortingAlgorithms {
	// static variable for keeping track of number of swaps performed
	public static long compCounter, asnCounter;
	
	// quicksort: perform the quicksort algorithm on array of integers arr on index interval [leftBound, rightBound]
	public static void quicksort(int[] arr, int leftBound, int rightBound, boolean up, boolean trace) {
		if(leftBound >= rightBound) {
			return;
		}
		// choose middle element as the pivot value
		asnCounter++;
		int pivot = arr[(leftBound + rightBound)/2];
		// Set left and right pointers
		int l = leftBound;
		int r = rightBound;
		// while pointers have not met
		while(l <= r) {
			// while value pointed to by l is smaller/larger than pivot, move pointer left
			compCounter++;
			while((up) ? arr[l] < pivot : arr[l] > pivot) {
				compCounter++;
				l++;
			}
			// while vale pointed to by r is larger/smaller than pivot, move pointer right
			compCounter++;
			while((up) ? arr[r] > pivot : arr[r] < pivot) {
				compCounter++;
				r--;
			}
			// if l and r have not crossed
			if(l <= r) {
				// swap elements pointed to by l and r
				asnCounter += 3;
				swap(arr, l, r);
				// move l one element to the right and r one element to the left
				l++;
				r--;
			}
		}
		 // System.out.printf("l == %d, r == %d, pivot == %d\n", l - leftBound, r - leftBound, pivot);
		if(trace){quicksortTrace(arr, leftBound, rightBound, l, r);}
		// recursive call for section of array with values smaller than pivot
		quicksort(arr, leftBound, r, up, trace);
		// recursive call for section of array with values larger or equal to pivot
		quicksort(arr, l, rightBound, up, trace);
	}
	// heapsort: performs the heapsort sorting algorithm on the array arr
	public static void heapsort(int[] arr, boolean up, boolean trace) {
		// start with first node with children and recursively build array representation of heap
		for(int i = arr.length/2 - 1; i >= 0; i--) {
			heapify(arr, i, arr.length, up);
		}
		// move root of heap to end of array and fix heap
		for(int i = arr.length - 1; i >= 0; i--) {
			if(trace){heapsortTrace(arr, i);}
			asnCounter += 3;
			swap(arr, 0, i);
			heapify(arr, 0, i, up);
		}
	}

	// heapify: auxilliary method for recursively building the array representation of a heap
	private static void heapify(int[] arr, int rootIndex, int rightBound, boolean up) {
		// initialize pointers to root (assumed critical), left child of root and right child of roots
		int critical = rootIndex;
		int leftChild = 2*rootIndex + 1;
		int rightChild = 2*rootIndex + 2;

		// check if any child is more critical than root let critical point to critical child
		if(leftChild < rightBound && ++compCounter >= 0 && ((up) ? arr[leftChild] > arr[critical] : arr[leftChild] < arr[critical])) {
			critical = leftChild;
		}
		if(rightChild < rightBound && ++compCounter >= 0 && ((up) ? arr[rightChild] > arr[critical] : arr[rightChild] < arr[critical])) {
			critical = rightChild;
		}
		// if root is not critical, swap and recursively fix heap with root at critical child
		if(critical != rootIndex) {
			asnCounter += 3;
			swap(arr, critical, rootIndex);
			heapify(arr, critical, rightBound, up);
		}
	}

	// insertionsort: performs the insertionsort algorithm on the array pointed to by arr
	public static void insertionsort(int[] arr, boolean up, boolean trace) {
		// assume first element is sorted
		// iterate over array on [1, arr.length - 1]
		if(trace){insertionsortTrace(arr, -1);}
		for(int i = 1; i < arr.length; i++) {
			// initialize new variable used for insertion
			int j = i;
			// while current element is larger than its predecessor
			while(j > 0 && ++compCounter >= 0 && ((up) ? arr[j] < arr[j - 1] : arr[j] > arr[j - 1])) {
				// swap the two elements and repeat for element originaly pointed to by i
				asnCounter += 3;
				swap(arr, j, j - 1);
				j--;
			}
			if(trace){insertionsortTrace(arr, i);}
		}
	}

	// bubblesort: performs the basic bubblesort algorithm on the array pointed to by arr
	public static void bubblesort(int[] arr, boolean up, boolean trace) {
		int edge = 1;
		for(int i = 0; i < arr.length; i++) {
			if(trace){bubblesortTrace(arr, edge);}
			for(int j = arr.length - 1; j >= edge; j--) {
				compCounter++;
				if((up) ? arr[j] < arr[j - 1] : arr[j] > arr[j - 1]) {
					asnCounter += 3;
					swap(arr, j, j - 1);
				}
			}
			edge++;
		}
	}

	// selectionsort: performs the selectionsort algorithm on the array pointed to by arr
	public static void selectionsort(int[] arr, boolean up, boolean trace) {
	    if(trace) {selectionsortTrace(arr, -1);}
		// outer loop is at boundary of sorted and unsorted part
		for(int i = 0; i < arr.length - 1; i++) {
			// save assumed critical value and its index
			int critical = arr[i];
			int indexCritical = i;

			// go over unsorted part and check if any values are smaller than assumed critical
			for(int j = i + 1; j < arr.length; j++) {
				compCounter++;
				if((up) ? arr[j] < critical : arr[j] > critical) {
					critical = arr[j];
					indexCritical = j;
				}
			}
			// if smaller value found, put in sorted section
			asnCounter +=3;
			swap(arr, i, indexCritical);
			if(trace){selectionsortTrace(arr, i);}
		}
	}
	
	// count_sort: performs the countsort algorithm on an array of single digit integers pointed to by arr
	public static int[] countingsort(int[] arr, boolean up, boolean trace) {

		// initialize array that will hold the sorted array
	   	int[] sortedArr = new int[arr.length];

	    // create array with indices corresponding to numbers on [0,9]
	    int[] count = new int[256];

	    // count each number in the array that is being sorted
	    for(int i = 0; i < arr.length; i++) {
	        count[arr[i]]++;
	    }

	    // compute the cumulative sums
	    for(int i = 1; i < count.length; i++) {
	        count[i] += count[i - 1];
	    }
	    if(trace){countingsortTrace(count);}

	    // build sorted array
	    // go over initial array in reverse
	    for(int i = arr.length - 1; i >= 0; i--) {
	        // get last value in the array that is being sorted
	        int value = arr[i];
	        // the cumulative sum represents index + 1 of the current value
	        int place = count[value];
	        // put value in sorted array
	        sortedArr[(up) ? (place - 1) : sortedArr.length - place] = value;
	        // subtract from cumulative count
	        count[value]--;
	    }
	    
	   	// return the array that was sorted
	   	return sortedArr;
	}

	// countsortAux: performs the countsort algorithm on array of integers pointed to by arr
	// the array sorts the digit at decimalplace decPlace (which increment as 1, 10, 100,... and NOT 1, 2, 3,...)
	public static int[] countsortAux(int[] arr, int[] mask, boolean up, boolean trace) {

	    // allocate memory to the array that will hold the sorted array
	    int[] sortedArr = new int[arr.length];

	    // create array with indices corresponding to numbers on [0,9]
	    // set all elements to 0
	    int[] count = new int[256];

	    // count each digit at the specified decimal place in the array that is being sorted
	    for(int i = 0; i < arr.length; i++) {
	        count[(arr[i] & mask[0]) >> mask[1]]++;
	    }

	    // compute the cumulative sums
	    for(int i = 1; i < count.length; i++) {
	        count[i] += count[i - 1];
	    }
	    if(trace){countingsortTrace(count);}
	    // build sorted array
	    // go over initial array in reverse
	    for(int i = arr.length - 1; i >= 0; i--) {
	        // get last value in the array that is being sorted
	        int value = (arr[i] & mask[0]) >> mask[1];
	        // the cumulative sum represents index + 1 of the current value
	        int place = count[value];
	        // put value in sorted array
	        sortedArr[(up) ? (place - 1) : (arr.length - place)] = arr[i];
	        // subtract from cumulative count
	        count[value]--;
	    }    
	    return sortedArr;
	}

	// TODO: add sorting by bytes
	// radixsort - performs the radixsort sorting algorithm on array pointed to by arr
	public static int[] radixsort(int[] arr, boolean up, boolean trace) {
	    // Find the maximum number to know number of digits
	    int max = getMax(arr);
	    int[][] masks = {{0x000000FF, 0}, {0x0000FF00, 8}, {0x00FF0000, 16}, {0xFF000000, 24}};
	    // Perform countsort on incrementing digit positions
	    for(int maskIndex = 0; maskIndex < masks.length; maskIndex++) {
	        arr = countsortAux(arr, masks[maskIndex], up, trace);
	        if(true){radixsortTrace(arr);}
	    }
	    return arr;
	}

	// getMax: returns the maximum element in the array pointed to by arr
	public static int getMax(int[] arr) {
	    int max = arr[0];
	    for (int i = 1; i < arr.length; i++) {
	        if (arr[i] > max){
	            max = arr[i];
	        }
	    }
	    return max;
	}

	// merge: merges two subarrays of arr
	// the first subarray is arr[left..m]
	// the second subarray is arr[m + 1..right]
	public static void merge(int[] arr, int left, int m, int right, boolean up, boolean trace) {
	    // define indices
	    int i, j, k;
	    // subarray_l_length := size of the first subarray
	    int subarray_l_length = m - left + 1;
	    // subarray_r_length := size of the second subarray
	    int subarray_r_length =  right - m;

		int[] merged = new int[right - left + 1];
		
	    // i is the starting index of the first subarray
	    i = left;
	    // j is the starting index of the second subarray
	    j = m + 1;
	    // k is the starting index of the merged subarray
	    k = 0;

	    // while neither of the subarray indices has reached the respective subarray length
	    while (i <= m && j <= right) {
	        // compare values in the temp subarrays and merge into the main array
	        compCounter++;
	        if((up) ? arr[i] <= arr[j] : arr[i] >= arr[j]) {
	        	asnCounter++;
				merged[k] = arr[i];
	            i++;
	        } else {
	        	asnCounter++;
				merged[k] = arr[j];
	            j++;
	        }
	        // increment the index of the main array that is being merged into
	        k++;
	    }
	 
	    // copy the remainder of elements from L to the main array (if it exists)
	    while (i <= m) {
	    	asnCounter++;
			merged[k] = arr[i];
	        i++;
	        k++;
	    }
	 
	    // copy the remainder of elements from L to the main array (if it exists)
	    while (j <= right) {
	    	asnCounter++;
	        merged[k] = arr[j];
	        j++;
	        k++;
	    }
	
		if(trace){mergesortTrace_merge(merged);}
		int s = 0;
		for(int l = left; l <= right; l++) {
			arr[l] = merged[s];
			s++;
		}
	}

	// mergesort: sorts the values in the array pointed to by arr using the mergesort algorithm
	public static void mergesort(int[] arr, int left, int right, boolean up, boolean trace) {
	    if(left < right) {
	        // compute the middle index
	    	asnCounter++;	
	        int m = (right + left)/2;
			
			if(trace){mergesortTrace_split(arr, left, right, m);}
			
	        // recursive call for the left and right subarrays
	        mergesort(arr, left, m, up, trace);
	        mergesort(arr, m + 1, right, up, trace);
			
	        // merge the two subarrays
			merge(arr, left, m, right, up, trace);
	        
	    }
	}

	// swap: swaps elements at index1 and index2 in array arr
	private static void swap(int[] arr, int index1, int index2) {
		int temp = arr[index1];
		arr[index1] = arr[index2];
		arr[index2] = temp;
	}

	public static void heapsortTrace(int[] arr, int printLen) {
		int barIndex = 1;
		int printCounter = 0;
		for(int i = 0; i <= printLen; i++) {
			System.out.printf((i == printLen) ? "%d\n" : "%d ", arr[i]);
			printCounter++;
			if((i != printLen) && printCounter == barIndex) {
				System.out.printf("| ");
				barIndex *= 2;
				printCounter = 0;
			}
		}
	}

	// selectionsortTrace: auxilliary method for printing the trace of the selectionsort algorithm
	public static void selectionsortTrace(int[] arr, int loopIndex) {
	    // if loopIndex contains signal for initial print of unsorted array (-1)
	    // print initial bar (array state before performing selectionsort)
	    if(loopIndex == -1) {
	        System.out.printf("| ");
	        for(int i = 0; i < arr.length; i++) {
	            System.out.printf((i == arr.length - 1) ? "%d\n" : "%d ", arr[i]);
	        }
	    // else print array and insert vertical bar at the correct place (can be expressed in terms of the loop index)
	    } else {
	        for(int i = 0; i < arr.length; i++) {
	            System.out.printf((i == arr.length - 1) ? "%d" : "%d ", arr[i]);
	            if(i == loopIndex) {
	                System.out.printf((i == arr.length - 1) ? " |" : "| ");
	            }
	            // print newline after printign the array
	            if(i == arr.length - 1) {
	                System.out.println();
	            }
	        }
	    } 
	}

	// insertionsortTrace: auxilliary method for printing the trace of the selectionsort algorithm
	public static void insertionsortTrace(int[] arr, int loopIndex) {
	    // if loopIndex contains signal for initial print of unsorted array (-1)
	    // print initial bar (array state before performing selectionsort)
	    if(loopIndex == -1) {
	    	System.out.printf("%d |", arr[0]);
	    	if(arr.length == 1) {System.out.println();}
	        for(int i = 1; i < arr.length; i++) {

	            System.out.printf((i == arr.length - 1) ? " %d\n" : " %d", arr[i]);
	        }
	    // else print array and insert vertical bar at the correct place (can be expressed in terms of the loop index)
	    } else {
	        for(int i = 0; i < arr.length; i++) {
	            System.out.printf((i == arr.length - 1) ? "%d" : "%d ", arr[i]);
	            if(i == loopIndex) {
	                System.out.printf((i == arr.length - 1) ? " |" : "| ");
	            }
	            // print newline after printign the array
	            if(i == arr.length - 1) {
	                System.out.println();
	            }
	        }
	    } 
	}

	public static void countingsortTrace(int[] count) {
		for(int i = 0; i < count.length; i++) {
			System.out.printf((i == count.length - 1) ? "%d\n" : "%d ", count[i]);
		}
	}

	public static void radixsortTrace(int[] arr) {
		for(int i = 0; i < arr.length; i++) {
			System.out.printf((i == arr.length - 1) ? "%d\n" : "%d ", arr[i]);
		}	
	}

	public static void quicksortTrace(int[] arr, int leftBound, int rightBound, int l, int r) {
		for(int i = leftBound; i <= rightBound; i++) {
			if(i == l) {
				System.out.printf("| ");
			}
			if(i == r + 1) {
				System.out.printf("| ");
			}
			System.out.printf((i == rightBound) ? "%d\n" : "%d ", arr[i]);
		}
	}


	public static void bubblesortTrace(int[] arr, int edge) {
		for(int i = 0; i < arr.length; i++) {
			if(edge - 1 == i) {
				System.out.printf("| ");
			}
			System.out.printf((i == arr.length - 1) ? "%d\n" : "%d ", arr[i]);
		}
	}
	
	public static void mergesortTrace_merge(int[] arr) {
		for(int i = 0; i < arr.length; i++) {
			System.out.printf((i == arr.length - 1) ? "%d\n" : "%d ", arr[i]);
		}
	}
	
	public static void mergesortTrace_split(int[] arr, int left, int right, int m) {
		for(int i = left; i <= right; i++) {
			System.out.printf((i == right) ? "%d\n" : "%d ", arr[i]);
			if(i == m) {
				System.out.printf("| ");
			}
		}
	}
}
