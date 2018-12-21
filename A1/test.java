import java.util.zip.DataFormatException;
//import java.util.zip.NumberFormatException;
import java.io.*;
public class test implements Runnable
{
	//Thread wrapper class
	char s1;
	String as;
	double start;
	public static int end_time=10;
	static stock st;
	static int end = 0;
	// Constructor
	public test(char s,double start)
	{
		s1=s;
		this.start=start;
		queue_front=null;
		queue_end=null;
		sell_front=null;
		sell_end=null;
		buy_front=null;
		buy_end=null;
		as=null;
		st = new stock(start);
		end =0;
	}
	public void set_string(String actionString)
	{
		this.as=actionString;
	}
	public void run()
	{
		if(s1=='o')  //Order Thread
		{
			double time = ((double)System.nanoTime()/1000000000) - start;;
			while((time<end_time+2) || end==0)
			{	
				if(as!=null)
				{
					try
					{
						object temp = new object(as);
						queue_member qu = new queue_member(temp);
						enqueue(qu);
						time = ((double)System.nanoTime()/1000000000) - start;
						System.out.println("Enquing"+temp.name+" at "+time);
						//System.out.println("Enqued at" + time);
						as=null;
						if(temp.time+temp.texp>end_time)
							end_time = temp.time + temp.texp;
						st.write_order(temp,true,time);			
					}
					catch(DataFormatException e)
					{
						as=null;
						st.write_order(null,false,1.0);
					}
					catch(NumberFormatException ne)
					{
						as=null;
						st.write_order(null,false,2.0);
					}
					catch(IndexOutOfBoundsException ib)
					{
						as=null;
						st.write_order(null,false,3.0);
					}
				}
				time = ((double)(System.nanoTime()/1000000000) - start);
				try
				{
					Thread.sleep(20);
				}
				catch(InterruptedException a){}
			}
			/*queue_member current=queue_front;
			while(current!=null)
			{
				System.out.println(current.data.name);
				current=current.next;
			}*/
		}
		
		else if(s1=='e')  //Exchange Thread
		{
			int profit;
			double time = ((double)System.nanoTime()/1000000000) - start;; 
			while((time<end_time+2) || end==0)
			{
				if(queue_front!=null)
				{
					/*System.out.println("Sell Stack");
					object current = sell_front;
					while(current!=null)
					{
						System.out.println(current.name);
						current=current.next;	
					} 
					System.out.println("Buy Stack");
					current = buy_front;
					while(current!=null)
					{
						System.out.println(current.name);
						current=current.next;	
					} 
					System.out.println("");*/
					object temp = dequeue().data;
					time = ((double)System.nanoTime()/1000000000) - start;
					System.out.println("Dequing"+temp.name+" at "+time);
					Exchange ex = new Exchange(start);
					if(time<=temp.time+temp.texp)
					{
						//System.out.println("if"+temp.name);
						if(temp.type.equals("buy"))
						{
							//System.out.println(temp.name);
							profit = ex.transaction(temp,sell_front,sell_end);
							stock.profit += profit;
							/*System.out.println("");
							System.out.println("");
							System.out.println(profit);
							System.out.println("");
							System.out.println("");*/
							if(profit==0)
							{
								insert_front(temp,'b');
							}
							else
							{
								if(temp.qty>0)
								{
									insert_front(temp,'b');
								}
								else
								{

								}
							}
						}
						else
						{
							//System.out.println(temp.name);
							profit = ex.transaction(temp,buy_front,buy_end);
							stock.profit += profit;
							if(profit==0)
							{
								insert_front(temp,'s');
							}
							else
							{
								if(temp.qty>0)
								{
									insert_front(temp,'s');
								}
								else
								{
									
								}
								//System.out.println(profit);
							}
						}
					}
					System.out.println("Sell Stack");
					object current1 = sell_front;
					while(current1!=null)
					{
						System.out.println(current1.name);
						current1=current1.next;	
					} 
					System.out.println("Buy Stack");
					current1 = buy_front;
					while(current1!=null)
					{
						System.out.println(current1.name);
						current1=current1.next;	
					}
					System.out.println("");
				}
				try
				{
					Thread.sleep(5);
				}
				catch(InterruptedException a){}
				time = ((double)System.nanoTime()/1000000000) - start;
			}
			stock.write_profit();
			//System.out.println("Stop dequeu "+time);
			/*System.out.println("Sell Stack");
			object current = sell_top;
			while(current!=null)
			{
				System.out.println(current.name);
				current=current.next;	
			} 
			System.out.println("Buy Stack");
			current = buy_front;
			while(current!=null)
			{
				System.out.println(current.name);
				current=current.next;	
			} */
		}
		else  //Cleanup Thread
		{
			double time = ((double)System.nanoTime()/1000000000) - start;
			while((time<end_time+2) || end==0)
			{
				//System.out.println("Entering at "+time);
				time = ((double)System.nanoTime()/1000000000) - start;
				object current = buy_front;
				while(current!=null)
				{
					time = ((double)System.nanoTime()/1000000000) - start;
					if(current.qty==0 || (time>(current.time+current.texp)))
					{
						if(current==buy_front)
						{
							if(buy_front==null)
							{

							}
							else if(buy_front == buy_end)
							{
								buy_end = null;
								buy_front = null;
								st.write_cleanup(current,time);
							}
							else
							{
								buy_front = current.next;
								buy_front.previous = null;
								st.write_cleanup(current,time);
							}
						}
						else if(current==buy_end)
						{
							if(buy_end==null)
							{

							} 
							else if(buy_end == buy_front)
							{
								buy_front=null;
								buy_end=null;
								st.write_cleanup(current,time);
							}
							else
							{
								buy_end = current.previous;
								buy_end.next = null;
								st.write_cleanup(current,time);
							}
						}
						else
						{
							current.previous.next = current.next;
							current.next.previous = current.previous;
							st.write_cleanup(current,time);
						}
						System.out.println("Cleaned "+current.name+" at "+time);
						System.out.println("Sell Stack");
						object current1 = sell_front;
						while(current1!=null)
						{
							System.out.println(current1.name);
							current1=current1.next;	
						} 
						System.out.println("Buy Stack");
						current1 = buy_front;
						while(current1!=null)
						{
							System.out.println(current1.name);
							current1=current1.next;	
						}
						System.out.println("");
						//System.out.println(current.time);
						//System.out.println(current.texp);
					}
					current = current.next;
				}
				current = sell_front;
				time = ((double)System.nanoTime()/1000000000) - start;
				while(current!=null)
				{
					time = ((double)System.nanoTime()/1000000000) - start;
					if(current.qty==0 || (time>(current.time+current.texp)))
					{
						if(current==sell_front)
						{
							if(sell_front==null)
							{

							}
							else if(sell_front == sell_end)
							{
								sell_end = null;
								sell_front = null;
								st.write_cleanup(current,time);
							}
							else
							{
								sell_front = current.next;
								sell_front.previous = null;
								st.write_cleanup(current,time);
							}
						}
						else if(current==sell_end)
						{
							if(sell_end==null)
							{

							} 
							else if(sell_end == sell_front)
							{
								sell_front=null;
								sell_end=null;
								st.write_cleanup(current,time);
							}
							else
							{
								sell_end = current.previous;
								sell_end.next = null;
								st.write_cleanup(current,time);
							}
						}
						else
						{
							current.previous.next = current.next;
							current.next.previous = current.previous;
							st.write_cleanup(current,time);
						}
						System.out.println("Cleaned "+current.name+" at "+time);
						System.out.println("Sell Stack");
						object current1 = sell_front;
						while(current1!=null)
						{
							System.out.println(current1.name);
							current1=current1.next;	
						} 
						System.out.println("Buy Stack");
						current1 = buy_front;
						while(current1!=null)
						{
							System.out.println(current1.name);
							current1=current1.next;	
						}
						System.out.println("");
						}
						current = current.next;
				}
				try
				{
					Thread.sleep(2);
				}
				catch(InterruptedException a){}
				time = ((double)System.nanoTime()/1000000000) - start;
			}
		}
	}

	// Order Information Object class
	public class object
	{
		public object(String actionString) throws DataFormatException,NumberFormatException,IndexOutOfBoundsException
		{
			String[] splited = actionString.split("\\t");
			if(splited[1].matches("[a-zA-Z]+")&&splited[3].matches("[a-zA-Z]+")&&splited[5].matches("[a-zA-Z]+")&&(splited[7].equals("T")||splited[7].equals("F")))//&&isInteger(splited[0])&&isInteger(splited[2])&&isInteger(splited[4])&&isInteger(splited[6])&&(splited[7]=="T"||splited[7]=="F"))
			{
				name=splited[1];
				type=splited[3];
				stock=splited[5];
				partial=splited[7].equals("F")?false:true;
				time=Integer.parseInt(splited[0]);
				texp=Integer.parseInt(splited[2]);
				qty=Integer.parseInt(splited[4]);
				price=Integer.parseInt(splited[6]);
				if(time<0 || texp<0 || qty<0 || price <0)
				{
					throw new NumberFormatException();
				}
				this.next = null;
				this.previous = null;
				transacted = 0;
			}
			else
			{
				//System.out.println("Darn you");
				throw new DataFormatException();
			}
		}
		public int time,texp,qty,price;
		public boolean partial;
		public String name,type,stock;
		public object next,previous;
		public int transacted;
	}

	// Just queue things
	static queue_member queue_front,queue_end;
	public class queue_member
	{
		object data;
		queue_member next;
		public queue_member(object temp)
		{
			data = temp;
			next = null;
		}
	}

	void enqueue(queue_member temp)
	{
		if(queue_front==null)
		{
			queue_front = temp;
			queue_end = temp;
		}
		else
		{
			queue_end.next = temp;
			queue_end = temp;
		}
	}

	queue_member dequeue()
	{
		if(queue_front==null)
		{
			System.out.println("Queue empty, can't deque");
			return null;

		}
		else if(queue_front==queue_end)
		{
			queue_member temp = queue_front;
			queue_end = null;
			queue_front = null;
			return temp;
		}
		else
		{
			queue_member temp = queue_front;
			queue_front = queue_front.next;
			return temp;
		}
	}

	// Buy and sell stacks
	static object buy_front, sell_front, buy_end, sell_end;
	//static int buy_size=0, sell_size=0;
	void insert_front(object temp, char ch)
	{
		if(ch=='b')
		{
			if(buy_front==null)
			{
				buy_front = temp;
				buy_end = temp;
			}
			else
			{
				temp.next = buy_front;
				buy_front.previous = temp;
				buy_front = temp;
			}
		}
		else
		{
			if(sell_front==null)
			{
				sell_front = temp;
				sell_end = temp;
			}
			else
			{
				temp.next = sell_front;
				sell_front.previous = temp;
				sell_front = temp;
			}
		}
	}

	/*object pop(char ch)
	{
		if(ch=='b')
		{
			if(buy_front==null)
			{
				System.out.println("Stack empty");
				return null;
			}
			else
			{
				object temp = buy_front;
				buy_front = buy_front.next;
				return temp;
			}
		}
		else
		{
			if(sell_top==null)
			{
				System.out.println("Stack empty");
				return null;
			}
			else
			{
				object temp = sell_top;
				sell_top = sell_top.next;
				return temp;
			}
		}
	}*/


	
}
