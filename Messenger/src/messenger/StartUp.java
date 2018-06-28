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
		
		
		g.fillDatabase(10000);
		
		
	
	
		
	}
}
