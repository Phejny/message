package messenger;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
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
			htable = con.getTable(TableName.valueOf("nichtkunden"));			
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
	public void close()
	{
		try 
		{
			htable.close();
			con.close();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void getChatRecord(String userid) 
	{
	
		try 
		{
			Get g = new Get(Bytes.toBytes(userid));
			Result result = htable.get(g);
			
			
			byte [] empfaenger = result.getValue(Bytes.toBytes("Chat"), Bytes.toBytes("Empfaenger"));
			byte [] record = result.getValue(Bytes.toBytes("Chat"), Bytes.toBytes("state"));
			
			String city = Bytes.toString(empfaenger); 
			String state = Bytes.toString(record);
			
			System.out.println("");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void getPlz(String city)
	{
		Configuration conf = HBaseConfiguration.create();
		
		try(Connection con = ConnectionFactory.createConnection(conf); Table htable = con.getTable(TableName.valueOf("plz"))) 
		{
			Scan scan = new Scan();
			SingleColumnValueFilter filter = new SingleColumnValueFilter(
					Bytes.toBytes("info"), Bytes.toBytes("city"), CompareOp.EQUAL, Bytes.toBytes(city));
			scan.setFilter(filter);
			
			try(ResultScanner scanner = htable.getScanner(scan))
			{
				for(Result r : scanner )
				{
					System.out.println("PLZ: "+ new String(r.getRow()));
				}
			}					
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
