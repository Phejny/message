package messenger;


public class StartUp 
{
	public static void main(String[] args)
	{
		NichtKunde a = new NichtKunde("Peter1", "Peter", "Lustig", "30", "Lustigstieg 1", "12345", "l@lustig.de");
		
		
		a.empfangeNachricht("2018-06-16T14:06:58.058");
	}
}
