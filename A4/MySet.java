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
		temp = otherSet.first;
		while(temp != null)
		{
			if(!ans.isElement(temp.data))
				ans.addElement(temp.data);
			temp = temp.next;
		}
		return ans;
	}

	public MySet<X> intersection(MySet<X> otherSet)
	{
		MySet<X> ans = new MySet<X>();
		Node temp = first;
		while(temp != null)
		{
			if(otherSet.isElement(temp.data))
				ans.addElement(temp.data);
			temp = temp.next;
		}
		return ans;
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