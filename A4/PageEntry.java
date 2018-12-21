import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class PageEntry
{
	PageIndex page;
	String name;

	public PageEntry(String pageName) throws IOException
	{
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
								}
								else
								{
									/*if(word.toLowerCase().equals("allain"))
										System.out.println("Fuck yeah "+word_position);*/
									Position position = new Position(this,word_position);
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
	
	public PageIndex getPageIndex()
	{
		return page;
	}
}