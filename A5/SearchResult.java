public class SearchResult implements Comparable<SearchResult>
{
	PageEntry p;
	float r;

	public SearchResult(PageEntry p, float r)
	{
		this.p = p;
		this.r = r;
	}

	public PageEntry getPageEntry()
	{
		return p;
	}
	public float getRelevance()
	{
		return r;
	}
	
	public int compareTo(SearchResult otherObject)
	{
		if((this.r - otherObject.r)>0.0)
			return 1;
		else if((this.r - otherObject.r)==0.0)
			return 0;
		else
			return -1;
	}

}