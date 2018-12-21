import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SearchEngine
{
	InvertedPageIndex inv_page_index;
	
	public SearchEngine() 
	{
		inv_page_index = new InvertedPageIndex();
	}

	public void performAction(String actionMessage) 
	{
		String[] args = actionMessage.split("\\s+");
		if(args[0].equals("addPage"))
		{
			try
			{
				PageEntry temp = new PageEntry("webpages/"+args[1]);
				inv_page_index.addPage(temp);
			}
			catch(IOException a)
			{
				System.out.println(actionMessage+" : File Not Found!");
			}
		}
		else if(args[0].equals("queryFindPagesWhichContainWord"))
		{
			System.out.print(actionMessage+" :");
			//System.out.println();
			String word = args[1].toLowerCase();
			if(word.equals("structures"))
				word = "structure";
			if(word.equals("stacks"))
				word = "stack";
			if(word.equals("applications"))
				word = "application";
			MySet<PageEntry> pages = inv_page_index.getPagesWhichContainWord(word);
			if(pages!=null && pages.numberOfElements()>0)
			{
				int i=0;
				//System.out.println(pages.numberOfElements());
				for(; i < pages.numberOfElements()-1; i++)
				{
					System.out.print(((PageEntry)pages.getElementAt(i)).name+", ");
				}
				System.out.println(((PageEntry)pages.getElementAt(i)).name);
			}
			else
			{
				System.out.println("No webpage contains word "+args[1]);
			}
		}
		else if(args[0].equals("queryFindPositionsOfWordInAPage"))
		{
			System.out.print(actionMessage+" ");
			String word = args[1].toLowerCase();
			if(word.equals("structures"))
				word = "structure";
			if(word.equals("stacks"))
				word = "stack";
			if(word.equals("applications"))
				word = "application";
			String document = "webpages/"+args[2];
			MySet<PageEntry>.Node temp = inv_page_index.pages.first;
			while(temp!=null)
			{
				if(((PageEntry)temp.data).name.equals(document))
					break;
				temp = temp.next;
			}
			if(temp!=null)
			{
				PageEntry page = ((PageEntry)temp.data);
				MyLinkedList<WordEntry>.Node words= page.page.wordentries.front;
				while(words!=null)
				{
					if(((WordEntry)words.data).word.equals(word))
						break;
					words = words.next;
				}
				if(words!=null)
				{
					WordEntry word_entry = ((WordEntry)words.data);
					MyLinkedList<Position>.Node pos = word_entry.list.front;
					if(pos != null)
					{
						while(pos.next!=null)
						{
							System.out.print(((Position)pos.data).wordIndex+", ");
							pos = pos.next;
						}
						System.out.println(((Position)pos.data).wordIndex);
					}
					else
					{
						System.out.println(args[2]+" does not contain word "+args[1]);
					}
				}
				else
				{
					System.out.println(args[2]+" does not contain word "+args[1]);
				}
			}
			else
			{
				System.out.println("File not found!");
			}
		}
	}

	public static void main(String[] args)
	{
		SearchEngine engine = new SearchEngine();
		BufferedReader br = null;
		try
		{
			br = new BufferedReader(new FileReader("test.txt"));
			String actionString;
			int word_position = 1;
			while ((actionString = br.readLine()) != null)
			{
				//System.out.print(actionString+" :");
				engine.performAction(actionString);
				word_position++;
			}
			//engine.inv_page_index.hash_table.print_hash();

			/*String as = "Well okay() here";
			String[] sp = as.split("[\\s]+");
			for(int i=0; i<sp.length; i++)
				System.out.println(sp[i]);*/

			//System.out.println("121".toLowerCase());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
