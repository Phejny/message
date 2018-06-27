package messenger;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StartUp 
{
	public static void main(String[] args) throws IOException, SQLException
	{
		MapReduce p = new MapReduce();
		Hbase h = new Hbase();
		
		List<String> list = h.getMessages("Pascal");
		
		p.wordCount(list);
		
	}
}
