import java.util.*;

public class TaxiService
{

	public TaxiService() 
	{
		graph = new Graph();
		univTime = 0;
	}

	Graph graph;
	int univTime;

	public void performAction(String actionMessage) 
	{
		
		String[] args = actionMessage.split("\\s+");
		//System.out.println();
		if(args[0].equals("edge"))
		{
			System.out.println("action to be performed: " + actionMessage);
			String src = args[1];
			String dest = args[2];
			String timeStr = args[3];
			int time = Integer.valueOf(timeStr);
			graph.addEdge(src, dest, time);

			/*Iterator<Graph.Vertex> it = graph.vertices.iterator();
			while(it.hasNext())
			{
				Graph.Vertex cur = it.next();
				System.out.println(cur.id);
			}*/
		}
		else if(args[0].equals("taxi"))
		{
			System.out.println("action to be performed: " + actionMessage);
			String name = args[1];
			String pos = args[2];
			try
			{
				Graph.Taxi newTaxi = graph.new Taxi(name, pos);
				graph.taxies.add(newTaxi);
			}
			catch(NoSuchElementException a)
			{
				System.out.println("No Vertex by the name "+pos+" found!");
			}
		}
		else if(args[0].equals("customer"))
		{
			int time = Integer.valueOf(args[7]);
			updateStuff(time);
			System.out.println("action to be performed: " + actionMessage);
			int t1 = Integer.valueOf(args[3]);
			int t2 = Integer.valueOf(args[6]); 
			String src1 = args[1];
			String dest1 = args[2];
			String src2 = args[4];
			String dest2 = args[5];
			
			Iterator<Graph.Taxi> itr = graph.taxies.iterator();
			
			try
			{
				List<Graph.Vertex> errorchecker = graph.minDistanceFromBetween(src2, dest2, t2 , src1, dest1, t1);
				int minTaxiTime = Integer.MAX_VALUE;
				Graph.Taxi minTaxi = null;
				System.out.println("Available Taxis:");
				while(itr.hasNext())
				{
					Graph.Taxi tempTaxi = itr.next();
					if(tempTaxi.endAt <= time)
					{
						System.out.print("Path of "+tempTaxi.name+": ");
						Graph.Position pos = graph.findPositionOfTaxi(tempTaxi, time);
						//System.out.println(pos.src.id+pos.dest.id+pos.time);
						List<Graph.Vertex> ans = graph.minDistanceFromBetween(pos.src.id, pos.dest.id, pos.time , src1, dest1, t1);
						Collections.reverse(ans);
						Iterator<Graph.Vertex> it = ans.iterator();
						int taxiTime = calculateTimeFromBetween(ans, pos.src.id, pos.dest.id, pos.time, src1, dest1, t1);
						for(int i=0; i<ans.size()-1; i++)
						{
							System.out.print(it.next().id+", ");
						}
						System.out.print(it.next().id+". Time taken is "+ taxiTime+" units.");
						System.out.println();
						if(taxiTime < minTaxiTime)
						{
							minTaxiTime = taxiTime;
							minTaxi = tempTaxi;
						}
					}
					else
					{
						//System.out.println(1);
						System.out.print("Path of "+tempTaxi.name+": ");
						/*Iterator<Graph.Vertex> it2 = tempTaxi.path.iterator();
						while(it2.hasNext())
						{
							System.out.println(it2.next().id+"  ");
						}*/
						Graph.Position pos = graph.findPositionOfTaxi(tempTaxi, time);
						//System.out.println(pos.src.id+pos.dest.id+pos.time);
						//System.out.println(tempTaxi.end.src.id+tempTaxi.end.dest.id+tempTaxi.end.time);
						//System.out.println(src1+dest1+t1);
						List<Graph.Vertex> ans = graph.minDistanceFromBetween(pos.src.id, pos.dest.id, pos.time , tempTaxi.end.src.id, tempTaxi.end.dest.id, tempTaxi.end.time);
						Collections.reverse(ans);
						Iterator<Graph.Vertex> it = ans.iterator();
						int taxiTime = tempTaxi.endAt - time;
						//int taxiTime = calculateTimeFromBetween(ans, pos.src.id, pos.dest.id, pos.time, tempTaxi.end.src.id, tempTaxi.end.dest.id, tempTaxi.end.time);
						for(int i=1; i<ans.size(); i++)
						{
							System.out.print(it.next().id+", ");
						}
						ans = graph.minDistanceFromBetween(tempTaxi.end.src.id, tempTaxi.end.dest.id, tempTaxi.end.time , src1, dest1, t1);
						Collections.reverse(ans);
						it = ans.iterator();
						taxiTime += calculateTimeFromBetween(ans,tempTaxi.end.src.id, tempTaxi.end.dest.id, tempTaxi.end.time, src1, dest1, t1);
						//taxiTime += tempTaxi.endAt;
						for(int i=0; i<ans.size()-1; i++)
						{
							System.out.print(it.next().id+", ");
						}
						System.out.print(it.next().id+". Time taken is "+ taxiTime+" units.");
						System.out.println();
						if(taxiTime < minTaxiTime)
						{
							minTaxiTime = taxiTime;
							minTaxi = tempTaxi;
						}
					}
				}
				System.out.println("**Choose taxi "+minTaxi.name+" to service the customer request***");
				System.out.print("Path of customer: ");
				List<Graph.Vertex> ans = graph.minDistanceFromBetween(src1, dest1, t1, src2, dest2, t2);
				Collections.reverse(ans);
				Iterator<Graph.Vertex> it = ans.iterator();
				int customerTime = calculateTimeFromBetween(ans, src1, dest1, t1, src2, dest2, t2);
				for(int i=0; i<ans.size()-1; i++)
				{
					System.out.print(it.next().id+", ");
				}
				if(minTaxi.endAt <= time)
				{
					minTaxi.start = graph.findPositionOfTaxi(minTaxi, time);
					Graph.Position pos = minTaxi.start;
					List<Graph.Vertex> ans1 = graph.minDistanceFromBetween(pos.src.id, pos.dest.id, pos.time , src1, dest1, t1);
					Collections.reverse(ans1);

					List<Graph.Vertex> ans2 = new ArrayList<Graph.Vertex>(); 
					ans2.addAll(ans);
					ans2.remove(0);
					ans1.addAll(ans2);
					minTaxi.path = ans1;

					/*System.out.println();
					System.out.println();
					System.out.println("Path of ");
					Iterator<Graph.Vertex> it2 = ans1.iterator();
					while(it2.hasNext())
					{
						System.out.println(it2.next().id);
					}
					System.out.println();
					System.out.println();*/


				}
				else
				{
					minTaxi.start = graph.findPositionOfTaxi(minTaxi, time);

					Graph.Position pos = minTaxi.start;
					List<Graph.Vertex> ans1 = graph.minDistanceFromBetween(pos.src.id, pos.dest.id, pos.time , minTaxi.end.src.id, minTaxi.end.dest.id, minTaxi.end.time);
					Collections.reverse(ans1);
					List<Graph.Vertex> ans2 = graph.minDistanceFromBetween(minTaxi.end.src.id, minTaxi.end.dest.id, minTaxi.end.time , src1, dest1, t1);
					Collections.reverse(ans2);
					List<Graph.Vertex> ans3 = new ArrayList<Graph.Vertex>();
					ans3.addAll(ans2);
					ans3.remove(0);
					ans1.addAll(ans3);
					List<Graph.Vertex> ans4 = new ArrayList<Graph.Vertex>();
					ans4.addAll(ans);
					ans4.remove(0);
					ans1.addAll(ans4);
					minTaxi.path = ans1;

					/*System.out.println();
					System.out.println();
					System.out.println("Path of :");
					Iterator<Graph.Vertex> it2 = ans1.iterator();
					while(it2.hasNext())
					{
						System.out.println(it2.next().id);
					}
					System.out.println();
					System.out.println();*/

					
				}




				minTaxi.end = graph.new Position( graph.returnVertex(src2), graph.returnVertex(dest2), t2); //graph.returnVertex(args[2]);
				minTaxi.endAt = time + customerTime + minTaxiTime;
				minTaxi.startAt = time;
				//System.out.println("EndTime is "+minTaxi.endAt);
				//minTaxi.path = ans;
				System.out.print(it.next().id+". Time taken is "+ customerTime +" units.");
				System.out.println();
			}
			catch(NoSuchElementException a)
			{
				System.out.print("No such vertex exists!");
				System.out.println();
			}
		}
		else if(args[0].equals("printTaxiPosition"))
		{
			Iterator<Graph.Taxi> itr = graph.taxies.iterator();
			Graph.Taxi cur = null;
			int time = Integer.valueOf(args[1]);
			updateStuff(time);
			System.out.println("action to be performed: " + actionMessage);
			Graph.Position curPosition = null;
			while(itr.hasNext())
			{
				cur = itr.next();
				curPosition = graph.findPositionOfTaxi(cur, time);
				//if(cur.endAt <= time)
				System.out.println(cur.name+": "+curPosition.src.id +" "+ curPosition.dest.id +" "+ curPosition.time);
			}
		}
		//System.out.println();
	}

	public Graph.Vertex returnNextVertex(Graph.Vertex a)
	{
		return graph.vertices.get((graph.vertices.indexOf(a)+1)%graph.vertices.size());
	}

	public void updateStuff(int t)
	{
		for(int i=univTime; i<=t; i++)
		{
			Iterator<Graph.Taxi> itr = graph.taxies.iterator();
			while(itr.hasNext())
			{
				Graph.Taxi tempTaxi = itr.next();
				if(tempTaxi.endAt <= i && tempTaxi.transitTime <= i)
				{
					int a = tempTaxi.end.time;
					int b = tempTaxi.end.src.neighbours.get(tempTaxi.end.dest).cost - a;
					//System.out.println("a "+a+" b "+b);
					List<Graph.Vertex> ans;
					//System.out.println();
					if(a<b)
					{
						Graph.Position pos = tempTaxi.end;
						//System.out.println(11);
						//System.out.println(pos.src.id+pos.dest.id+returnNextVertex(pos.src).id+returnNextVertex(pos.src).edge.id);
						ans = graph.minDistanceFromBetween(pos.src.id, pos.dest.id, 0 , returnNextVertex(pos.src).id, returnNextVertex(pos.src).edge.id, 0);
						Collections.reverse(ans);
						Iterator<Graph.Vertex> it = ans.iterator();
						int taxiTime = calculateTimeFromBetween(ans, pos.src.id, pos.dest.id, 0, returnNextVertex(pos.src).id, returnNextVertex(pos.src).edge.id, 0) + a;
						tempTaxi.startAt = i;
						tempTaxi.transitTime = i + taxiTime;
						tempTaxi.start = pos;
						tempTaxi.end = graph.new Position(returnNextVertex(pos.src), returnNextVertex(pos.src).edge, 0);
						tempTaxi.path = ans;
						System.out.println("At "+i+" Taxi"+ tempTaxi.name + " chose a new destination vertex Vertex" + tempTaxi.end.src.id);// +" till " + tempTaxi.transitTime+" along ");
						/*Iterator<Graph.Vertex> it1 = ans.iterator();
						while(it1.hasNext())
						{
							System.out.println(it1.next().id+" ");
						}*/
					}
					else
					{
						Graph.Position pos = tempTaxi.end;
						//System.out.println(pos.src.id+pos.dest.id);
						//System.out.println(12);
						ans = graph.minDistanceFromBetween(pos.dest.id, pos.src.id, 0 , returnNextVertex(pos.dest).id, returnNextVertex(pos.dest).edge.id, 0);
						Collections.reverse(ans);
						Iterator<Graph.Vertex> it = ans.iterator();
						int taxiTime = calculateTimeFromBetween(ans, pos.dest.id, pos.src.id, 0, returnNextVertex(pos.dest).id, returnNextVertex(pos.dest).edge.id, 0) + b;
						tempTaxi.startAt = i;
						tempTaxi.transitTime = i + taxiTime;
						tempTaxi.start = pos;
						tempTaxi.end = graph.new Position(returnNextVertex(pos.src), returnNextVertex(pos.src).edge, 0);
						tempTaxi.path = ans;
						System.out.println("At "+i+" Taxi"+ tempTaxi.name + " chose a new destination vertex Vertex" + tempTaxi.end.src.id);// +" till " + tempTaxi.transitTime+" along ");
						Iterator<Graph.Vertex> it1 = ans.iterator();
						while(it1.hasNext())
						{
							System.out.println(it1.next().id+" ");
						}
					}
					//System.out.println();
					List<Graph.Vertex> list = new ArrayList<Graph.Vertex>();
					list.add(tempTaxi.end.src);


					/*tempTaxi.start = tempTaxi.end;
					tempTaxi.end = graph.vertices.get(graph.vertices.indexOf(tempTaxi.end));
					tempTaxi.startAt = i;
					tempTaxi.endAt = i + tempTaxi.start.neighbours.get(tempTaxi.end).cost;*/
					/*System.out.println("===>At "+i+" "+ tempTaxi.name + " will be directed from " + tempTaxi.start.src.id + " to " + tempTaxi.end.src.id +" till " + tempTaxi.transitTime+" along ");
					Iterator<Graph.Vertex> it = ans.iterator();
					while(it.hasNext())
					{
						System.out.println(it.next().id+" ");
					}*/
				}
			}
		}
		univTime = t;
	}

	public int calculateTimeFromBetween(List<Graph.Vertex> ans, String src1, String dest1, int t1, String src2, String dest2, int t2)
	{
		/*System.out.println("Answer");
		Iterator<Graph.Vertex> it = ans.iterator();
		System.out.println("ans");
		while(it.hasNext())
		{
			System.out.println(it.next().id+" ");
		}*/
		if(src1.equals(src2) && dest1.equals(dest2) && ans.size()==1)
		{
			return Math.abs(t2 - t1);
		}
		else if(src1.equals(dest2) && dest1.equals(src2) && ans.size()==1)
		{
			return Math.abs(graph.returnVertex(src1).neighbours.get(graph.returnVertex(dest1)).cost - t1 - t2);
		}



		int ret = calculateTime(ans);
		//System.out.println("Ret " +ret);
		//System.out.println("Calculate time "+src1+dest1+t1+src2+dest2+t2);
		if(ans.get(0).id.equals(src1))
		{
			ret += t1;
		}
		else
		{
			ret += (graph.returnVertex(src1).neighbours.get(ans.get(0)).cost - t1);
		}
		if(ans.get(ans.size()-1).id.equals(src2))
		{
			ret += t2;
		}
		else
		{
			//if(ans.size()!=2)
			ret += (graph.returnVertex(src2).neighbours.get(ans.get(ans.size()-1)).cost - t2);	
		}
		return ret;
	}

	public int calculateTime(List<Graph.Vertex> input)
	{
		int ans = 0;
		Iterator<Graph.Vertex> it = input.iterator();
		Graph.Vertex previous = null;
		Graph.Vertex cur = it.next();
		while(it.hasNext())
		{
			previous = cur;
			cur = it.next();
			ans += previous.neighbours.get(cur).cost;
		}
		return ans;
	}

	public static void main(String[] args)
	{
		TaxiService taxi = new TaxiService();
		taxi.performAction("edge V1 V2 1");
		taxi.performAction("edge V2 V3 2");
		taxi.performAction("edge V1 V3 4");
		taxi.performAction("taxi Taxi1 V2");
		taxi.performAction("customer V1 V3 1");
		taxi.performAction("printTaxiPosition");
	}
}
