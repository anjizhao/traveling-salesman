// *****************************
// Anji Zhao (az2324)
// COMS W3134 - Homework 5
// SalesmanGUI.java
// GUI to display traveling salesman results 
// *****************************

import java.util.*; 
import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*; 
import java.awt.geom.*; 
import java.text.*; 


public class SalesmanGUI {

	private static JFrame frame; 
	public static TravelingSalesman salesman; 
	private static JPanel top; 
	private static JLabel message; 
	private static Vertices vertices; 
	private static JPanel bottom; 
	private static JLabel inputLabel; 
	private static JTextField inputBox; 
	private static int numCities; 
	private static JButton start; 
	private static JButton bruteForce; 
	private static JButton nearestNeighbor; 
	private static Integer[] route; 

	public static void main(String[] args) {
		makeGUI(); 
	}

	private static void makeGUI() { 

		frame  = new JFrame(); 
		try {
			salesman = new TravelingSalesman(0); 
		} catch (TooLargeNException e) {
		}
		vertices = new Vertices(salesman.route); 

		inputLabel = new JLabel("choose N (1-9): "); 
		inputBox = new JTextField(4); 
		start = new JButton("go"); 
		start.addActionListener(new StartListener());

		bruteForce = new JButton("optimal path"); 
		bruteForce.addActionListener(new BruteForceListener()); 
		nearestNeighbor = new JButton("nearest neighbor tour"); 
		nearestNeighbor.addActionListener(new NearestNeighborListener()); 
		
		message = new JLabel("enter # of cities and press go to begin");

		top = new JPanel();
		top.add(message); 

		bottom = new JPanel(); 
		bottom.add(inputLabel); 
		bottom.add(inputBox); 
		bottom.add(start); 
		bottom.add(bruteForce); 
		bottom.add(nearestNeighbor); 

		frame.setLayout(new BorderLayout()); 
		frame.add(top, BorderLayout.NORTH);
		frame.add(vertices, BorderLayout.CENTER);
		frame.add(bottom, BorderLayout.SOUTH); 

		frame.setTitle("Traveling Salesman"); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.pack(); 
		frame.setSize(600,600); 
		frame.setVisible(true); 

	}


	public static class Vertices extends JPanel {

		static Collection<Vertex> c = salesman.cities.values();; 
		static Integer[] route;

		public Vertices(Integer[] r) {
			route = r;
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g; 
			if (c.size()!=0) {
				for (Vertex v : c) {
					Ellipse2D circle2D = new Ellipse2D.Double(v.x*500, (500-v.y*500), 6, 6);
					g2.draw(circle2D);
					g2.drawString((String) v.name, (int) (v.x*500-3), (int) (500-v.y*500)); 
				}
			}
			// draw edges 
			if (route != null) {
				int i = 0; 
				Vertex v; Vertex w; 
				while (i < numCities-1) {
					v = salesman.cities.get(salesman.route[i]); 
					w = salesman.cities.get(salesman.route[++i]); 
					g2.drawLine((int) (v.x*500+3), (int) (500-v.y*500+3), (int) (w.x*500+3), (int) (500-w.y*500+3));  
				} 
				v = salesman.cities.get(salesman.route[i]); 
				w = salesman.cities.get(salesman.route[0]); 
				g2.drawLine((int) (v.x*500+3), (int) (500-v.y*500+3), (int) (w.x*500+3), (int) (500-w.y*500+3));  
			}
		} 
	}

	private static class StartListener implements ActionListener {

		public StartListener() {
		}

		public void actionPerformed(ActionEvent event) {
			int n = Integer.parseInt(inputBox.getText()); 
			numCities = n;
			try {
				salesman.newSalesman(n);
				Vertices newGraph = new Vertices(salesman.route); 
				vertices = newGraph; 
			} catch (TooLargeNException e) {
				message.setText(e.getMessage()); 
				return;
			}
			message.setText("click to display the optimal route or " 
				+ "nearest neighbor tour");
			vertices.revalidate();
			vertices.repaint();
			frame.pack();
			frame.setSize(600,600);
		}

	}

	public static TravelingSalesman getSalesman() {
		return salesman; 
	}

	public static Vertices getVertices() {
		return vertices; 
	}

	private static class BruteForceListener implements ActionListener {

		public BruteForceListener() {
		}

		public void actionPerformed(ActionEvent event) {
			salesman.bruteForce(); 
			Vertices newGraph = new Vertices(salesman.route); 
			vertices = newGraph; 
			vertices.revalidate();
			vertices.repaint();
			message.setText("distance: " + String.format("%.6f", salesman.totalDistance)); 
			frame.pack();
			frame.setSize(600,600);
		}

	}

	private static class NearestNeighborListener implements ActionListener {

		public NearestNeighborListener() {
		}

		public void actionPerformed(ActionEvent event) {
			salesman.nearestNeighbor(); 
			Vertices newGraph = new Vertices(salesman.route); 
			vertices = newGraph; 
			vertices.revalidate();
			message.setText("distance: " + String.format("%.6f", salesman.totalDistance)); 
			vertices.repaint();
			frame.pack();
			frame.setSize(600,600);
		}

	}

}