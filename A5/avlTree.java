import java.util.NoSuchElementException;
import java.lang.Math;

public class avlTree<X>
{
	class Node
	{
		public Node(X element, int position)
		{
			data = element;
			this.position = position;
			parent = null;
			leftChild = null;
			rightChild = null;
			height = 0;
		}

		public Node sibling()
		{
			if(this.parent.leftChild == this)
			{
				return this.parent.rightChild;
			}
			else
			{
				return this.parent.leftChild;
			}
		}
		public Node heavier()
		{
			if(leftChild!=null)
			{
				if(rightChild!=null)
				{
					if(leftChild.height>rightChild.height)
						return leftChild;
					else
						return rightChild;
				}
				else
				{
					return leftChild;
				}
			}
			else
			{
				if(rightChild!=null)
				{
					return rightChild;
				}
				else
				{
					return null;
				}
			}
		}

		Node parent;
		Node leftChild;
		Node rightChild;
		X data;
		int position;
		int height;

	}

	Node root;

	public avlTree()
	{
		root = null;
	}

	public boolean isLeaf(Node element)
	{
		if(element.height==0)
			return true;
		return false;
	}

	public int isBalanced(Node element)
	{
		if(element.leftChild!=null)
		{
			if(element.rightChild!=null)
			{
				if((Math.abs(element.leftChild.height - element.rightChild.height)/2) == 0)
					return 0;
				else
					return element.leftChild.height - element.rightChild.height;
			}
			else
			{
				return element.leftChild.height;
			}
		}
		else
		{
			if(element.rightChild!=null)
			{
				return element.rightChild.height;
			}
			else
			{
				return 0;
			}
		}
	}

	public int height(Node element)
	{
		if(element.leftChild!=null)
		{
			if(element.rightChild!=null)
			{
				return Math.max(element.leftChild.height, element.rightChild.height) + 1;
			}
			else
			{
				return element.leftChild.height + 1;
			}
		}
		else
		{
			if(element.rightChild!=null)
			{
				return element.rightChild.height + 1;
			}
			else
			{
				return 0;
			}
		}
	}



	/*


	DUDE DUDE TAKE CARE OF HEIGHTS REASSIGNING AFTER ROTATION


	*/

	public void insert(X element, int position)
	{
		Node add = new Node(element, position);
		if(root == null)
		{
			root = add;
			add.height = 0;
		}
		else
		{
			Node temp = root;
			//System.out.println("Root "+temp.position);
			//this.print(this.root);
			Node current1 = root;
			/*while(!isLeaf(temp))
			{
				if(add.position<temp.position)
				{
					temp= temp.leftChild;
				}
				else
				{
					temp = temp.rightChild;
				}
				System.out.println(temp.position + temp.height);
			}*/

			while(current1!=null)
			{
				if(add.position<current1.position)
				{
					temp = current1;
					current1 = current1.leftChild;
				}
				else
				{
					temp = current1;
					current1 = current1.rightChild;
				}
			}

			if(add.position<temp.position)
			{
				temp.leftChild = add;
				add.parent = temp;
			}
			else
			{
				temp.rightChild = add;
				add.parent = temp;
			}
			//System.out.println(add.position+" added to "+temp.position);
			Node current = temp;
			while(current != null)
			{
				//System.out.println("Entering for "+current.position);
				current.height = height(current);
				if(isBalanced(current) == 0)
				{
					//System.out.println("Balanced at "+current.position);
					current = current.parent;
				}
				else
				{
					Node z = current;
					Node y = z.heavier();
					Node x = y.heavier();
					Node branch_parent = z.parent;
					if(y == z.leftChild)
					{
						if(x == y.leftChild)
						{
							if(root == z)
								root = y;
							z.parent = y;
							z.leftChild = y.rightChild;
							if(y.rightChild!=null)
								y.rightChild.parent = z;
							y.rightChild = z;
							y.parent = branch_parent;
							if(branch_parent != null)
							{
								if(branch_parent.leftChild == z)
									branch_parent.leftChild = y;
								else
									branch_parent.rightChild = y;
							}

							z.height = height(z);
							y.height = height(y);

							//z.height = z.height - 1;
							
							//h.height = h.height 
							//x.height = x.height - 1;
						}
						else
						{
							if(root == z)
								root = x;
							z.parent = x;
							z.leftChild = x.rightChild;
							if(x.rightChild != null)
								x.rightChild.parent = z;
							y.rightChild = x.leftChild;
							if(x.leftChild !=null)
								x.leftChild.parent = y;
							y.parent = x;
							x.leftChild = y;
							x.rightChild = z;
							x.parent = branch_parent;
							if(branch_parent != null)
							{
								if(branch_parent.leftChild == z)
									branch_parent.leftChild = x;
								else 
									branch_parent.rightChild = x;
							}

							y.height = height(y);
							z.height = height(z);
							x.height = height(x);

							/*x.height = x.height + 1;
							z.height = z.height - 2;
							y.height = y.height - 1;*/
						}
						break;
					}
					else
					{
						if(x == y.leftChild)
						{
							if(root == z)
								root = x;
							z.parent = x;
							z.rightChild = x.leftChild;
							if(x.leftChild != null)
								x.leftChild.parent = z;
							y.leftChild = x.rightChild;
							if(x.rightChild !=null)
								x.rightChild.parent = y;
							y.parent = x;
							x.leftChild = z;
							x.rightChild = y;
							x.parent = branch_parent;
							if(branch_parent != null)
							{
								if(branch_parent.leftChild == z)
									branch_parent.leftChild = x;
								else 
									branch_parent.rightChild = x;
							}

							y.height = height(y);
							z.height = height(z);
							x.height = height(x);

							/*x.height = x.height + 1;
							z.height = z.height - 2;
							y.height = y.height - 1;*/
						}
						else
						{
							if(root == z)
								root = y;
							z.parent = y;
							z.rightChild = y.leftChild;
							if(y.leftChild != null)
								y.leftChild.parent = z;
							y.leftChild = z;
							y.parent = branch_parent;
							if(branch_parent != null)
							{
								if(branch_parent.leftChild == z)
									branch_parent.leftChild = y;
								else
									branch_parent.rightChild = y;
							}

							z.height = height(z);
							y.height = height(y);

							/*y.parent = branch_parent;
							z.height = z.height - 1;*/
						}
						break;
					}
				}
			}
			
		}
	}


	public X search(int value)
	{
		if(root == null)
			return null;
		if(value == root.position)
			return root.data;
		if(value < root.position)
			return search(value, root.leftChild);
		else
			return search(value, root.rightChild);
	}

	public X search(int value, Node node)
	{
		if(node == null)
			return null;
		if(value == node.position)
			return node.data;
		if(value < node.position)
			return search(value, node.leftChild);
		else
			return search(value, node.rightChild);
	}

	public MyLinkedList<X> returnList(Node element, MyLinkedList<X> start)
	{
		if(element==null)
			return start;
		//System.out.println(element.position + "calling");
		returnList(element.leftChild, start);
		start.insertAtEnd(element.data);
		returnList(element.rightChild, start);
		return start;
	}

	public void print(Node element)
	{
		if(element==null)
			return;
		//System.out.println(element.position + "calling");
		print(element.leftChild);
		System.out.println(element.position + " " + element.height);
		print(element.rightChild);
	}

	public static void main(String[] args)
	{
		avlTree<Integer> a = new avlTree<Integer>();
		a.insert(2,2);
		a.insert(2,6);
		a.insert(2,1);
		a.insert(2,4);
		a.insert(2,5);
		a.print(a.root);
	}

}
