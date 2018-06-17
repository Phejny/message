package messenger;


public class StartUp 
{
	public static void main(String[] args)
	{
		//NichtKunde a = new NichtKunde("Lars1", "Lars", "eins", "18", "Larsstieg 1", "123456", "l1@l.de");
		//NichtKunde b = new NichtKunde("Lars2", "Larsa", "zwei", "19", "Larsstieg 2", "1234567", "l2@l.de");
		//NichtKunde c = new NichtKunde("Lars3", "Larso", "drei", "20", "Larsstieg 3", "1234568", "l3@l.de");
		//NichtKunde d = new NichtKunde("Lars4", "Larse", "vier", "21", "Larsstieg 4", "1234569", "l4@l.de");
		
		//NichtKunde e = new NichtKunde("Pascal1", "Pascal", "fuenf", "22", "Pascalstieg 1", "555333", "p1@p.de");
		//NichtKunde f = new NichtKunde("Pascal2", "Pascala", "sechs", "23", "Pascalstieg 2", "555444", "p2@p.de");
		//NichtKunde g = new NichtKunde("Pascal3", "Pascalo", "sieben", "24", "Pascalstieg 3", "555666", "p3@p.de");
		//NichtKunde h = new NichtKunde("Pascal4", "Pascale", "acht", "25", "Pascalstieg 4", "555777", "p4@p.de");
		
		//a.schreibeNachricht("grillen schlafen schwimmen", b);
		//b.schreibeNachricht("spielen klettern sport", a);
		
		Hbase h = new Hbase();
		h.getRecievedMessages("Lars1");
		
	}
}
