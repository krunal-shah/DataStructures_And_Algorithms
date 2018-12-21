public class WordEntry
{
	String word;
	MyLinkedList<page_positions> list;

	class page_positions
	{
		avlTree<Position> list_avlTrees;
		String pageName;

		public page_positions(String name)
		{
			list_avlTrees = new avlTree<Position>();
			pageName = name;
		}
	}

	public page_positions getAvlTree(String name)
	{
		MyLinkedList<page_positions>.Node temp = list.front;
		while(temp != null)
		{
			page_positions temp_page_pos = temp.data;
			if(temp_page_pos.pageName.equals(name))
				return temp_page_pos;
		}
		return null;
	}

	public Position returnPosition(int value, String name)
	{
		MyLinkedList<page_positions>.Node temp = list.front;
		while(temp!=null)
		{
			page_positions temp_pos = temp.data;
			if(temp.data.pageName.equals(name))
			{
				avlTree<Position> tree = temp_pos.list_avlTrees;
				return tree.search(value);
			}
			temp = temp.next;
		}
		return null;
	}
	

	WordEntry(String word)
	{
		this.word = word;
		list = new MyLinkedList<page_positions>();
	}
	
	public void addPosition(Position position)
	{
		String name = position.page.name;
		MyLinkedList<page_positions>.Node temp = list.front;
		while(temp!=null)
		{
			page_positions temp_pos = temp.data;
			if(temp.data.pageName.equals(name))
			{
				temp_pos.list_avlTrees.insert(position, position.wordIndex);
				break;
			}
			temp = temp.next;
		}
		if(temp == null)
		{
			page_positions add = new page_positions(name);
			this.list.insertAtEnd(add);
			add.list_avlTrees.insert(position, position.wordIndex);
		}
	}

	public void addPositions(MyLinkedList<Position> positions)
	{
		MyLinkedList<Position>.Node temp = positions.front;
		while(temp != null)
		{
			addPosition(temp.data);
			temp = temp.next;
		}
	}

	public void addPositionsByWord(WordEntry word)
	{
		MyLinkedList<page_positions>.Node temp = word.list.front;
		MyLinkedList<Position> toPass = new MyLinkedList<Position>();
		while(temp != null)
		{
			page_positions pos = temp.data;
			addPositions(pos.list_avlTrees.returnList(pos.list_avlTrees.root, toPass));
			temp = temp.next;
		}
	}

	public MyLinkedList<Position> getAllPositionsForThisWord()
	{
		MyLinkedList<page_positions>.Node temp = list.front;
		MyLinkedList<Position> toPass = new MyLinkedList<Position>();
		while(temp != null)
		{
			page_positions pos = temp.data;
			pos.list_avlTrees.returnList(pos.list_avlTrees.root, toPass);
			temp = temp.next;
		}
		return toPass;
	}

	public MySet<PageEntry> getAllPageEntries()
	{
		MyLinkedList<page_positions>.Node temp = list.front;
		MySet<PageEntry> ans = new MySet<PageEntry>();
		while(temp != null)
		{
			avlTree<Position> tree = temp.data.list_avlTrees;
			if(!ans.isElement(tree.root.data.page))
			{
				ans.addElement(tree.root.data.page);
			}
			temp = temp.next;
		}
		return ans;
	}

	/*public static void main(String[] args)
	{
		WordEntry a = new WordEntry("Hello");
		Position b = new Position(1,2)
	}*/
}