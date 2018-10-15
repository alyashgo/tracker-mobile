public class Linkedlist<Object>
{
	//Class variables for the Linked List
	private Node<Object> head;
	public Linkedlist(){
		head = null;
	}
	
	public Node<Object> getHead(){
		return this.head; /*getter for head*/
	}

	public void setHead(Node<Object> newhead){
		head = newhead; /*setter for head*/
	}

	public void add(Object dat)
	{
		Node<Object> newnode  = new Node<Object>(dat);
		newnode.setnext(null);
		if(this.isempty()){ //handling boundary case
			head = newnode; 
		}
		else{
			Node<Object> temp = head;
			while(temp.getnext()!=null){ //adding element to the last
				temp = temp.getnext();
			}
			temp.setnext(newnode);
		}
	}

	public Boolean contains(Object dat){

		if(this.isempty()){ //boundary case
			return false;
		}

		Node<Object> temp = head;

		while(temp!=null){ //checking in the list till reached the end(null)
				if(temp.getdata().equals(dat)){
					return true;
				}
				else{
					temp = temp.getnext();
				}
		}
		return false;
	}

	public void delete(Object dat) throws ObjectNotFoundException{
		Node<Object> temp = this.getHead();
		if(this.isempty()){ //boundary case
			throw new ObjectNotFoundException("Object Not Found");
		}

		if(this.contains(dat)){
			if(temp.getdata().equals(dat)){ //handling deleting from head node, changing the head
				this.setHead(temp.getnext());
				return;
		}
		else{
			while(temp.getnext()!=null){ //handling general case when not deleting from head
				if(temp.getnext().getdata().equals(dat)){ //reaching to the node
					Node<Object> temp1;
					temp1=temp.getnext().getnext(); //changing pointers to delete
					temp.setnext(temp1);
					return;
				}
				else{
					temp=temp.getnext();
				}
			}
		}
		}
		else{
			throw new ObjectNotFoundException("Object not found");
		}
	}

	public boolean isempty(){
		if(this.getHead() == null){
			return true;
		}
		else{
			return false;
		}
	}

	public void print(){ //to print the list
		Node<Object> temp = this.getHead();
		while(temp!=null){
			System.out.println(temp.getdata());
			temp= temp.getnext();
		}
	}
}
class ObjectNotFoundException extends Exception{
	public ObjectNotFoundException(String message ){
		super(message);
	}
}