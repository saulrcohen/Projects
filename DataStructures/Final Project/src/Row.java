import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.ColumnDescription;
import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.ColumnValuePair;
import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.CreateTableQuery;
import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.InsertQuery;
/**
 * 
 */

/**
 * @author Shimon Cohen
 *
 */
public class Row<T extends Comparable<T>>{
	
	private int top;
	private String[] row;
	private int end;
	private ColumnDescription.DataType primaryKeyType;
	private String primaryKeyColumnName;
	private String primaryKeyValue;
	
	public Row(ColumnDescription.DataType key, int size)
	{
		this.primaryKeyType = key;
		top = 0;
		row = new String[size];
	}
	
	/**
	public void setType(ColumnDescription  cd)
	{
		row.cd = cd;
	}
	*/
	
	/**
	 * Sets the primary key value for the row
	 */
	public void setPrimaryKeyvalue(String value)
	{
		primaryKeyValue = value;
	}
	
	/**
	 * Sets the name of the primaryKey 
	 */
	public void setPrimaryKeyName(String s)
	{
		this.primaryKeyColumnName = s;
	}
	/**
	 * Sets the key to the specific row
	 * @param newKey
	 */
	public void setPrimaryKey(ColumnDescription.DataType newKey)
	{
		this.primaryKeyType = newKey;
	}
	/**
	 * Returns the primaryKey of the row
	 * @return
	 */
	public String getPrimaryKeyValue()
	{
		return primaryKeyValue; 
	}
	/**
	 * 
	 * @return the row array
	 */
	public String[] getRow()
	{
		return row;
	}
	/**
	 * Adds to the row in the table 
	 * @param o
	 * @throws Exception 
	 */
	public void addToRow(InsertQuery iq, CreateTableQuery ctq, Tables tbl, BTree bt) throws Exception
	{
		//first move, set the primaryKey for the row which will be important for the BTree
		ColumnDescription pk = ctq.getPrimaryKeyColumn();
		this.setPrimaryKeyName(pk.getColumnName());
		this.setPrimaryKey(pk.getColumnType());
		//Next move, set the actual array to be equal to all the values of the create query
		ColumnDescription[] ctqVp = ctq.getColumnDescriptions();
		ColumnValuePair[] iqVp = iq.getColumnValuePairs();
		//check where the names of the tables are similar, and when you find it, but the values in the right spo
		for(int r =0; r < ctqVp.length; r++){
			String yo = ctqVp[r].getColumnName();
			for(int p = 0; p < ctqVp.length; p++){
				String hi = iqVp[p].getColumnID().getColumnName();
				if(yo.equals(hi)){
					row[r] = iqVp[p].getValue();
					if(ctqVp[r].getHasDefault() == true){
						String check = row[r].toLowerCase();
						if(check.toLowerCase().contains("null")){
							row[r] = ctqVp[r].getDefaultValue();
						}
					}
					//check if the current spot in the array is our primaryKey, if it is, then you should set the primaryKey value to be the value of that array
					if(ctq.getPrimaryKeyColumn().getColumnName().equals(ctqVp[r].getColumnName())){
						this.setPrimaryKeyvalue(iqVp[p].getValue());//set the primary key to be the value of the slot we're at
					}
					break;
				}
			}
		}//one of the conditions failed, so we can't add anything to the row, and instead throw an exception
		if(tbl.checkInsert(row) == false){
			try {
				throw new Exception("This row has a problem in it's placement, check for null values,  unique values, or improper column types");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else
		{//we succeeded, so now we can add the new Row to our table
			tbl.addRow(this);
			//build up a new Key to place in all of our BTrees
			if(tbl.getBTrees().size() > 0){
			for(int p = 0; p < tbl.getBTrees().size(); p++){
				ArrayList<BTree> list = tbl.getBTrees();
				String currentBTreeName = list.get(p).getPrimaryKey();
				for(int q = 0; q < row.length; q++){
					if(ctq.getColumnDescriptions()[q].getColumnName().equals(currentBTreeName)){
						Key key = new Key(row[q], this);
						list.get(p).put(key);
					}
				}
			}
		}
			else
			{
				Key key1 = new Key(this.primaryKeyValue, null);
				key1.addValue(this);
				bt.put(key1);
			}
		}
	}
	
	public Object[] specificRow(Tables t)
	{
		ArrayList<Object> hi = t.getListOfRows();
		for(int i = 0; i < hi.size(); i++){
			return (Object[]) hi.get(i);
		}
		return null;
	}

	@Override
	public String toString() {
		return "Student= " + "" + primaryKeyValue + "" + Arrays.toString(row) + "\n";
	}
}