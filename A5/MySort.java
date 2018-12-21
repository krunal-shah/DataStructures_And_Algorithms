import java.util.ArrayList;

public class MySort
{
	public ArrayList<Comparable> sortThisList(MySet<Comparable> listOfSortableEntries)
	{
		ArrayList<Comparable> ans = new ArrayList<Comparable>();
		int n = listOfSortableEntries.numberOfElements();
		Comparable max;
		int max_int = 0;
		for(int i=0; i < n; i++)
		{
			ans.add(listOfSortableEntries.getElementAt(i));
		}
		for(int i = 0; i < n; i++)
		{
			max = ans.get(i);
			max_int = i;
			for(int j = i+1; j < n; j++)
			{
				Comparable tem = ans.get(j);
				if(tem.compareTo(max) > 0)
				{
					max = ans.get(j);
					max_int = j;
				}
			}
			if(i!=max_int)
			{
				Comparable temp = ans.get(i);
				ans.set(i, max);
				ans.set(max_int, temp);
			}
			//ans.add(max);
		}
		return ans;


		/*for(int i = 0; i < n; i++)
		{
			max = listOfSortableEntries.getElementAt(i);
			max_int = i;
			for(int j = i+1; j < n; j++)
			{
				Comparable tem = listOfSortableEntries.getElementAt(j);
				if(tem.compareTo(max) > 0)
				{
					max = listOfSortableEntries.getElementAt(j);
					max_int = j;
				}
			}
			if(i!=max_int)
				listOfSortableEntries.exchange(i,max_int);
			ans.add(max);
		}
		return ans;*/
	}

	/*public static void main(String[] args)
	{
		MySort t = new MySort();
		MySet<Comparable> a = new MySet<Comparable>();
		a.addElement(new Integer(2));
		a.addElement(new Integer(3));
		a.addElement(new Integer(7));
		a.addElement(new Integer(1));
		ArrayList<Comparable> b = t.sortThisList(a);
		for(int i=0; i<b.size(); i++)
		{
			System.out.println(b.get(i));
		}
	}*/
}