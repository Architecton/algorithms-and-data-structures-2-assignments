import java.util.Scanner;

interface OsFunctionality {
	// Mutliply this Vnumber with Vnumber num using the "Grade school algorithm"
	// Trace ? Print the trace : Do not print the trace
	public Vnumber schoolMultiply(Vnumber num, boolean trace);
	// Multiply this Vnumber with Vnumber num using the naive divide and conquer algorithm
	public Vnumber multiply_NDC(Vnumber num);
	// Multiply this Vnumber with Vnumber num using the Karatsuba algorithm.
	public Vnumber multiplyKaratsuba(Vnumber num);
}

interface MatFunctionality {
	// Multiply by definition
	public Matrix multiplyByDefinition(Matrix mat);
	// Multiply using the naive divide and conquer algorithm
	public Matrix multiply_NDC(Matrix mat, int finishRecDim);
	// Multiply using the Strassen matrix multiplication algorithm
	public Matrix strassenMultipy(Matrix mat, int finishRecDim);
}

public class Naloga2 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		// integer multiplication
		if(args[0].equals("num")) {
			// "Grade School" multiplication
			if(args[1].equals("os")) {
				Vnumber fst = new Vnumber(sc);
				Vnumber snd = new Vnumber(sc);
				Vnumber result = fst.schoolMultiply(snd, true);
				String res = result.toString();
				for(int i = 0; i < res.length(); i++) {
					System.out.printf((i == res.length() - 1) ? "%s\n" : "%s", "-");
				}
				System.out.println(res);
			// Naive Divide and Conquer multiplication
			} else if(args[1].equals("dv")) {
				Vnumber fst = new Vnumber(sc);
				Vnumber snd = new Vnumber(sc);
				fst.multiply_NDC(snd);
			// Karatsuba algorithm for multiplication
			} else if(args[1].equals("ka")) {
				Vnumber fst = new Vnumber(sc);
				Vnumber snd = new Vnumber(sc);
				fst.multiplyKaratsuba(snd);
			}
			
		}
		
		// matrix multiplication
		if(args[0].equals("mat")) {
			// parsing the matrices (Parsing happens inside constructor)
			Matrix mat1 = new Matrix(sc);
			Matrix mat2 = new Matrix(sc);
			
			// Set default size of matrix when recursion stops.
			int finishRecSize = 1;
			
			// if recursion exit size is specified in the arguments
			if(args.length == 3) { 
				finishRecSize = Integer.parseInt(args[2]);
			}
			
			// multiplication by definition
			if(args[1].equals("os")) {
				Matrix res = mat1.multiplyByDefinition(mat2);
				System.out.print(res.toString());
			// naive divide and conquer multiplication
			} else if(args[1].equals("dv")) {
				Matrix res = mat1.multiply_NDC(mat2, finishRecSize);
				System.out.print(res.toString());
			// strassen algorithm for matrix multiplication
			} else if(args[1].equals("st")) {
				Matrix res2 = mat1.strassenMultipy(mat2, finishRecSize);
				System.out.print(res2.toString());
			}
		}
		
		sc.close();
	}
}