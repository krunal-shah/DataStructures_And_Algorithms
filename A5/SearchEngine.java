import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
				PageEntry temp = new PageEntry("webpages/"+args[1], inv_page_index.hash_table);
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
			String[] ans_str = {word};
			MySet<PageEntry> pages = inv_page_index.getPagesWhichContainWord(word);
			if(pages!=null && pages.numberOfElements()>0)
			{
					ArrayList<Comparable> search_results = new ArrayList<Comparable>();
					MySet<Comparable> temp_search = new MySet<Comparable>();
					for(int i=0; i<pages.numberOfElements(); i++)
					{
						PageEntry temp_page = pages.getElementAt(i);
						SearchResult temp = new SearchResult(temp_page, temp_page.getRelevanceOfPage(ans_str, false));
						temp_search.addElement(temp);
					}


					MySort sort = new MySort();
					/*for(int i=0; i<ans.numberOfElements(); i++)
					{
						System.out.println(((PageEntry)temp_search.getElementAt(i).p).name);
					}*/


					search_results = sort.sortThisList(temp_search);
					for(int i=0; i<search_results.size()-1; i++)
					{
						SearchResult temp_entry = (SearchResult)search_results.get(i);
						System.out.print(temp_entry.p.name+" ");
					}
					
					SearchResult temp_entry = (SearchResult)search_results.get(search_results.size()-1);
					System.out.println(temp_entry.p.name+" ");
			}
			else
			{
				System.out.println("No webpage contains word "+args[1]);
			}
		}
		else if(args[0].equals("queryFindPositionsOfWordInAPage"))
		{
			System.out.print(actionMessage+" : ");
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
				if((temp.data).name.equals(document))
					break;
				temp = temp.next;
			}
			if(temp!=null)
			{
				PageEntry page = (temp.data);
				MyLinkedList<WordEntry>.Node words= page.page.wordentries.front;
				while(words!=null)
				{
					if((words.data).word.equals(word))
						break;
					words = words.next;
				}
				if(words!=null)
				{
					WordEntry word_entry = (words.data);
					MyLinkedList<Position> positionList = new MyLinkedList<Position>();
					WordEntry.page_positions temp_pos = word_entry.list.front.data;
					MyLinkedList<Position>.Node pos = temp_pos.list_avlTrees.returnList(temp_pos.list_avlTrees.root, positionList).front;
					if(pos != null)
					{
						while(pos.next!=null)
						{
							System.out.print((pos.data).wordIndex+", ");
							pos = pos.next;
						}
						System.out.println((pos.data).wordIndex);
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
		else if(args[0].equals("queryFindPagesWhichContainAllWords"))
		{
			System.out.print(actionMessage+" :");
			String[] words = new String[args.length-1];
			System.arraycopy(args, 1, words, 0, args.length - 1);
			MySet<PageEntry> ans = new MySet<PageEntry>();
			words[0] = words[0].toLowerCase();
			String word = words[0];
			if(word.equals("structures"))
			{
				words[0] = "structure";
				word = "structure";
			}
			if(word.equals("stacks"))
			{
				words[0] = "stack";
				word = "stack";
			}
			if(word.equals("applications"))
			{
				words[0] = "application";
				word = "application";
			}
			MySet<PageEntry> pages = inv_page_index.getPagesWhichContainWord(word);
			if(pages!=null)
			{
				ans = ans.union(pages);
				for(int i=1; i<words.length; i++)
				{
					words[i] = words[i].toLowerCase();
					word = words[i];
					if(word.equals("structures"))
					{
						words[i] = "structure";
						word = "structure";
					}
					if(word.equals("stacks"))
					{
						words[i] = "stack";
						word = "stack";
					}
					if(word.equals("applications"))
					{
						words[i] = "application";
						word = "application";
					}
					pages = inv_page_index.getPagesWhichContainWord(word);
					ans = ans.intersection(pages);
				}
				if(ans!=null)
				{
					ArrayList<Comparable> search_results = new ArrayList<Comparable>();
					MySet<Comparable> temp_search = new MySet<Comparable>();
					for(int i=0; i<ans.numberOfElements(); i++)
					{
						PageEntry temp_page = ans.getElementAt(i);
						SearchResult temp = new SearchResult(temp_page, temp_page.getRelevanceOfPage(words, false));
						temp_search.addElement(temp);
					}
					MySort sort = new MySort();
					/*for(int i=0; i<ans.numberOfElements(); i++)
					{
						System.out.println(((PageEntry)temp_search.getElementAt(i).p).name);
					}*/


					search_results = sort.sortThisList(temp_search);
					for(int i=0; i<search_results.size()-1; i++)
					{
						SearchResult temp_entry = (SearchResult)search_results.get(i);
						System.out.print(temp_entry.p.name+" ");
					}
					
					SearchResult temp_entry = (SearchResult)search_results.get(search_results.size()-1);
					System.out.println(temp_entry.p.name+" ");
				}
				else
				{
					System.out.print(" No such webpages!"+"\n");
				}
			}
			else
			{
				System.out.print(" No such webpages!"+"\n");
			}
		}
		else if(args[0].equals("queryFindPagesWhichContainAnyOfTheseWords"))
		{
			System.out.print(actionMessage+" :");
			String[] words = new String[args.length-1];
			System.arraycopy(args, 1, words, 0, args.length - 1);
			MySet<PageEntry> ans = new MySet<PageEntry>();
			words[0] = words[0].toLowerCase();
			String word = words[0];
			if(word.equals("structures"))
			{
				words[0] = "structure";
				word = "structure";
			}
			if(word.equals("stacks"))
			{
				words[0] = "stack";
				word = "stack";
			}
			if(word.equals("applications"))
			{
				words[0] = "application";
				word = "application";
			}
			MySet<PageEntry> pages = inv_page_index.getPagesWhichContainWord(word);
			if(pages!=null)
			{
				ans = ans.union(pages);
				for(int i=1; i<words.length; i++)
				{
					words[i] = words[i].toLowerCase();
					word = words[i];
					if(word.equals("structures"))
					{
						words[i] = "structure";
						word = "structure";
					}
					if(word.equals("stacks"))
					{
						words[i] = "stack";
						word = "stack";
					}
					if(word.equals("applications"))
					{
						words[i] = "application";
						word = "application";
					}
					pages = inv_page_index.getPagesWhichContainWord(word);
					ans = ans.union(pages);
				}
				if(ans!=null)
				{
					ArrayList<Comparable> search_results = new ArrayList<Comparable>();
					MySet<Comparable> temp_search = new MySet<Comparable>();
					for(int i=0; i<ans.numberOfElements(); i++)
					{
						PageEntry temp_page = ans.getElementAt(i);
						SearchResult temp = new SearchResult(temp_page, temp_page.getRelevanceOfPage(words, false));
						temp_search.addElement(temp);
					}
					MySort sort = new MySort();
					/*for(int i=0; i<ans.numberOfElements(); i++)
					{
						System.out.println(((PageEntry)temp_search.getElementAt(i).p).name);
					}*/


					search_results = sort.sortThisList(temp_search);
					for(int i=0; i<search_results.size()-1; i++)
					{
						SearchResult temp_entry = (SearchResult)search_results.get(i);
						System.out.print(temp_entry.p.name+" ");
					}
					
					SearchResult temp_entry = (SearchResult)search_results.get(search_results.size()-1);
					System.out.println(temp_entry.p.name+" ");
				}
				else
				{
					System.out.print(" No such webpages!"+"\n");
				}
			}
			else
			{
				System.out.print(" No such webpages!"+"\n");
			}
		}
		else if(args[0].equals("queryFindPagesWhichContainPhrase"))
		{
			System.out.print(actionMessage+" :");
			String[] words = new String[args.length-1];
			System.arraycopy(args, 1, words, 0, args.length - 1);
			for(int i=0; i<words.length ; i++)
			{
				words[i] = words[i].toLowerCase();
				String word = words[i];
				if(word.equals("structures"))
				{
					words[i] = "structure";
				}
				if(word.equals("stacks"))
				{
					words[i] = "stack";
				}
				if(word.equals("applications"))
				{
					words[i] = "application";
				}
			}
			MySet<PageEntry> ans = inv_page_index.getPagesWhichContainPhrase(words);

			/*System.out.println("Start");
			for(int i=0; i<ans.numberOfElements(); i++)
			{
				PageEntry r = ans.getElementAt(i);
				System.out.println(r.name);
			}*/

			//System.out.println("Hey");
			if(ans!=null)
			{
				ArrayList<Comparable> search_results = new ArrayList<Comparable>();
				MySet<Comparable> temp_search = new MySet<Comparable>();
				for(int i=0; i<ans.numberOfElements(); i++)
				{
					PageEntry temp_page = ans.getElementAt(i);
					SearchResult temp = new SearchResult(temp_page, temp_page.getRelevanceOfPage(words, true));
					temp_search.addElement(temp);
				}
				/*System.out.println("Before sort");
				for(int i=0; i<temp_search.numberOfElements(); i++)
				{
					SearchResult temp_entry = (SearchResult)temp_search.getElementAt(i);
					System.out.print(temp_entry.p.name+" ");
				}*/


				MySort sort = new MySort();
				/*for(int i=0; i<ans.numberOfElements(); i++)
				{
					System.out.println(((PageEntry)temp_search.getElementAt(i).p).name);
				}*/


				search_results = sort.sortThisList(temp_search);
				//System.out.println("After sort");
				for(int i=0; i<search_results.size()-1; i++)
				{
					SearchResult temp_entry = (SearchResult)search_results.get(i);
					System.out.print(temp_entry.p.name+" ");
				}
				
				if(search_results.size()>0)
				{
					SearchResult temp_entry = (SearchResult)search_results.get(search_results.size()-1);
					System.out.println(temp_entry.p.name+" ");
				}
			}
			
		}
	}

	public static void main(String[] args)
	{
		SearchEngine engine = new SearchEngine();
		BufferedReader br = null;
		try
		{
			br = new BufferedReader(new FileReader("actions.txt"));
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
