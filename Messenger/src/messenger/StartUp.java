package messenger;

import java.io.IOException;

public class StartUp 
{
	public static void main(String[] args) throws IOException
	{
		
		
		Hbase hb = new Hbase();
		
		hb.getRecievedMessages("Lars2");
		
	}
}
