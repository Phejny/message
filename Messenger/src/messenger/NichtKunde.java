package messenger;

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
	 */
	public NichtKunde(String uid, String vn, String nn, String age, String adr, String tel, String email)
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
	 * 
	 */
	public void schreibeNachricht(String message, NichtKunde empfaenger)
	{
		Hbase h = new Hbase();
		h.setChatRecord(userId, empfaenger.getuserId(), message);
		h.closeAll();
	}
	
	/**
	 * 
	 * @return
	 */
	private boolean testeObVorhanden()
	{
		boolean b = false;
		if(h.getUser(this.userId).equals(this.userId))
		{
			b = true;
		}
		return b;
	}
	
	/**
	 * 
	 */
	public void empfangeNachricht(String ts)
	{
		h.getChatRecord(this.userId, ts);
	}
	
	public String getuserId()
	{
		return userId;
	}
	
	public void PersonalData()
	{
		h.getPersonalData(this.userId);
	}
}
