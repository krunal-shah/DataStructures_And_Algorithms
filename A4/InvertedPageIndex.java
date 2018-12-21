import java.util.NoSuchElementException;

public class InvertedPageIndex
{
	public MySet<PageEntry> pages;
	public MyHashTable hash_table;

	public InvertedPageIndex()
	{
		pages = new MySet<PageEntry>();
		hash_table = new MyHashTable();
	}

	public void addPage(PageEntry p)
	{
		pages.addElement(p);
		MyLinkedList<WordEntry> words = p.page.getWordEntries();
		MyLinkedList.Node temp = words.front;
		while(temp!=null)
		{
			hash_table.addPositionsForWord(((WordEntry)temp.data));
			temp = temp.next;
		}
		//System.out.println(pages.numberOfElements());
	}

	public MySet<PageEntry> getPagesWhichContainWord(String str)
	{
		try
		{
			return hash_table.return_page_entries(str);
		}
		catch(NoSuchElementException a)
		{
			//System.out.println("No Pages!");
		}
		//////
		//////
		//////	DUDE TAKE CARE OF THIS
		return null;
	}

	/*public static void main()
	{

	}*/
}