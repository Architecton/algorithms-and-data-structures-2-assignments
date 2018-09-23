import java.util.Scanner;

// Vnumber: represents an integer of an arbitrary size
class Vnumber implements OsFunctionality{
	// attributes ///////////////////
	public byte[] digitArray;
	private boolean isZero;
	/////////////////////////////////
	
	
	// 1. constructor - the number is parsed with a passed Scanner instance
	public Vnumber(Scanner sc) {
		String num = sc.nextLine();
		this.isZero = true;
		char[] temp = num.toCharArray();
		this.digitArray = new byte[temp.length];
		for(int i = 0; i < temp.length; i++) {
			this.digitArray[i] = Byte.parseByte(Character.toString(temp[i]));
			if(this.digitArray[i] > 0) {
				this.isZero = false;
			}
		}
		this.digitArray = this.clearPadding().digitArray;
	}
	
	// 2. constructor - make a front padded version of a passed Vnumber instance
	public Vnumber(Vnumber num, int newLength) {
		byte[] paddedDigitArr = new byte[newLength];
		for(int i = paddedDigitArr.length - num.digitArray.length; i < paddedDigitArr.length; i++) {
			paddedDigitArr[i] = num.digitArray[i - (paddedDigitArr.length - num.digitArray.length)];
		}
		this.digitArray = paddedDigitArr;
		this.isZero = num.isZero;
	}
	
	// 3. constructor: intialize Vnumber with a Java int value
	public Vnumber(int initVal) {
		if(initVal == 0) {
			this.isZero = true;
			this.digitArray = new byte[1];
			this.digitArray[0] = 0;
			return;
		} else {
			this.isZero = false;
		}
		int len = 0;
		int lenTest = initVal;
		// get digit array length
		while(lenTest != 0) {
			lenTest /= 10;
			len++;
		}
		// initialize and fill digit array
		this.digitArray = new byte[len];
		int indexArr = this.digitArray.length - 1;
		while(initVal != 0) {
			this.digitArray[indexArr] = (byte)(initVal % 10);
			initVal /= 10;
			indexArr--;
		}
		this.digitArray = this.clearPadding().digitArray;
	}
	
	// 4. constructor: intialize Vnumber with a given Byte array
	public Vnumber(byte[] initArray) {
		this.digitArray = initArray;
		this.isZero = true;
		for(int i = 0; i < initArray.length; i++) {
			if(initArray[i] > 0) {
				this.isZero = false;
			}
		}
	}
	
	// 5. constructor: initialize Vnumber instance from String representation of a number
	public Vnumber(String num) {
		char[] numTemp = num.toCharArray();
		this.digitArray = new byte[numTemp.length];
		this.isZero = true;
		for(int i = 0; i < numTemp.length; i++) {
			this.digitArray[i] = (byte)Character.getNumericValue(numTemp[i]);
			if(this.digitArray[i] > 0) {
				this.isZero = false;
			}
		}
		this.digitArray = this.clearPadding().digitArray;
	}
	
	// isZero: Check if this number is equal to zero or not.
	public boolean isZero() {
		return this.isZero;
	}
	
	// schoolMultiply: Multiply this number with the number num using the "Grade School" algorithm
	public Vnumber schoolMultiply(Vnumber num, boolean trace) {
		// Extract and save number arrays.
		byte[] num1 = this.digitArray;
		byte[] num2 = num.digitArray;
		
		String tempResString = "";
		// Evaluate expression num1*sum2
		// Go over digits in num2 and multiply by digits in num1
		for(int i = 0; i < num2.length; i++) {
			// Go over number 2
			byte digit2 = num2[i];
			// initial carry is 0
			byte carry = 0;
			for(int j = num1.length - 1; j >= 0; j--) {
				byte digit1 = num1[j];
				// Product is at most 81 (+ possible carry) and can be stored in a Byte type.
				byte resNum = (byte)(digit2*digit1 + carry);
				byte resDigit = (byte)(resNum % 10);
				carry = (byte)(resNum / 10);
				tempResString = resDigit + tempResString;
			}
			// if exists final carry
			if(carry > 0) {
				tempResString = carry + tempResString;
			}
			if(i < num2.length - 1) {
				tempResString = ' ' + tempResString;
			}
		}
		// Sum partial result into final number.
		Vnumber result = addSubresults_aux(tempResString, trace);
		
		return result;
	}
	
	// Vnumber addSubresults_aux: Add partial results into grand total result
	private Vnumber addSubresults_aux(String tempResString, boolean trace) {
		String res = "";
		String[] tempResults = tempResString.split(" ");
		for(int i = tempResults.length - 1; i >= 0; i--) {
			String nextNum = tempResults[i];
			if(trace){System.out.println((nextNum.charAt(0) == '0') ? '0' : nextNum);}
			res = add_aux(res + '0', nextNum);
		}
		return new Vnumber(res);
	}
	
	// add_aux: auxiliary function for adding the partial results in grade school multiplication algorithm
	public static String add_aux(String num1, String num2) {
		char[] larger = (num1.length() > num2.length()) ? num1.toCharArray() : num2.toCharArray();
		char[] smaller = (num1.length() <= num2.length()) ? num1.toCharArray() : num2.toCharArray();
		byte[] res = new byte[larger.length];
		for(int i = 0; i < larger.length; i++) {
			if(i >= larger.length - smaller.length) {
				res[i] = (byte)(Character.getNumericValue(larger[i]) + Character.getNumericValue(smaller[i - (larger.length - smaller.length)]));
			} else {
				res[i] = (byte)Character.getNumericValue(larger[i]);
			}
		}

		String resString = normaliseVnumStr(res);
		return resString;
	}
	
	// add: Add two instances of Vnumber and return result
	public static Vnumber add(Vnumber num1, Vnumber num2) {
		if(num1 == null || num2 == null) {
			return null;
		}
		byte[] larger = (num1.digitArray.length > num2.digitArray.length) ? num1.digitArray.clone() : num2.digitArray.clone();
		byte[] smaller = (num1.digitArray.length <= num2.digitArray.length) ? num1.digitArray.clone() : num2.digitArray.clone();
		byte[] res = new byte[larger.length];
		for(int i = 0; i < larger.length; i++) {
			if(i >= larger.length - smaller.length) {
				res[i] = (byte)(larger[i] + smaller[i - (larger.length - smaller.length)]);
			} else {
				res[i] = larger[i];
			}
		}
		
		Vnumber resNum = normaliseVnum(res);
		return resNum;
	}
	
	// normalisevNumStr: auxilliary function for applying carry to add_aux function subresults
	private static String normaliseVnumStr(byte[] num) {
		String strResult = "";
		for(int i = num.length - 1; i >= 0; i--) {
			byte resDigit = (byte)(num[i] % 10);
			byte carry = (byte)(num[i] / 10);
			if(i > 0) {
				num[i] = resDigit;
				num[i - 1] += (byte)carry;
			} else {
				num[i] = resDigit;
				strResult = makeString_aux(num);
				if(carry > 0) {	
					strResult = carry + strResult;
				}
			}
		}
		return strResult;
	}
	
	// normalisevNum: auxilliary function for applying carry to add function subresults
	private static Vnumber normaliseVnum(byte[] num) {
		Vnumber res = null;
		for(int i = num.length - 1; i >= 0; i--) {
			byte resDigit = (byte)(num[i] % 10);
			byte carry = (byte)(num[i] / 10);
			if(i > 0) {
				num[i] = resDigit;
				num[i - 1] += (byte)carry;
			} else {
				num[i] = resDigit;
				res = new Vnumber(num);
				if(carry > 0) {
					byte[] numCorr = new byte[num.length + 1];
					System.arraycopy(num, 0, numCorr, 1, num.length);
					numCorr[0] = carry;
					res = new Vnumber(numCorr);
				}
			}
		}
		return res;
	}
	
	// makeString_aux: auxiliary function for making a String representation of number represented by digits in arr
	private static String makeString_aux(byte[] arr) {
		String res = "";
		for(int i = 0; i < arr.length; i++) {
			res += arr[i];
		}
		return res;
	}
	
	// multiply_NDC: Multiply this number with the number num using the Naive Divide and Conquer Algorithm
	public Vnumber multiply_NDC(Vnumber num) {
		// call to algorithm implementation
		System.out.printf("%s %s%n", this.toString(), num.toString());
		Vnumber res = NDC(this, num);
		System.out.println(res.toString());
		
		return res;
	}
	
	// NDC: The Naive Divide and Conquer algorithm implementation method
	private Vnumber NDC(Vnumber num1, Vnumber num2) {
		num1.digitArray = num1.clearPadding().digitArray;
		num2.digitArray = num2.clearPadding().digitArray;
		// base cases
		// if num1 == 0 or num2 == 0, return 0
		if(num1.isZero || num2.isZero) {
			return new Vnumber(0);
		// if any number has only one digit, perform "Grade School" multiplication
		} else if(num1.clearPadding().digitArray.length == 1 || num2.clearPadding().digitArray.length == 1) {
			num1 = num1.clearPadding();
			num2 = num2.clearPadding();
			Vnumber res = num1.schoolMultiply(num2, false);
			
			return res;
		}
		
		// Extend both number to nearest common even length
		int newLength = max(num1.digitArray.length, num2.digitArray.length);
		while(!isEven(newLength)) {
			newLength++;
		}

		// extend numbers
		num1 = new Vnumber(num1, newLength);
		num2 = new Vnumber(num2, newLength);
				
		
		// get number of digits
		int len = num1.digitArray.length;
		
		// compute and save number partitons a1, a0, b1, b0
		byte[] passArray;
		passArray = new byte[num1.digitArray.length / 2];
		System.arraycopy(num1.digitArray, 0, passArray, 0, num1.digitArray.length / 2);
		Vnumber a1 = new Vnumber(passArray);
		
		passArray = new byte[num1.digitArray.length / 2];
		System.arraycopy(num1.digitArray, num1.digitArray.length / 2, passArray, 0, num1.digitArray.length / 2);
		Vnumber a0 = new Vnumber(passArray);
		
		passArray = new byte[num2.digitArray.length / 2];
		System.arraycopy(num2.digitArray, 0, passArray, 0, num2.digitArray.length / 2);
		Vnumber b1 = new Vnumber(passArray);
		
		passArray = new byte[num2.digitArray.length / 2];
		System.arraycopy(num2.digitArray, num2.digitArray.length / 2, passArray, 0, num2.digitArray.length / 2);
		Vnumber b0 = new Vnumber(passArray);
		
		// Compute subproducts (recursive calls)
		System.out.printf("%s %s%n", a0.toString(), b0.toString());
		Vnumber a0b0 = NDC(a0, b0);
		System.out.println(a0b0.toString());
		
		System.out.printf("%s %s%n", a0.toString(), b1.toString());
		Vnumber a0b1 = NDC(a0, b1);
		System.out.println(a0b1.toString());
		
		System.out.printf("%s %s%n", a1.toString(), b0.toString());
		Vnumber a1b0 = NDC(a1, b0);
		System.out.println(a1b0.toString());
		
		System.out.printf("%s %s%n", a1.toString(), b1.toString());
		Vnumber a1b1 = NDC(a1, b1);
		System.out.println(a1b1.toString());
		
		// get result
		Vnumber res = add(add(appendZeros(a1b1, len), appendZeros(add(a0b1, a1b0), len/2)), a0b0);
		
		return res;
	}
	
	// appendZeros: append trailing zeroes to number represented by num (multiply by 10^numZeros)
	private static Vnumber appendZeros(Vnumber num, int numZeros) {
		if(num == null) {
			return null;
		}
		if(num.isZero) {
			return new Vnumber(0);
		}
		byte[] newArr = new byte[num.digitArray.length + numZeros];
		System.arraycopy(num.digitArray, 0, newArr, 0, num.digitArray.length);
		
		return new Vnumber(newArr);
	}
	
	// multiply_Karatsuba: Multiply this number with the number num using the Naive Divide and Conquer Algorithm
	public Vnumber multiplyKaratsuba(Vnumber num) {	
		// call to algorithm implementation
		System.out.printf("%s %s%n", this.toString(), num.toString());
		Vnumber res = karatsuba(this, num);
		System.out.printf("%s%n", res.toString());
		return res;
	}
	
	// karatsuba: implementation of the Karatsuba multiplication algorithm
	private Vnumber karatsuba(Vnumber num1, Vnumber num2) {
		num1.digitArray = num1.clearPadding().digitArray;
		num2.digitArray = num2.clearPadding().digitArray;
		// base cases
		// If num1 == 0 or num2 == 0, return 0.
		if(num1.isZero || num2.isZero) {
			return new Vnumber(0);
		// if numbers have one digit
		} else if(num1.clearPadding().digitArray.length == 1 || num2.clearPadding().digitArray.length == 1) {
			// trivial multiplication
			return num1.schoolMultiply(num2, false);
		}
		
		// Extend both number to nearest common even length (if applicable)
		int newLength = max(num1.digitArray.length, num2.digitArray.length);
		while(!isEven(newLength)) {
			newLength++;
		}
		
		// Extend numbers for algorithm implementation
		num1 = new Vnumber(num1, newLength);
		num2 = new Vnumber(num2, newLength);
	
		// Get number of digits.
		int len = num1.digitArray.length;
		
		// Compute and save partitions a1, a0, b1, b0.
		byte[] passArray;
		passArray = new byte[num1.digitArray.length / 2];
		System.arraycopy(num1.digitArray, 0, passArray, 0, num1.digitArray.length / 2);
		Vnumber a1 = new Vnumber(passArray);
		
		passArray = new byte[num1.digitArray.length / 2];
		System.arraycopy(num1.digitArray, num1.digitArray.length / 2, passArray, 0, num1.digitArray.length / 2);
		Vnumber a0 = new Vnumber(passArray);
		
		passArray = new byte[num2.digitArray.length / 2];
		System.arraycopy(num2.digitArray, 0, passArray, 0, num2.digitArray.length / 2);
		Vnumber b1 = new Vnumber(passArray);
		
		passArray = new byte[num2.digitArray.length / 2];
		System.arraycopy(num2.digitArray, num2.digitArray.length / 2, passArray, 0, num2.digitArray.length / 2);
		Vnumber b0 = new Vnumber(passArray);
		
		// Compute sub products (recursive calls).
		System.out.printf("%s %s%n", a0.toString(), b0.toString());
		Vnumber a0b0 = karatsuba(a0, b0);
		System.out.printf("%s%n", a0b0.toString());
		
		System.out.printf("%s %s%n", a1.toString(), b1.toString());
		Vnumber a1b1 = karatsuba(a1, b1);
		System.out.printf("%s%n", a1b1.toString());
		
		// Compute some needed sums
		Vnumber sum_a0a1 = add(a0, a1);
		Vnumber sum_b0b1 = add(b0, b1);
		
		System.out.printf("%s %s%n", sum_a0a1.toString(), sum_b0b1.toString());
		Vnumber prod_a0a1plusb0b1 = karatsuba(sum_a0a1, sum_b0b1);
		System.out.printf("%s%n", prod_a0a1plusb0b1.toString());
		
		// Get result.
		Vnumber res = add(add(appendZeros(a1b1, len), appendZeros(subtract(subtract(prod_a0a1plusb0b1, a1b1), a0b0), len/2)), a0b0);		
		return res;
	}
	
	// subtract: subtracts number represented by Vnumber num1 from number represented by num2
	private Vnumber subtract(Vnumber num1, Vnumber num2) {
		byte[] larger = num1.digitArray.clone();
		byte[] smaller = num2.digitArray.clone();
		byte[] res = new byte[larger.length];
		for(int i = 0; i < larger.length; i++) {
			if(i >= larger.length - smaller.length) {
				res[i] = (byte)(larger[i] - smaller[i - (larger.length - smaller.length)]);
			} else {
				res[i] = larger[i];
			}
		}
		// normalise result (apply borrow)
		Vnumber resNum = normaliseVnumSub(res);
		return resNum;
	}
	
	// normalievNumSub: auxiliary method for applying borrow to subtraction results obtained by subtract method
	private Vnumber normaliseVnumSub(byte[] num) {
		for(int i = num.length - 1; i >= 0; i--) {
			if(num[i] < 0 && i > 0) {
				num[i] = (byte)(num[i] + 10);
				num[i - 1]--;
			}
		}
		return new Vnumber(num);
	}
	
	// clearPadding: clear padding zeros from digitArray and return Vnumber with such digitArray.
	public Vnumber clearPadding() {
		int numDigits = 0;
		for(int i = 0; i < this.digitArray.length; i++) {
			if(this.digitArray[i] > 0) {
				numDigits = this.digitArray.length - i;
				break;
			}
		}
		byte[] newDigitArr = new byte[numDigits];
		System.arraycopy(this.digitArray, this.digitArray.length - numDigits, newDigitArr, 0, numDigits);
		return new Vnumber(newDigitArr);
	}
	
	// max: get max(a, b)
	private int max(int a, int b) {
		if(a > b) {
			return a;
		} else {
			return b;
		}
	}
	
	// isEven: Check if integer num is even.
	private boolean isEven(int num) {
		return num % 2 == 0;
	}
	
	// Return string representation of the number represented by this instance of Vnumber.
	public String toString() {
		String res = "";
		int i = 0;
		while(i < this.digitArray.length && this.digitArray[i] == 0) {
			i++;
		}
		
		if(i == this.digitArray.length) {
			return "0";
		}
		
		for(; i < digitArray.length; i++) {
			res += String.valueOf(digitArray[i]);
		}
		return res;
	}
}