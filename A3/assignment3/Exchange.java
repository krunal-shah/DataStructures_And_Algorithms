import java.util.NoSuchElementException;

public class Exchange
{
	
	public Exchange(int i)
	{
		id = i;
		parent_element = null;
		children = null;
		mobiles = new MobilePhoneSet();
	}
	
	public int id;
	Exchange parent_element;
	ExchangeList children;
	MobilePhoneSet mobiles;

	public int id()
	{
		return id;
	}

	public void insert_child(Exchange b)
	{
		if(children==null)
		{
			children = new ExchangeList();
			children.insert(b);
		}
		else
		{
			children.insert(b);
		}
	}

	public Exchange parent()
	{
		return this.parent_element;
	}

	public void set_parent(Exchange a)
	{
		this.parent_element = a;
	}

	public int numChildren()
	{
		if(children!=null)
			return children.number_of_nodes();
		else
			return 0;
	}

	public Exchange child(int i)
	{
		if(children!=null)
			return (children.return_node(i));
		else
			throw new NoSuchElementException();
	}

	public boolean isRoot()
	{
		return (parent_element==null);
	}


	public RoutingMapTree subtree(int i)
	{
		RoutingMapTree ans = new RoutingMapTree(this.child(i));
		return ans;
	}

	public boolean isLeaf()
	{
		Myset.node cur = mobiles.set.start;
		while(cur!=null)
		{
			if(((MobilePhone)cur.data).exc == this)
			{
				
			}
			else
			{
				return false;
			}
			cur = cur.next;
		}
		return true;
	}

	public MobilePhoneSet residentSet()
	{
		return mobiles;
	}
}