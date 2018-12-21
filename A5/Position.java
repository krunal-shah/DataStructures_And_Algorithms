public class Position
{
	PageEntry page;
	int wordIndex;
	int previousConnectors;
	
	public Position(PageEntry p, int wordIndex, int prevCon)
	{
		page = p;
		this.wordIndex = wordIndex;
		previousConnectors = prevCon;
	}
					
	public PageEntry getPageEntry()
	{
		return page;
	}
	
	public int getWordIndex()
	{
		return wordIndex;
	}
}