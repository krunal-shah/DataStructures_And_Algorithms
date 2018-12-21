public class Position
{
	PageEntry page;
	int wordIndex;
	
	public Position(PageEntry p, int wordIndex)
	{
		page = p;
		this.wordIndex = wordIndex;
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