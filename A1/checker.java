import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class checker
{


	//	TAKE CARE OF THE NEGATIVE VALUES AND PRINT THE PROFIT(YET TO BE DONE)

	public static void main ( String args [])
	{
		BufferedReader br = null;
		//stock r = new stock();

		try
		{
			String actionString;
			br = new BufferedReader(new FileReader("input2.txt"));
			double start = ((double)System.nanoTime()/1000000000);
			test order = new test('o',start);
			test exchange = new test('e',start);
			test cleanup = new test('c',start);
			Thread Order = new Thread(order);
			Thread Exchange = new Thread(exchange);
			Thread Cleanup = new Thread(cleanup);
			Order.start();
			Exchange.start();
			Cleanup.start();
			double time;
			while ((actionString = br.readLine()) != null)
			{
				String[] arg = actionString.split("\\t");
				int input_time = Integer.parseInt(arg[0]);
				int time_exp = Integer.parseInt(arg[2]);
				time = (((double)System.nanoTime()/1000000000) - start);
				while(input_time>time)
				{
					time = ((double)System.nanoTime()/1000000000) - start;
				}
				if(time<(time + time_exp))
				{
					//System.out.println(actionString);
					order.set_string(actionString);
				}
				try
				{
					Thread.sleep(50);
				}
				catch(InterruptedException a){}
			}
			//Order.start();
			test.end=1;
			try
			{
				Order.join();
				Exchange.join();
				Cleanup.join();
			}
			catch(InterruptedException a)
			{}
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
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}
}
