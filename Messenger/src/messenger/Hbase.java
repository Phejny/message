package messenger;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;

import javax.imageio.ImageIO;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.Bytes;


public class Hbase 
{
	private Connection con;
	private Table htable;
	
	
	public Hbase() throws IOException
	{
		Configuration conf = HBaseConfiguration.create();
		con = ConnectionFactory.createConnection(conf);
		htable = con.getTable(TableName.valueOf("NichtKunden"));			
	}
	
	/**
	 * Schließt die Verbindung und den htable.
	 */
	public void closeAll() throws IOException
	{
		htable.close();
		con.close();
	}
	
	/**
	 * 
	 */
	public void setChatRecordSent(String userId, String empfaenger, String message) throws IOException
	{
		LocalDateTime ldt = LocalDateTime.now();
		String local = ldt.toString();
				
		Put pn = new Put(Bytes.toBytes(userId));
		pn.addColumn(Bytes.toBytes("Chat"), Bytes.toBytes("gesendet_"+local),Bytes.toBytes("An: "+empfaenger+" gesendet am: "+local+" "+message));
		htable.put(pn);
		
		System.out.println("Chatverlauf gespeichert.");
	}
	
	/**
	 * 
	 */
	public void setChatRecordRecieved(String userId, String empfaenger, String message) throws IOException
	{
		LocalDateTime ldt = LocalDateTime.now();
		String local = ldt.toString();
				
		Put pn = new Put(Bytes.toBytes(empfaenger));
		pn.addColumn(Bytes.toBytes("Chat"), Bytes.toBytes("empfangen_"+local),Bytes.toBytes("Von: "+userId+" gesendet am: "+local+" "+message));
		htable.put(pn);
		
		System.out.println("Chatverlauf gespeichert.");
	}
	
	public void putImage(String uid, String imageName, String empfaenger) throws IOException
	{
		File f = new File("/home/nosql/Pictures/"+imageName);
		BufferedImage bi = ImageIO.read(f);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bi, "jpg", baos);
		byte[] imageInBytes = baos.toByteArray();
		
		Put pn = new Put(Bytes.toBytes(uid));
		pn.addColumn(Bytes.toBytes("Data"), Bytes.toBytes("gesendet an: "+empfaenger+" "+imageName), imageInBytes);
		htable.put(pn);
		
		Put p = new Put(Bytes.toBytes(empfaenger));
		p.addColumn(Bytes.toBytes("Data"), Bytes.toBytes("Absender: "+uid+" "+imageName), imageInBytes);
		htable.put(p);
		
		System.out.println("Image gespeichert.");
	}
	
	public void getImage(String uid, String imageName) throws IOException
	{
		Get g = new Get(Bytes.toBytes(uid));
		Result result = htable.get(g);
		
		byte [] imageInByte = result.getValue(Bytes.toBytes("Data"), Bytes.toBytes(imageName));
		InputStream in = new ByteArrayInputStream(imageInByte);
		BufferedImage bi = ImageIO.read(in);
		ImageIO.write(bi, "jpg", new File("/home/nosql/Pictures/new-"+imageName));
		
		System.out.println("Image geladen.");
	}
	
	/**
	 * Liefert die gesendeten und empfangene Nachrichten eines Users
	 * @param userid - User
	 * @param time - Datum und Uhrzeit des Chatverlaufs
	 * @throws IOException 
	 */
	public List<String> getMessages(String userid) throws IOException 
	{
		List<String> list = listOfQualifiers(userid, "Chat");
		List<String> mList = new ArrayList<>();
		
		for(String s : list)
		{
			mList.add(getValueWithQualifier("Chat", s));
		}
		
		return mList;
	}
	
	public void getData(String userid) throws IOException 
	{
		List<String> list = listOfQualifiers(userid, "Data");
		
		for(String s : list)
		{
			getImage(userid, s);
		}
	}
	
	public List<String> getRowKeys() throws IOException
	{
		List<String> rlist = new ArrayList<>();
		Scan scan = new Scan();
		ResultScanner rscanner = htable.getScanner(scan);
		for(Result r : rscanner){
		   rlist.add(new String(r.getRow()));
		}
		return rlist;
	}
	
	/**
	 * Hilfsmethode
	 * Ueberprueft ob User schon vorhanden ist.
	 * @param userid UserId
	 * @return
	 */
	public String getUser(String userid) throws IOException
	{
		System.out.println("Suche nach: "+userid);
		String user = "User nicht vorhanden.";
		
		Scan scan = new Scan();
		RowFilter filter = new RowFilter(CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(userid)));
		scan.setFilter(filter);                                        
		
		ResultScanner scanner = htable.getScanner(scan);
	
		for(Result r : scanner )
		{
			user = new String(r.getRow());
		}
										
		return user;
	}
	
	/**
	 * Hilfsmethode
	 * @param id
	 * @param column
	 * @param value
	 */
	private void putUser(String id, String column, String value) throws IOException
	{
		Put p = new Put(Bytes.toBytes(id));
		p.addColumn(Bytes.toBytes("PersonalData"), Bytes.toBytes(column), Bytes.toBytes(value));
		htable.put(p);
	}
	
	/**
	 * Erstellt User mit Personal Data, wenn user nicht vorhanden ist.
	 * @param uid UserId
	 * @param vname Vorname
	 * @param nname Nachanme
	 * @param alter Alter
	 * @param adress Adresse
	 * @param tel Telefonnummer
	 * @param mail Emailadresse
	 * @throws IOException 
	 */
	public void setUser(String uid, String vname, String nname, String alter, String adress, String tel, String mail, String hobby) throws IOException
	{
		String u = getUser(uid);
		
		if(!u.equals(uid))
		{
			System.out.println("User wird anglegt ...");
			
			putUser(uid, "Vorname", vname);
			putUser(uid, "Nachname", nname);
			putUser(uid, "Alter", alter);
			putUser(uid, "Adress", adress);
			putUser(uid, "Telefon", tel);
			putUser(uid, "E-Mail", mail);
			putUser(uid, "Hobby", hobby);
			
			System.out.println("User angelegt");
		}
		else
		{
			System.out.println("User Vorhanden");
		}
	}
	
	/**
	 * 
	 */
	public void getPersonalData(String userid) throws IOException
	{
		try 
		{
			Get g = new Get(Bytes.toBytes(userid));
			Result result = htable.get(g);
						
			byte [] vn = result.getValue(Bytes.toBytes("PersonalData"), Bytes.toBytes("Vorname"));
			byte [] nn = result.getValue(Bytes.toBytes("PersonalData"), Bytes.toBytes("Nachname"));
			byte [] age = result.getValue(Bytes.toBytes("PersonalData"), Bytes.toBytes("Alter"));
			byte [] adr = result.getValue(Bytes.toBytes("PersonalData"), Bytes.toBytes("Adress"));
			byte [] tel = result.getValue(Bytes.toBytes("PersonalData"), Bytes.toBytes("Telefon"));
			byte [] mail = result.getValue(Bytes.toBytes("PersonalData"), Bytes.toBytes("E-Mail"));
			byte [] hobby = result.getValue(Bytes.toBytes("PersonalData"), Bytes.toBytes("Hobby"));
			
			String vns = Bytes.toString(vn); 
			String nns = Bytes.toString(nn);
			String ages = Bytes.toString(age); 
			String adrs = Bytes.toString(adr);
			String tels = Bytes.toString(tel); 
			String mails = Bytes.toString(mail);
			String hob = Bytes.toString(hobby);
			
			System.out.println("Vorname: "+vns);
			System.out.println("Nachname: "+nns);
			System.out.println("Alter: "+ages);
			System.out.println("Adresse: "+adrs);
			System.out.println("Telefon: "+tels);
			System.out.println("E-Mail: "+mails);
			System.out.println("Hobby: "+hob);
		}
		catch (IOException e)
		{
			System.out.println(e.toString());
		}
	}
	
	public List<String> listOfQualifiers(String row, String columnFamily) throws IOException
	{
		Get g = new Get(Bytes.toBytes(row));
		Result result = htable.get(g);
		NavigableMap<byte[], byte[]> qualifiers = result.getFamilyMap(Bytes.toBytes(columnFamily));
		List<String> qualifNames = new ArrayList<>();
		for(byte[] qualifier : qualifiers.keySet())
		{
			qualifNames.add(Bytes.toString(qualifier));
		}
		return qualifNames;
	}
	
	public void printQualifiers(String row, String columnFamily) throws IOException
	{
		for(String qualifier : listOfQualifiers(row, columnFamily))
		{
			System.out.println(qualifier);
		}
	}
	
	public List<String> findValuesWithQualifier(String columnFamily, String qualifier) throws IOException
	{
		Scan s = new Scan();
		s.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(qualifier));
		ResultScanner scanner = htable.getScanner(s);
		List<String> valList = new ArrayList<String>();
		for (Result r : scanner)
		{
			valList.add((Bytes.toString(r.value())));
		}
		
		return valList;
	}
	
	public String getValueWithQualifier(String columnFamily, String qualifier) throws IOException
	{
		String value = "geht nicht";
		Scan s = new Scan();
		s.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(qualifier));
		ResultScanner scanner = htable.getScanner(s);
		for(Result r : scanner)
		{
			value = Bytes.toString(r.value());
		}
		
		return value;
	}
		
	
	public List<String> findRowsWithQualifier(String columnFamily, String qualifier) throws IOException
	{
		Scan s = new Scan();
		s.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(qualifier));
		ResultScanner scanner = htable.getScanner(s);
		List<String> rowList = new ArrayList<String>();
		for (Result r : scanner)
		{
			rowList.add(Bytes.toString(r.getRow()));
		}
		return rowList;
	}
}
