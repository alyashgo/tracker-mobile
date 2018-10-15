public class ExchangeList extends Linkedlist<Exchange>{
	public String printId(){
		String out ="";
		Node<Exchange> temp = this.getHead();
		while(temp!=null){
			if(temp.getnext()==null){
				out =out+temp.getdata().getId();
			}
			else{
					out = out+temp.getdata().getId()+", ";
			}
			temp= temp.getnext();
		}
		return out;
	}
}