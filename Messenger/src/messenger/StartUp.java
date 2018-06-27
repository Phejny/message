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
		List<String> list = new ArrayList<String>(Arrays.asList("Peter", "Hans", "Hans","franz", "hans"));
		p.wordCount(list);
	}
}
