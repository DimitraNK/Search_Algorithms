// Class LinkedNode creates nodes for our SingleLinkedList and our CircularLinkedList.
public class LinkedNode<T>{
	
	// generic type T data represents each node's data.
	T data;
	// nextNode variable is a a reference to the next node in our list.
	// nextNode is an object of the class we are creating, our LinkedNode class.
    LinkedNode<T> nextNode;
	// Our first constructor creates a node using the data parameter.
	LinkedNode(T data) {
        this.data = data;
		this.nextNode = null;
    }
	// Our second constructor creates a LinkedNode using the data parameter to store it's  
	// information and the nextNode parameter as reference for the next LinkedNode we create.
	LinkedNode(T data, LinkedNode<T> nextNode) {
        this.data = data;
		this.nextNode = nextNode;
    }
	// Returns the data.
	public T GetData(){
		return data;
	}
	// Returns reference to the next LinkedNode.
	public LinkedNode<T> GetNext(){
		return nextNode;
	}
	// Sets this LinkedNode's reference to the next node in our list.
	public void SetNext(LinkedNode<T> next) {
        nextNode = next;
    }
}