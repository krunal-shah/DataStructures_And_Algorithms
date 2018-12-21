import java.util.ArrayList;
import java.util.List;
import java.io.*;
public class stock
{
	//Perform I/O operation
	static double start;
	static float time;
	static FileOutputStream fs;
	static FileOutputStream fs1;
	static FileOutputStream fs2;
	static PrintStream or;
	static PrintStream exchng;
	static PrintStream cu;
	static int profit = 0;
	Thread Order;
	public stock(double start)
	{
		try
		{
			fs = new FileOutputStream("order.txt",false); 
			or = new PrintStream(fs);
		}
		catch(FileNotFoundException e){}
		try
		{
			fs1 = new FileOutputStream("exchange.txt",false); 
			exchng = new PrintStream(fs1);
		}
		catch(FileNotFoundException e){}
		try
		{
			fs2 = new FileOutputStream("cleanup.txt",false); 
			cu = new PrintStream(fs2);
		}
		catch(FileNotFoundException e){}
		this.start = start;
	}
	
	public static void write_order(test.object temp, boolean a, double t)
	{
		time = (float)t;
		if(a)
		{
			String str;
			if(temp.partial)
				str="T";
			else
				str="F";
			or.println(Float.toString(time)+"\t"+Integer.toString(temp.time)+"\t"+temp.name+"\t"+Integer.toString(temp.texp)+"\t"+temp.type+"\t"+Integer.toString(temp.qty)+"\t"+temp.stock+"\t"+Integer.toString(temp.price)+"\t"+str);
		}
		else
		{
			if(t==1.0)
			{
				or.println("EXCEPTION: EXPECTED STRING IS NOT ALPHABETICAL");
			}
			else if(t==2.0)
			{
				or.println("EXCEPTION: EXPECTED NUMBER IS NOT A VALID NUMBER");
			}
			else
			{
				or.println("EXCEPTION: NUMBER OF ARGUMENTS IS WRONG");
			}
		}
	}

	public static void write_profit()
	{
		exchng.println(profit);
	}


	public static void write_cleanup(test.object temp, double t)
	{
		String str;
		if(temp.partial)
			str="T";
		else
			str="F";
		time = (float)t;
		cu.println(Float.toString(time)+"\t"+Integer.toString(temp.time)+"\t"+temp.name+"\t"+Integer.toString(temp.texp)+"\t"+temp.type+"\t"+Integer.toString(temp.qty)+"\t"+temp.stock+"\t"+Integer.toString(temp.price)+"\t"+str);
		
	}

	public static void write_exchange(int profit,test.object temp,double t)
	{
		if(profit==0)
		{
			if(temp.type.equals("sell"))
			{
				//System.out.println(temp.name);
				String str;
				if(temp.partial)
					str="T";
				else
					str="F";
				time = (float)t;
				exchng.println("S"+"\t"+Float.toString(time)+"\t"+Integer.toString(temp.time)+"\t"+temp.name+"\t"+Integer.toString(temp.texp)+"\t"+temp.type+"\t"+Integer.toString(temp.qty)+"\t"+temp.stock+"\t"+Integer.toString(temp.price)+"\t"+str);
			}
			else
			{
				//System.out.println(temp.name);
				String str;
				if(temp.partial)
					str="T";
				else
					str="F";
				time = (float)t;
				exchng.println("P"+"\t"+Float.toString(time)+"\t"+Integer.toString(temp.time)+"\t"+temp.name+"\t"+Integer.toString(temp.texp)+"\t"+temp.type+"\t"+Integer.toString(temp.qty)+"\t"+temp.stock+"\t"+Integer.toString(temp.price)+"\t"+str);
			}
		}
		else
		{
			if(temp.type.equals("sell"))
			{
				test.object current = test.buy_end;
				System.out.println("Transact");
				int temp_transactions=0;
				while(current!=null)
				{
					if(current.transacted!=0)
					{
						System.out.println("Temp Name "+current.name);
						temp_transactions+=current.transacted;
						String str;
						if(current.partial)
							str="T";
						else
							str="F";
						time = (float)t;
						exchng.println("T"+"\t"+Float.toString(time)+"\t"+Integer.toString(current.transacted)+"\t"+Integer.toString(current.time)+"\t"+current.name+"\t"+Integer.toString(current.texp)+"\t"+current.type+"\t"+Integer.toString(current.qty)+"\t"+current.stock+"\t"+Integer.toString(current.price)+"\t"+str);
						current.transacted = 0;
					}
					current = current.previous;
				}
				System.out.println("Name "+temp.name);
				System.out.println("Transactions "+temp_transactions);
				String str;
				if(temp.partial)
					str="T";
				else
					str="F";
				time = (float)t;
				exchng.println("T"+"\t"+Float.toString(time)+"\t"+Integer.toString(temp_transactions)+"\t"+Integer.toString(temp.time)+"\t"+temp.name+"\t"+Integer.toString(temp.texp)+"\t"+temp.type+"\t"+Integer.toString(temp.qty)+"\t"+temp.stock+"\t"+Integer.toString(temp.price)+"\t"+str);
			}	
			else
			{
				test.object current = test.sell_end;
				System.out.println("Transact");
				int temp_transactions=0;
				while(current!=null)
				{
					if(current.transacted!=0)
					{
						System.out.println("Current name "+current.name);
						temp_transactions+=current.transacted;
						String str;
						if(current.partial)
							str="T";
						else
							str="F";
						time = (float)t;
						exchng.println("T"+"\t"+Float.toString(time)+"\t"+Integer.toString(current.transacted)+"\t"+Integer.toString(current.time)+"\t"+current.name+"\t"+Integer.toString(current.texp)+"\t"+current.type+"\t"+Integer.toString(current.qty)+"\t"+current.stock+"\t"+Integer.toString(current.price)+"\t"+str);
						current.transacted = 0;
					}
					current = current.previous;
				}
				System.out.println("Name "+temp.name);
				System.out.println("Transactions "+temp_transactions);
				String str;
				if(temp.partial)
					str="T";
				else
					str="F";
				time = (float)t;
				exchng.println("T"+"\t"+Float.toString(time)+"\t"+Integer.toString(temp_transactions)+"\t"+Integer.toString(temp.time)+"\t"+temp.name+"\t"+Integer.toString(temp.texp)+"\t"+temp.type+"\t"+Integer.toString(temp.qty)+"\t"+temp.stock+"\t"+Integer.toString(temp.price)+"\t"+str);
			}
			if(temp.qty>0)
			{
				String str;
				if(temp.partial)
					str="T";
				else
					str="F";
				if(temp.type.equals("sell"))
				{
					//System.out.println(temp.name);
					time = (float)t;
					exchng.println("S"+"\t"+Float.toString(time)+"\t"+Integer.toString(temp.time)+"\t"+temp.name+"\t"+Integer.toString(temp.texp)+"\t"+temp.type+"\t"+Integer.toString(temp.qty)+"\t"+temp.stock+"\t"+Integer.toString(temp.price)+"\t"+str);
				}
				else
				{
					//System.out.println(temp.name);
					time = (float)t;
					exchng.println("P"+"\t"+Float.toString(time)+"\t"+Integer.toString(temp.time)+"\t"+temp.name+"\t"+Integer.toString(temp.texp)+"\t"+temp.type+"\t"+Integer.toString(temp.qty)+"\t"+temp.stock+"\t"+Integer.toString(temp.price)+"\t"+str);
				}
			}	
		}
	}
}
