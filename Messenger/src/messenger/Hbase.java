package messenger;

import java.io.IOException;
import java.time.LocalDateTime;

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
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.Bytes;


public class Hbase 
{
	private Connection con;
	private Table htable;
	
	
	public Hbase()
	{
		Configuration conf = HBaseConfiguration.create();
		
		try 
		{
			con = ConnectionFactory.createConnection(conf);
			htable = con.getTable(TableName.valueOf("NichtKunden"));			
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Schließt die Verbindung und den htable.
	 */
	public void closeAll()
	{
		try 
		{
			htable.close();
			con.close();
		} 
		catch (IOException e) 
		{
			System.out.println(e.toString());
		}
		
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("deprecation")
	public void setChatRecord(String userId, String empfaenger, String message)
	{
		LocalDateTime ldt = LocalDateTime.now();
		String local = ldt.toString();
		
		try 
		{
			Put pe = new Put(Bytes.toBytes(userId));
			pe.add(Bytes.toBytes("Chat"), Bytes.toBytes("Empfaenger"), Bytes.toBytes(empfaenger));
			htable.put(pe);
			
			Put pn = new Put(Bytes.toBytes(userId));
			pn.add(Bytes.toBytes("Chat"), Bytes.toBytes(local), Bytes.toBytes(message));
			htable.put(pn);
		} 
		catch (IOException e) 
		{
			System.out.println(e.toString());
		}
	}
	
	/**
	 * Liefert den Chatverlauf und Empfaenger einse Users
	 * @param userid - User
	 * @param time - Datum und Uhrzeit des Chatverlaufs
	 */
	public void getChatRecord(String userid, String time) 
	{
		try 
		{
			Get g = new Get(Bytes.toBytes(userid));
			Result result = htable.get(g);
						
			byte [] empfaenger = result.getValue(Bytes.toBytes("Chat"), Bytes.toBytes("Empfaenger"));
			byte [] record = result.getValue(Bytes.toBytes("Chat"), Bytes.toBytes(time));
			
			String ef = Bytes.toString(empfaenger); 
			String rec = Bytes.toString(record);
			
			System.out.println("Empfaenger: "+ef);
			System.out.println("");
			System.out.println("Verlauf: "+rec);
		}
		catch (IOException e)
		{
			System.out.println(e.toString());
		}
	}
	
	/**
	 * Hilfsmethode
	 * Ueberprueft ob User schon vorhanden ist.
	 * @param userid UserId
	 * @return
	 */
	public String getUser(String userid) 
	{
		System.out.println("Suche nach: "+userid);
		String user = "User nicht vorhanden.";
		try
		{
			Scan scan = new Scan();
			RowFilter filter = new RowFilter(CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(userid)));
			scan.setFilter(filter);                                        
			
			try(ResultScanner scanner = htable.getScanner(scan))
			{
				for(Result r : scanner )
				{
					user = new String(r.getRow());
				}
			}						
						
		} 
		catch (IOException e) 
		{
			System.out.println(e.toString());
		}
		
		System.out.println(user);
		return user;
	}
	
	/**
	 * Hilfsmethode
	 * @param id
	 * @param column
	 * @param value
	 */
	@SuppressWarnings("deprecation")
	private void putUser(String id, String column, String value)
	{
		try 
		{
			Put p = new Put(Bytes.toBytes(id));
			p.add(Bytes.toBytes("PersonalData"), Bytes.toBytes(column), Bytes.toBytes(value));
			htable.put(p);
		} 
		catch (IOException e) 
		{
			System.out.println(e.toString());
		}
	}
	
	/**
	 * Erstellt User mit Personal Data, wenn user nicht vorhanden ist.
	 * @param uid UserId
	 * @param vname Vorname
	 * @param nname Nachanme
	 * @param adress Adresse
	 * @param tel Telefonnummer
	 * @param mail Emailadresse
	 */
	public void setUser(String uid, String vname, String nname, String alter, String adress, String tel, String mail)
	{
		String u = getUser(uid);
		
		if(!u.equals(uid))
		{
			System.out.println("User nicht vorhanden. Wird anglegt ...");
			
			putUser(uid, "Vorname", vname);
			putUser(uid, "Nachname", nname);
			putUser(uid, "Alter", alter);
			putUser(uid, "Adress", adress);
			putUser(uid, "Telefon", tel);
			putUser(uid, "E-Mail", mail);
			
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
	public void getPersonalData()
	{
		//TODO
	}
}
