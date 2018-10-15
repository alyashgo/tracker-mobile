public class Node<Object>
	{
		//Declare class variables
		private Node<Object> next=null;
		private Object data;
		
		public Node(Object dat){
			this.data = dat;
		}
		
		public Object getdata(){/*getter for data attribute*/
			return this.data;
		}

		public Node<Object> getnext(){/*getter for next node*/
			return this.next;
		}

		public void setnext(Node<Object> nextnode){/*setter for next node*/
			next = nextnode;
		}
	}