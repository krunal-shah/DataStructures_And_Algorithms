import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class PageEntry
{
	PageIndex page;
	String name;
	MyHashTable hash_table;

	public PageEntry(String pageName, MyHashTable hash_table) throws IOException
	{
		this.hash_table = hash_table;
		name = pageName;
		BufferedReader br = null;
		String[] connectors = {"a", "an", "the", "they", "these", "this", "for", "is", "are", "was", "of", "or", "and", "does", "will", "whose"};
		String[] punctuations = {"{","}","[","]","<",">","=","(",")",".",",",";","'","\"","?","#","!","-",":"};
		try
		{
			br = new BufferedReader(new FileReader(pageName));
			page = new PageIndex();
			String actionString;
			int word_position = 1;
			int previousConnectors = 0;
			while ((actionString = br.readLine()) != null)
			{
				String[] words = actionString.split("[\\s\\{\\}\\[\\]\\<\\>\\=\\(\\)\\.\\,\\;\\'\\”\\?\\#\\!\\-\\:]+");
				//System.out.println(actionString);
				for(int i=0; i < words.length; i++)
				{
					String word = words[i];
					if(word.length()>0)
					{
						if(Arrays.asList(punctuations).contains(word.substring(0,1)))
						{
							word = word.substring(1,word.length());
						}
						if(word.length()>0)
						{
							if(Arrays.asList(punctuations).contains(word.substring(word.length()-1,word.length())))
							{
								word = word.substring(0,word.length()-1);
							}
							if(word.length()>0)
							{
								if(word.equals("structures"))
									word = "structure";
								if(word.equals("stacks"))
									word = "stack";
								if(word.equals("applications"))
									word = "application";
								if(Arrays.asList(connectors).contains(word.toLowerCase()))
								{
									//System.out.println("Connector  "+word);
									//System.out.println("!!!!!!!!!!!!!!!!!!!!!!!"+" "+word);
									word_position++;
									previousConnectors++;
								}
								else
								{
									/*if(word.toLowerCase().equals("allain"))
										System.out.println("Fuck yeah "+word_position);*/
									Position position = new Position(this,word_position, previousConnectors);
									previousConnectors = 0;
									//System.out.println(word.toLowerCase());
									page.addPositionForWord(word.toLowerCase(),position);
									word_position++;
								}
							}
						}	
					}
				}
			}
		}
		catch (IOException e)
		{
			throw new IOException();
		}
	}

	public float getRelevanceOfPage(String[] str, boolean doTheseWordsRepresentAPhrase)
	{
		if(doTheseWordsRepresentAPhrase)
		{
			MyLinkedList<WordEntry> entries = page.getWordEntries();
			MyLinkedList<WordEntry>.Node entry_node = entries.front;
			MyLinkedList<Position> ans = new MyLinkedList<Position>();
			entry_node = entries.front;
			while(entry_node != null)
			{
				WordEntry temp_word = entry_node.data;
				if(temp_word.word.equals(str[0]))
				{
					MyLinkedList<Position> temp = new MyLinkedList<Position>();
					if(temp_word.getAvlTree(name)!=null)
					{
						avlTree<Position> temp_tree = temp_word.getAvlTree(name).list_avlTrees;
						MyLinkedList<Position> incoming = new MyLinkedList<Position>();
						incoming = temp_tree.returnList(temp_tree.root, incoming);
						MyLinkedList<Position>.Node temp_position = incoming.front;
						while(temp_position != null)
						{
							Position iterative_position = temp_position.data;
							//System.out.println(iterative_position.wordIndex);
							Position insert = new Position(iterative_position.page, iterative_position.wordIndex, iterative_position.previousConnectors);
							temp.insertAtEnd(insert);
							temp_position = temp_position.next;
						}

						/*MyLinkedList<Position>.Node temp_position1 = incoming.front;
						System.out.println("here it1 comes");
						while(temp_position1 != null)
						{
							Position iterative_position1 = temp_position1.data;
							System.out.println(iterative_position1.wordIndex);
							temp_position1 = temp_position1.next;
						}*/

						ans = ans.union(incoming);
					}
					else
						return 0;
				}
				entry_node = entry_node.next;
			}

			//System.out.println("end");

			for(int i = 1; i < str.length; i++)
			{
				entry_node = entries.front;
				while(entry_node != null)
				{
					WordEntry temp_word = entry_node.data;
					if(temp_word.word.equals(str[i]))
					{
						MyLinkedList<Position> temp = new MyLinkedList<Position>();
						if(temp_word.getAvlTree(name)!=null)
						{
							avlTree<Position> temp_tree = temp_word.getAvlTree(name).list_avlTrees;
							MyLinkedList<Position> incoming = new MyLinkedList<Position>();
							incoming = temp_tree.returnList(temp_tree.root, incoming);
							MyLinkedList<Position>.Node temp_position = incoming.front;
							while(temp_position != null)
							{
								Position iterative_position = temp_position.data;
								//System.out.println(iterative_position.wordIndex);
								Position insert = new Position(iterative_position.page, iterative_position.wordIndex - i - iterative_position.previousConnectors, iterative_position.previousConnectors);
								temp.insertAtEnd(insert);
								temp_position = temp_position.next;
							}
							

						
							/*MyLinkedList<Position>.Node temp_position1 = temp.front;
							System.out.println("here it2 comes");
							while(temp_position1 != null)
							{
								Position iterative_position1 = temp_position1.data;
								System.out.println(iterative_position1.wordIndex);
								temp_position1 = temp_position1.next;
							}*/
							ans = intersection(temp, ans);
							break;
						}
						else
							return 0;
					}
					entry_node = entry_node.next;
				}
				if(entry_node == null)
					return 0;
			}
			float relevance = 0;
			MyLinkedList<Position>.Node temp_position = ans.front;
			//System.out.println("Here it comes");
			while(temp_position != null)
			{
				Position iterative_position = temp_position.data;
				//System.out.println(iterative_position.wordIndex);
				relevance += (1/Math.pow(iterative_position.wordIndex,2));
				temp_position = temp_position.next;
			}

			//System.out.println("Relevance of "+name+" is "+relevance);

			return relevance;
		}
		else
		{
			//System.out.println("Enter ");
			MyLinkedList<WordEntry> entries = page.getWordEntries();
			MyLinkedList<WordEntry>.Node entry_node = entries.front;
			MyLinkedList<Position> ans = new MyLinkedList<Position>();
			entry_node = entries.front;
			for(int i = 0; i < str.length; i++)
			{
				entry_node = entries.front;
				while(entry_node != null)
				{
					WordEntry temp_word = entry_node.data;
					if(temp_word.word.equals(str[i]))
					{
						//System.out.println(temp_word.word);
						MyLinkedList<Position> temp = new MyLinkedList<Position>();
						if(temp_word.getAvlTree(name)!=null)
						{
							avlTree<Position> temp_tree = temp_word.getAvlTree(name).list_avlTrees;
							MyLinkedList<Position> incoming = new MyLinkedList<Position>();
							incoming = temp_tree.returnList(temp_tree.root, incoming);
							/*MyLinkedList<Position>.Node temp_position1 = incoming.front;
							System.out.println("here it2 comes");
							while(temp_position1 != null)
							{
								Position iterative_position1 = temp_position1.data;
								System.out.println(iterative_position1.wordIndex);
								temp_position1 = temp_position1.next;
							}*/



							ans = union(incoming, ans);
						}
					}
					entry_node = entry_node.next;
				}
			}
			float relevance = 0;
			MyLinkedList<Position>.Node temp_position = ans.front;



			//System.out.println("Here it comes");
			while(temp_position != null)
			{
				Position iterative_position = temp_position.data;
				//System.out.println(iterative_position.wordIndex);
				relevance += (1/Math.pow(iterative_position.wordIndex,2));
				temp_position = temp_position.next;
			}

			//System.out.println("Relevance of "+name +" "+relevance);

			return relevance;
		}

		
	}


	public MyLinkedList<Position> intersection(MyLinkedList<Position> set1, MyLinkedList<Position> set2)
	{
		MyLinkedList<Position> ans = new MyLinkedList<Position>();
		MyLinkedList<Position>.Node temp = set1.front;
		while(temp != null)
		{
			if(isPresent(temp.data,set2))
				ans.insertAtEnd(temp.data);
			temp = temp.next;
		}
		return ans;
	}

	public MyLinkedList<Position> union(MyLinkedList<Position> set1, MyLinkedList<Position> set2)
	{
		MyLinkedList<Position> ans = new MyLinkedList<Position>();
		MyLinkedList<Position>.Node temp = set1.front;
		while(temp != null)
		{
			if(!isPresent(temp.data,ans))
				ans.insertAtEnd(temp.data);
			temp = temp.next;
		}
		temp = set2.front;
		while(temp != null)
		{
			if(!isPresent(temp.data,ans))
				ans.insertAtEnd(temp.data);
			temp = temp.next;
		}
		return ans;
	}

	public boolean isPresent(Position pos, MyLinkedList<Position> pos_list)
	{
		MyLinkedList<Position>.Node temp = pos_list.front;
		while(temp != null)
		{
			Position temp_pos = temp.data;
			if(temp_pos.wordIndex == pos.wordIndex && temp_pos.page.name.equals(pos.page.name))
				return true;
			temp = temp.next;
		}
		return false;
	}






	public PageIndex getPageIndex()
	{
		return page;
	}
}