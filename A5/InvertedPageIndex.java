import java.util.NoSuchElementException;
import java.util.Arrays;

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
		MyLinkedList<WordEntry>.Node temp = words.front;
		while(temp!=null)
		{
			hash_table.addPositionsForWord(temp.data);
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
		return null;
	}

	public MySet<PageEntry> getPagesWhichContainPhrase(String str[])
	{
		MySet<PageEntry> ans = new MySet<PageEntry>();
		MySet<PageEntry>.Node temp_node = pages.first;
		while(temp_node!=null)
		{
			PageEntry temp_page = temp_node.data;
			if(temp_page.getRelevanceOfPage(str, true) > 0)
			{
				ans.addElement(temp_page);
			}
			temp_node = temp_node.next;
		}
		return ans;
	}
}