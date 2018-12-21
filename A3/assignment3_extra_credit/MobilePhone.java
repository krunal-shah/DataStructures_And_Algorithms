import java.util.NoSuchElementException;
import java.util.Random;

public class MobilePhone implements Runnable
{
	public int id;
	public boolean state;
	public Exchange exc;
	public boolean busy;
	public String todo;
	RoutingMapTree tree;
	public double start_time;
	public MobilePhone busy_with;
	
	public void run()
	{
		if(extra.random)
		{
			while(get_time() < extra.random_end_time) 
			{
				Random rand = new Random();
				int task = rand.nextInt(3);
				int wait_time = rand.nextInt(6) + 5;
				int wait_start = (int)get_time();
				while(get_time() < (wait_time + wait_start))
				{

				}
				if(task == 0)
				{
					if(query_busy())
					{
						get_busy_with().set_busy(false);
						get_busy_with().set_busy_with(null);
						set_busy_with(null);
						set_busy(false);
					}
					set_state(false);
					//System.out.print("\033[H\033[2J");
					//System.out.println("Switching off "+this.number());
					tree.random_print_dashboard("Switching off "+this.number());
				}
				else if(task == 1)
				{
					Exchange b = tree.return_random_leaf();
					try
					{
						tree.movePhone(this, b);
						//System.out.print("\033[H\033[2J");
						//System.out.println("Moving "+this.number()+" to "+b.id());
						tree.random_print_dashboard("Moving "+this.number()+" to "+b.id());
					}
					catch(NoSuchElementException z)
					{
						//System.out.print("\033[H\033[2J");
						System.out.println("Moving "+this.number()+" to "+b.id());
						System.out.print("Data not updated properly! ");
						System.out.println("");
					}
					catch(UnsupportedOperationException e)
					{
						//System.out.print("\033[H\033[2J");
						//System.out.println("Moving "+this.number()+" to "+b.id());
						//System.out.print("The mobile is switched off!");
						//System.out.println("");
						tree.random_print_dashboard("Moving "+this.number()+" to "+b.id()+"=>"+"The mobile is switched off!");
					}
					catch(IllegalArgumentException r)
					{
						//System.out.print("\033[H\033[2J");
						//System.out.println("Moving "+this.number()+" to "+b.id());
						//System.out.print("The Exchange is not a base station!");
						//System.out.println("");
						tree.random_print_dashboard("Moving "+this.number()+" to "+b.id()+"=>"+"The Exchange is not a base station!");
					}
				}
				else
				{
					MobilePhone b = tree.return_random_mobile();
					if(b==null)
					{

					}
					else if(b==this)
					{

					}
					else
					{
						if(this.status())
						{
							if(!this.query_busy())
							{
								if(b.status())
								{
									if(!b.query_busy())
									{
										b.set_busy(true);
										this.set_busy(true);
										b.set_busy_with(this);
										this.set_busy_with(b);
										int call_duration = rand.nextInt(3) + 4;
										int call_start = (int)get_time();
										//System.out.print("\033[H\033[2J");
										//System.out.println("Calling "+b.number()+" from "+this.number());
										tree.random_print_dashboard("Calling "+b.number()+" from "+this.number());
										//System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
										while(get_time()< call_start+call_duration)
										{
											
										}
										b.set_busy(false);
										this.set_busy(false);
										b.set_busy_with(null);
										this.set_busy_with(null);
									}
									else
									{
										//System.out.print("\033[H\033[2J");
										//System.out.println("Calling "+b.number()+" from "+this.number());
										//System.out.print("Error: Phone "+b.number()+" is busy on another call!");
										//System.out.println("");
										tree.random_print_dashboard("Calling "+b.number()+" from "+this.number()+"=>"+"Error: Phone "+b.number()+" is busy on another call!");
									}
								}
								else
								{
									//System.out.print("\033[H\033[2J");
									//System.out.println("Calling "+b.number()+" from "+this.number());
									//System.out.print("Error: Phone "+b.number()+" is switched off!");
									//System.out.println("");
									tree.random_print_dashboard("Calling "+b.number()+" from "+this.number()+"=>"+"Error: Phone "+b.number()+" is switched off!");
								}
							}
						}
					}
				}
				
				
			}
		}
		else
		{
			while(extra.end == 0 || get_time() < (RoutingMapTree.final_time+2))
			{
				if(get_todo()!=null)
				{
					String arg[] = get_todo().split("\\s+");
					if(arg[1].equals("switchOffMobile"))
					{
						if(query_busy())
						{
							get_busy_with().set_busy(false);
							get_busy_with().set_busy_with(null);
							set_busy_with(null);
							set_busy(false);
						}
						set_state(false);
					}
					else if(arg[1].equals("movePhone"))
					{
						int b_id = Integer.parseInt(arg[3]);
						try
						{
							Exchange b = tree.return_exchange_by_exchangeid(b_id);
							try
							{
								tree.movePhone(this, b);
							}
							catch(NoSuchElementException z)
							{
								System.out.print(get_todo()+": ");
								System.out.print("Data not updated properly! ");
								System.out.println("");
							}
							catch(UnsupportedOperationException e)
							{
								System.out.print(get_todo()+": ");
								System.out.print("===> The mobile is switched off!");
								System.out.println("");
							}
							catch(IllegalArgumentException r)
							{
								System.out.print(get_todo()+": ");
								System.out.print("===> The Exchange is not a base station!");
								System.out.println("");
							}
						}
						catch(NoSuchElementException q)
						{
							System.out.print(get_todo()+": ");
							System.out.println("===> No Exchange by "+b_id+" exists!");
							System.out.println("");
						}
					}
					else if(arg[1].equals("initiate_call"))
					{
						int b_id = Integer.parseInt(arg[3]);
						if(status())
						{
							if(!query_busy()) 
							{
								try
								{
									Exchange b_exc = tree.return_exchange_by_phoneid(b_id);
									try
									{
										MobilePhone b = b_exc.mobiles.return_mobile_by_mobileid(b_id);
										if(b.status())
										{
											if(!b.query_busy())
											{
												b.set_busy(true);
												this.set_busy(true);
												b.set_busy_with(this);
												this.set_busy_with(b);
												int call_start = Integer.parseInt(arg[0]);
												int call_duration = Integer.parseInt(arg[4]);
												//System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
												if(call_start + call_duration > RoutingMapTree.final_time)
													RoutingMapTree.final_time = call_start+call_duration;
												while((get_time() < call_start+call_duration))
												{
													if((get_todo().split("\\s+"))[1].equals("switchOffMobile"))
													{
														switchOff();
														break;
													}
												}
												b.set_busy(false);
												this.set_busy(false);
												b.set_busy_with(null);
												this.set_busy_with(null);
											}
											else
											{
												System.out.print("===> Error: Phone "+b_id+" is busy on another call!");
												System.out.println("");
											}
										}
										else
										{
											System.out.print("===> Error: Phone "+b_id+" is switched off!");
											System.out.println("");
										}
									}
									catch(NoSuchElementException q)
									{
										System.out.print("===> Data not updated properly! ");
										System.out.println("");
									}
								}
								catch(NoSuchElementException q)
								{
									System.out.print("===> Error: Phone "+b_id+" does not exist!");
									System.out.println("");
								}
							}
							else
							{
								System.out.print("===> Error: Phone "+number()+" is busy on another call!");
								System.out.println("");
							}
						}
						else 
						{
							System.out.print("===> Error: Phone "+number()+" is switched off!");
							System.out.println("");
						}
					}
					set_todo(null);
				}
			}
		}
	}

	public MobilePhone(int a, RoutingMapTree t, double start)
	{
		id = a;
		state = false;
		exc = null;
		busy = false;
		todo = null;
		tree = t;
		start_time = start;
		busy_with = null;
	}

	public synchronized MobilePhone get_busy_with()
	{
		return busy_with;
	}

	public synchronized void set_busy_with(MobilePhone t)
	{
		busy_with = t;
	}

	public int number()
	{
		return id;
	}

	public synchronized void set_todo(String actionString)
	{
		todo = actionString;
	}

	public synchronized String get_todo()
	{
		return todo;
	}

	public synchronized boolean query_busy()
	{
		return busy;
	}

	public synchronized void set_busy(Boolean a)
	{
		busy = a;
	}

	public synchronized boolean status()
	{
		return state;
	}

	public synchronized void set_state(Boolean a)
	{
		state = a;
	}

	public synchronized void switchOn()
	{
		state = true;
	}

	public void switchOff()
	{
		state = false;
	}

	public Exchange location() throws NoSuchElementException
	{
		if(state)
		{
			return exc;
		}
		else
		{
			throw new NoSuchElementException();
		}
	}

	public double get_time()
	{
		return (((double)System.nanoTime()/1000000000) - start_time);
	}
}