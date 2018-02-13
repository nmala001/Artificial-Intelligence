import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class Node implements Comparable<Node> {
	
	int torch;
	int[] ctime;
	static int count;
	int[] side;
	int id;
	int p_id;
	int cost;
	String person_moved = "";
	
	//default constructor
	
	Node()
	{
		p_id=0;
		torch= 0;
		count++;
		this.id=count;
		this.cost=0;
		
		}
	
	// Parameterized constructor
	
	Node(int[] side, int torch, int[] constantTime, int p_id, int cost){
		
		count++;
		this.ctime = new int[side.length];
		this.side = new int[side.length];
		
		for(int a=0; a<side.length;a++){
			
			this.side[a] = this.side[a];
			this.ctime[a] = constantTime[a];
			
			}
		this.id = count;
		this.p_id = p_id;
		this.torch = torch;
		this.cost = cost;
		
		
	}
	
	//Display on screen
	
		
		
		public int compareTo(Node n) {
			// TODO Auto-generated method stub
			if(this.cost == n.cost)
				return 0;
			else if(this.cost > n.cost)
				return 1;
			else
				return -1;
			
		}

	
	//Main method starts here
	
	public static void main(String[] args){
         long startTime = System.currentTimeMillis();
		
		
		int[] root = {0,0,0,0};
		int torch = 0;
		int[] constantTime = new int[]{1,2,5,10};
		int p_id=0;
		int cost=0;
		Queue<Node> f = new LinkedList<Node>();
                Stack<Node> f1 = new Stack<Node>();
                PriorityQueue<Node> f2 = new PriorityQueue<Node>();
		
		Node node = new Node(root,torch,constantTime,p_id,cost);//check here
		for(int m=0; m<node.side.length; m++){
			node.side[m] = root[m];
			
		}
		f.add(node);
                f1.add(node);
                f2.add(node);

		bfssolution( node , f);
                //dfssolution(node,f1);
                //ucssolution(node,f2);

                long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(totalTime+ "  Milliseconds ");
		
		
		//System.out.println(node);
		}
	
	//Main methoid ends here
	
	
	
	public static void bfssolution( Node search, Queue<Node> f)
	{
		System.out.println("The Solution for the breadth first search is:\n");
		Node[] visited = new Node[5460000];
		
		int vcount = 0;
		Node res = new Node();
		res.cost=50000;
		
		
		while(!f.isEmpty()){
			Node node = f.remove();
			visited[vcount] = node;
			boolean flag = true;
			
			//Check weather destination or not
			 if(node.torch == 1)
			 {
				 for(int m=0;m<node.side.length;m++)
				 {
					 if(node.side[m] == 0){
						 flag = false;
					 }
				 }
				 
				 if(flag)
				 {
					 if(node.cost < res.cost)
				     res.cost =node.cost;
					 res.id=node.id;
					 res.p_id=node.p_id;
					 res.person_moved = node.person_moved;
					 
				 }
			 }
			 
			 
			 //End of Weather destination
			 
			 
			 if(node.torch == 1 && flag==false || node.torch == 0)
				 
			 {
				 //For one person crossing the tunnel
				 
				 int max = node.side.length;
				 int index = 0;
				 for(int i=0;i<max;i++){
					 
					 Node newnode = new Node(node.side, node.torch, node.ctime, node.id, 0);
					 
			// Taking the nodes to the new state		 
					 newnode.torch= node.torch;
					 for(int j=0;j<max;j++){
						 newnode.side[j]=node.side[j];
				     }
					 for(int j=0;j<max;j++){
						 newnode.ctime[j]=node.ctime[j];
					 }
					 
			 //end of taking the nodes
				 
				 
				 int highest=1;
				
				for(int k=i; k<max; k++)
				 {
					if( newnode.side[k] == newnode.torch && highest<2){
						
						if(newnode.side[k]==0)
						{
							node.side[k]=1;
						}
						if(newnode.torch ==1)
						{
							node.torch=0;
						}
						newnode.cost = node.cost + newnode.ctime[k];
		            	index = k;
		            	highest++;
					 
				 
					if (newnode.torch == 1){
		     		       newnode.person_moved = newnode.ctime[index] + " crosses the Tunnel in " + newnode.ctime[index] + " minutes. ";
		     		       }
		     		       if (newnode.torch == 0){
		     		       newnode.person_moved = newnode.ctime[index] + " comes back in " + newnode.ctime[index] + " minutes. ";
		     		       }
				 
				 
			 }
				 }
			 
				 boolean flag2 = true;
				 
				 for(int nodes=0; nodes<vcount; nodes++ ){
					 
					 if(Arrays.equals( newnode.side, visited[nodes].side) && newnode.torch == visited[nodes].torch ){
	        				
	        				
	        				flag2= false;
				 }
					 
		         }
				 
				 if(flag2){
					 
					 f.add(newnode);
		    			int temp = vcount+1;
		    			visited[temp] = newnode;
		    			vcount++;
					 
					 
					  }
				 
				 // For two persons crossing the tunnel
				 
				 
				 
					 }
				 
				 for(int i=0;i<max-1;i++){
						
						for (int j=i+1;j<max;j++){
							int highest=1;
							//create and copy
							Node newnode= new Node(node.side,node.torch,node.ctime,node.id,0);
							//copy node
							newnode.torch=node.torch;
							for(int p=0;p<max;p++){
								newnode.side[p]=node.side[p];
							}
							for(int p=0;p<max;p++){
								newnode.ctime[p]=node.ctime[p];
							}
							//endofcopying
							//int max=1;
							if(newnode.side[i]== newnode.torch && newnode.side[j] == newnode.torch && highest<3){
				            	if(newnode.side[i]==0)
				            	{
				            		node.side[i]=1;
				            	}
				            	
				            	if(newnode.side[j]==1)
				            	{
				            		node.side[j]=0;
				            	}
				            	if(newnode.torch ==0)
				            	{
				            			node.torch=1;
				            	}
				            	newnode.cost = node.cost + newnode.ctime[j];
				            	index = j;
				            	highest++;
				            	int person1 = i;
				            	int person2 = j;
				      	       if (newnode.torch == 1){
				      		       newnode.person_moved = newnode.ctime[person1] + " and " + newnode.ctime[person2] +" Crosses the Tunnel " + newnode.ctime[index] + " minutes. ";
				      		       }
				      		       if (newnode.torch == 0){
					      		       newnode.person_moved = newnode.ctime[person1] + " and " + newnode.ctime[person2] +" comes back in " + newnode.ctime[index] + " minutes. ";
				      		       }
										}
				        	// compare with every element in visited nodes array, if exist dont put in fringe
							boolean flag3 = true;
				        	for(int nodes=0; nodes<=vcount;nodes++){
				        		
				        			if(Arrays.equals(newnode.side ,visited[nodes].side) && newnode.torch == visited[nodes].torch){
				        				//fringe.add(newstate);
				        				flag3= false;

				        			}
				        		
				        	}
				        	if (flag3){
				        		f.add(newnode);
				    			int temp = vcount+1;
				    			visited[temp] = newnode;
				    			vcount++;
				    			//System.out.println(newnode.toString() + "lp");
				        	}
				        	// end ofcomparewith visited array
						//	fringe.add(newnode);
							
							
					}
						
					 }
			}
		}//endof while
					
		
		//start of output
					boolean completed= true;
					int id = res.id;
					int p_id = res.p_id;
					String output = " \n Total time taken is "+ res.cost + " minutes.";
					output = " \n "+ res.person_moved + output;
					
					System.out.println("root is destination " + res.cost +" "+ res.id + " parent_id "+ res.p_id + " " +res.person_moved);

					while(completed){
						for(int n=0;n<vcount;n++){
							if(p_id == visited[n].id ){
								output = " \n " + visited[n].person_moved + output;
								id = visited[n].id;
								p_id=visited[n].p_id;
								
								
							}
							if(id == 1){
								completed=false;
							}
						}
						
					}
					System.out.println(output + "");
					
					//end of output
			} 
		
		//start of DFS Solution		 
	
	public static void dfssolution( Node search, Stack<Node> f1)
	{
		System.out.println("The Solution implementing the  depth first search is:\n");
		Node[] visited = new Node[5460000];
		
		int vcount = 0;
		Node res = new Node();
		res.cost=50000;
		
		
		while(!f1.isEmpty()){
			Node node = f1.pop();
			visited[vcount] = node;
			boolean flag = true;
			
			//Check weather destination or not
			 if(node.torch == 1)
			 {
				 for(int m=0;m<node.side.length;m++)
				 {
					 if(node.side[m] == 0){
						 flag = false;
					 }
				 }
				 
				 if(flag)
				 {
					 if(node.cost < res.cost)
				     res.cost =node.cost;
					 res.id=node.id;
					 res.p_id=node.p_id;
					 res.person_moved = node.person_moved;
					 
				 }
			 }
			 
			 
			 //End of Weather destination
			 
			 
			 if(node.torch == 1 && flag==false || node.torch == 0)
				 
			 {
				 //For one person crossing the tunnel
				 
				 int max = node.side.length;
				 int index = 0;
				 for(int i=0;i<max;i++){
					 
					 Node newnode = new Node(node.side, node.torch, node.ctime, node.id, 0);
					 
			// Taking the nodes to the new state		 
					 newnode.torch= node.torch;
					 for(int j=0;j<max;j++){
						 newnode.side[j]=node.side[j];
				     }
					 for(int j=0;j<max;j++){
						 newnode.ctime[j]=node.ctime[j];
					 }
					 
			 //end of taking the nodes
				 
				 
				 int highest=1;
				
				for(int k=i; k<max; k++)
				 {
					if( newnode.side[k] == newnode.torch && highest<2){
						
						if(newnode.side[k]==0)
						{
							node.side[k]=1;
						}
						if(newnode.torch ==1)
						{
							node.torch=0;
						}
						newnode.cost = node.cost + newnode.ctime[k];
		            	index = k;
		            	highest++;
					 
				 
					if (newnode.torch == 1){
		     		       newnode.person_moved = newnode.ctime[index] + " crosses the Tunnel in " + newnode.ctime[index] + " minutes. ";
		     		       }
		     		       if (newnode.torch == 0){
		     		       newnode.person_moved = newnode.ctime[index] + " comes back in " + newnode.ctime[index] + " minutes. ";
		     		       }
				 
				 
			 }
				 }
			 
				 boolean flag2 = true;
				 
				 for(int nodes=0; nodes<vcount; nodes++ ){
					 
					 if(Arrays.equals( newnode.side, visited[nodes].side) && newnode.torch == visited[nodes].torch ){
	        				
	        				
	        				flag2= false;
				 }
					 
		         }
				 
				 if(flag2){
					 
					 f1.add(newnode);
		    			int temp = vcount+1;
		    			visited[temp] = newnode;
		    			vcount++;
					 
					 
					  }
				 
				 // For two persons crossing the tunnel
				 
				 
				 
					 }
				 
				 for(int i=0;i<max-1;i++){
						
						for (int j=i+1;j<max;j++){
							int highest=1;
							//create and copy
							Node newnode= new Node(node.side,node.torch,node.ctime,node.id,0);
							//copy node
							newnode.torch=node.torch;
							for(int p=0;p<max;p++){
								newnode.side[p]=node.side[p];
							}
							for(int p=0;p<max;p++){
								newnode.ctime[p]=node.ctime[p];
							}
							
							if(newnode.side[i]== newnode.torch && newnode.side[j] == newnode.torch && highest<3){
				            	if(newnode.side[i]==0)
				            	{
				            		node.side[i]=1;
				            	}
				            	
				            	if(newnode.side[j]==1)
				            	{
				            		node.side[j]=0;
				            	}
				            	if(newnode.torch ==0)
				            	{
				            			node.torch=1;
				            	}
				            	newnode.cost = node.cost + newnode.ctime[j];
				            	index = j;
				            	highest++;
				            	int person1 = i;
				            	int person2 = j;
				      	       if (newnode.torch == 1){
				      		       newnode.person_moved = newnode.ctime[person1] + " and " + newnode.ctime[person2] +" Crosses the Tunnel " + newnode.ctime[index] + " minutes. ";
				      		       }
				      		       if (newnode.torch == 0){
					      		       newnode.person_moved = newnode.ctime[person1] + " and " + newnode.ctime[person2] +" comes back in " + newnode.ctime[index] + " minutes. ";
				      		       }
										}
				        	// compare with every element in visited nodes array, if exist dont put in fringe
							boolean flag3 = true;
				        	for(int nodes=0; nodes<=vcount;nodes++){
				        		
				        			if(Arrays.equals(newnode.side ,visited[nodes].side) && newnode.torch == visited[nodes].torch){
				        				//fringe.add(newstate);
				        				flag3= false;

				        			}
				        		
				        	}
				        	if (flag3){
				        		f1.add(newnode);
				    			int temp = vcount+1;
				    			visited[temp] = newnode;
				    			vcount++;
				    			//System.out.println(newnode.toString() + "lp");
				        	}
				        	
							
							
					}
						
					 }
			}
		}//endof while
					
		
		//start of output
					boolean completed= true;
					int id = res.id;
					int p_id = res.p_id;
					String output = " \n Total time taken is "+ res.cost + " minutes.";
					output = " \n "+ res.person_moved + output;
					
					System.out.println("root is destination " + res.cost +" "+ res.id + " parent_id "+ res.p_id + " " +res.person_moved);

					while(completed){
						for(int n=0;n<vcount;n++){
							if(p_id == visited[n].id ){
								output = " \n " + visited[n].person_moved + output;
								id = visited[n].id;
								p_id=visited[n].p_id;
								
								
							}
							if(id == 1){
								completed=false;
							}
						}
						
					}
					System.out.println(output + "");
					
					//end of output
			} 
	
	
	public static void ucssolution( Node search, PriorityQueue<Node> f2)
	{
		System.out.println("The Solution Implementing the uniform cost search is:\n");
		Node[] visited = new Node[5460000];
		
		int vcount = 0;
		Node res = new Node();
		res.cost=50000;
		
		
		while(!f2.isEmpty()){
			Node node = f2.remove();
			visited[vcount] = node;
			boolean flag = true;
			
			//Check weather destination or not
			 if(node.torch == 1)
			 {
				 for(int m=0;m<node.side.length;m++)
				 {
					 if(node.side[m] == 0){
						 flag = false;
					 }
				 }
				 
				 if(flag)
				 {
					 if(node.cost < res.cost)
				     res.cost =node.cost;
					 res.id=node.id;
					 res.p_id=node.p_id;
					 res.person_moved = node.person_moved;
					 
				 }
			 }
			 
			 
			 //End of Weather destination
			 
			 
			 if(node.torch == 1 && flag==false || node.torch == 0)
				 
			 {
				 //For one person crossing the tunnel
				 
				 int max = node.side.length;
				 int index = 0;
				 for(int i=0;i<max;i++){
					 
					 Node newnode = new Node(node.side, node.torch, node.ctime, node.id, 0);
					 
			// Taking the nodes to the new state		 
					 newnode.torch= node.torch;
					 for(int j=0;j<max;j++){
						 newnode.side[j]=node.side[j];
				     }
					 for(int j=0;j<max;j++){
						 newnode.ctime[j]=node.ctime[j];
					 }
					 
			 //end of taking the nodes
				 
				 
				 int highest=1;
				
				for(int k=i; k<max; k++)
				 {
					if( newnode.side[k] == newnode.torch && highest<2){
						
						if(newnode.side[k]==0)
						{
							node.side[k]=1;
						}
						if(newnode.torch ==1)
						{
							node.torch=0;
						}
						newnode.cost = node.cost + newnode.ctime[k];
		            	index = k;
		            	highest++;
					 
				 
					if (newnode.torch == 1){
		     		       newnode.person_moved = newnode.ctime[index] + " crosses the Tunnel in " + newnode.ctime[index] + " minutes. ";
		     		       }
		     		       if (newnode.torch == 0){
		     		       newnode.person_moved = newnode.ctime[index] + " comes back in " + newnode.ctime[index] + " minutes. ";
		     		       }
				 
				 
			 }
				 }
			 
				 boolean flag2 = true;
				 
				 for(int nodes=0; nodes<vcount; nodes++ ){
					 
					 if(Arrays.equals( newnode.side, visited[nodes].side) && newnode.torch == visited[nodes].torch ){
	        				
	        				
	        				flag2= false;
				 }
					 
		         }
				 
				 if(flag2){
					 
					 f2.add(newnode);
		    			int temp = vcount+1;
		    			visited[temp] = newnode;
		    			vcount++;
					 
					 
					  }
				 
				 // For two persons crossing the tunnel
				 
				 
				 
					 }
				 
				 for(int i=0;i<max-1;i++){
						
						for (int j=i+1;j<max;j++){
							int highest=1;
							//create and copy
							Node newnode= new Node(node.side,node.torch,node.ctime,node.id,0);
							//copy node
							newnode.torch=node.torch;
							for(int p=0;p<max;p++){
								newnode.side[p]=node.side[p];
							}
							for(int p=0;p<max;p++){
								newnode.ctime[p]=node.ctime[p];
							}
							
							if(newnode.side[i]== newnode.torch && newnode.side[j] == newnode.torch && highest<3){
				            	if(newnode.side[i]==0)
				            	{
				            		node.side[i]=1;
				            	}
				            	
				            	if(newnode.side[j]==1)
				            	{
				            		node.side[j]=0;
				            	}
				            	if(newnode.torch ==0)
				            	{
				            			node.torch=1;
				            	}
				            	newnode.cost = node.cost + newnode.ctime[j];
				            	index = j;
				            	highest++;
				            	int person1 = i;
				            	int person2 = j;
				      	       if (newnode.torch == 1){
				      		       newnode.person_moved = newnode.ctime[person1] + " and " + newnode.ctime[person2] +" Crosses the Tunnel " + newnode.ctime[index] + " minutes. ";
				      		       }
				      		       if (newnode.torch == 0){
					      		       newnode.person_moved = newnode.ctime[person1] + " and " + newnode.ctime[person2] +" comes back in " + newnode.ctime[index] + " minutes. ";
				      		       }
										}
				        	// compare with every element in visited nodes array, if exist dont put in fringe
							boolean flag3 = true;
				        	for(int nodes=0; nodes<=vcount;nodes++){
				        		
				        			if(Arrays.equals(newnode.side ,visited[nodes].side) && newnode.torch == visited[nodes].torch){
				        				//fringe.add(newstate);
				        				flag3= false;

				        			}
				        		
				        	}
				        	if (flag3){
				        		f2.add(newnode);
				    			int temp = vcount+1;
				    			visited[temp] = newnode;
				    			vcount++;
				    			
				        	}
				        
							
							
					}
						
					 }
			}
		}//endof while
					
		
		//start of output
					boolean completed= true;
					int id = res.id;
					int p_id = res.p_id;
					String output = " \n Total time taken is "+ res.cost + " minutes.";
					output = " \n "+ res.person_moved + output;
					
					System.out.println("root is destination " + res.cost +" "+ res.id + " parent_id "+ res.p_id + " " +res.person_moved);

					while(completed){
						for(int n=0;n<vcount;n++){
							if(p_id == visited[n].id ){
								output = " \n " + visited[n].person_moved + output;
								id = visited[n].id;
								p_id=visited[n].p_id;
								
								
							}
							if(id == 1){
								completed=false;
							}
						}
						
					}
					System.out.println(output + "");
					
					//end of output
			} 
	
	}

		
		
		
		
		
		
	
	
	
	
	
	
	
	



		
		
		
		
		
		
	
	
	
	
	
	
	
	


