package messenger;

import java.io.IOException;
import java.sql.SQLException;

public class NichtKunde 
{
	
	private String userId;
	private String vorname;
	private String nachname;
	private String alter;
	private String adress;
	private String telefon;
	private String eMail;
	private String hhobby;
	private Hbase h;
	
	/**
	 * Initialisert einen NichtKunden
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public NichtKunde(String uid, String vn, String nn, String age, String adr, String tel, String email, String hobby) throws IOException, SQLException
	{
		userId = uid;
		vorname = vn;
		nachname= nn;
		alter = age;
		adress = adr;
		telefon = tel;
		eMail = email;
		hhobby = hobby;
		
		h = new Hbase();
		
		if(testeObVorhanden() == false)
		{
			h.setUser(userId, vorname, nachname, alter, adress, telefon, eMail, hhobby);
			Neo4j n = new Neo4j();
			n.setUser(this.userId, this.hhobby);
		}
	}
	
	/**
	 * @throws IOException 
	 * @throws SQLException 
	 * 
	 */
	public void schreibeNachricht(String message, NichtKunde empfaenger) throws IOException, SQLException
	{
		Hbase h = new Hbase();
		h.setChatRecordSent(userId, empfaenger.getuserId(), message);
		h.setChatRecordRecieved(userId, empfaenger.getuserId(), message);
		h.closeAll();
	}
	
	/**
	 * 
	 * @return
	 * @throws IOException 
	 */
	private boolean testeObVorhanden() throws IOException
	{
		boolean b = false;
		if(h.getUser(this.userId).equals(this.userId))
		{
			b = true;
		}
		return b;
	}
	
	/**
	 * @throws IOException 
	 * 
	 */
	public void getNachrichten() throws IOException
	{
		h.getMessages(this.userId);
	}
	
	public String getuserId()
	{
		return userId;
	}
	
	public void seePersonalData() throws IOException
	{
		h.getPersonalData(this.userId);
	}
}
