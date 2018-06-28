package messenger;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class StartUp 
{
	public static void main(String[] args) throws IOException, SQLException
	{
		
		
		DataGenerator g = new DataGenerator();
		
		long s = System.currentTimeMillis();
		g.fillDatabase(2000);
		long e = System.currentTimeMillis() - s;
		long sek = e/1000;
		System.out.println("Sekunden: "+sek);
		
		
	
	
		
	}
}
