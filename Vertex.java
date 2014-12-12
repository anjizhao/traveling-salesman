// *****************************
// Anji Zhao (az2324)
// COMS W3134 - Homework 6
// Vertex.java
// vertex class for traveling salesman problem 
// adjacency list is a binary heap so removeMin() can be performed
// *****************************

import java.util.*; 

public class Vertex implements Comparable<Vertex> {

		public BinaryHeap<Edge> adjacents; 
		public boolean known; 
		public double pathDistance; 
		public Vertex path; 
		public String name; 
		public double x; 
		public double y; 
		public int key; 

		public Vertex(String n) { 

			adjacents = new BinaryHeap<Edge>(); 
			known = false; 
			pathDistance = Double.POSITIVE_INFINITY;
			path = null; 
			name = n; 
		}

		public void addAdjacent(Edge e) {
			adjacents.insert(e); 
		}

		public int compareTo(Vertex other) {
			if (this.pathDistance < other.pathDistance) {
				return -1; 
			} else if (this.pathDistance == other.pathDistance) {
				return 0; 
			} else { 
				return 1; }
		}

	}