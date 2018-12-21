public class PageIndex
{
	MyLinkedList<WordEntry> wordentries;

	public PageIndex()
	{
		wordentries = new MyLinkedList<WordEntry>();
	}
	
	public void addPositionForWord(String str, Position p)
	{
		WordEntry new_entry = new WordEntry(str);
		MyLinkedList<WordEntry>.Node temp = wordentries.front;
		int position = 0;
		while(temp != null)
		{
			if(temp.data.word.equals(str))
				break;
			position++;
			temp = temp.next;
		}
		//System.out.println("Insert    "+str);
		if(position < wordentries.numberOfElements())
		{
			//System.out.println(str);
			/*if(str.equals("allain"))
				System.out.println(1);*/
			//System.out.println("Insert "+str);
			wordentries.getElementAt(position).addPosition(p);
		}
		else
		{
			/*if(str.equals("allain"))
			{
				System.out.println(2);
				wordentries.insertAtFront(new_entry);
				System.out.println(p.page.name);
				return;
			}*/
			wordentries.insertAtFront(new_entry);
			wordentries.getElementAt(0).addPosition(p);
		}
	}

	public MyLinkedList<WordEntry> getWordEntries()
	{
		return wordentries;
	}

}