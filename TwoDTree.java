// Class TwoDTree represents a 2d-tree, which is a generalization of a BST(Binary Search Tree) to two-dimensional keys.
import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.BufferedReader;
import java.io.FileReader;
public class TwoDTree {
	// Class TreeNode represents our tree's nodes. 
	// The nodes are points and we use the x-coordinate and y-coordinate of the points as keys in alternating sequence.
	private class TreeNode {
		Point item; // An object of the class Point
		TreeNode l; // pointer to left subtree
		TreeNode r; // pointer to right subtree
		// Constructor: creates a tree node.
		TreeNode(Point p){
			item = p;
		}
	};
	
	private TreeNode head; // root of the tree
	// Constructor: we construct an empty 2d tree.
	public TwoDTree(){
		head = null;
	} 
	// Method isEmpty() returns true if our 2d tree is empty and false otherwise.
	public boolean isEmpty() {
		if (head == null)
			return true;
		return false;
	}
	// Returns the size of our 2d tree (the number of points in our 2d tree) using method size(TreeNode node).
	public int size() {
		return size(head); 
	}
	// Returns the size of a given branch
	// Method size(TreeNode node) has one parameter of type TreeNode, node, which is the node we use to count from.
	private int size(TreeNode node) { 
		// if there are no more nodes.
		if (node == null) 
			return 0; 
		// Using our parameter we compute recursively the size of the right and the left subtree.
		else { 
			return size(node.l) + 1 + size(node.r); 
		} 
	}
	// Inserts a point p to our tree using method insert(TreeNode node, Point p, boolean level).
	public void insert(Point p){
		boolean level = true;
		head = insert(head, p, level);
	}
	// Method insert(TreeNode node, Point p, boolean level) has 3 arguments.
	// node of type TreeNode, which is the node we use to start the insertion process each time,
	// Point p, which is the point the user wants to insert in our tree,
	// and a boolean variable, level, which helps us define in which subtree we insert the node (level is true for x and false for y).
	private TreeNode insert(TreeNode node, Point p, boolean level){
        // if new node, create it
        if (node == null) {
            return new TreeNode(p);
        }

        // if it's already in our tree, we notify the user and return the node.
        if ((node.item.x == p.x()) && (node.item.y == p.y())) {
			System.out.println("Can\'t insert, the node is in the tree!");
			return node;
		}

        // else, we insert it recursively where it corresponds (to the left or the right).
        if (((level) && (p.x() <= node.item.x)) || ((!level) && (p.y() <= node.item.y)))
            node.l = insert(node.l, p, !level);		
        else
            node.r = insert(node.r, p, !level);

        return node;
    }
	// Searches a point provided by the user in our tree using method search(TreeNode node, Point p, boolean subtree).
	public boolean search(Point p) {
		boolean subtree = true;
		return search(head, p, subtree);
	}
	// Method search(TreeNode node, Point p, boolean subtree) has 3 arguments.
	// node of type TreeNode, which is the node we use to start searching our tree each time,
	// Point p, which is the point the user wants to search in our tree,
	// and a boolean variable, subtree, which helps us define in which subtree we search the node (subtree is true for x and false for y).
	private boolean search(TreeNode node, Point p, boolean subtree) {
		// If we can't find the node, notify the user and return false.
		if (node == null) {
			System.out.println("Node not found!");
            return false;
        }

        // if the node is found notify the user and return true.
        if ((node.item.x == p.x()) && (node.item.y == p.y())) {
			System.out.println("Node found!");
			return true;
		}

        // else, we search it recursively to the left or to the right subtree.
        if (((subtree) && (p.x() <= node.item.x)) || ((!subtree) && (p.y() <= node.item.y)))
            return search(node.l, p, !subtree);
        else
            return search(node.r, p, !subtree);
	}
	// Method nearestNeighbor(TreeNode node, Rectangle Rect, int x, int y, Point closest) returns nearest neighbor of 
	// (x,y) in the subtree rooted at node and it has 5 arguments.
	// node, of type TreeNode, which is the node we use to start the process.
	// Rectangle Rect, which is the rectangle we search in each time.
	// x and y, which are the coordinates of the given point.
	// Point closest, which is the closest point each time.
	private Point nearestNeighbor(TreeNode node, Rectangle Rect, int x, int y, Point closest) {
        if (node == null) 
			return closest;
		boolean level = true; // true for x, false for y.
        double distToPoint = 0.0;
        double distToRec = 0.0;
        Rectangle left = null;
        Rectangle right = null;
        Point current = new Point(x, y);
        Point nearest = closest;

        if (nearest != null) {
            distToPoint = current.squareDistanceTo(nearest);		// distance between the two points.
            distToRec = Rect.squareDistanceTo(current);				// distance between the given point and the rectangle.
        }

        if ((nearest == null) || (distToPoint > distToRec)) {
			Point point = new Point(node.item.x(), node.item.y());
			// If the new point is closer to the given point than the previously closest point.
            if ((nearest == null) || (distToPoint > current.squareDistanceTo(point)))
                nearest = point;

            if (level) {
				level = false;	// Next time go to else
				// Create new rectangles using node's x-coordinate.
                left = new Rectangle(Rect.xmin(), Rect.ymin(), node.item.x(), Rect.ymax());
                right = new Rectangle(node.item.x(), Rect.ymin(), Rect.xmax(), Rect.ymax());

                if (x < node.item.x()) {
                    nearest = nearestNeighbor(node.l, left, x, y, nearest);		// Call nearestNeighbor for the left node.
                    nearest = nearestNeighbor(node.r, right, x, y, nearest);	// Call nearestNeighbor for the right node.
                } 
				else {
                    nearest = nearestNeighbor(node.r, right, x, y, nearest);	// Call nearestNeighbor for the right node.
                    nearest = nearestNeighbor(node.l, left, x, y, nearest);		// Call nearestNeighbor for the left node.
                }
            } 
			else {
				level = true;	// Next time go to if
				// Create new rectangles using node's y-coordinate.
                left = new Rectangle(Rect.xmin(), Rect.ymin(), Rect.xmax(), node.item.y());
                right = new Rectangle(Rect.xmin(), node.item.y(), Rect.xmax(), Rect.ymax());

                if (y < node.item.y()) {
                    nearest = nearestNeighbor(node.l, left, x, y, nearest);		// Call nearestNeighbor for the left node.
                    nearest = nearestNeighbor(node.r, right, x, y, nearest);	// Call nearestNeighbor for the right node.
                } 
				else {
                    nearest = nearestNeighbor(node.r, right, x, y, nearest);	// Call nearestNeighbor for the right node.
                    nearest = nearestNeighbor(node.l, left, x, y, nearest);		// Call nearestNeighbor for the left node.
                }
            }
        }
		return nearest;
    }
	// Finds a point's nearest neighbor (a point in our tree that is closest to p), 
	//using method nearestNeighbor(TreeNode node, Rectangle Rect, int x, int y, Point closest).
	public Point nearestNeighbor(Point p) {
		Rectangle Rect = new Rectangle(0, 0, 100, 100);
		return nearestNeighbor(head, Rect, p.x(), p.y(), null);
	} 
	// Method rangeSearch(boolean level, TreeNode node, Rectangle newRect, Rectangle rect, SingleLinkedList<Point> list) 
	// is recursive and it has 5 arguments.
	// boolean level which is true for x, false for y, determines where we start the process.
	// node, of type TreeNode, which is the node we use to create a point and check if it is contained in the given rectangle.
	// Rectangle newRect, which is the rectangle we use to check a node each time.
	// Rectangle rect, which is the given rectangle.
	// SingleLinkedList<Point> list, which is the list of points contained in the rectangle.
	private void rangeSearch(boolean level, TreeNode node, Rectangle newRect, Rectangle rect, SingleLinkedList<Point> list){
		// If there are no more nodes return.
        if (node == null) 
			return;
		// Do the rectangles intersect?
		if (newRect.intersects(rect)) {
            Point p = new Point(node.item.x(), node.item.y());
			// Does the given rectangle contain the point?
            if (rect.contains(p)) {
				list.InsertAtBack(p);	// Update list
			}
			if (level){
				// Create new rectangles using node's x-coordinate.
				Rectangle rec1 = new Rectangle(node.item.x, newRect.ymin(), newRect.xmax(), newRect.ymax());
				Rectangle rec2 = new Rectangle(newRect.xmin(), newRect.ymin(), node.item.x, newRect.ymax());
                rangeSearch(!level, node.l, rec2, rect, list);		// Call rangeSearch for the left node.
				rangeSearch(!level, node.r, rec1, rect, list);		// Call rangeSearch for the right node.
			}
			if (!level){
				// Create new rectangles using node's y-coordinate.
				Rectangle rec1 = new Rectangle(newRect.xmin(), node.item.y, newRect.xmax(), newRect.ymax());
				Rectangle rec2 = new Rectangle(newRect.xmin(), newRect.ymin(), newRect.xmax(), node.item.y);
                rangeSearch(!level, node.l, rec2, rect, list);		// Call rangeSearch for the left node.
				rangeSearch(!level, node.r, rec1, rect, list);		// Call rangeSearch for the right node.
			}
        }
	}
	// Returns a list of the Points that are contained in the rectangle,
	// using method rangeSearch(boolean level, TreeNode node, Rectangle newRect, Rectangle rect, SingleLinkedList<Point> list).
	public SingleLinkedList<Point> rangeSearch(Rectangle rect) {
		SingleLinkedList<Point> list = new SingleLinkedList<>();
		Rectangle Rect = new Rectangle(0, 0, 100, 100);
		boolean level = true;
        rangeSearch(level, head, Rect, rect, list);
		return list;
	}
	// Method print(TreeNode node, int space) is recursive and it has two arguments.
	// node, the node we start printing from.
	// space, which is the space between each level.
	private void print(TreeNode node, int space){
		final int counter = 10; 
		// if there are no more nodes return.
		if (node == null)  
			return;  
	  
		// Increasing the distance between each level  
		space += counter;  
	  
		// Start printing from the right node.  
		print(node.r, space);  
	  
		// Print space 
		System.out.println();  
		for (int i = counter; i < space; i++)  
			System.out.print(" "); 
		System.out.print(node.item.toString());	// Print node.
		System.out.println();  
	  
		// Print from the left node.  
		print(node.l, space);  
	}  
	  
	// Prints our tree using method print(TreeNode node, int space).  
	void print(){   
		print(head, 0);  
	} 
	public static void main (String[] args){
		try{
			TwoDTree OurTree = new TwoDTree();
			// Get file name from the user.
			Path path = Paths.get(args[0]);
			Path fileName = path.getFileName();
			Scanner in= new Scanner(new BufferedReader(new FileReader(fileName.toString())));
			int points = in.nextInt();		// how many points will we read?
			boolean done = false;
			int i = 0;
			boolean next = in.hasNext();
			int coorX, coorY;
			int Xmin,Xmax,Ymin,Ymax;
			// while there are two more integers to read and done is false.
			while ((in.hasNext())&&(next)&&(!done)){
				i++;
				if (i > points){
					done = true;
					continue;
				}
				coorX = in.nextInt();
				if (in.hasNext()){
					coorY = in.nextInt();
					// Checking if we read wrong data.
					if ((coorX > 100)||(coorX < 0)||(coorY > 100)||(coorY < 0)){
						next = false;
						continue;	// Go to the next loop and exit!
					}
				}
				// if we have coorX but not coorY go to the next loop without creating the point and exit!
				else {
					next = false;
					continue;
				}
				// Create the point since we have both coordinates and insert it to the tree.
				Point p = new Point(coorX, coorY);
				OurTree.insert(p);
			}
			// Did we get less data?
			if ((next == false)||(i < points)){
				System.out.print("Wrong data!");
				System.exit(1);
			}
			// Creating an infinite loop
			boolean InfLoop = true;
			while (InfLoop){
				// Printing menu
				System.out.println("1. Compute the size of the tree");
				System.out.println("2. Insert a new point");
				System.out.println("3. Search if a given point exists in the tree");
				System.out.println("4. Provide a query rectangle");
				System.out.println("5. Provide a query point");
				System.out.println("6. Print the current tree");
				System.out.println("Enter your choice...");
				Scanner ans = new Scanner(System.in);
				// Reading the user's choice
				int choice = ans.nextInt();
				int X,Y;
				boolean found;
				SingleLinkedList<Point> list; 
				if (choice == 1){
					int s = OurTree.size();
					System.out.println("The size of our tree is " + s);
				}
				else if (choice == 2){
					System.out.println("Enter coordinates...");
					System.out.println("Enter x coordinate...");
					X = ans.nextInt();
					System.out.println("Enter y coordinate...");
					Y = ans.nextInt();
					// If the user gave wrong data we return to the menu.
					if ((X > 100)||(X < 0)||(Y > 100)||(Y < 0)){
						System.out.println("Wrong data! Returning to menu...");
						continue;
					}
					Point p = new Point(X, Y);
					OurTree.insert(p);
				}
				else if (choice == 3){
					System.out.println("Enter coordinates...");
					System.out.println("Enter x coordinate...");
					X = ans.nextInt();
					System.out.println("Enter y coordinate...");
					Y = ans.nextInt();
					// If the user gave wrong data we return to the menu.
					if ((X > 100)||(X < 0)||(Y > 100)||(Y < 0))
						System.out.println("Wrong data! Returning to menu...");
					Point p = new Point(X, Y);
					found = OurTree.search(p);
				}
				else if (choice == 4){
					System.out.println("Enter coordinates...");
					System.out.println("Enter minimum x coordinate...");
					Xmin = ans.nextInt();
					System.out.println("Enter minimum y coordinate...");
					Ymin = ans.nextInt();
					System.out.println("Enter maximum x coordinate...");
					Xmax = ans.nextInt();
					System.out.println("Enter maximum y coordinate...");
					Ymax = ans.nextInt();
					// If the user gave wrong data we return to the menu.
					if ((Xmin > 100)||(Xmin < 0)||(Ymin > 100)||(Ymin < 0)||(Xmax > 100)||(Xmax < 0)||(Ymax > 100)||(Ymax < 0)){
						System.out.println("Wrong data! Returning to menu...");
						continue;
					}
					Rectangle rec = new Rectangle(Xmin, Ymin, Xmax, Ymax);
					list = OurTree.rangeSearch(rec);
					list.print();		// Print list of nodes contained in the rectangle.
				}
				else if (choice == 5){
					System.out.println("Enter coordinates...");
					System.out.println("Enter x coordinate...");
					X = ans.nextInt();
					System.out.println("Enter y coordinate...");
					Y = ans.nextInt();
					// If the user gave wrong data we return to the menu.
					if ((X > 100)||(X < 0)||(Y > 100)||(Y < 0))
						System.out.println("Wrong data! Returning to menu...");
					Point p = new Point(X, Y);
					Point near = OurTree.nearestNeighbor(p);
					System.out.print("Closest point is: ");
					System.out.println(near.toString());		// Print the closest point.
				}
				else if (choice == 6){
					OurTree.print();		// Print tree.
				}
			}
		}
		// Throw exception if the path is wrong or inexistent.
		catch(IOException ioe){
			System.err.println("Error! File not found!");
			System.exit(1);
		}
	}
}
