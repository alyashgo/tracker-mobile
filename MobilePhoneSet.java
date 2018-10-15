public class MobilePhoneSet extends Myset<MobilePhone>{
	public Boolean IsMember(MobilePhone a){ //overriding the Ismember of set by comapring through Id's of two phones rather than references
		Node<MobilePhone> temp = this.set.getHead();
		while(temp!=null){
			if(temp.getdata().getID()==a.getID()){
				return true;
			}
			else{
				temp = temp.getnext();
			}
		}
		return false;
	}
	public void Delete(MobilePhone a) throws ObjectNotFoundException{ //overriding the set delete functon by comapring through Id's
		if(this.IsMember(a)==false){
			throw new ObjectNotFoundException("phone Not Found");
		}
		else{
			Node<MobilePhone> temp = this.set.getHead();
			if(temp.getdata().getID()==a.getID()){
				set.setHead(temp.getnext());
				return;
			}
			else{
				while(temp.getnext()!=null){
				if(temp.getnext().getdata().getID()==a.getID()){
					Node<MobilePhone> temp1;
					temp1=temp.getnext().getnext();
					temp.setnext(temp1);
					return;
				}
				else{
					temp=temp.getnext();
				}
			}
		}
	}
}
	public MobilePhoneSet Union(MobilePhoneSet a){ //overriding union of set by comparing through Id's
		MobilePhoneSet unionset = new MobilePhoneSet();
		Node<MobilePhone> temp = this.set.getHead(); 
		while(temp!=null){
			unionset.Insert(temp.getdata());
			temp = temp.getnext();
		}

		temp = a.set.getHead();
		while(temp!=null){
			if(unionset.IsMember(temp.getdata())==false){
				unionset.Insert(temp.getdata());
			}
			temp = temp.getnext();
		}
		return unionset;
	}
	public MobilePhone findPhone(MobilePhone a) throws phoneNotFoundException {
			Node<MobilePhone> temp = this.set.getHead();
			while(temp!=null){
				if(temp.getdata().getID()==a.getID()){ //returnig the Exchnage where phone exists in Linked list by comparison through Id's
					return temp.getdata();
				}
				else{
					temp = temp.getnext(); //else reaching to the node where phone exists
				}
			}
			throw new phoneNotFoundException("phone doesn't exist");
	}
	public String printId(){ //functioin to handle output of action message to print id of all phones in a mobibleSet
		String out ="";
		Node<MobilePhone> temp = this.set.getHead();
		while(temp!=null){
			if(temp.getnext()==null && temp.getdata().status()){
				out =out+temp.getdata().getID();
			}
			else{
				if(temp.getdata().status()){
					out = out+temp.getdata().getID()+", ";
				}
			}
			temp= temp.getnext();
		}
		return out;
	}
}
