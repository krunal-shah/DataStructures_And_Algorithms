import java.util.NoSuchElementException;

class Myset
{
	node start;
	node end;
	class node
	{
		Object data;
		node next;
		public node(Object data)
		{
			this.data = data;
			this.next = null;
		}
	}

	public Myset()
	{
		start = null;
	}

	public int num()
	{
		node cur = start;
		int ans = 1;
		while(cur!=null)
		{
			ans++;
			cur = cur.next;
		}
		return ans;
	}

	public void Insert(Object o)
	{
		if(start == null)
		{
			node temp = new node(o);
			start = temp;
			end = temp;
		}
		else
		{
			node temp = new node(o);
			end.next = temp;
			end = temp;
		}
	}

	public void Delete(Object o) throws NoSuchElementException
	{
		if(start == null)
		{
			throw new NoSuchElementException();
		}
		else
		{
			node cur = start;
			if(start.data == o)
			{
				start = start.next;
			}
			else
			{
				while(cur.next!=null && cur.next.data != o)
				{
					cur = cur.next;
				}
				if(cur.next!=null)
					cur.next = cur.next.next;
				else
					throw new NoSuchElementException();
			}
		}
	}

	public boolean isEmpty()
	{
		return (start==null);
	}

	public boolean isMember(Object o)
	{
		node cur = start;
		while(cur != null)
		{
			if(cur.data == o)
				return true;
			cur = cur.next;
		}
		return false;
	}

	public Myset Union(Myset a)
	{
		Myset ans = new Myset();
		node cur = a.start;
		while(cur != null)
		{
			if(!ans.isMember(cur.data))
			{
				ans.Insert(cur.data);
			}
			cur = cur.next;
		}
		cur = this.start;
		while(cur != null)
		{
			if(!ans.isMember(cur.data))
			{
				ans.Insert(cur.data);
			}
			cur = cur.next;
		}
		return ans;
	}

	public Myset Intersection(Myset a)
	{
		Myset ans = new Myset();
		node cur_a = a.start;
		node cur_this = this.start;
		while(cur_a != null)
		{
			cur_this = this.start;
			while(cur_this != null)
			{
				if(cur_this.data == cur_a.data)
				{
					ans.Insert(cur_this.data);	
				}
				cur_this = cur_this.next;
			}
			cur_a = cur_a.next;
		}
		return ans;
	}

	/*public static void main(String[] args)
	{
		Myset a = new Myset();
		Myset b = new Myset();
		for(int i=0; i<10; i++)
			a.Insert(i);
		for(int i=5; i<15; i++)
			b.Insert(i);
		a.Delete(9);
		Myset c = a.Intersection(b);
		node cur = a.start;
		while(cur!=null)
		{
			System.out.println(cur.data);
			cur = cur.next;
		}
		cur = b.start;
		while(cur!=null)
		{
			System.out.println(cur.data);
			cur = cur.next;
		}
		cur = c.start;
		while(cur!=null)
		{
			System.out.println(cur.data);
			cur = cur.next;
		}
	}*/

}