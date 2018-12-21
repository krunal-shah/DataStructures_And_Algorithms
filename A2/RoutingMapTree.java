import java.util.NoSuchElementException;

public class RoutingMapTree
{
	Exchange root;
	
	public RoutingMapTree() 
	{
		root = new Exchange(0);
	}

	public RoutingMapTree(Exchange a)
	{
		root = a;
	}

	public boolean containsNode(Exchange a)
	{
		Exchange cur = root;
		if(root==a)
		{
			return true;
		}
		for(int i=0; i < cur.numChildren(); i++)
		{
			RoutingMapTree sub = cur.subtree(i);
			if(sub.containsNode(a))
				return true;
		}
		return false;
	}

	public void switchOn(MobilePhone a, Exchange b)
	{
		if(!a.status())
		{
			a.switchOn();
			a.exc = b;
			Exchange cur = b;
			if(root.mobiles.contains_mobile(a.number()))
			{
				while(cur!=root.parent())
				{
					MobilePhone temp = cur.mobiles.return_mobile_by_mobileid(a.number());
					temp.switchOn();
					cur = cur.parent();
				}
			}
			else
			{
				while(cur!=root.parent())
				{
					cur.mobiles.set.Insert(a);
					cur = cur.parent();
				}
			}
		}
		else
		{
			
		}
	}

	public void switchOff(MobilePhone a) throws NoSuchElementException
	{
		if(a.status())
		{
			a.switchOff();
			Exchange cur = return_exchange_by_phoneid(a.number());
			while(cur!=root.parent())
			{
				MobilePhone temp = cur.mobiles.return_mobile_by_mobileid(a.number());
				temp.switchOff();
				cur = cur.parent();
			}
		}
		else
		{

		}
	}

	public boolean contains_exchangeid(int a)
	{
		Exchange cur = root;
		if(root.id == a)
		{
			return true;
		}
		for(int i=0; i < cur.numChildren(); i++)
		{
			RoutingMapTree sub = cur.subtree(i);
			if(sub.contains_exchangeid(a))
				return true;
		}
		return false;
	}

	public Exchange return_exchange_by_exchangeid(int a) throws NoSuchElementException
	{
		if(!contains_exchangeid(a))
			throw new NoSuchElementException();
		else
		{
			Exchange cur = root;
			if(root.id==a)
			{
				return root;
			}
			for(int i=0; i < cur.numChildren(); i++)
			{
				RoutingMapTree sub = cur.subtree(i);
				if(sub.contains_exchangeid(a))
				{
					return sub.return_exchange_by_exchangeid(a);
				}
			}
			throw new NoSuchElementException();
		}
	}

	public boolean contains_phoneid(int a)
	{
		return root.mobiles.contains_mobile(a);
	}

	public Exchange return_exchange_by_phoneid(int a) throws NoSuchElementException
	{
		if(!contains_phoneid(a))
		{
			throw new NoSuchElementException();
		}
		else
		{
			Exchange cur = root;
			if(root.mobiles.contains_mobile(a) && root.children==null)
				return root;
			for(int i=0; i < cur.numChildren(); i++)
			{
				RoutingMapTree sub = cur.subtree(i);
				if(sub.contains_phoneid(a))
				{
					return sub.return_exchange_by_phoneid(a);
				}
			}
			throw new NoSuchElementException();
		}
	}

	void print_all_mobiles(Exchange a)
	{
		a.mobiles.print_mobiles();
	}














	// ASSIGNMENT 3 METHODS

	public Exchange findPhone(MobilePhone a) throws NoSuchElementException
	{
		if(a.status())
		{
			try
			{
				return a.location();
			}
			catch(NoSuchElementException q)
			{
				throw new NoSuchElementException();
			}
		}
		else
		{
			throw new NoSuchElementException(); 
		}
	}

	public Exchange lowestRouter(Exchange a, Exchange b) throws NoSuchElementException
	{
		if(a == b)
		{
			return a;
		}
		else
		{
			if(contains_exchangeid(a.id) && contains_exchangeid(b.id))
			{
				for(int i=0; i<root.numChildren(); i++)
				{
					RoutingMapTree sub = new RoutingMapTree(root.child(i));
					if(sub.contains_exchangeid(a.id) && sub.contains_exchangeid(b.id))
					{
						return sub.lowestRouter(a,b);
					}
				}
				return root;
			}
		}
		throw new NoSuchElementException();
	}

	public ExchangeList routeCall(MobilePhone a, MobilePhone b) throws NoSuchElementException
	{
		try
		{
			Exchange a_exc = a.location();
			try
			{
				Exchange b_exc = b.location();
				Exchange cur = a_exc;
				ExchangeList ans = new ExchangeList();
				while(cur != lowestRouter(a_exc,b_exc))
				{
					ans.insert(cur);
					cur = cur.parent();
				}
				while(cur != b_exc)
				{
					ans.insert(cur);
					for(int i=0; i<cur.numChildren(); i++)
					{
						RoutingMapTree sub = new RoutingMapTree(cur.child(i));
						if(sub.contains_exchangeid(b_exc.id))
						{
							cur = cur.child(i);
							break;
						}
					}
				}
				ans.insert(b_exc);
				return ans;
			}
			catch(NoSuchElementException z)
			{
				System.out.print("Error: Mobile "+b.number()+" is turned off!");
			}
		}
		catch(NoSuchElementException q)
		{
			System.out.print("Error: Mobile "+a.number()+" is turned off!");
		}
		throw new NoSuchElementException();
	}

	public void movePhone(MobilePhone a, Exchange b) throws NoSuchElementException
	{
		if(contains_exchangeid(b.id))
		{
			Exchange a_exc = a.location();
			Exchange cur = a_exc;
			while(cur != lowestRouter(a_exc, b))
			{
				try
				{
					cur.mobiles.set.Delete(a);
				}
				catch(NoSuchElementException e)
				{
					throw new NoSuchElementException();
				}
				cur = cur.parent();
			}
			cur = b;
			while( cur != lowestRouter(a_exc, b))
			{
				cur.mobiles.set.Insert(a);	
				cur = cur.parent();			
			}
			a.exc = b;
		}
		else
		{
			throw new NoSuchElementException();
		}
	}













	public void performAction(String actionMessage) 
	{
		String arg[] = actionMessage.split("\\s+");
		/*for(int i=0; i<arg.length; i++)
			System.out.println(arg[i]);*/
		if(arg[0].equals("addExchange"))
		{
			//System.out.print(actionMessage+": ");
			int a_id = Integer.parseInt(arg[1]);
			int b_id = Integer.parseInt(arg[2]);
			if(!contains_exchangeid(b_id))
			{
				Exchange b = new Exchange(b_id);
				try
				{
					Exchange a = return_exchange_by_exchangeid(a_id);
					a.insert_child(b);
					b.set_parent(a);
				}
				catch(NoSuchElementException a)
				{
					System.out.print(actionMessage+": ");
					System.out.print("Error: Exchange "+a_id+" does not exist!");
					System.out.println("");
				}
				//System.out.println("");
			}
			else
			{
				System.out.print(actionMessage+": ");
				System.out.print("Error: Exchange "+b_id+" already exists!");
				System.out.println("");
			}
		}
		else if(arg[0].equals("switchOnMobile"))
		{
			//System.out.print(actionMessage+": ");
			int a_id = Integer.parseInt(arg[1]);
			int b_id = Integer.parseInt(arg[2]);
			try
			{
				Exchange b = return_exchange_by_exchangeid(b_id);
				if(b.isLeaf())
				{
					if(b.mobiles.contains_mobile(a_id))
					{
						try
						{
							MobilePhone a = b.mobiles.return_mobile_by_mobileid(a_id);
							if(a.status())
							{
								System.out.print(actionMessage+": ");
								System.out.print("Error: Mobile already switched on!");
								System.out.println("");
							}
							else
							{
								switchOn(a,b);
							}
						}
						catch(NoSuchElementException q)
						{
							System.out.print(actionMessage+": ");
							System.out.print("Data not updated properly! Bug!");
							System.out.println("");
						}
					}
					else
					{
						if(root.mobiles.contains_mobile(a_id))
						{
							System.out.print(actionMessage+": ");
							System.out.print("Error: Mobile Phone already present in another Exchange");
							System.out.println("");
						} 
						else
						{
							MobilePhone a = new MobilePhone(a_id);
							switchOn(a,b);
						}
					}
				}
				else
				{
					System.out.print(actionMessage+": ");
					System.out.print("Error: Exchange is not a level 0 Exchange");
					System.out.println("");
				}
			}
			catch(NoSuchElementException a)
			{
				System.out.print(actionMessage+": ");
				System.out.print("Error: Exchange "+b_id+" does not exist!");
				System.out.println("");
			}
			//System.out.println("");
		}
		else if(arg[0].equals("switchOffMobile"))
		{
			//System.out.print(actionMessage+": ");
			int a_id = Integer.parseInt(arg[1]);
			try
			{
				Exchange b = return_exchange_by_phoneid(a_id);
				if(b.mobiles.contains_mobile(a_id))
				{
					try
					{
						MobilePhone a = b.mobiles.return_mobile_by_mobileid(a_id);
						switchOff(a);
					}
					catch(NoSuchElementException q)
					{
						System.out.print(actionMessage+": ");
						System.out.print("Data not updated properly! Bug!");
						System.out.println("");
					}
				}
			}
			catch(NoSuchElementException a)
			{
				System.out.print(actionMessage+": ");
				System.out.print("Error: Phone "+a_id+" does not exist!");
				System.out.println("");
			}
			//System.out.println("");
		}
		else if(arg[0].equals("queryNthChild"))
		{
			System.out.print(actionMessage+": ");
			int a_id = Integer.parseInt(arg[1]);
			int i = Integer.parseInt(arg[2]);
			try
			{
				Exchange a = return_exchange_by_exchangeid(a_id);
				try
				{
					System.out.print(a.child(i).id());
				}
				catch(NoSuchElementException z)
				{
					i = i+1;
					System.out.print("Error: Exchange "+a_id+" does not have "+i+" children");
				}
			}
			catch(NoSuchElementException g)
			{
				System.out.print("Error: Exchange "+a_id+" does not exist!");
			}
			System.out.println("");
		}
		else if(arg[0].equals("queryMobilePhoneSet"))
		{
			System.out.print(actionMessage+": ");
			int a_id = Integer.parseInt(arg[1]);
			try
			{
				Exchange a = return_exchange_by_exchangeid(a_id);
				print_all_mobiles(a);
			}
			catch(NoSuchElementException g)
			{
				System.out.print("Error: Exchange "+a_id+" does not exist!");
			}
			System.out.println("");
		}




		else if(arg[0].equals("queryFindPhone"))
		{
			System.out.print(actionMessage+": ");
			int a_id = Integer.parseInt(arg[1]);
			try
			{
				Exchange a_exc = return_exchange_by_phoneid(a_id);
				try
				{
					MobilePhone a = a_exc.mobiles.return_mobile_by_mobileid(a_id);
					System.out.print(findPhone(a).id);
				}
				catch(NoSuchElementException w)
				{
					System.out.print("Error: Mobile "+a_id+" does not exist!");
				}
			}
			catch(NoSuchElementException q)
			{
				System.out.print("Error: Mobile "+a_id+" does not exist!");
			}
			System.out.println("");
		}
		else if(arg[0].equals("queryLowestRouter"))
		{
			System.out.print(actionMessage+": ");
			int a_id = Integer.parseInt(arg[1]);
			int b_id = Integer.parseInt(arg[2]);
			try 
			{
				Exchange a = return_exchange_by_exchangeid(a_id);
				try
				{
					Exchange b = return_exchange_by_exchangeid(b_id);
					System.out.print(lowestRouter(a,b).id);
				}
				catch(NoSuchElementException z)
				{
					System.out.print("Error: Exchange "+b_id+" does not exist!");
				}
			}	
			catch(NoSuchElementException q)
			{
				System.out.print("Error: Exchange"+a_id+" does not exist!");
			}
			System.out.println("");
		}
		else if(arg[0].equals("queryFindCallPath"))
		{
			System.out.print(actionMessage+": ");
			int a_id = Integer.parseInt(arg[1]);
			int b_id = Integer.parseInt(arg[2]);
			try 
			{
				Exchange a_exc = return_exchange_by_phoneid(a_id);
				try
				{
					Exchange b_exc = return_exchange_by_phoneid(b_id);
					try
					{
						MobilePhone a = a_exc.mobiles.return_mobile_by_mobileid(a_id);
						MobilePhone b = b_exc.mobiles.return_mobile_by_mobileid(b_id);
						ExchangeList print = routeCall(a,b);
						for(int i =0; i< print.number_of_nodes() - 1; i++)
						{
							System.out.print(print.return_node(i).id+", ");
						}
						System.out.print(print.return_node(print.number_of_nodes() - 1).id);
					}
					catch(NoSuchElementException w)
					{
						
					}
				}
				catch(NoSuchElementException z)
				{
					System.out.print("Error: Phone "+b_id+" does not exist!");
				}

			}
			catch(NoSuchElementException q)
			{
				System.out.print("Error: Phone "+a_id+" does not exist!");
			}
			System.out.println("");
		}
		else if(arg[0].equals("movePhone"))
		{
			//System.out.print(actionMessage+": ");
			int a_id = Integer.parseInt(arg[1]);
			int b_id = Integer.parseInt(arg[2]);
			try
			{
				Exchange a_exc = return_exchange_by_phoneid(a_id);
				try
				{
					Exchange b = return_exchange_by_exchangeid(b_id);
					try
					{
						MobilePhone a = a_exc.mobiles.return_mobile_by_mobileid(a_id);
						movePhone(a,b);
					}
					catch(NoSuchElementException z)
					{
						System.out.print(actionMessage+": ");
						System.out.print("Data not updated properly! ");
						System.out.println("");
					}
				}
				catch(NoSuchElementException w)
				{
					System.out.print(actionMessage+": ");
					System.out.print("Error: Exchange "+b_id+" does not exist!");
					System.out.println("");
				}
			}
			catch(NoSuchElementException q)
			{
				System.out.print(actionMessage+": ");
				System.out.print("Error: Phone "+b_id+" does not exist!");
				System.out.println("");
			}
			//System.out.println("");
		}
		else
		{
			System.out.println("Incorrect input!");
		}
	}
}
