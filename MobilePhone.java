
public class MobilePhone{
	int number;
	Boolean Status;
	Exchange baseStation; //storing the base station where mobile is added
	public MobilePhone(int num){
		number = num;
	}
	public void setBaseStation(Exchange a){ //setting base station to where mobile phone is added
		baseStation = a;
	}
	public Exchange getBaseStation(){
		return this.baseStation;
	}
	public int number(){
		return this.number;
	}
	public Boolean status(){
		return Status;
	}
	public void switchOn(){
		this.Status = true;
	}
	public void switchOff(){
		this.Status = false;
	}
	public int getID(){
		return this.number;
	}
	public Exchange location() throws PhoneSwitchedOffException{
		if(this.status()){
			return this.getBaseStation(); //returning the base station of phone if switched on
		}
		else{
			throw new PhoneSwitchedOffException("Error: Phone is switched off");
		} 
	}
}

class PhoneSwitchedOffException extends Exception{
	public PhoneSwitchedOffException(String message){
		super(message);
	}
}