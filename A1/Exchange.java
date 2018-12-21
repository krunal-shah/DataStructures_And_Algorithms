import java.util.zip.DataFormatException;
import java.io.*;
public class Exchange
{
	double start;
	double time;
	public Exchange(double start)
	{
		this.start = start;
	}
	public int transaction(test.object temp, test.object front, test.object end)
	{
		int profit=0;
		test.object current=front;
		int price,min_price;
		if(front==null)
		{
			time = ((double)System.nanoTime()/1000000000) - start;
			stock.write_exchange(profit,temp,time);
			return 0;
		}
		int flag = 0;
		if(temp.type.equals("buy"))
		{
			//System.out.println("Enter");
			//price = front.price;
			test.object min=null;
			while(temp.qty>0)
			{
				min_price = Integer.MAX_VALUE;
				current = front;
				min = null;
				while(current!=null)
				{
					//System.out.println(".");
					if(current.price<min_price && current.transacted==0 && current.price<temp.price && current.stock.equals(temp.stock) && (temp.time<(current.time+current.texp)))
					{
						if(flag==0)
						{
							min = current;
							min_price = current.price;
						}
						else if(current.qty>temp.qty && current.partial )
						{
							min = current;
							min_price = current.price;
						}
					}
					if(current.qty>temp.qty && current.price<temp.price && current.partial && current.stock.equals(temp.stock) && (temp.time<(current.time+current.texp)))
					{
						flag = 1;
						min = current;
						min_price = current.price;
					}
					current = current.next;
				}
				if(min==null)
					break;
				if(min.qty==0 || (min.qty>temp.qty && min.partial==false) || (min.qty<temp.qty && temp.partial==false))
				{
					break;
				}
				//System.out.println("Min");
				//System.out.println(min.name);
				if(min.qty==temp.qty && (temp.price > min.price))
				{
					profit += temp.qty*(temp.price - min.price);
					min.transacted = temp.qty;
					temp.qty = 0;
					min.qty = 0;
				}
				else if(min.qty<temp.qty)
				{
					if(temp.partial && (temp.price > min.price))
					{
						profit += min.qty*(temp.price - min.price);
						temp.qty -= min.qty;
						min.transacted = min.qty;
						min.qty = 0;
					}
				}
				else
				{
					if(min.partial && (temp.price > min.price))
					{
						profit += temp.qty*(temp.price - min.price);
						min.transacted = temp.qty;
						min.qty -= temp.qty;
						temp.qty = 0;
					}
				}
				//min_price = min.price;
				//System.out.println(profit);
			}
		}
		else
		{
			//System.out.println("Enter");
			//price = front.price;
			int max_price = 0;
			test.object max=null;
			while(temp.qty>0)
			{
				current = front;
				max_price = 0;
				max = null;
				while(current!=null)
				{
					//System.out.println(".");
					if(current.price>max_price && current.transacted==0 && current.price>temp.price && current.stock.equals(temp.stock) && (temp.time<(current.time+current.texp)))
					{
						if(flag==0)
						{
							max = current;
							max_price = current.price;
						}
						else if(current.qty>temp.qty && current.partial)
						{
							max = current;
							max_price = current.price;
						}
					}
					if(current.qty>temp.qty && current.price>temp.price && current.partial && current.stock.equals(temp.stock) && (temp.time<(current.time+current.texp)))
					{
						flag = 1;
						max = current;
						max_price = current.price;
					}
					current = current.next;
				}
				if(max == null)
					break;
				if(max.qty==0 || (max.qty>temp.qty && max.partial==false) || (max.qty<temp.qty && temp.partial==false))
				{
					break;
				}
				//System.out.println("Max");
				//System.out.println(max.name+max.qty);
				if(max.qty==temp.qty && (max.price > temp.price))
				{
					profit += temp.qty*(max.price - temp.price);
					max.transacted = temp.qty;
					temp.qty = 0;
					max.qty = 0;
				}
				else if(max.qty<temp.qty)
				{
					if(temp.partial && (max.price > temp.price))
					{
						profit += max.qty*(max.price - temp.price);
						max.transacted = max.qty;
						temp.qty -= max.qty;
						max.qty = 0;
					}
				}
				else
				{
					if(max.partial && (max.price > temp.price))
					{
						profit += temp.qty*(max.price - temp.price);
						max.transacted = temp.qty;
						max.qty -= temp.qty;
						temp.qty = 0;
					}
				}
				//max_price = max.price;
				//System.out.println(profit);
			}
		}
		time = ((double)System.nanoTime()/1000000000) - start;
		stock.write_exchange(profit,temp,time);
		//System.out.println("");
		//System.out.println("");
		//System.out.println(profit);
		//System.out.println("");
		//System.out.println("");
		return profit;

	}
}
