/* Roots.java
 * Brenda Rivera
 * briverav
 * PA4
 * PA4 determines the real roots of a polynomial within a specified range.
 */

import java.util.Scanner;
import java.util.Arrays;

public class Roots {
	
	public static double[] totPoly, D;
	public static int degree, coefficient;
	
	public static void main(String[] args) {

		double resolution = Math.pow(10, -2), 
		tolerance = Math.pow(10, -7),
		threshold = Math.pow(10, -3),
		L, R;

		int rootN = 0, 
		diffrootN = 0;

		boolean rootNum = false;

		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter the degree: ");

		degree = sc.nextInt();
		coefficient = degree+1;
		double[] polyRoot = new double[degree];
		double[] diffpolyRoot = new double[degree];

		System.out.print("Enter "+coefficient+" coefficients: ");

		double[] derPoly = new double[coefficient];
		for (int z = 0; z < coefficient; z++) {
			derPoly[z] = sc.nextDouble();
		}

		System.out.print("Enter the left and right endpoints: ");
		L = sc.nextDouble();
		R = sc.nextDouble();

		sc.close();
		
		diff(derPoly);
		
		for (double z = L; z < (R-resolution); z += resolution) {
			if (valNum(D, z) == 0 || valNum(D, z) != valNum(D, (z+resolution))) {
				diffpolyRoot[diffrootN] = findRoot(D, z, (z+resolution), tolerance);
				diffrootN++;
			}
		}

		for (int z = 0; z < diffpolyRoot.length; z++) {
			double prob;
			prob = poly(derPoly, diffpolyRoot[z]);
			prob = Math.abs(prob);

			if (prob < threshold) {
				polyRoot[rootN] = diffpolyRoot[z];
				rootNum = true;
				rootN++;
			}
		}

		for (double z = L; z < (R-resolution); z += resolution) {
			if (valNum(derPoly, z) == 0 || valNum(derPoly, z) != valNum(derPoly, (z+resolution))) {
				polyRoot[rootN] = findRoot(derPoly, z, (z+resolution), tolerance);
				rootNum = true;
				rootN++;
			}
		}
		
		if (rootNum) {
			Arrays.sort(polyRoot);
			for (int z = 0; z < polyRoot.length; z++) {
				if (polyRoot[z] != 0.0) System.out.printf("Root found at %.5f%n", polyRoot[z]);
			}
		} else {
			System.out.println("No roots were found in the specified range.");
		}
	}
	
	static double poly(double[] C, double x) {
		
		double total = 0;
		totPoly = new double[C.length];
		totPoly[0] = C[0];
		for(int z = 1; z < C.length; z++) {
			totPoly[z] = (C[z]*(Math.pow(x, z)));
		}
		for(int z = 0; z < C.length; z++) {
			total += totPoly[z];
		}
		return total;
	}
	
	static double[] diff(double[] C) {
		
		D = new double[C.length];
		int y;
		for(int z = 0; z < degree; z++) {
			y = z++;
			D[z] = (y*C[y]);
		}
		return D;
	}

	static double findRoot(double[] C, double a, double b, double tolerance) {
		
		double root = 0.0, end;

		while (Math.abs(b-a) > tolerance) {
			root = (a+b) / 2.0;
			end = poly(C, root);

			if (poly(C, a) > 0 && poly(C, b) < 0) {
				if (end > 0) a = root; else b = root;

			} else if (poly(C, a) < 0 && poly(C, b) > 0) {
				if (end > 0) b = root; else a = root;

			} else if (poly(C, a) < 0 && poly(C, a) != 0.0 && poly(C, b) == 0.0) {
				a = Math.abs(a); a = Math.sqrt(a); a = root;

			} else if (poly(C, b) < 0 && poly(C, b) != 0.0 && poly(C, a) == 0.0) {
				b = Math.abs(b); b = Math.sqrt(b); b = root;
			}
		}
		return root;
	}
	
	static int valNum(double[] C, double a) {
		
		double endPoint = poly(C, a);
		
		if (endPoint > 0) return 2; else if (endPoint < 0) return 1; else return 0;
	}
}
