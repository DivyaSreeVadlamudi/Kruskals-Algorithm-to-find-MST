// Java program for Kruskal's algorithm for Minimum spanning tree
import java.util.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
public class KruskalsAlgoMST {
	// Defines edge structure
	static class Edge {
	    //s is source of the edge d is destination of the edge
		int s, d, weight;
		//Edge Constructor which initializes the values of new edges added
		public Edge(int s, int d, int weight) {
			this.s = s;
			this.d = d;
			this.weight = weight;
		}
	}
	// Defines subset element structure
	static class Subset {
		int parent, rank;
		//Subset Constructor which initializes the values of parent of subset and rank of subset
		public Subset(int parent, int rank) {
			this.parent = parent;
			this.rank = rank;
		}
	}
	// Function to find the MST
	public static void kruskals(int n, List<Edge> edges) {
		int j = 0;
		int noOfEdges = 0;
		// Allocating memory for creating n subsets as there are n elements
		Subset subsets[] = new Subset[n];
		// Allocating memory for adding edges to MST
		Edge results[] = new Edge[n];
		// Creating n subsets with single elements
		for (int i = 0; i < n; i++) {
			subsets[i] = new Subset(i, 0);
		}
		// Number of edges to be taken is maximum of to n-1
		while (noOfEdges < n - 1) {
			// Pick the smallest edge from sorted edges and increment the index for next iteration
			Edge nextEdge = edges.get(j);
			//find the parent of the vertices on either side of the edge
			int x = Root(subsets, nextEdge.s);
			int y = Root(subsets, nextEdge.d);
			//if the parents of the vertices on the either side of the edge are not same then enter the loop
			if (x != y) {
			    // include the edge in the results array
				results[noOfEdges] = nextEdge;
				//Find the union of two subsets before proceeding to the next iteration
				union(subsets, x, y);
				//increment the index of noOfEdges to store next edge to be added
				noOfEdges++;
			}
			//proceed to adding next edge by incrementing j when noOfEdges is less than n-1
			j++;
		}
		// Printing the edges added to results[] to display the built MST
		//and computing the cost of MST by adding the weights of edges added to MST
		System.out.println("Following are the edges of the constructed MST:");
		int minCost = 0;
		for (int i = 0; i < noOfEdges; i++) {
			System.out.println(results[i].s + " -- "
							+ results[i].d + " == "
							+ results[i].weight);
			minCost += results[i].weight;
		}
		System.out.println("Total cost of MST: " + minCost);
	}
	// Function to unite two disjoint sets
	public static void union(Subset[] subsets, int x, int y) {
	    //Finding parents of both the subsets
	    //parent of x is rootX and y os rootY
		int rootX = Root(subsets, x);
		int rootY = Root(subsets, y);
		//if subsets of rootY has less rank then subsets of rootX
		//then parent of subsets of rootY is set to rootX
		if (subsets[rootY].rank < subsets[rootX].rank) {
			subsets[rootY].parent = rootX;
		}
		//if subsets of rootX has less rank then subsets of rootY
		//then parent of subsets of rootX is set to rootY
		else if (subsets[rootX].rank < subsets[rootY].rank) {
			subsets[rootX].parent = rootY;
		}
		//if both subsets ranks  are equal then set parent of subsets of rootY as rootX
		//and increment rank of rootX
		else {
			subsets[rootY].parent = rootX;
			subsets[rootX].rank++;
		}
	}
	// Function to find parent of a set
	public static int Root(Subset[] subsets, int i) {
		if (subsets[i].parent == i)
			return subsets[i].parent;
		subsets[i].parent = Root(subsets, subsets[i].parent);
		return subsets[i].parent;
	}
	// Starting point of program execution i.e, main function
    public static void main(String[] args) {
		//recording start time
        long start = System.nanoTime();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of vertices:");
        //taking the number of vertices of the graph as input
		int n = sc.nextInt();
		System.out.println("Enter the number of Edges:");
		//taking number of edges of the graph as input
		int m = sc.nextInt();
		//initializing the graph
		List<Edge> edges = new ArrayList<Edge>();
		//adding the edges to the graph randomly with the help of for loop and random function
        for (int i = 0; i < m; i++) {
            int source = (int) (Math.random() * n);
            int destination = (int) (Math.random() * n);
            int weight = (int) (Math.random() * 100);
            edges.add(new Edge(source, destination, weight));
        }
        //sorting the edges based on weights
        edges.sort(new Comparator<Edge>() {
            @Override public int compare(Edge o1, Edge o2)
            {
                return o1.weight - o2.weight;
            }
        });
        //calling the function to find MST
        kruskals(n, edges);
        //recording stop time
        long stop = System.nanoTime();
        //calclating the elapsed time
        long elapsedtime = stop-start;
        System.out.println("elapsed time = "+elapsedtime);

    }
}
