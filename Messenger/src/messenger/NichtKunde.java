package messenger;

import java.io.IOException;

public class NichtKunde 
{
	private String userId;
	private String vorname;
	private String nachname;
	private String alter;
	private String adress;
	private String telefon;
	private String eMail;
	private Hbase h;
	
	/**
	 * Initialisert einen NichtKunden
	 * @throws IOException 
	 */
	public NichtKunde(String uid, String vn, String nn, String age, String adr, String tel, String email) throws IOException
	{
		userId = uid;
		vorname = vn;
		nachname= nn;
		alter = age;
		adress = adr;
		telefon = tel;
		eMail = email;
		
		h = new Hbase();
		
		if(testeObVorhanden() == false)
		{
			h.setUser(userId, vorname, nachname, alter, adress, telefon, eMail);
		}
	}
	
	/**
	 * @throws IOException 
	 * 
	 */
	public void schreibeNachricht(String message, NichtKunde empfaenger) throws IOException
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
