import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class extra
{
	static public int end = 0;
	public static double start_time;
	public static int random_end_time = 0;
	public static boolean random = false;
	public static void main ( String args [])
	{
		BufferedReader br = null;
		start_time = ((double)System.nanoTime()/1000000000);
		RoutingMapTree r = new RoutingMapTree(start_time);
		System.out.println("Do you want random mode or file reading mode? Answer in r or f");
		Scanner s = new Scanner(System.in);
		String res = s.nextLine();
		if(res.equals("r"))
		{
			System.out.println("Input the simulation time in seconds:");
			random_end_time = s.nextInt();
			random = true;
			try 
			{
				String actionString;
				br = new BufferedReader(new FileReader("test1.txt"));
				double time;
				while ((actionString = br.readLine()) != null) 
				{
					String arg[] = actionString.split("\\s+");
					time = ((double)System.nanoTime()/1000000000) - start_time;
					while(time < Integer.parseInt(arg[0]))
					{
						time = ((double)System.nanoTime()/1000000000) - start_time;
					}
					r.performAction(actionString);
				}
				end = 1;
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			} 
			finally 
			{
				try 
				{
					if (br != null)br.close();
				} 
				catch (IOException ex) 
				{
					ex.printStackTrace();
				}
			}	
		}
		else
		{
			try 
			{
				String actionString;
				br = new BufferedReader(new FileReader("test.txt"));
				double time;
				while ((actionString = br.readLine()) != null) 
				{
					String arg[] = actionString.split("\\s+");
					time = ((double)System.nanoTime()/1000000000) - start_time;
					while(time < Integer.parseInt(arg[0]))
					{
						time = ((double)System.nanoTime()/1000000000) - start_time;
					}
					r.performAction(actionString);
				}
				end = 1;
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			} 
			finally 
			{
				try 
				{
					if (br != null)br.close();
				} 
				catch (IOException ex) 
				{
					ex.printStackTrace();
				}
			}
		}
		s.close();
	}
}
