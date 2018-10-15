public class Myset<Object> {
    public Linkedlist<Object> set;//using a Linked list to implement set
	public Myset(){
		set = new Linkedlist<Object>(); //initialising Linked list
	}

	public Boolean IsEmpty(){
		return this.set.isempty();
	}

	public void Insert(Object o){
		this.set.add(o); //adding using Linked list method
	}

	public Boolean IsMember(Object o){
		return this.set.contains(o); //checking using Linked list method
	}

	public void Delete(Object o ) throws ObjectNotFoundException{
		if(this.IsMember(o)==false){
			throw new ObjectNotFoundException("Object Not Found");
		}
		else{
			this.set.delete(o); //deleting if found using Linked list method
		}
		
	}

	public Myset<Object> Union(Myset<Object> a ){
		Myset<Object> unionset = new Myset<Object>();
		Node<Object> temp = set.getHead(); 
		while(temp!=null){ //first adding all the elements of one set
			unionset.Insert(temp.getdata());
			temp = temp.getnext();
		}

		temp = a.set.getHead();
		while(temp!=null){ 
			if(unionset.IsMember(temp.getdata())==false){ //checking if element of second set already exists in union set, if not then adding
				unionset.Insert(temp.getdata());
			}
			temp = temp.getnext();
		}
		return unionset;
	}

	public Myset<Object> Intersection(Myset<Object> a){
		Myset<Object> intersectionset = new Myset<Object>();
		Node<Object> temp = this.set.getHead();
		while(temp!=null){
			if(a.IsMember(temp.getdata())){ //checking if element if first list exists in the otger, if yes then adding to intersection set
				intersectionset.Insert(temp.getdata());
			}
			temp = temp.getnext();
		}
		return intersectionset;
	}
}
