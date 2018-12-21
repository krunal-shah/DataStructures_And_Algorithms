import java.util.NoSuchElementException;

public class MobilePhone
{
	public int id;
	public boolean state;
	public Exchange exc;

	public MobilePhone(int a)
	{
		id = a;
		state = false;
		exc = null;
	}

	public int number()
	{
		return id;
	}

	public boolean status()
	{
		return state;
	}

	public void switchOn()
	{
		state = true;
	}

	public void switchOff()
	{
		state = false;
	}

	public Exchange location() throws NoSuchElementException
	{
		if(state)
		{
			return exc;
		}
		else
		{
			throw new NoSuchElementException();
		}
	}
}