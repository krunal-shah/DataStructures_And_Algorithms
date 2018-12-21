import java.util.NoSuchElementException;

public class ExchangeList
{
	node front, end;

	class node
	{
		Exchange data;
		node next;
		public node(Exchange a)
		{
			data = a;
			next = null;
		}
	}

	public ExchangeList()
	{
		front = null;
		end = null;
	}

	public Exchange return_node(int i) throws NoSuchElementException
	{
		int counter = 0;
		node cur = front;
		while(cur!=null)
		{
			if(counter==i)
			{
				return cur.data;
			}
			counter++;
			cur = cur.next;
		}
		throw new NoSuchElementException();
	}

	public void insert(Exchange a)
	{
		node temp = new node(a);
		if(front == null)
		{
			front = temp;
			end = temp;
		}
		else
		{
			end.next = temp;
			end = temp;
		}
	}

	public int number_of_nodes()
	{
		int ans=0;
		node cur = front;
		while(cur!=null)
		{
			ans++;
			cur = cur.next;
		}
		return ans;
	}

}