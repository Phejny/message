package messenger;

import java.io.IOException;

public class StartUp 
{
	public static void main(String[] args) throws IOException
	{
		NichtKunde a = new NichtKunde("Pascal", "a", "b", "c", "d", "e", "f");
		NichtKunde b = new NichtKunde("Lars", "g", "h", "i", "j", "k", "l");
		
		a.schreibeNachricht("Hallo g", b);
		
		Hbase hb = new Hbase();
		hb.putImage(a.getuserId(), "haw.jpg");
		hb.getImage(a.getuserId(), "haw.jpg");
	}
}
