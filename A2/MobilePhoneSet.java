import java.util.NoSuchElementException;

public class MobilePhoneSet
{
	Myset set;
	public MobilePhoneSet()
	{
		set = new Myset();
	}

	public MobilePhone return_mobile_by_mobileid(int a) throws NoSuchElementException
	{
		Myset.node cur = set.start;
		while(cur!=null)
		{
			if(((MobilePhone)cur.data).id == a)
			{
				return (MobilePhone)cur.data;
			}
			cur = cur.next;
		}
		throw new NoSuchElementException();
	}

	public  boolean contains_mobile(int a)
	{
		Myset.node cur = set.start;
		while(cur!=null)
		{
			if(((MobilePhone)cur.data).id == a)
			{
				return true;
			}
			cur = cur.next;
		}
		return false;
	}

	public void print_mobiles()
	{
		Myset.node cur = set.start;
		if(cur == null)
		{
			System.out.print("This Exchange does not have any mobile phones!");
			return;
		}
		while(cur.next!=null)
		{
			if(((MobilePhone)cur.data).status())
				System.out.print(((MobilePhone)cur.data).id +", ");
			cur = cur.next;
		}
		System.out.print(((MobilePhone)cur.data).id);
	}
}