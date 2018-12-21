import java.util.*;

public class Graph
{
	public class Vertex
	{
		String id;
		HashMap<Vertex, Edge> neighbours;

		public Vertex(String id)
		{
			this.id = id;
			neighbours = new HashMap<Vertex, Edge>();
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

	public class Taxi
	{
		String name;
		Vertex position;
		int freeAt;

		public Taxi(String taxiName, String taxiPos) throws NoSuchElementException
		{
			name = taxiName;
			position = returnVertex(taxiPos);
			if(position == null)
			{
				position = new Vertex(taxiName);
				throw new NoSuchElementException();
			}
			freeAt = 0;
		}
	}

	public List<Vertex> vertices;
	public List<Taxi> taxies;

	public Graph()
	{
		vertices = new ArrayList<Vertex>();
		taxies =  new ArrayList<Taxi>();
	}

	public Vertex returnVertex(String str)
	{
		Iterator<Vertex> itr = vertices.iterator();
		Vertex cur;
		while(itr.hasNext())
		{
			cur = itr.next();
			if(cur.id.equals(str))
				return cur;
		}
		return null;
	}

	public void addEdge(String src, String dest, int cost)
	{
		Vertex srcVertex = returnVertex(src);
		Vertex destVertex = returnVertex(dest);
		if(srcVertex == null)
		{
			srcVertex = new Vertex(src);
			vertices.add(srcVertex);
		}
		if(destVertex == null)
		{
			destVertex = new Vertex(dest);
			vertices.add(destVertex);
		}
		Edge edge = new Edge(cost);
		srcVertex.neighbours.put(destVertex, edge);
		destVertex.neighbours.put(srcVertex, edge);
	}

	public List<Vertex> minDistance(String src1, String dest1)
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
		/*gr.addEdge("d", "e", 4);
		gr.addEdge("e", "f", 5);
		gr.addEdge("f", "a", 6);*/
		gr.print();
		//System.out.println(gr.minDistance(gr.returnVertex("a"), gr.returnVertex("c")));
	}
}