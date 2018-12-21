import java.util.NoSuchElementException;

public class MySet<X> 
{
	
	Node first;

	public MySet()
	{
		first = null;
	}

	public class Node
	{
		X data;
		Node next;
		public Node(X item)
		{
			data = item;
			next = null;
		}
	}

	public void addElement(X element)
	{	
		Node temp = new Node(element);
		if(first == null)
		{
			first = temp;
		}
		else
		{
			temp.next = first;
			first = temp;
		}
	}

	public MySet<X> union(MySet<X> otherSet)
	{
		MySet<X> ans = new MySet<X>();
		Node temp = first;
		while(temp != null)
		{
			ans.addElement(temp.data);
			temp = temp.next;
		}
		if(otherSet!=null)
		{
			temp = otherSet.first;
			while(temp != null)
			{
				if(!ans.isElement(temp.data))
					ans.addElement(temp.data);
				temp = temp.next;
			}
			return ans;
		}
		else
			return ans;
	}

	public MySet<X> intersection(MySet<X> otherSet)
	{
		MySet<X> ans = new MySet<X>();
		Node temp = first;
		if(otherSet != null)
		{
			while(temp != null)
			{
				if(otherSet.isElement(temp.data))
					ans.addElement(temp.data);
				temp = temp.next;
			}
			return ans;
		}
		else
		{
			return null;
		}
	}

	public boolean isElement(X element)
	{
		Node temp = first;
		while(temp != null)
		{
			if(temp.data.equals(element))
				return true;
			temp = temp.next;
		}
		return false;
	}

	public int numberOfElements()
	{
		Node temp = first;
		int counter = 0;
		while(temp != null)
		{
			temp = temp.next;
			counter++;
		}
		return counter;
	}

	public X getElementAt(int i) throws NoSuchElementException
	{
		Node temp = first;
		int counter = 0;
		while(temp!=null && counter < i)
		{
			counter++;
			temp = temp.next;
		}
		if(temp!=null)
		{
			return temp.data;
		}
		else
		{
			throw new NoSuchElementException();
		}
	}

	public void exchange(int i, int j)
	{
		//System.out.println(i + "   " + j);
		if(i==0)
		{
			if(j==i+1)
			{
				Node jNode = first.next;
				Node jNext = jNode.next;
				Node iNode = first;
				first = jNode;
				iNode.next = jNext;
				jNode.next = iNode;
			}	
			else
			{
				Node j_node = first;
				int counter = 0;
				while(j_node!=null && counter < j-1)
				{
					counter++;
					j_node = j_node.next;
				}
				if(j_node!=null)
				{
					Node iNode = first;
					Node jNode = j_node.next;
					Node iNext = iNode.next;
					Node jNext = jNode.next;
					first = jNode;
					j_node.next = iNode;
					jNode.next = iNext;
					iNode.next = jNext;
				}
			}
		}
		else
		{
			if(i==j+1)
			{
				Node i_node = first;
				int counter = 0;
				while(i_node!=null && counter < i-1)
				{
					counter++;
					i_node = i_node.next;
				}
				Node iNode = i_node.next;
				Node jNode = iNode.next;
				Node jNext = jNode.next;
				i_node.next = jNode;
				jNode.next = iNode;
				iNode.next = jNext;
			}
			else
			{
				Node i_node = first;
				int counter = 0;
				while(i_node!=null && counter < i-1)
				{
					counter++;
					i_node = i_node.next;
				}
				Node j_node = first;
				counter = 0;
				while(j_node!=null && counter < j-1)
				{
					counter++;
					j_node = j_node.next;
				}
				if(i_node!=null && j_node!=null)
				{
					Node iNode = i_node.next;
					Node jNode = j_node.next;
					i_node.next = jNode;
					j_node.next = iNode;
					Node iNext = iNode.next;
					Node jNext = jNode.next;
					jNode.next = iNext;
					iNode.next = jNext;
				}
			}
		}
	}

	/*public static void main(String[] args)
	{
		MySet<Integer> a = new MySet<Integer>();
		a.addElement(1);
		a.addElement(2);
		MySet<Integer> b = new MySet<Integer>();
		b.addElement(1);
		b.addElement(3);
		MySet<Integer> c = a.intersection(b);
		MySet.Node temp = c.first;
		while(temp!=null)
		{
			System.out.println(temp.data);
			temp = temp.next;
		}
	}*/
}