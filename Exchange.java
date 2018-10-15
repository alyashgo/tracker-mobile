public class Exchange{
	int id;
	public MobilePhoneSet residentSet;//keeping the set of it phones
	Exchange parent;
	ExchangeList children; //Linked list of the children

	public Exchange(int num){
		id = num;
		residentSet = new MobilePhoneSet(); //allocating memory to MobilephoneSet and Linked list of children 
		children = new ExchangeList();
	}
	public int getId(){//getter for Id
		return this.id;
	}
	public Exchange getParent(){//getter for parent exchange
		return this.parent;
	}
	public MobilePhoneSet ResidentSet(){//getter for resident set
		return this.residentSet;
	}
	public ExchangeList getChildren(){//getter for Linked list of children
		return this.children;
	}
	public void addMobile(MobilePhone a){//adding phone by method of set
		this.residentSet.Insert(a);
	}
	public void setParent(Exchange parent){
		this.parent = parent;//setting the parent
		if(parent != null){//handling the boundary case of root
			parent.residentSet = parent.residentSet.Union(this.residentSet); //updating the resident set of parent
			parent.children.add(this);//updating the children list of parent 
		}
	}
	public void addChild(Exchange child){
		child.setParent(this);
	}
	public int numChildren(){
		Node<Exchange> temp = this.children.getHead();
		int count = 0;
		while(temp!=null){
			count+=1;
			temp = temp.getnext();
		}
		return count;
	}
	public Exchange child(int i ) throws indexNotInRangeException{
		if(i>=this.numChildren()){ //if index is greater than number of children throw exception
			throw new indexNotInRangeException("index not in range");
		}
		Node<Exchange> temp = this.children.getHead(); //else return the child at ith position starting from zero 
		int count = 0;
		while(count<i){
			count+=1;
			temp = temp.getnext();
		} 
		return temp.getdata();	
	}
	public Boolean isRoot(){
		return (this.getId()==0);
	}
	public Boolean isLeaf(){
		return (this.numChildren()==0);
	}
	public RoutingMapTree subtree(int i) throws indexNotInRangeException{//returning the subtree with ith child as root
		
		if(i>this.numChildren()){
			throw new indexNotInRangeException("not a valid child index");
		}
		else{
			RoutingMapTree newsubtree = new RoutingMapTree(this.child(i)); //calling second constructor of the routingMapTree to just pass the root by reference
			return newsubtree;//returning the subtree of same tree by changing the root by reference
		}
	}  
}
class indexNotInRangeException extends Exception{
	indexNotInRangeException(String message){
		super(message);
	}
}