import java.util.NoSuchElementException;
import java.io.PrintStream;
public class SingleLinkedList<T>{
	// We use first and last to represent our SingleLinkedList's first and last LinkedNodes.
	private LinkedNode<T> first;
	private LinkedNode<T> last;
	
	public SingleLinkedList() {
		first = null;
		last = null;
    }
	// Method isEmpty() returns true if our list is empty and false otherwise.
	public boolean isEmpty(){
		return first == null;
	}
	// Inserts an item at the front of the list.
	// generic type T ItemToInsert represents our new node's data.
	public void InsertAtFront(T ItemToInsert){
		// first and last nodes refer to the same object.
		if (isEmpty()){
			first = last = new LinkedNode<T>(ItemToInsert);
		}
		// first node refers to a new node.
		else{
			first = new LinkedNode<T>(ItemToInsert, first);
		}
	}
	// Inserts an item at the end of the list.
	// generic type T ItemToInsert represents our new node's data.
	public void InsertAtBack(T ItemToInsert){
		// first and last nodes refer to the same object.
		if (isEmpty()){
			first = last = new LinkedNode<T>(ItemToInsert);
		}
		// last node's next node refers to a new node.
		else{
			last = last.nextNode = new LinkedNode<T>(ItemToInsert);
		}
	}
	// If the list is empty throws NoSuchElementException.
	// If not it returns and removes the data from our list's first node.
	public T RemoveFromFront() throws NoSuchElementException{
		// if our list contains no nodes.
		if (isEmpty())
			throw new NoSuchElementException();
		T ItemToRemove = first.data;		// Storing the removed node's data in order to return it.
		// if our list contains only one node.
		
		if (first == last){
			first = null;
			last = null;
		}
		// if our list has more than one nodes.
		else{
			first = first.GetNext();
		}
		return ItemToRemove;	// Returns the removed node's data.
	}
	// If the list is empty throws NoSuchElementException.
	// If not it returns and removes the data from our list's last node.
	public T RemoveFromBack() throws NoSuchElementException{
		// if our list contains no nodes.
		if (isEmpty())
			throw new NoSuchElementException();
		T ItemToRemove = last.data;			// Storing the removed node's data in order to return it.
		// if our list contains only one node.
		
		if (first == last){
			first = null;
			last = null;
		}
		// if our list has more than one nodes.
		else{
			LinkedNode<T> temp = first;
			
			while (temp.GetNext() != last)
				temp = temp.GetNext();
			last = temp;
			temp.nextNode = null;
		}
		return ItemToRemove; 	// Returns the removed node's data.
	}
	// Returns the first node's data.
	public T peek() throws NoSuchElementException{
		// If the list is empty throws NoSuchElementException.
		if (isEmpty()){
			throw new NoSuchElementException();
		}
		else{
			return first.data;
		}
	}
	// Prints our list.
	public void print(){
		// if our list contains no nodes.
		if (isEmpty()){
			System.out.println("There are no nodes in the rectangle");
			return;
		}
		// If our list is not empty.
		System.out.println("The nodes contained in the rectangle are: ");
		LinkedNode<T> temp = first;
		while (temp != null){
			System.out.print(temp.data);
			temp = temp.nextNode;
			System.out.print(" ");
		}
		System.out.println();
	}
	// Returns our list's size.
	public int size(){
		// if our list contains no nodes.
		if (isEmpty()){
			return 0;
		}
		// If our list is not empty.
		else{
			LinkedNode<T> temp = first;
			int counter = 0;
			while (temp != null){
				temp = temp.nextNode;
				counter++;
			}
			return counter;
		}
	}
}