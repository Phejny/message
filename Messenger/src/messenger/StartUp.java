package messenger;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.neo4j.driver.v1.types.MapAccessor;

public class StartUp 
{
	public static void main(String[] args) throws IOException, SQLException
	{
		
		Hbase h = new Hbase();
		List<String> list = h.getRowKeys();
			
		for(String s : list)
		{
			System.out.println(s);
		}
		
	}
}
