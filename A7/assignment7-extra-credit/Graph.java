import java.util.*;

public class Graph
{
	public class Vertex implements Comparable<Vertex>
	{
		String id;
		HashMap<Vertex, Edge> neighbours;
		Vertex edge;

		public Vertex(String id)
		{
			this.id = id;
			neighbours = new HashMap<Vertex, Edge>();
			edge = null;
		}

		public int compareTo(Vertex other)
		{
			return this.id.compareToIgnoreCase(other.id);
		}
	}

	public class Edge
	{
		int cost;

		public Edge(int cost)
		{
			this.cost = cost;
		}
	}

	public class Position
	{
		Vertex src;
		Vertex dest;
		int time;
		public Position(Vertex src, Vertex dest, int t)
		{
			this.src = src;
			this.dest = dest;
			time = t;
		}
		public Position()
		{

		}
	}

	public class Taxi
	{
		String name;
		Position start;
		Position end;
		List<Vertex> path;
		int cost;
		/*Vertex startSrc;
		Vertex startDest;
		int startTime;
		Vertex endSrc;
		Vertex endDest
		int endTime;*/
		int startAt;
		int endAt;
		int transitTime;
		
		public Taxi(String taxiName, String taxiPos) throws NoSuchElementException
		{
			name = taxiName;
			Vertex src = returnVertex(taxiPos);
			if(src == null)
			{
				//start = new Vertex(taxiName);
				throw new NoSuchElementException();
			}
			Vertex dest = src.edge;
			start = new Position(src, dest, 0);	
			end = start;
			startAt = 0;
			endAt = 0;
			transitTime = 0;
			cost = 0;
			path = null;
		}
	}

	public List<Vertex> vertices;
	public List<Taxi> taxies;

	public Graph()
	{
		vertices = new ArrayList<Vertex>();
		taxies =  new ArrayList<Taxi>();
	}

	public Vertex returnVertex(String str) throws NoSuchElementException
	{
		Iterator<Graph.Vertex> itr = vertices.iterator();
		Vertex cur;
		while(itr.hasNext())
		{
			cur = itr.next();
			if(cur.id.equals(str))
				return cur;
		}
		throw new NoSuchElementException();
	}

	public Position findPositionOfTaxi(Taxi taxi, int t)
	{
		Position ans = new Position();
		int counter = 0;
		int compareT = (t - taxi.startAt);
		//System.out.println("ComareT "+compareT);
		if(taxi.path == null)
		{
			return taxi.start;
		}

		/*System.out.println("Path is ");
		Iterator<Vertex> it1 = taxi.path.iterator();
		while(it1.hasNext())
		{
			System.out.println(it1.next().id);
		}
		System.out.println("ends");
		System.out.println("Src is "+taxi.start.src.id);
		System.out.println("Dest is "+taxi.start.dest.id);
		System.out.println("Start time "+taxi.start.time);*/


		Iterator<Vertex> it = taxi.path.iterator();
		Vertex temp = it.next();
		if(temp == taxi.start.src)
		{
			counter += taxi.start.time;
			//System.out.println("!!!"+counter);
			//System.out.println(counter);
			if(counter >= compareT)
			{
				ans.src = taxi.start.src;
				ans.dest = taxi.start.dest;
				ans.time = taxi.start.time - compareT;
				//System.out.println(22);
				return ans;
			}
		}
		else
		{
			counter += taxi.start.src.neighbours.get(taxi.start.dest).cost - taxi.start.time;
			//System.out.println("Counter is "+counter+taxi.startAt);
			if(counter >= compareT)
			{
				ans.src = taxi.start.dest;
				ans.dest = taxi.start.src;
				ans.time = counter - compareT;
				//System.out.println(5);
				return ans;
			}
		}
		Vertex cur = temp;
		Vertex previous = temp;
		while(it.hasNext())
		{
			previous = cur;
			cur = it.next();
			counter += previous.neighbours.get(cur).cost;
			if(counter >= compareT)
			{
				ans.src = previous;
				ans.dest = cur;
				ans.time = previous.neighbours.get(cur).cost - counter + (t - taxi.startAt);
				//System.out.println(4);
				return ans;
			}
		}
		if(previous == cur)
			cur = taxi.end.dest;
		ans.src = previous;
		ans.dest = cur;
		ans.time = compareT - counter;
		//System.out.println(3);
		return ans;
	}

	public Vertex returnNextVertex(Vertex a)
	{
		return vertices.get((vertices.indexOf(a)+1)%vertices.size());
	}

	public void addVertex(Vertex temp)
	{
		if(vertices.size()==0)
		{
			vertices.add(temp);
		}
		else
		{
			Iterator<Graph.Vertex> it = vertices.iterator();
			int i=0;
			while(it.hasNext())
			{
				Vertex cur = it.next();
				if(cur.compareTo(temp) > 0)
				{
					vertices.add(i, temp);
					break;
				}
				i++;
			}
			if(i==vertices.size())
				vertices.add(temp);
		}
	}

	public void addEdge(String src, String dest, int cost)
	{
		try
		{
			Vertex srcVertex = returnVertex(src);
			try
			{
				Vertex destVertex = returnVertex(dest);
				Edge edge = new Edge(cost);
				srcVertex.neighbours.put(destVertex, edge);
				destVertex.neighbours.put(srcVertex, edge);
				srcVertex.edge = destVertex;
				destVertex.edge = srcVertex;
			}
			catch(NoSuchElementException y)
			{
				Vertex destVertex = new Vertex(dest);
				addVertex(destVertex);
				Edge edge = new Edge(cost);
				srcVertex.neighbours.put(destVertex, edge);
				destVertex.neighbours.put(srcVertex, edge);
				srcVertex.edge = destVertex;
				destVertex.edge = srcVertex;
			}
		}
		catch(NoSuchElementException g)
		{
			Vertex srcVertex = new Vertex(src);
			addVertex(srcVertex);
			try
			{
				Vertex destVertex = returnVertex(dest);
				Edge edge = new Edge(cost);
				srcVertex.neighbours.put(destVertex, edge);
				destVertex.neighbours.put(srcVertex, edge);
				srcVertex.edge = destVertex;
				destVertex.edge = srcVertex;
			}
			catch(NoSuchElementException y)
			{
				Vertex destVertex = new Vertex(dest);
				addVertex(destVertex);
				Edge edge = new Edge(cost);
				srcVertex.neighbours.put(destVertex, edge);
				destVertex.neighbours.put(srcVertex, edge);
				srcVertex.edge = destVertex;
				destVertex.edge = srcVertex;
			}
		}
			
	}

	public List<Vertex> minDistanceFromBetween(String src1, String dest1, int t1, String src2, String dest2, int t2) throws NoSuchElementException
	{
		/*if(src1.equals(src2) && dest1.equals(dest2))
		{
			System.out.println("Exis");
			List<Vertex> ans = new ArrayList<Vertex>();
			ans.add(returnVertex(src1));
			return ans;
		}
		else if(src1.equals(dest2) && dest1.equals(src2))
		{
			System.out.println("Exis");
			List<Vertex> ans = new ArrayList<Vertex>();
			ans.add(returnVertex(src1));
			return ans;
		}*/
		//System.out.println(src1+dest1);
		try
		{
			List<Vertex> A = minDistance(src1, src2);
			List<Vertex> B = minDistance(src1, dest2);
			List<Vertex> C = minDistance(dest1, src2);
			List<Vertex> D = minDistance(dest1, dest2);
			//System.out.println(src1+dest1+t1+src2+dest2+t2);;
			int cost1, cost2;
			//if(t1 != 0 )
				cost1 = returnVertex(src1).neighbours.get(returnVertex(dest1)).cost;
			//else 
				//cost1 = 0;
			//if(t2 != 0)
				cost2 = returnVertex(src2).neighbours.get(returnVertex(dest2)).cost;
			//else 
				//cost2 = 0;
			int a = t1 + calculateTime(A) + t2;
			int b = t1 + calculateTime(B) + cost2 - t2;
			int c = cost1 - t1 + calculateTime(C) + t2;
			int d = cost1 - t1 + calculateTime(D) + cost2 - t2;
			//System.out.println("There"+a+" "+b+" "+c+" "+d);
			int min = a;
			if(b<min)
				min = b;
			if(c<min)
				min = c;
			if(d<min)
				min = d;

			List<Vertex> ans = new ArrayList<Vertex>();
			if(a == min)
			{
				return A;
			}
			else if(b == min)
			{
				return B;
			}
			else if(c == min)
			{
				return C;
			}
			else
			{
				return D;
			}
		}
		catch(NoSuchElementException r)
		{
			throw new NoSuchElementException();
		}
	}

	public List<Vertex> minDistance(String src1, String dest1) throws NoSuchElementException
	{
		try
		{
			Vertex a = returnVertex(src1);
			Vertex b = returnVertex(dest1);
			if(a == null || b == null)
			{
				return null;	
			}

			HashMap<Vertex, Integer> map = new HashMap<Vertex, Integer>();
			HashMap<Vertex, Vertex> previous = new HashMap<Vertex, Vertex>();
			List<Vertex> ans = new ArrayList<Vertex>();

			Iterator<Vertex> itr = vertices.iterator();
			Vertex cur;
			while(itr.hasNext())
			{
				map.put(itr.next(), Integer.MAX_VALUE);
			}
			List<Vertex> settled = new ArrayList<Vertex>();
			List<Vertex> unsettled = new ArrayList<Vertex>();

			unsettled.add(a);
			map.remove(a);
			map.put(a, 0);

			while(unsettled.size() != 0)
			{
				Vertex evaluationNode = getNodeWithLowestDistance(unsettled, map);
				unsettled.remove(evaluationNode);
				settled.add(evaluationNode);
				evaluatedNeighbors(evaluationNode, map, settled, unsettled, previous);
			}

			Vertex cur1 = b;
			ans.add(cur1);
			while(cur1!=a)
			{
				Vertex temp = previous.get(cur1);
				if(temp!=null)
					ans.add(previous.get(cur1));
				else
					System.out.println("Darn it!");
				cur1 = temp;
			}
			
			return ans;
		}
		catch(NoSuchElementException a)
		{
			throw new NoSuchElementException();
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

	public Vertex getNodeWithLowestDistance(List<Vertex> unsettled, HashMap<Vertex, Integer> map)
	{
		int min = Integer.MAX_VALUE;
		Vertex minNode = null;
		Iterator<Vertex> itr = unsettled.iterator();
		Vertex temp;
		while(itr.hasNext())
		{
			temp = itr.next();
			if(map.get(temp)<min)
			{
				minNode = temp;
				min = map.get(temp);
			}
		}
		return minNode;
	}

	public void evaluatedNeighbors(Vertex evaluationNode, HashMap<Vertex, Integer> map, List<Vertex> settled, List<Vertex> unsettled, HashMap<Vertex, Vertex> previous)
	{
		Iterator<Vertex> itr = evaluationNode.neighbours.keySet().iterator();
		Vertex cur;
		while(itr.hasNext())
		{
			cur = itr.next();
			if(!settled.contains(cur))
			{
				int edgeDistance = evaluationNode.neighbours.get(cur).cost;
				int newDistance = map.get(evaluationNode) + edgeDistance;
				if(map.get(cur) > newDistance)
				{
					map.remove(cur);
					map.put(cur, newDistance);
					unsettled.add(cur);
					previous.put(cur, evaluationNode);
				}
			}
		}
	}


		/*Foreach node set distance[node] = HIGH
		SettledNodes = empty
		UnSettledNodes = empty

		Add sourceNode to UnSettledNodes
		distance[sourceNode]= 0

		while (UnSettledNodes is not empty) {
		        evaluationNode = getNodeWithLowestDistance(UnSettledNodes)
		        remove evaluationNode from UnSettledNodes
		    add evaluationNode to SettledNodes
		    evaluatedNeighbors(evaluationNode)
		}

		getNodeWithLowestDistance(UnSettledNodes){
		        find the node with the lowest distance in UnSettledNodes and return it
		}

		evaluatedNeighbors(evaluationNode){
		        Foreach destinationNode which can be reached via an edge from evaluationNode AND which is not in SettledNodes {
		                edgeDistance = getDistance(edge(evaluationNode, destinationNode))
		                newDistance = distance[evaluationNode] + edgeDistance
		                if (distance[destinationNode]  > newDistance ) {
		                        distance[destinationNode]  = newDistance
		                        add destinationNode to UnSettledNodes
		                }
		        }
		}
	}*/

	public void printEdges(Vertex current)
	{
		System.out.println("Vertex "+current.id+" is connected to:");
		Iterator<Vertex> itr = current.neighbours.keySet().iterator();
		Vertex cur;
		while(itr.hasNext())
		{
			cur = itr.next();
			System.out.println(cur.id+" with cost "+current.neighbours.get(cur).cost);
		}
	}

	public void print()
	{
		Iterator<Vertex> itr = vertices.iterator();
		Vertex cur;
		while(itr.hasNext())
		{
			cur = itr.next();
			printEdges(cur);
		}
	}

	public static void main(String[] args)
	{
		Graph gr = new Graph();
		gr.addEdge("a", "b", 1);
		gr.addEdge("b", "c", 2);
		gr.addEdge("c", "a", 2);

		gr.print();
		//System.out.println(gr.minDistance(gr.returnVertex("a"), gr.returnVertex("c")));
	}
}