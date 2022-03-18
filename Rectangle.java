// Class Rectangle represents a rectangle in a coordinate plane.
// importing java.lang.Math in order to compute the absolute value of a number.
import java.lang.Math;
public class Rectangle {
	int xmin,ymin,xmax,ymax;
	// Constructor: each rectangle is defined by the minimum values of its x-coordinate and its y-coordinate, 
	// as well as the maximum values of its x-coordinate and its y-coordinate.
	Rectangle(int xmin,int ymin,int xmax,int ymax){
		this.xmin = xmin;
		this.ymin = ymin;
		this.xmax = xmax;
		this.ymax = ymax;
	}
	// Returns minimum x-coordinate of our rectangle.
	public int xmin(){
		return xmin;
	} 
	// Returns minimum y-coordinate of our rectangle.
	public int ymin(){
		return ymin;
	} 
	// Returns maximum x-coordinate of our rectangle.
	public int xmax(){
		return xmax;
	} 
	// Returns maximum y-coordinate of our rectangle.
	public int ymax(){
		return ymax;
	} 
	// Returns true if our rectangle contains a point and false otherwise.
	public boolean contains(Point p){
		if ((p.x >= xmin && p.x <= xmax)&&(p.y >= ymin && p.y <= ymax)){	//does p belong to the rectangle?
			return true;
		}
		return false;
	} 
	// Returns true if our rectangle and another rectangle intersect and false otherwise.
	public boolean intersects(Rectangle that){
		// Creating rectangle that's angles.
		Point a = new Point(that.xmin, that.ymin);		
		Point b = new Point(that.xmax, that.ymax);
		Point c = new Point(that.xmin, that.ymax);
		Point d = new Point(that.xmax, that.ymin);
		if(contains(a)||contains(b)||contains(c)||contains(d))	// We use method contains to check if our rectangle contains any of that's angles.
			return true;
		return false;
	} 
	// Computes euclidean distance from p to its closest point in our rectangle.
	/***********
	*			xmin			zone #2			xmax
	*				|							|
	*		E		|			A				|		G
	*	 ___________|___________________________|______________	ymax						
	*				|							|
	*				|							|
	*		B		|			I				|		D	zone #1
	*				|							|
	*	 ___________|___________________________|______________	ymin
	*				|							|
	*		F		|			C				|		H
	*				|							|
	*
	************/
	public double distanceTo(Point p){
		double dist;				// distance from p to the closest point in our rectangle
		Point closest = null;		// closest point in our rectangle
		// Is the point in zone #1?
		if ((p.y <= ymax)&&(p.y >= ymin)){
			// Is the point in field B?
			if (Math.abs(p.x - xmin) <= Math.abs(p.x - xmax)){
				closest = new Point(xmin, p.y);
				dist = p.distanceTo(closest);
			} 
			// Is the point in field D?
			else{
				closest = new Point(xmax, p.y);
				dist = p.distanceTo(closest);
			}
		}
		// Is the point in zone #2?
		else if ((p.x <= xmax)&&(p.x >= xmin)){
			// Is the point in field C?
			if (Math.abs(p.y - ymin) <= Math.abs(p.y - ymax)){
				closest = new Point(p.x, ymin);
				dist = p.distanceTo(closest);
			} 
			// Is the point in field A?
			else{
				closest = new Point(p.x, ymax);
				dist = p.distanceTo(closest);
			}
		}
		// Is the point in field I? Does it belong to our rectangle? If it does return 0.
		else if (contains(p)){
			return 0.0;
		}
		// If the point does not belong to any of the two zones or our rectangle.
		else{
			// Is the x-coordinate of the point greater than xmax?
			if (p.x > xmax){
				// Is the point in field G?
				if (p.y > ymax){
					closest = new Point(xmax, ymax);
					dist = p.distanceTo(closest);
				}
				// Is the point in field H?
				else {
					closest = new Point(xmax, ymin);
					dist = p.distanceTo(closest);
				}
			}
			// Is the x-coordinate of the point smaller than xmin?
			else {
				// Is the point in field E?
				if (p.y > ymax){
					closest = new Point(xmin, ymax);
					dist = p.distanceTo(closest);
				}
				// Is the point in field F?
				else {
					closest = new Point(xmin, ymin);
					dist = p.distanceTo(closest);
				}
			}
		}
		return dist;
	} 
	// Computes square of euclidean distance from p to its closest point in our rectangle.
	public int squareDistanceTo(Point p){
		double sq_dist = distanceTo(p)*distanceTo(p);
		return (int)sq_dist;
	} 
	// Returns the string representation of our rectangle [xmin, xmax] x [ymin, ymax].
	public String toString(){
		return "[" + xmin + ", " + xmax + "] x [" + ymin +", " + ymax + "]";
	} 
}