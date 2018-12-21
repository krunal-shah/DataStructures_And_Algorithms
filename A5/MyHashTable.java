import java.util.NoSuchElementException;
import java.lang.Math;

public class MyHashTable
{
	private class Wrapper
	{
		MyLinkedList<WordEntry> list;
		public Wrapper()
		{
			list = new MyLinkedList<WordEntry>();
		}

		public int wordPresentAt(String w) throws NoSuchElementException
		{
			MyLinkedList<WordEntry>.Node temp = list.front;
			int position = 0;
			while(temp != null)
			{
				if(temp.data.word.equals(w))
					return position;
				position++;
				temp = temp.next;
			}
			throw new NoSuchElementException();
		}
	}

	Wrapper[] wrapper_list;

	public MyHashTable()
	{
		wrapper_list = new Wrapper[100];
	}

	private int getHashIndex(String str)
	{
		int first_letter = 0;
		int second_letter = 0;
		if(str.length()==1)
		{
			//first_letter = Character.tolowercase(str.charAt(0)) - 'a';
			first_letter = ((str.charAt(0)-'a'+1)<0)?(str.charAt(0)-'A'+1):(str.charAt(0)-'a'+1);
			//System.out.println(first_letter);
			//System.out.println(second_letter);
		}
		else if(str.length()>0)
		{
			first_letter = ((str.charAt(0)-'a'+1)<0)?(str.charAt(0)-'A'+1):(str.charAt(0)-'a'+1);
			second_letter = ((str.charAt(1)-'a'+1)<0)?(str.charAt(1)-'A'+1):(str.charAt(1)-'a'+1);	
			//System.out.println(first_letter);
			//System.out.println(second_letter);
		}
		return Math.abs(((first_letter*100+second_letter)%100));
	}

	public void addPositionsForWord(WordEntry w)
	{
		int word_index = getHashIndex(w.word);
		WordEntry temp_word = new WordEntry(w.word);
		temp_word.addPositionsByWord(w);
		/*temp_word.addPositions(w.list);
		System.out.println(w.word);*/

		Wrapper temp = wrapper_list[word_index];
		if(temp==null)
		{
			wrapper_list[word_index] = new Wrapper();
			temp = wrapper_list[word_index];
		}
		int word_position = 0;
		MyLinkedList<WordEntry> list = temp.list;
		MyLinkedList<WordEntry>.Node temp_node = list.front;
		while(temp_node!=null)
		{
			if(temp_node.data.word.equals(temp_word.word))
				break;
			word_position++;
			temp_node = temp_node.next;
		}
		if(word_position >= list.numberOfElements())
		{
			temp.list.insertAtEnd(temp_word);
		}
		else
		{
			WordEntry a = temp.list.getElementAt(word_position);
			a.addPositionsByWord(temp_word);
		}
	}

	/*public boolean checkPhrase(String[] str, String page_name)
	{
		for(int i = 0; i < str.length; i++)
		{
			int word_index = getHashIndex(str[i]);
			Wrapper temp = wrapper_list[word_index];
			if(temp==null)
			{
				return false;
			}
			int word_position = 0;
			MyLinkedList<WordEntry> list = temp.list;
			MyLinkedList<WordEntry>.Node temp_node = list.front;
			while(temp_node!=null)
			{
				if(temp_node.data.word.equals(str[i]))
					break;
				word_position++;
				temp_node = temp_node.next;
			}
			if(temp_node != null)
			{
				WordEntry word = temp_node.data;

			}
			else
			{
				return false;
			}
		}




		int word_index = getHashIndex(str[0]);
		WordEntry temp_word = new WordEntry(w.word);
		temp_word.addPositionsByWord(w);
		temp_word.addPositions(w.list);
		System.out.println(w.word);

		Wrapper temp = wrapper_list[word_index];
		if(temp==null)
		{
			wrapper_list[word_index] = new Wrapper();
			temp = wrapper_list[word_index];
		}
		int word_position = 0;
		MyLinkedList<WordEntry> list = temp.list;
		MyLinkedList<WordEntry>.Node temp_node = list.front;
		while(temp_node!=null)
		{
			if(temp_node.data.word.equals(temp_word.word))
				break;
			word_position++;
			temp_node = temp_node.next;
		}
		if(word_position >= list.numberOfElements())
		{
			temp.list.insertAtEnd(temp_word);
		}
		else
		{
			WordEntry a = temp.list.getElementAt(word_position);
			if(w.word.equals("to"))
				System.out.println(a.word);
			a.addPositionsByWord(temp_word);
		}
	}*/

	public MySet<PageEntry> return_page_entries(String w) throws NoSuchElementException
	{
		int word_id = getHashIndex(w);
		try
		{
			if(wrapper_list[word_id]!=null)
			{
				int word_position = wrapper_list[word_id].wordPresentAt(w);
				return wrapper_list[word_id].list.getElementAt(word_position).getAllPageEntries();
			}
		}
		catch(NoSuchElementException a)
		{
			throw new NoSuchElementException();
		}
		throw new NoSuchElementException();
	}

	/*public static void main(String[] args)
	{
		MyHashTable a = new MyHashTable();
		System.out.println(a.getHashIndex("Ab"));
	}*/

	public void print_hash()
	{
		for(int i=0;i<100; i++)
		{
			Wrapper temp1 = wrapper_list[i];
			//System.out.println(temp1.id);
			if(temp1!=null)
			{
				MyLinkedList<WordEntry> temp2 = temp1.list;
				MyLinkedList<WordEntry>.Node temp3 = temp2.front;
				while(temp3!=null)
				{
					System.out.println((temp3.data).word);
					temp3 = temp3.next;
				}
			}
		}
	}
}