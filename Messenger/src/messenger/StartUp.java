package messenger;

import java.io.IOException;
import java.sql.SQLException;

public class StartUp 
{
	public static void main(String[] args) throws IOException, SQLException
	{
		Neo4j n = new Neo4j();
		n.setUser("Pascal", "grillen");
		n.setUser("Miri", "putzen");
		
		n.setFriend("Pascal", "Miri");
	}
}
