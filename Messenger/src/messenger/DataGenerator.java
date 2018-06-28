package messenger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


public class DataGenerator 
{

	public static String getHobby() throws IOException{
    	String filename = "/Users/larsadler/Desktop/NoSql/sport.txt";
        
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
    	String filename = "/Users/larsadler/Desktop/NoSql/name.txt";
        
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
    	String filename = "/Users/larsadler/Desktop/NoSql/vornamen.txt";
        
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
    	String filename = "/Users/larsadler/Desktop/NoSql/mail.txt";
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
    	String filenameSta = "/Users/larsadler/Desktop/NoSql/stadt.txt";
    	String filenameStr = "/Users/larsadler/Desktop/NoSql/strasse.txt";
    	
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

}