public class RoutingMapTree{ 
	Exchange root;
	public RoutingMapTree(){
	 root=new Exchange(0);//creating root with exchange id zero
	 root.setParent(null);//setting parent to null
	}
	public RoutingMapTree(Exchange newroot){
	 root = newroot; //just setting the root by reference
	}
	

	public Exchange getRoot(){
		return this.root;
	}
	public void addExchange(Exchange parent, Exchange child) throws notAnExchangeException{
		if(this.containsExchange(parent)){
			child.setParent(parent); //adding using method of Exchange class if parent Exchange exists in the tree
		}
		else{
			throw new notAnExchangeException("Error: parent not found");
		}
	}
	public Boolean containsExchange(Exchange node){
		if(this.getRoot().getId()==node.getId()){ //returning root by comapring through id
			return true;
		}
		else{
			int i=0;
			while(i<this.getRoot().numChildren()){
				try{
					if(this.getRoot().subtree(i).containsExchange(node)){//calling recursion on every subtree
						return true;//returning true where ever found
					}
				}
				catch(indexNotInRangeException e){ //handling Exception of subtree method
					System.out.println(e.getMessage());
				}
				i+=1;	
			}
		}
		return false; //returning false at the end of the recursion of true not returned anywhere else
	}
	public void switchOn(MobilePhone a, Exchange b /*b is a base station*/) throws notAnExchangeException, phoneAlreadyOnException,phoneNotFoundException {
		if(this.containsExchange(b)){
			if(this.getRoot().residentSet.IsMember(a)){//switching on if Exchange exists
				if(this.getRoot().residentSet.findPhone(a).status()){//if phone is already switched on throw exception
					throw new phoneAlreadyOnException("phone already switched on");
				}
				else{ //else delete the phone weherever it is existing as switched off and then add it in the given base station 
					Exchange temp = this.getRoot();
					while(temp.numChildren()!=0){
						try{
							temp.residentSet.Delete(a);//deleting from resisdent set of root
						}
						catch(ObjectNotFoundException e){System.out.println("phone not found!!");}
						int i=0;
						while(i<temp.numChildren()){
							try{
								if(temp.child(i).residentSet.IsMember(a)){//finding the subtree in which mobile phone exists
									temp = temp.child(i);//moving down the tree
									break;
								}
							}
							catch(indexNotInRangeException e){//handling exception of child(i) method
								System.out.println(e.getMessage());
							}
							i+=1;
						}
					}
					try{
						temp.residentSet.Delete(a);//deleting from the resident set of base station
					}
					catch(ObjectNotFoundException e){System.out.println("phone not found");}//handling exception of delete method
					a.switchOn();//switching the phone on and adding it
					a.setBaseStation(b);
					b.residentSet.Insert(a);
					temp = b;
					while(temp.getParent()!=null){
						temp.getParent().residentSet.Insert(a);//updating the whole tree]
						temp= temp.getParent();
					}
				}
			}
			else{//else if phone doesn't exist just add it and update the tree
				a.switchOn();
				a.setBaseStation(b);
				b.residentSet.Insert(a);
				Exchange temp = b;
				while(temp.getParent()!=null){
					temp.getParent().residentSet.Insert(a);
					temp= temp.getParent();
				}
			}
		}
		else{//handling exception of contains exchange method
			throw new notAnExchangeException("Exchange not in the tree");
		}		
	}

	public void switchOff(int a) throws phoneNotFoundException{
		MobilePhone temp = new MobilePhone(a);//creating a new phone
		if(this.getRoot().residentSet.IsMember(temp)){//checking if phone exists
				this.getRoot().ResidentSet().findPhone(temp).switchOff();//finding the phone by id in tree and switching it off at root mobilePhoneSet as phones are added by reference so gets switched off everywhere
		}
		else{
			throw new phoneNotFoundException("phone not found!");
		}
	}
	public Exchange findExchange(int uid) throws notAnExchangeException{//method to find exchange with uid
		Exchange node = new Exchange(uid);
		Exchange temp = this.getRoot();
		if(this.containsExchange(node)){
			 if(temp.getId( )== uid){//return root if id matched
			 	return temp;
			 }
			 else{
			 	int i =0;
			 	while(i<temp.numChildren()){
			 		try{
			 			if(temp.subtree(i).containsExchange(node)){//calling recursion on subtree which contains the exchange
			 			temp = temp.subtree(i).findExchange(uid);
			 			}
			 			else{
			 				i+=1;
			 			}
			 		}
			 		catch(indexNotInRangeException e){
			 			System.out.println(e.getMessage());
			 		}
			 	}
			 }  
				
			
		}
		else{
			throw new notAnExchangeException("Exchange not found");
		}
		return temp;

	}
	public Exchange findPhone(MobilePhone m) throws phoneNotFoundException{
		Exchange temp=null;
		if(this.getRoot().ResidentSet().IsMember(m)){
			try{
				temp = m.location();
			}
			catch(PhoneSwitchedOffException e){	
				System.out.println(e.getMessage());
			}
		}
		else{
			throw new phoneNotFoundException("phone not found in network");
		}
		return temp;
	}
	public Exchange lowestRouter(Exchange a, Exchange b){
		Exchange temp = a;
		RoutingMapTree subtree = new RoutingMapTree(a);
		while(!(subtree.containsExchange(b))){
			temp = temp.getParent();
			subtree = new RoutingMapTree(temp);
		}
		return temp;
	}
	public ExchangeList routeCall(MobilePhone a, MobilePhone b) throws phoneNotFoundException{
		ExchangeList callPath = new ExchangeList();
		if(this.getRoot().ResidentSet().IsMember(a) && this.getRoot().ResidentSet().IsMember(b)){
			try{
				Exchange baseStationA = a.location();
				Exchange baseStationB = b.location();
				Exchange lowestRouter = this.lowestRouter(baseStationA, baseStationB);
				Exchange temp = baseStationA;
				while(temp.getId()!= lowestRouter.getId()){
					callPath.add(temp);
					temp = temp.getParent();
				}
				callPath.add(temp);
				while(temp.getId()!=baseStationB.getId()){
					int i=0;
					while(i<temp.numChildren()){
						if(temp.subtree(i).containsExchange(baseStationB)){
							temp = temp.child(i);
							break;
						}
						else{
							i+=1;
						}
					}
					callPath.add(temp);
				}
			}
			catch( Exception e){
				int id = a.getID();
				if(!b.status()){
					id = b.getID();
				}
				System.out.println("Error - Mobile phone with identifier " +id+ " is currently switched off");
			}

		}
		else{ 
			throw new phoneNotFoundException("phone not found in network");
		}
		return callPath;
	}
	public void movePhone(MobilePhone a, Exchange b)throws notLeafException, PhoneSwitchedOffException{
		if(b.isLeaf()){
			if(a.status()){
				try{
					a.switchOff();
					this.switchOn(a,b);
				}
				catch(Exception e){
					System.out.println(e.getMessage());
				}
			}
			else{
				throw new PhoneSwitchedOffException("Error - Mobile phone with identifier " +a.getID()+ " is currently switched off");
			}

		}
		else{
			throw new notLeafException("given exchange is not a baseStation");
		}

	}


	public String performAction(String actionMessage){
		String[] input  = actionMessage.split(" ");//slpitting input string into 2 or 3 parts
		String out ="";
		if(input[0].equals("addExchange")){
			int parentId = Integer.parseInt(input[1]);
			int childId = Integer.parseInt(input[2]);
			Exchange child = new Exchange(childId);
			try{
				Exchange parent = this.findExchange(parentId);
				this.addExchange(parent, child);
				out = "";
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			} 
		}
		else if(input[0].equals("switchOnMobile")){
			int exchnageId = Integer.parseInt(input[2]);
			int mobileId = Integer.parseInt(input[1]);
			try{
				Exchange baseStation = this.findExchange(exchnageId);
				MobilePhone a = new MobilePhone(mobileId);
				this.switchOn(a,baseStation);
				out ="";
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		else if(input[0].equals("switchOffMobile")){
			int mobileId = Integer.parseInt(input[1]);
			try{
				this.switchOff(mobileId);
				out ="";
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		else if(input[0].equals("queryNthChild")){
			int parentId = Integer.parseInt(input[1]);
			int childnumber = Integer.parseInt(input[2]);
			try{
				Exchange parent = this.findExchange(parentId);
				Exchange child = parent.child(childnumber);
				out ="queryNthChild "+input[1]+" "+input[2]+": "+child.getId();
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		else if(input[0].equals("queryMobilePhoneSet")){
			int exchnageId = Integer.parseInt(input[1]);
			try{
				Exchange node = this.findExchange(exchnageId);
				out = "queryMobilePhoneSet "+input[1]+": ";
				out = out + node.ResidentSet().printId();
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		else if(input[0].equals("queryFindPhone") || input[0].equals("findPhone")){
			int id = Integer.parseInt(input[1]);
			MobilePhone m = new MobilePhone(id);
			if(this.getRoot().ResidentSet().IsMember(m)){
				try{
					MobilePhone temp = this.getRoot().ResidentSet().findPhone(m);
					if(temp.status()){
						try{
							Exchange baseStation = this.findPhone(temp);
							out = "queryFindPhone "+id+": "+baseStation.getId();
						}
						catch(Exception e){
							System.out.println(e.getMessage());
						}
					}
					else{
						out = "queryFindPhone "+id+"Error - Mobile phone with identifier " +id+ " is currently switched off";
					}
				}
				catch(Exception e){}
			}
			else{
				out = "queryFindPhone "+id+": Error - No mobile phone with identifier "+id+" found in the network";
			}

		}
		else if(input[0].equals("queryLowestRouter") || input[0].equals("lowestRouter")){
			int id_a = Integer.parseInt(input[1]);
			int id_b = Integer.parseInt(input[2]);
			try{
				Exchange a = this.findExchange(id_a);
				Exchange b = this.findExchange(id_b);
				Exchange lowestrouter = this.lowestRouter(a,b);
				out = "queryLowestRouter "+id_a+" "+id_b+": "+lowestrouter.getId();
			}
			catch(Exception e){
				out = e.getMessage();
			}
		}
		else if(input[0].equals("queryFindCallPath") || input[0].equals("findCallPath")){
			int id_a = Integer.parseInt(input[1]);
			int id_b = Integer.parseInt(input[2]);
			MobilePhone a = new MobilePhone(id_a);
			MobilePhone b = new MobilePhone(id_b);
			if(this.getRoot().ResidentSet().IsMember(a) && this.getRoot().ResidentSet().IsMember(b)){
				try{
					a = this.getRoot().ResidentSet().findPhone(a);
					b = this.getRoot().ResidentSet().findPhone(b);
				}
				catch(Exception e){}
				if(a.status()){
					if(b.status()){
						try{
							ExchangeList callPath = this.routeCall(a,b);
							out = "queryFindCallPath "+id_a+" "+id_b+": "+callPath.printId();
						}
						catch(Exception e){}
					}
					else{
						out = "queryFindCallPath "+id_a+" "+id_b+": "+"Error - Mobile phone with identifier " +id_b+ " is currently switched off";
					}
				}
				else{
					out = "queryFindCallPath "+id_a+" "+id_b+": "+"Error - Mobile phone with identifier " +id_a+ " is currently switched off";
				}
			}
			else if(!(this.getRoot().ResidentSet().IsMember(a))){
				out = "queryFindCallPath "+id_a+" "+id_b+": "+"Error - No mobile phone with identifier "+id_a+" found in the network";
			}
			else{
				out = "queryFindCallPath "+id_a+" "+id_b+": "+"Error - No mobile phone with identifier "+id_b+" found in the network";
			}			 
		}
		else if(input[0].equals("movePhone")){
			int phoneId = Integer.parseInt(input[1]);
			int exchangeId = Integer.parseInt(input[2]);
			MobilePhone m = new MobilePhone(phoneId);
			if(this.getRoot().ResidentSet().IsMember(m)){
				try{
					m = this.getRoot().ResidentSet().findPhone(m);
				}
				catch(Exception e){}
				if(m.status()){
					Exchange temp = new Exchange(exchangeId);
					if(this.containsExchange(temp)){
						try{
							temp = this.findExchange(exchangeId);
							this.movePhone(m,temp);
							out = "";
						}
						catch(Exception e){}
					}
					else{
						out = "movePhone "+phoneId+": Error - No exchange with identifier "+exchangeId+" found in the network";
					}
				}
				else{
					out  =  "movePhone "+phoneId+"Error - Mobile phone with identifier " +phoneId+ " is currently switched off";
				}

			}
			else{
				out = "movePhone "+phoneId+": Error - No mobile phone with identifier "+phoneId+" found in the network";
			}

		}
		return out;
	}
}
class phoneAlreadyOnException extends Exception{
	phoneAlreadyOnException(String  message ){
		super(message);
	}
}
class notAnExchangeException extends Exception{
	notAnExchangeException(String message ){
		super(message);
	}
}
class phoneNotFoundException extends Exception{
	phoneNotFoundException(String message ){
		super(message);
	}
}
class PhoneSwitchedOffException extends Exception{
	public PhoneSwitchedOffException(String message){
		super(message);
	}
}
class notLeafException extends Exception{
	notLeafException(String message ){
		super(message);
	}
}
