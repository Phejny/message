package messenger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;


public class DataGenerator 
{

	public static String getHobby() throws IOException{
    	String filename = "/home/nosql/Desktop/eclipse/repo/Messenger/src/docs/sport.txt";
        
    	BufferedReader in = new BufferedReader(new FileReader(filename));
        int zeile = 0;
        while ( in.readLine() != null ) {
        	zeile++;
        }
        
        int zahl2 = (int)((Math.random()) * zeile + 1);
    	try (Stream<String> lines = Files.lines(Paths.get(filename))) {
    		return lines.skip(zahl2).findFirst().get();
    	}
    	catch(Exception e) {
    		return "ERROR HOBBIE";
    	}
    }
    
    
    public static String getName() throws IOException{
    	String filename = "/home/nosql/Desktop/eclipse/repo/Messenger/src/docs/name.txt";
        
    	BufferedReader in = new BufferedReader(new FileReader(filename));
        int zeile = 0;
        while ( in.readLine() != null ) {
        	zeile++;
        }
        
        int zahl2 = (int)((Math.random()) * zeile + 1);
    	try (Stream<String> lines = Files.lines(Paths.get(filename))) {
    		return lines.skip(zahl2).findFirst().get();
    	}
    	catch(Exception e) {
    		return "ERROR Name";
    	}
    }
    
    public static String getVorname() throws IOException{
    	String filename = "/home/nosql/Desktop/eclipse/repo/Messenger/src/docs/vornamen.txt";
        
    	BufferedReader in = new BufferedReader(new FileReader(filename));
        int zeile = 0;
        while ( in.readLine() != null ) {
        	zeile++;
        }
        
        int zahl2 = (int)((Math.random()) * zeile + 1);
    	try (Stream<String> lines = Files.lines(Paths.get(filename))) {
    		return lines.skip(zahl2).findFirst().get();
    	}
    	catch(Exception e) {
    		return "ERROR Vorname";
    	}
    }
    
    public static int getAlter() throws IOException{
    	
    	int alter = (int)((Math.random()) * 80 + 1);
    	return alter;
    	
    }
    
    public static String getMail(String vorname, String nachname) throws IOException{
    	String filename = "/home/nosql/Desktop/eclipse/repo/Messenger/src/docs/mail.txt";
        String mail = vorname + "." + nachname;
        String anbieter ="";
        
    	BufferedReader in = new BufferedReader(new FileReader(filename));
        int zeile = 0;
        while ( in.readLine() != null ) {
        	zeile++;
        }
        
        int zahl2 = (int)((Math.random()) * zeile + 1);
    	try (Stream<String> lines = Files.lines(Paths.get(filename))) {
    		anbieter = lines.skip(zahl2).findFirst().get();
    	}
    	catch(Exception e) {
    		return "ERROR Mail";
    	}
    	mail = mail + anbieter;
    	return mail;
    }
    
    public static String getAdresse() throws IOException{
    	String filenameSta = "/home/nosql/Desktop/eclipse/repo/Messenger/src/docs/stadt.txt";
    	String filenameStr = "/home/nosql/Desktop/eclipse/repo/Messenger/src/docs/strasse.txt";
    	
    	String stadt = "";
    	String strasse = "";
    	
    	BufferedReader in = new BufferedReader(new FileReader(filenameSta));
        int zeile = 0;
        while ( in.readLine() != null ) {
        	zeile++;
        }
        
        int zahl = (int)((Math.random()) * zeile + 1);
    	
        
        try (Stream<String> lines = Files.lines(Paths.get(filenameSta))) {
        	stadt = lines.skip(zahl).findFirst().get();
    	}
        
        BufferedReader in2 = new BufferedReader(new FileReader(filenameStr));
        int zeile2 = 0;
        while ( in2.readLine() != null ) {
        	zeile++;
        }
        
        int zahl2 = (int)((Math.random()) * zeile2 + 1);
    	
        
        try (Stream<String> lines = Files.lines(Paths.get(filenameStr))) {
        	strasse = lines.skip(zahl2).findFirst().get();
    	}
        
        
        int hNummer = (int)((Math.random()) * 100 + 1);
    	
    	String adresse = stadt + " " + strasse + " " + hNummer;
    	return adresse;
    }
    
    public static String getTelefon() throws IOException{
        
        int telpast = (int)((Math.random()) * 10000000 + 1);
        
        String tel = "0176" + telpast;
        return tel;
    }
    
    public void fillDatabase(int anzahl) throws IOException, SQLException
    {
    	
    	List<String> hobbylist = new ArrayList<>();
    	List<String> namelist = new ArrayList<>();
    	List<String> vornamelist = new ArrayList<>();
    	List<Integer> alterlist = new ArrayList<>();
    	List<String> adresslist = new ArrayList<>();
    	List<String> telelist = new ArrayList<>();
    	List<NichtKunde> kundenlist = new ArrayList<>();
    	
		for(int i = 0; i < anzahl; i++)
		{
			hobbylist.add(getHobby());
			namelist.add(getName());
			vornamelist.add(getVorname());
			alterlist.add(getAlter());
			adresslist.add(getAdresse());
			telelist.add(getTelefon());
		}
		
		
		Random gen = new Random();
		for (int i = 0; i < anzahl; i++) 
		{
			String vn = vornamelist.get(Math.abs(gen.nextInt()) % vornamelist.size());
			String nn = namelist.get(Math.abs(gen.nextInt()) % namelist.size());
			String age = String.valueOf(alterlist.get(Math.abs(gen.nextInt()) % alterlist.size()));
			String adr = adresslist.get(Math.abs(gen.nextInt()) % adresslist.size());
			String tel = telelist.get(Math.abs(gen.nextInt()) % telelist.size());
			String email = getMail(vn, nn);
			String hobby = hobbylist.get(Math.abs(gen.nextInt()) % hobbylist.size());
			
			kundenlist.add(new NichtKunde(vn+anzahl, vn, nn, age, adr, tel, email, hobby));
		}
		
		Random nachricht = new Random();
		for (int i = 0; i < anzahl; i++) 
		{
			String message = hobbylist.get(Math.abs(nachricht.nextInt()) % hobbylist.size()) +" "+ hobbylist.get(Math.abs(nachricht.nextInt()) % hobbylist.size()) +" "+ hobbylist.get(Math.abs(nachricht.nextInt()) % hobbylist.size());
			NichtKunde a = kundenlist.get(Math.abs(nachricht.nextInt()) % kundenlist.size());
			NichtKunde b = kundenlist.get(Math.abs(nachricht.nextInt()) % kundenlist.size());
			
			a.schreibeNachricht(message, b);
		}
		
		
		
		
    }

}