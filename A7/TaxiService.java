import java.util.*;

public class TaxiService
{

	public TaxiService() 
	{
		graph = new Graph();
	}

	Graph graph;

	public void performAction(String actionMessage) 
	{
		System.out.println("action to be performed: " + actionMessage);
		String[] args = actionMessage.split("\\s+");
		if(args[0].equals("edge"))
		{
			String src = args[1];
			String dest = args[2];
			String timeStr = args[3];
			int time = Integer.valueOf(timeStr);
			graph.addEdge(src, dest, time);
		}
		else if(args[0].equals("taxi"))
		{
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
			int time = Integer.valueOf(args[3]);
			
			Iterator<Graph.Taxi> itr = graph.taxies.iterator();
			
			int minTaxiTime = Integer.MAX_VALUE;
			Graph.Taxi minTaxi = null;
			System.out.println("Available Taxis:");
			while(itr.hasNext())
			{
				Graph.Taxi tempTaxi = itr.next();
				if(tempTaxi.freeAt <= time)
				{
					System.out.print("Path of "+tempTaxi.name+": ");
					List<Graph.Vertex> ans = graph.minDistance(tempTaxi.position.id, args[1]);
					Collections.reverse(ans);
					Iterator<Graph.Vertex> it = ans.iterator();
					int taxiTime = calculateTime(ans);
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
			List<Graph.Vertex> ans = graph.minDistance(args[1], args[2]);
			Collections.reverse(ans);
			Iterator<Graph.Vertex> it = ans.iterator();
			int customerTime = calculateTime(ans);
			for(int i=0; i<ans.size()-1; i++)
			{
				System.out.print(it.next().id+", ");
			}
			minTaxi.position = graph.returnVertex(args[2]);
			minTaxi.freeAt = time + minTaxiTime + customerTime;
			System.out.print(it.next().id+". Time taken is "+ customerTime+" units.");
			System.out.println();
		}
		else if(args[0].equals("printTaxiPosition"))
		{
			Iterator<Graph.Taxi> itr = graph.taxies.iterator();
			int time = Integer.valueOf(args[1]);
			int i=0;
			Graph.Taxi cur = null;
			while(itr.hasNext())
			{
				cur = itr.next();
				if(cur.freeAt <= time)
				{
					System.out.println(cur.name+": "+cur.position.id);
					i++;
				}
			}
			if(i==0)
				System.out.println("No taxis available!");
		}
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
