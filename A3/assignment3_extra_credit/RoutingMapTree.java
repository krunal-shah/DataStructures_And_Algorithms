import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;
import java.lang.IllegalArgumentException;
import java.util.Random;

public class RoutingMapTree
{
	Exchange root;
	public double start_time;
	public static int final_time = 0;

	public RoutingMapTree(double a) 
	{
		root = new Exchange(0);
		start_time = a;
	}

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

	public synchronized void switchOff(MobilePhone a) throws NoSuchElementException
	{
		synchronized(root)
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
	}

	public synchronized boolean contains_exchangeid(int a)
	{
		synchronized(root)
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
	}

	public synchronized Exchange return_exchange_by_exchangeid(int a) throws NoSuchElementException
	{
		synchronized(root)
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
	}

	public synchronized boolean contains_phoneid(int a)
	{
		synchronized(root)
		{
			return root.mobiles.contains_mobile(a);
		}
	}

	public synchronized Exchange return_exchange_by_phoneid(int a) throws NoSuchElementException
	{
		synchronized(root)
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
	}

	public synchronized void print_all_mobiles(Exchange a)
	{
		synchronized(root)
		{
			a.mobiles.print_mobiles();
		}
	}


	public synchronized void print_tree(RoutingMapTree a)
	{
		synchronized(root)
		{
			Exchange cur = a.root;
			System.out.print(cur.id()+" :");
			print_all_mobiles(cur);
			System.out.println();
			for(int i=0; i < cur.numChildren(); i++)
			{
				RoutingMapTree sub = cur.subtree(i);
				print_tree(sub);
			}
		}
	}

	public synchronized void print_tree_1(Exchange a)
	{
		synchronized(root)
		{
			Exchange cur = a;
			if(cur.numChildren()>=1)
			{
				System.out.print("Children of "+a.id()+" :");
				for(int i=0; i < cur.numChildren()-1; i++)
				{
					System.out.print(a.child(i).id()+" ");
				}
				System.out.println(a.child(cur.numChildren()-1).id());
			}
			else
			{
				System.out.println("Children of "+a.id()+" :");
			}
			for(int i=0; i < cur.numChildren(); i++)
			{
				print_tree_1(a.child(i));
			}
		}
	}



	public synchronized void random_print_dashboard(String as)
	{
		synchronized(root)
		{
			System.out.print("\033[H\033[2J");
			System.out.println(as);
			System.out.println("The tree is:");
			System.out.println("Root :"+root.id());
			print_tree_1(root);

			System.out.println();
			System.out.println("Status of each phone:");
			MobilePhoneSet mobile_set = root.residentSet();
			Myset.node cur = mobile_set.set.start;
			if(cur == null)
			{
				System.out.print("No MobilePhone in the network!");
			}
			else
			{
				while(cur!=null)
				{
					MobilePhone cur_phone = ((MobilePhone)cur.data);
					if(cur_phone.status())
					{
						if(cur_phone.query_busy())
						{
							System.out.print(cur_phone.number()+" :");
							System.out.println("Status: Mobile is busy with " + cur_phone.get_busy_with().number() + "!");
							System.out.println("     Under Exchange: "+cur_phone.exc.id());
						}
						else
						{
							System.out.print(cur_phone.number()+" :");
							System.out.println("Status: Mobile is switched on and not busy!");
							System.out.println("     Under Exchange: "+cur_phone.exc.id());
						}
					}
					else
					{
						System.out.print(cur_phone.number()+" :");
						System.out.println("Status: Mobile is switched off!");
						System.out.println("     Under Exchange: "+cur_phone.exc.id());
					}
					if(cur.next!=null)
						cur = cur.next;
					else
						break;
				}
			}
		}
	}


	
	public synchronized void print_dashboard()
	{
		synchronized(root)
		{
			System.out.println("The tree is:");
			System.out.println("Root :"+root.id());
			print_tree_1(root);

			System.out.println();
			System.out.println("Status of each phone:");
			MobilePhoneSet mobile_set = root.residentSet();
			Myset.node cur = mobile_set.set.start;
			if(cur == null)
			{
				System.out.print("No MobilePhone in the network!");
			}
			else
			{
				while(cur!=null)
				{
					MobilePhone cur_phone = ((MobilePhone)cur.data);
					if(cur_phone.status())
					{
						if(cur_phone.query_busy())
						{
							System.out.print(cur_phone.number()+" :");
							System.out.println("Status: Mobile is busy with " + cur_phone.get_busy_with().number() + "!");
							System.out.println("     Under Exchange: "+cur_phone.exc.id());
						}
						else
						{
							System.out.print(cur_phone.number()+" :");
							System.out.println("Status: Mobile is switched on and not busy!");
							System.out.println("     Under Exchange: "+cur_phone.exc.id());
						}
					}
					else
					{
						System.out.print(cur_phone.number()+" :");
						System.out.println("Status: Mobile is switched off!");
						System.out.println("     Under Exchange: "+cur_phone.exc.id());
					}
					if(cur.next!=null)
						cur = cur.next;
					else
						break;
				}
			}
		}
	}


	public synchronized Exchange return_random_leaf()
	{
		synchronized(root)
		{
			Exchange cur = root;
			Random rand = new Random();
			while(!cur.isLeaf())
			{
				int r = rand.nextInt(cur.numChildren());
				cur = cur.child(r);
			}
			return cur;
		}
	}

	public MobilePhone return_random_mobile()
	{
		synchronized(root)
		{
			MobilePhoneSet m_set = root.residentSet();
			Random rand = new Random();
			int r = rand.nextInt(m_set.set.num());
			if(m_set.set.num()==0)
				return null;
			return m_set.return_mobile_by_index(r);
		}
	}












	// ASSIGNMENT 3 METHODS

	public Exchange findPhone(MobilePhone a) throws NoSuchElementException,UnsupportedOperationException
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
			throw new UnsupportedOperationException();
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
		synchronized(root)
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
	}

	public synchronized void movePhone(MobilePhone a, Exchange b) throws NoSuchElementException, UnsupportedOperationException, IllegalArgumentException
	{
		synchronized(root)
		{
			if(a.status())
			{
				if(b.isLeaf())
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
				else
				{
					throw new IllegalArgumentException();
				}
			}
			else
			{
				throw new UnsupportedOperationException();
			}
		}
	}















	public void performAction(String actionMessage) 
	{
		System.out.print("\033[H\033[2J");
		System.out.println("Query Handling started at " + (((double)System.nanoTime()/1000000000) - start_time));
		System.out.println();
		/*System.out.println("Tree Starts");
		print_tree(this);
		System.out.println("Tree Ends");*/
		try
		{
			Thread.sleep(5);
		}
		catch(InterruptedException wq)
		{

		}
		String arg[] = actionMessage.split("\\s+");
		/*for(int i=0; i<arg.length; i++)
			System.out.println(arg[i]);*/
		if(arg[1].equals("addExchange"))
		{
			System.out.println(actionMessage+": ");
			int a_id = Integer.parseInt(arg[2]);
			int b_id = Integer.parseInt(arg[3]);
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
		else if(arg[1].equals("switchOnMobile"))
		{
			System.out.println(actionMessage+": ");
			int a_id = Integer.parseInt(arg[2]);
			int b_id = Integer.parseInt(arg[3]);
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
							MobilePhone a = new MobilePhone(a_id, this, start_time);
							Thread th = new Thread(a);
							th.start();
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
		else if(arg[1].equals("switchOffMobile"))
		{
			System.out.println(actionMessage+": ");
			int a_id = Integer.parseInt(arg[2]);
			try
			{
				Exchange b = return_exchange_by_phoneid(a_id);
				if(b.mobiles.contains_mobile(a_id))
				{
					try
					{
						MobilePhone a = b.mobiles.return_mobile_by_mobileid(a_id);
						a.set_todo(actionMessage);
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
		else if(arg[1].equals("queryNthChild"))
		{
			System.out.print(actionMessage+": ");
			int a_id = Integer.parseInt(arg[2]);
			int i = Integer.parseInt(arg[3]);
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
		else if(arg[1].equals("queryMobilePhoneSet"))
		{
			System.out.print(actionMessage+": ");
			int a_id = Integer.parseInt(arg[2]);
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




		else if(arg[1].equals("queryFindPhone"))
		{
			System.out.print(actionMessage+": ");
			int a_id = Integer.parseInt(arg[2]);
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
				catch(UnsupportedOperationException e)
				{
					System.out.print("Error: Mobile is switched off!");
				}
			}
			catch(NoSuchElementException q)
			{
				System.out.print("Error: Mobile "+a_id+" does not exist!");
			}
			System.out.println("");
		}
		else if(arg[1].equals("queryLowestRouter"))
		{
			System.out.print(actionMessage+": ");
			int a_id = Integer.parseInt(arg[2]);
			int b_id = Integer.parseInt(arg[3]);
			try 
			{
				Exchange a = return_exchange_by_exchangeid(a_id);
				try
				{
					Exchange b = return_exchange_by_exchangeid(b_id);
					if(a.isLeaf() && b.isLeaf())
					{
						System.out.print(lowestRouter(a,b).id);
					}
					else
					{
						System.out.print("Error: The Exchanges are not base stations!");
					}
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
		else if(arg[1].equals("queryFindCallPath"))
		{
			System.out.print(actionMessage+": ");
			int a_id = Integer.parseInt(arg[2]);
			int b_id = Integer.parseInt(arg[3]);
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
		else if(arg[1].equals("movePhone"))
		{
			System.out.println(actionMessage+": ");
			int a_id = Integer.parseInt(arg[2]);
			int b_id = Integer.parseInt(arg[3]);
			try
			{
				Exchange a_exc = return_exchange_by_phoneid(a_id);
				try
				{
					MobilePhone a = a_exc.mobiles.return_mobile_by_mobileid(a_id);
					a.set_todo(actionMessage);
				}
				catch(NoSuchElementException z)
				{
					System.out.print(actionMessage+": ");
					System.out.print("Data not updated properly! ");
					System.out.println("");
				}
			}
			catch(NoSuchElementException q)
			{
				System.out.print(actionMessage+": ");
				System.out.print("Error: Phone "+a_id+" does not exist!");
				System.out.println("");
			}
			//System.out.println("");
		}
		else if(arg[1].equals("initiate_call"))
		{
			System.out.println(actionMessage+": ");
			int a_id = Integer.parseInt(arg[2]);
			try
			{
				Exchange a_exc = return_exchange_by_phoneid(a_id);
				try
				{
					MobilePhone a = a_exc.mobiles.return_mobile_by_mobileid(a_id);
					if(!a.query_busy())
					{
						a.set_todo(actionMessage);
					}
					else 
					{
						System.out.print("Error: Phone "+a_id+" is busy on another call!");
						System.out.println("");
					}
				}
				catch(NoSuchElementException q)
				{
					System.out.print(actionMessage+": ");
					System.out.print("Data not updated properly! ");
					System.out.println("");
				}
			}
			catch(NoSuchElementException q)
			{
				System.out.print(actionMessage+": ");
				System.out.print("Error: Phone "+a_id+" does not exist!");
				System.out.println("");
			}
		}
		else
		{
			System.out.println("Incorrect input!");
		}
		System.out.println();
		System.out.println("Query handling completed "+ (((double)System.nanoTime()/1000000000) - start_time));
		System.out.println();
		print_dashboard();
	}
}
