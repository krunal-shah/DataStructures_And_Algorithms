import java.util.NoSuchElementException;

public class MyLinkedList<X>
{
	Node front;

	public MyLinkedList()
	{
		front = null;
	}

	public class Node
	{
		X data;
		Node next;
		public Node(X element)
		{
			data = element;
			next = null;
		}
	}

	public void insertAtEnd(X element)
	{
		Node insert = new Node(element);
		Node temp = front;
		if(front == null)
		{
			front = insert;
		}
		else
		{
			while(temp.next!=null)
			{
				temp = temp.next;
			}
			temp.next = insert;
		}
	}

	public void insertAtFront(X element)
	{
		Node temp = new Node(element);
		if(front == null)
		{
			front = temp;
		}
		else
		{
			temp.next = front;
			front = temp;
		}
	}

	public void deleteFront()
	{
		if(front.next == null)
		{
			front = null;
		}
		else
		{
			front = front.next;
		}
	}

	public void delete(X element) throws NoSuchElementException
	{
		Node temp = front;
		if(temp.data.equals(element))
		{
			deleteFront();
			return;
		}
		if(temp.next == null)
			throw new NoSuchElementException();
		while((!temp.next.data.equals(element)) && temp.next!=null)
		{
			temp = temp.next;
		}
		temp.next = temp.next.next;
	}

	public X getElementAt(int i) throws NoSuchElementException
	{
		Node temp = front;
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

	public int numberOfElements()
	{
		Node temp = front;
		int count=0;
		while(temp!=null)
		{
			count++;
			temp = temp.next;
		}
		return count;
	}

	public int isPresent(X query)
	{
		Node temp = front;
		int counter=0;
		while(temp!=null)
		{
			if(temp.data.equals(query))
			{
				return counter;
			}
			temp = temp.next;
			counter++;
		}
		return -1;
	}


	public MyLinkedList<X> union(MyLinkedList<X> otherSet)
	{
		MyLinkedList<X> ans = new MyLinkedList<X>();
		Node temp = front;
		while(temp != null)
		{
			ans.insertAtEnd(temp.data);
			temp = temp.next;
		}
		temp = otherSet.front;
		while(temp != null)
		{
			if(ans.isPresent(temp.data)<0)
				ans.insertAtEnd(temp.data);
			temp = temp.next;
		}
		return ans;
	}

	public MyLinkedList<X> intersection(MyLinkedList<X> otherSet)
	{
		MyLinkedList<X> ans = new MyLinkedList<X>();
		Node temp = otherSet.front;
		while(temp != null)
		{
			if(ans.isPresent(temp.data)>0)
				ans.insertAtEnd(temp.data);
			temp = temp.next;
		}
		return ans;
	}

	/*public static void main(String[] args)
	{
		MyLinkedList<String> a = new MyLinkedList<String>();
		a.insertAtFront("Hey");
		a.insertAtFront("Krunal");
		a.insertAtFront("There");
		a.deleteFront();
		MyLinkedList.Node temp = a.front;
		while(temp!=null)
		{
			System.out.println(temp.data);
			temp = temp.next;
		}
	}*/
}