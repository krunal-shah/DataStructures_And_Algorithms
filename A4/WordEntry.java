public class WordEntry
{
	String word;
	MyLinkedList<Position> list;

	WordEntry(String word)
	{
		this.word = word;
		list = new MyLinkedList<Position>();
	}
	
	public void addPosition(Position position)
	{
		list.insertAtEnd(position);
	}

	public void addPositions(MyLinkedList<Position> positions)
	{
		for(int i=0; i<positions.numberOfElements(); i++)
		{
			list.insertAtEnd(positions.getElementAt(i));
		}
	}

	public MyLinkedList<Position> getAllPositionsForThisWord()
	{
		return list;
	}

	public MySet<PageEntry> getAllPageEntries()
	{
		MyLinkedList<Position>.Node temp = list.front;
		MySet<PageEntry> ans = new MySet<PageEntry>();
		while(temp != null)
		{
			if(!ans.isElement(((Position)temp.data).page))
			{
				ans.addElement(((Position)temp.data).page);
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