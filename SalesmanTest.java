
// *****************************
// Anji Zhao (az2324)
// COMS W3134 - Homework 5
// SalesmanTest.java
// main method to test TravelingSalesman.java methods
// *****************************

import java.util.*; 


public class SalesmanTest {

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in); 
		System.out.println("enter n between 1-9: "); 
		int n = input.nextInt();

		TravelingSalesman salesman;

		try {
			salesman = new TravelingSalesman(n); 
		} catch (TooLargeNException e) {
			System.err.println(e.getMessage());
			return;
		}

		salesman.bruteForce();
		salesman.nearestNeighbor(); 
	}


}