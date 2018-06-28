package messenger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;





public class Neo4j 
{

	public Neo4j() throws SQLException
	{
		
	}
	
	public void setUser(String userid, String hobby) throws SQLException
	{
		try(Connection con = DriverManager.getConnection("jdbc:neo4j:bolt://localhost", "neo4j", "bigdata"))
		{
			String createPerson = "MERGE (p:Person{userid: '"+userid+"'})"
								+ "MERGE (h:Hobby{userid: '"+hobby+"'})"
								+ "MERGE (p)-[:LIKES]->(h)";
			Statement stmt = con.createStatement();
			stmt.executeQuery(createPerson);
			System.out.println("Person angelegt");
			
			
			
			
		}
	}
	
	public void setFriend(String userid, String empfaenger) throws SQLException
	{
		try(Connection con = DriverManager.getConnection("jdbc:neo4j:bolt://localhost", "neo4j", "bigdata"))
		{
			String createPerson = "MERGE (p:Person{userid: '"+userid+"'})"
								+ "MERGE (q:Person{userid: '"+empfaenger+"'})"
								+ "MERGE (p)-[:KNOWS]->(q)";
			Statement stmt = con.createStatement();
			stmt.executeQuery(createPerson);
			System.out.println("Beziehung angelegt");
				
			
		}
	}

}
