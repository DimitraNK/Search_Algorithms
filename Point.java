// Class Point represents points in a coordinate plane.
// importing java.lang.Math in order to compute the square root of a number.
import java.lang.Math;
public class Point {
	int x;		// x-coordinate
	int y;		// y-coordinate
	Point(){
		x = 0;
		y = 0;
	}
	// Constructor: each point is named by its ordered pair of the form of (x, y).
	Point(int x,int y){
		this.x = x;
		this.y = y;
	}
	// Returns the x-coordinate of our point.
	public int x(){
		return x;
	} 
	// Returns the y-coordinate of our point.
	public int y(){
		return y;
	} 
	// Computes euclidean distance between two points.
	public double distanceTo(Point z){
		return Math.sqrt((((x-z.x)*(x-z.x))+((y-z.y)*(y-z.y))));
	} 
	// Computes square of the euclidean distance between two points.
	public int squareDistanceTo(Point z){
		double dist = distanceTo(z)*distanceTo(z);
		return (int)dist;
	} 
	// Returns the string representation of our point (x, y).
	public String toString(){
		return "(" + x + ", " + y + ")";
	} 
}