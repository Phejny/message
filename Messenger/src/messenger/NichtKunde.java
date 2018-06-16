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
	 */
	public void empfangeNachricht()
	{
		
	}
	
	public String getuserId()
	{
		return userId;
	}
}
