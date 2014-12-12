// *****************************
// Anji Zhao (az2324)
// COMS W3134 - Homework 5
// TravelingSalesman.java
// *****************************

import java.util.*; 

public class TravelingSalesman { 

	public static HashMap<Integer, Vertex> cities; 
	private static int n; 			// only allow in range [1,7]
	private static ArrayList<Integer[]> permutations; 
	public static Integer[] optimalRoute; 
	public static Integer[] nearestNeighborRoute;
	public static Integer[] route; 
	public static double totalDistance; 

	public TravelingSalesman(int n) throws TooLargeNException {

		if (n > 9 || n < 0) {
			throw new TooLargeNException("invalid n: " + n); 
		}
		cities = new HashMap<Integer, Vertex>(); 
		this.n = n; 
		generateVertices(); 
		addAllEdges(); 
		permutations = new ArrayList<Integer[]>(); 
		route = null; 
	}

	public void newSalesman(int n) throws TooLargeNException {
		if (n > 9 || n < 0) {
			throw new TooLargeNException("invalid n: " + n); 
		}
		cities.clear(); 
		this.n = n; 
		generateVertices(); 
		addAllEdges(); 
		permutations = new ArrayList<Integer[]>(); 
		route = null; 
	}

	public void nearestNeighbor() {
		Integer[] bestSequence = new Integer[n];
		double bestDistance = Double.POSITIVE_INFINITY;
		for (int i : cities.keySet()) {
			double distance = 0; 
			ArrayList<Integer> sequence = new ArrayList<Integer>(); 
			Vertex v = cities.get(i); 
			v.known = true; 
			sequence.add(i);
			while (!allKnown()) {
				Edge shortestEdge = v.adjacents.deleteMin();
				ArrayList<Edge> popped = new ArrayList<Edge>(); 
				popped.add(shortestEdge); 
				while (shortestEdge.adjacent.known) {
					// keep popping until an unknown vertex is reached
					shortestEdge = v.adjacents.deleteMin();
					popped.add(shortestEdge);
				} 
				for (Edge e : popped) {
					// add all popped edges back to the vertex 
					v.adjacents.insert(e); 
				}
				Vertex w = shortestEdge.adjacent; 
				distance += shortestEdge.distance; 
				w.known = true; 
				sequence.add(w.key); 
				v = w; 
			}
			distance += findDistance(v, cities.get(i)); 
			if (distance < bestDistance) {
				bestSequence = sequence.toArray(new Integer[sequence.size()]); 
				bestDistance = distance; 
			}
			for (Vertex x : cities.values()) {
				x.known = false; 
			}
		}
		route = bestSequence; 
		nearestNeighborRoute = bestSequence;
		totalDistance = bestDistance;
		// System.out.println("best:" + Arrays.toString(bestSequence));
		// System.out.println("distance: " + bestDistance);
	}

	private static boolean allKnown() {
		// checks if all the vertices have been marked as known 
		Collection<Vertex> c = cities.values(); 
		for (Vertex v : c) {
			if (!v.known) {
				return false; 
			}
		}
		return true;
	}

	public void bruteForce() {
		// find optimal path using a brute-force algorithm 
		ArrayList<Integer> front = new ArrayList<Integer>(cities.size());
		ArrayList<Integer> end = new ArrayList<Integer>(cities.keySet());
		permute(front, end); // find all permutations 
		Integer[] bestSequence = new Integer[n];
		double bestDistance = Double.POSITIVE_INFINITY;
		for (Integer[] permutation : permutations) {
			int i = 0; 
			double distance = 0; 
			while (i < n-1) {
				distance += findDistance(cities.get(permutation[i]), 
					cities.get(permutation[++i]));
			} distance += findDistance(cities.get(permutation[i]), 
				cities.get(permutation[0]));
			if (distance < bestDistance) {
				bestDistance = distance;
				bestSequence = permutation;
			}
			// System.out.println(Arrays.toString(permutation) + ": " + distance);
		}
		optimalRoute = bestSequence; 
		route = bestSequence;
		totalDistance = bestDistance;
		// System.out.println("best:" + Arrays.toString(bestSequence));
		// System.out.println("distance: " + bestDistance);
	}

	private double findDistance(Vertex v, Vertex w) {
		return Math.hypot(v.x-w.x, v.y-w.y);
	}

	private void permute(ArrayList<Integer> front0, ArrayList<Integer> end0) {
		// recursively find permutations of ArrayList front 
		int m = end0.size(); 
		if (m == 0) {
			// base case: front is the completed permutation 
			// convert to int[] and add it to overall list of permutations 
			Integer[] permutation = new Integer[front0.size()]; 
			permutation = front0.toArray(permutation); 
			permutations.add(permutation); 
		} else { 
			for (int i = 0; i < m; i++) { 
				ArrayList<Integer> front = new ArrayList<Integer>(front0); 
				ArrayList<Integer> end = new ArrayList<Integer>(end0); 
				front.add(end.get(i)); 
				end.remove(i); 
				permute(front, end); 
			}
		}
	}

	private void generateVertices() {
		String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K"}; 
		for (int i = 0; i < n; i++) {
			double x = Math.random(); 
			double y = Math.random(); 
			Vertex v = new Vertex(letters[i]); 
			v.x = x; v.y = y; v.key = i; 
			cities.put(i, v); 
		}
	}

	private void addAllEdges() {
		Collection<Vertex> c = cities.values(); 
		for (Vertex v : c) {
			Collection<Vertex> d = cities.values(); 
			for (Vertex w : d) {
				double distance = Math.hypot(v.x-w.x, v.y-w.y);
				makeEdge(v, w, distance); 
			}
		}
	}
	
	private void makeEdge(Vertex city1, Vertex city2, double distance) {
		Edge e = new Edge(city1, city2, distance); 
		city1.addAdjacent(e); 
	}

}



