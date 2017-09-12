import java.io.IOException;
import java.math.*;
import java.util.ArrayList;
import java.util.Arrays;
import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.ColumnDescription;
import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.ColumnDescription.DataType;
import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.ColumnID;
import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.ColumnValuePair;
import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.CreateIndexQuery;
import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.CreateTableQuery;
import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.DeleteQuery;
import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.InsertQuery;
import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.SelectQuery;
import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.UpdateQuery;
import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.Condition;


public class Tables <T extends Comparable<T>>{
	private enum DataType{INT,VARCHAR,DECIMAL,BOOLEAN};
	private String tableName;
	private ArrayList<Row> table;
	private CreateTableQuery ctq;
	private ColumnDescription[] cd;
	private ColumnDescription primaryKeyColumn;
	private String primaryKeyColumnName;
	private int width;
	private BTree bt;
	private ArrayList<BTree> bts;
	//private enum Operator {EQUALS("="), NOT_EQUALS("<>"), LESS_THAN("<"), lESS_THAN_OR_EQUALS("<="), GREATER_THAN(">"), GREATER_THAN_OR_EQUALS(">="), AND("AND"), OR("OR");
    //}//;
	
	public Tables(CreateTableQuery ctq)
	{
		table = new ArrayList<Row>();
		this.ctq = ctq;
		this.width = 0;
		this.tableName = ctq.getTableName();
		this.primaryKeyColumn = ctq.getPrimaryKeyColumn();
		primaryKeyColumnName = ctq.getPrimaryKeyColumn().getColumnName();
		Node node = new Node(0);
		BTree btree = new BTree(node, ctq.getPrimaryKeyColumn().getColumnName(), ctq.getTableName());
		this.bt = btree;
		this.bts = new ArrayList<BTree>();
		bts.add(bt);
	}
	public static void main(String[] args)
	{
	}
	 
	/**
	 * Returns all the rows in the table
	 * @return
	 */
	public ArrayList<Row> getListOfRows()
	{
		return table;
	}
	
	/**
	 * Adds a new row to the table
	 * @param row
	 */
	public void addRow(Row row)
	{
		table.add(row);
	}
	
	/**
	 * Returns the row associated with a specific primary key
	 * @param primaryKey
	 * @return
	 */
	public Object[] getRow(String primaryKey)
	{
		for(int i = 0; i < table.size(); i++){
			Row current = table.get(i);
			if(current.getPrimaryKeyValue() == primaryKey){
				Object[] hi = current.getRow();
				System.out.println(current.getRow());
				return current.getRow();
			}
		}
		return (Object[]) null;
	}
	
	/**
	 * Initializes a new list with the proper titles to each of the columns 
	 * @param ctq
	 * @return
	 * @throws Exception
	 */
	public String createTable(CreateTableQuery ctq) throws Exception
	{
		this.primaryKeyColumn = ctq.getPrimaryKeyColumn();
		this.tableName = ctq.getTableName();
		//First thing we have to do is create the Array of ColumnDescriptions. 
		ColumnDescription[] titles = ctq.getColumnDescriptions();
		width = titles.length;
		createBTree();
		return tableName;
	}
	
	/**
	 * Returns the "table" of this class
	 */
	
	public ArrayList<Row> getTable()
	{
		return this.table;
	}
	
	/**
	 * Creates a new BTree to be accessed at the same time as the table
	 */
	public void createBTree()
	{
		Node node = new Node(0);
		bt = new BTree(node, ctq.getPrimaryKeyColumn().getColumnName(), ctq.getTableName());
	}
	
	public ArrayList<BTree> getBTrees()
	{
		return this.bts;
	}
	/**
	 * Actually creates a new Array in the table, and adds the objects to that row
	 * @throws Exception 
	 */
	public void insert(InsertQuery iq) throws Exception
	{
		Row row = new Row(ctq.getPrimaryKeyColumn().getColumnType(), ctq.getColumnDescriptions().length);
		row.addToRow(iq, ctq, this, bt);
	}
	
	/**
	 * Checks the types of the columns and makes sure they all line up
	 * @throws Exception 
	 */
	
	public boolean checkInsert(String[] row) throws Exception
	{
		ColumnDescription[] titles = ctq.getColumnDescriptions();
		
		for(int i = 0; i < titles.length; i++){
			if(titles[i].isNotNull()){
				if(notNull(row, i) == false){
					return false;
				}
			}
			if(titles[i].isUnique()){
				if(checkUnique(row, i) == false){
					return false;
				}
			}
				
			//}
			ColumnDescription.DataType typeCheck = titles[i].getColumnType();
			if(typeCheck == (typeCheck.BOOLEAN)){
				if(checkBoolean(row, i) == false){
					return false;
				}
			}
			if(typeCheck == (typeCheck.INT)){
				if(checkInt(row, i) == false){
					return false;
				}
			}
			if(typeCheck == (typeCheck.VARCHAR)){
				if(checkVarchar(row, i) == false){
					return false;
				}
			}
			if(typeCheck == (typeCheck.DECIMAL)){
				if(checkDecimal(row, i) == false){
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Make sure all the columns in the table contain the proper types in that specific column
	 * @return
	 * @throws IOException
	 */
	
	public boolean checkBoolean(String[] row, int r) throws IOException
	{
			if(row[r].toLowerCase().equals("true") || row[r].toLowerCase().equals("false") || row[r].toLowerCase().equals("null")){
				return true;
			}
		return false;
}
	/**
	 * Checks to make sure all the rows are integers if they're supposed to be ints
	 * @param r
	 * @return
	 */
	public boolean checkInt(String[] row, int r) throws IOException
	{
			try{
			int x = Integer.parseInt(row[r]);
			}
			catch(NumberFormatException nfe){
				System.err.println("Must be of type int");;
			}
		return true;
	}
	
	/**
	 * Checks to make sure the column is of varchar type
	 * @param row
	 * @param r
	 * @return
	 */
	public boolean checkVarchar(String[] hi , int r)
	{
			int varCheck = ctq.getColumnDescriptions()[r].getVarCharLength();
			if(hi[r].length() > varCheck){
				return false;
			}
		return true;
	}
	
	/**
	 * Makes sure the column is of type decimal if necessary
	 * @param row
	 * @param r
	 * @return
	 * @throws Exception
	 */
	public boolean checkDecimal(String[] row, int r) throws Exception
	{
		try{
			char[] number = row[r].toCharArray();
			for(int i = 0; i < number.length; i++){
				if(number[i] == ('.') || Character.isDigit(number[i]) == false){
					return true;
				}
			}
		}
			catch(NumberFormatException nfe){
				System.out.println("Check your decimals, please");
				return false;
			}
		return true;

	}
	/**
	 * Checks whether or not the necessary column is unique
	 * @param r
	 * @return
	 * @throws IOException
	 */
	public boolean checkUnique(String[] row, int r) throws IOException//passes in the spot on the column description array 
	{
		Object o = null;
		for(int l = 0; l < table.size(); l++){//pulls out all the arrays on the table
			Row currentRow = table.get(l);
			Object[] useful = currentRow.getRow();
			o = useful[r];
			if(o.equals(row[r])){
					return false;
				}
			}
	return true;
}
	
	/**
	 * Checks if the column has to not be null, make sure there are no null values in the row
	 * @param r
	 * @return
	 * @throws IOException
	 */
	public boolean notNull(String[] row, int r) throws IOException
	{
			if(row[r].toString().contains("null")){
				throw new IOException("Can not place a Null value in this row");
			}
		return true;
	}
	
	/**
	 * Select method returns the requested items from the B-Tree
	 *  
	 */

	
	/**
	 * Returns the width of the table
	 * @return
	 */
	public int getWidth()
	{
		return width;
	}
	
	
	@Override
	public String toString() {
		return "Tables tableName=" + tableName + table +  ", primaryKeyColumnName= " + primaryKeyColumnName;
	}
	/**
	 * Sets a new Table 
	 * @param table
	 */
	public void setTable(ArrayList<Row> table) {
		this.table = table;
	}
	/**
	 * Returns the primary Key column
	 * @return
	 */
	public ColumnDescription getPrimaryKeyColumn() {
		return primaryKeyColumn;
	}
	/**
	 * sets the primary key column
	 * @param primaryKeyColumn
	 */
	public void setPrimaryKeyColumn(ColumnDescription primaryKeyColumn) {
		this.primaryKeyColumn = primaryKeyColumn;
	}
	
	/**
	 * Returns the table name 
	 * @return
	 */
	public String getTableName() {
		return tableName;
	}
	
	/**
	 * Sets the table name 
	 * @param tableName
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public void setPrimaryKeyColumnName(String s)
	{
		this.primaryKeyColumnName = s;
	}
	
	public CreateTableQuery getCTQ()
	{
		return this.ctq;
	}
	/**
	 * Sets a new index on the table by making a new table with the requested primarykey
	 * @param ciq
	 * @param tbl
	 */
	public void createIndex(CreateIndexQuery ciq, Tables tbl)
	{	
		bts.add(bt);
		Tables tbl1 = new Tables(getCTQ());
		ArrayList<Row> blah = new ArrayList<Row>();
		blah = this.getListOfRows();
		tbl1.table = blah;
		tbl1.setPrimaryKeyColumnName(ciq.getColumnName());
		ColumnDescription[] cd1 = tbl1.ctq.getColumnDescriptions();
		for(int i = 0; i < tbl1.ctq.getColumnDescriptions().length; i++){
			if(ciq.getColumnName().toLowerCase().equals(cd1[i].getColumnName().toLowerCase())){//we're at the column name in the create table query, so now set it as the PK
				tbl1.setPrimaryKeyColumn(ctq.getColumnDescriptions()[i]);
				tbl1.setPrimaryKeyColumnName(ctq.getColumnDescriptions()[i].getColumnName());
				break;
			}
		}
		ArrayList<Row> rows = tbl1.getListOfRows();
		//set the individual rows to utilize the primary key in their row
		for(int i = 0; i < table.size(); i++){
			Row pnimius = rows.get(i);
			for(int r = 0; r < tbl1.ctq.getColumnDescriptions().length; r++){
				if(tbl1.primaryKeyColumnName.equals(tbl1.ctq.getColumnDescriptions()[r].getColumnName())){
					String[] actualRow = pnimius.getRow();
					pnimius.setPrimaryKeyvalue(actualRow[r]);
				}
			}
		}//fix up the new BTree
		Node node = new Node(0);
		BTree bt = new BTree(node, ciq.getColumnName(), ciq.getIndexName());
		bt = tbl1.getBT();
		bt.setPrimaryKey(ciq.getColumnName());
		Row row = null;
		for(int i = 0; i < tbl1.table.size(); i++){
			row = table.get(i);//pulls out the whole BTree we're about to deal with
			Key key = new Key(row.getPrimaryKeyValue(), null);//creates a new key with the primary key column
			key.addValue(row);//adds the values to the key 
			bt.put(key);//places it in the New BTree and adds the new BTree to the List of BTrees this table can hold
		}
		bts.add(tbl1.bt);
}	
	public BTree getBT()
	{
		return this.bt;
	}
	/**
	 * First method called to select a certain set of results from the Table
	 */
	
	public ArrayList<Row> select(SelectQuery sq)
	{
		ColumnID[] cd = sq.getSelectedColumnNames();//only getting the important data the user wants, in this case
		ColumnID specific = null;
		Row row = null;
		ArrayList<Row> hi = new ArrayList<Row>();
		if(sq.getWhereCondition() == null && sq.getSelectedColumnNames() != null){
			for(int i = 0; i < table.size(); i++){
				row = table.get(i);
				String[] array = row.getRow();
				for(int r = 0; r < cd.length; r++){
					specific = cd[r];
				for(int p = 0; p < ctq.getColumnDescriptions().length; p++){
					if(ctq.getColumnDescriptions()[p].getColumnName().equals(specific.getColumnName())){
						System.out.println(row.getPrimaryKeyValue() +  " " + cd[r].getColumnName() + " " + array[p].toString());
					}
				}
			}
				//System.out.println(Arrays.toString(results));
		}
		hi.add(row);
		return hi;
	}
		else 
			if(sq.getSelectedColumnNames() == null){
				System.out.println(this.table.toString());
			}
		else
		{
			return (ArrayList<Row>) recursiveSelect(sq.getWhereCondition(), this.getTable(), sq);
		}
		return hi;
}
	/**
	 * Returns all the equals to values
	 * @param c
	 * @return
	 */
	public ArrayList<Row> getEquals(Condition c, ArrayList<Row> results)
	{
		for(int r = 0; r <= bts.size(); r++){//First try the BTree route because it's the most efficient
		if(c.getLeftOperand().toString().equals(bt.getPrimaryKey())){
			 BTree btree = bts.get(r);
				Key key = new Key((String) c.getRightOperand(), null);
				ArrayList<Row> row = bt.get(key);
				results = row;
				return results;
		}
	}
			for(int i = 0; i < results.size(); i++){//more than one value, so we have to test all the different rows
			Row row = results.get(i);//pull out the rows in the table
			String[] array = row.getRow();
			for(int n = 0; n < array.length; n++){
				if(ctq.getColumnDescriptions()[n].getColumnName().equals(c.getLeftOperand().toString())){
					if(!array[n].equals(c.getRightOperand())){//if it's a number, then we should be able to set it equals, and add the results to our array 
					results.remove(row);
					this.table.add(row);
					i--;
					break;
					}
				}
			}
		}
		return results;
}


	/**
	 * Gets anything greater than the inserted primaryKey 
	 * @param sq
	 */
	public ArrayList<Row> getGreater(Condition c, ArrayList<Row> results)
	{
		for(int i = 0; i < bts.size(); i++){
		if(bt.getPrimaryKey().equals(c.getLeftOperand().toString())){//we have an index in the BTree so we can use the BTree to find the greater rows
			Key key = new Key((String) c.getRightOperand(), null);
			results = bt.getGreater(key, c);
			return results;
		}
	}
			for(int p = 0; p < table.size(); p++){//p is the row we're up to because there was no indexed BTree to use
				Row row = table.get(p);
				String[] realRow = row.getRow();
				for(int k = 0; k < row.getRow().length; k++){// k is the place we're up to in the array 
					if(ctq.getColumnDescriptions()[k].getColumnName().equals(c.getLeftOperand().toString())){
						if(realRow[k].compareToIgnoreCase((String) c.getRightOperand()) <= 0){
							results.remove(row);
							p--;
							break;
						}
					}
				}
			}
		return results;
}
	/**
	 * Breaks up the operands that are passed into the select query
	 * @param c
	 * @return
	 */
	public ArrayList<Row> recursiveSelect(Condition c, ArrayList<Row> checker, SelectQuery sq)
	{
		ArrayList<Row> results = null;
				if(!(c.getLeftOperand() instanceof Condition) && !(c.getRightOperand() instanceof Condition)){
					results = checker;
						if(c.getOperator().toString().equals("=")){
								results = getEquals(c, checker);
						}
						else if(c.getOperator().toString().equals(">")){
							results = getGreater(c, checker);
					}
						else if(c.getOperator().toString().equals("<")){
							results = getLess(c, checker);
					}
						else if(c.getOperator().toString().equals("<>")){
							results = getNotEquals(c, checker);
					}
						else if(c.getOperator().toString().equals(">=")){
							results = getGreaterEquals(c, checker);
					}
						else if(c.getOperator().toString().equals("<=")){
							results = getLessEquals(c, checker);
					}
		}
				if(c.getLeftOperand() instanceof Condition && c.getRightOperand() instanceof Condition && c.getOperator().toString().equals("AND")){
					results = recursiveSelect((Condition) c.getLeftOperand(), this.getTable(), sq);
					results = recursiveSelect((Condition) c.getRightOperand(), results, sq);
				//loop through the results and check if all of them meet all the criteria suggested
					}
				if(c.getLeftOperand() instanceof Condition && c.getRightOperand() instanceof Condition && c.getOperator().toString().equals("OR")){
					results = recursiveSelect((Condition) c.getLeftOperand(), results, sq);//if it's only a case of or, we can just print out the results as we receieve them
					results = recursiveSelect((Condition) c.getRightOperand(), this.getTable(), sq);
				}
			if(sq.isDistinct()){
				for(int i = 0; i < results.size(); i++){//check the result set to make sure it's all distinct if necessary
					Row row = results.get(i);//get the rows [i]
					String[] realRow = row.getRow();
					for(int k = 0; k < sq.getSelectedColumnNames().length; k++){//check the column names 
						String check = sq.getSelectedColumnNames()[k].getColumnName(); //k gives us the spot in the array 	
						for(int l = 0; l < realRow.length; l++){//check the select query column names we want 
							if(check.equals(ctq.getColumnDescriptions()[l].getColumnName())){//l gives us the proper spot in the array we need to check for default values
								for(int r = i+1; r < results.size(); r++){
									Row otherRow = results.get(r);//get the other rows, and then go and check them compared the first row
									String[] realOtherRow = otherRow.getRow();
									if(realOtherRow[l].equals(realRow[l])){
										results.remove(r);
										r--;
									}
								}
							}
						}
					}
				}
			}
			
			if(sq.getSelectedColumnNames() != null){
				for(int i = 0; i < results.size(); i++){
					Row row = results.get(i);
					String[] realRow = row.getRow();
					for(int k = 0; k < sq.getSelectedColumnNames().length; k++){
						ColumnValuePair cvp = sq.getColumnValuePairs()[k];
					for(int p = 0; p < realRow.length; p++){
						if(ctq.getColumnDescriptions()[p].getColumnName().equals(cvp.getColumnID().getColumnName())){
							System.out.println(row.getPrimaryKeyValue() + realRow[p]);
						}
					}
				}
			}
		}
		return results;
	}
	/**
	 * Returns less than amounts
	 * @param c
	 * @return
	 */
	public ArrayList<Row> getLess(Condition c, ArrayList<Row> results)
	{
		for(int i = 0; i < bts.size(); i++){
			if(bt.getPrimaryKey().equals(c.getLeftOperand().toString())){//we have an index in the BTree so we can use the BTree to find the greater rows
				Key key = new Key((String) c.getRightOperand(), null);
				results = bt.getLess(key, c);
				return results;
			}
		}
				for(int p = 0; p < table.size(); p++){//p is the row we're up to because there was no indexed BTree to use
					Row row = table.get(p);
					String[] realRow = row.getRow();
					for(int k = 0; k < row.getRow().length; k++){// k is the place we're up to in the array 
						if(ctq.getColumnDescriptions()[k].getColumnName().equals(c.getLeftOperand().toString())){
							if(realRow[k].compareToIgnoreCase((String) c.getRightOperand()) < 0){
								results.remove(row);//if it's less than, remove it from our current result set
								p--;
								break;
							}
						}
					}
				}
			return results;
	}
	
	/**
	 * Returns greater than amounts
	 * @param c
	 * @return
	 */
	public ArrayList<Row> getNotEquals(Condition c, ArrayList<Row> results)
	{
		for(int i = 0; i < results.size(); i++){
			Row row = results.get(i);//pull out the row that we're going to be checking to verify fits the criteria
			for(int p = 0; p < row.getRow().length; p++){
				String[] easier = row.getRow();
				if(ctq.getColumnDescriptions()[p].equals(c.getLeftOperand())){//if the piece at our place in the array equals the wrong part, remove the row from the resultset
					if(easier[p].equals(c.getRightOperand())){
						results.remove(row);//get rid of him!	
					}
				}
			}
		}
		return results;
	}
	
	/**
	 * Returns greater than amounts
	 * @param c
	 * @return
	 */
	public ArrayList<Row> getLessEquals(Condition c, ArrayList<Row> results)
	{
		for(int i = 0; i < bts.size(); i++){
			if(bts.get(i).getPrimaryKey().equals(c.getLeftOperand().toString())){//we have an index in the BTree so we can use the BTree to find the greater rows
				Key key = new Key((String) c.getRightOperand(), null);
				results = bt.getLess(key, c);
				}
			return results;
			}
				for(int p = 0; p < table.size(); p++){//p is the row we're up to because there was no indexed BTree to use
					Row row = table.get(p);
					for(int k = 0; k < row.getRow().length; k++){// k is the place we're up to in the array 
						String[] realRow = row.getRow();
						if(ctq.getColumnDescriptions()[k].getColumnName().equals(c.getLeftOperand())){
							if(realRow[k].compareToIgnoreCase((String) c.getRightOperand()) > 0){
								results.remove(row);
							}
						}
					}
				}
		return results;
	}
	
	/**
	 * Returns greater than and equals to amounts
	 * @param c
	 * @return
	 */
	public ArrayList<Row> getGreaterEquals(Condition c, ArrayList<Row> results)
	{
			for(int i = 0; i < bts.size(); i++){
				if(bts.get(i).getPrimaryKey().equals(c.getLeftOperand().toString())){//we have an index in the BTree so we can use the BTree to find the greater rows
					Key key = new Key((String) c.getRightOperand(), null);
					results = bt.getLess(key, c);
				return results;
				}
				}
					for(int p = 0; p < table.size(); p++){//p is the row we're up to because there was no indexed BTree to use
						Row row = table.get(p); 
						String[] realRow = row.getRow();
						for(int k = 0; k < row.getRow().length; k++){// k is the place we're up to in the array
							if(ctq.getColumnDescriptions()[k].getColumnName().equals(c.getLeftOperand().toString())){//search for the proper spot in the array to validate, and go from there
								if(realRow[k].compareToIgnoreCase((String) c.getRightOperand()) < 0){
									results.remove(row);
									k--;
									break;
								}
							}
						}
					}
			return results;
		}
	
	/**
	 * Returns the sum of a requested column of the table
	 */
	public int getSum(Condition c, InsertQuery iq)
	{
		ArrayList<Integer> results = new ArrayList<Integer>();
		for(int i = 0; i < table.size(); i++){
			Row row = table.get(i);
			String[] realRow = row.getRow();
			for(int l = 0; l < iq.getColumnValuePairs().length; l++){
				String chosenOne = iq.getColumnValuePairs()[l].getColumnID().getColumnName();//gets the spot that we want the sum of 
				for(int r = 0; r < realRow.length; r++){
					if(ctq.getColumnDescriptions()[r].getColumnName().equals(chosenOne)){
						results.add(Integer.parseInt(realRow[r]));//adds it to the list to be able to eventually add it all up later
					}
				}
			}
		}
		int summation = 0;
		for(int i = 0; i < results.size(); i++){
			summation += results.get(i);
		}
		return summation;
	}
	
	
	/**
	 * If it's a simple case of deleting everything, it does that, if not it calls the fancy recursion to delete what needs to be deleted
	 * @param dq
	 * @return
	 */
	
	public ArrayList<Row> delete(DeleteQuery dq)
	{
		Row row = null;
		ArrayList<Row> hi = new ArrayList<Row>();
		if(dq.getWhereCondition() == null){
			for(int i = 0; i < table.size(); i++){
				row = table.get(i);
				String[] array = row.getRow();
				table.remove(row);			
			}
		}
		else
		{
			Tables tbl = new Tables(ctq);
			tbl.table = new ArrayList<Row>();
			tbl.table = this.getTable();
			
			return (ArrayList<Row>) recursiveDelete(dq.getWhereCondition(), tbl.getTable(), dq);
		}
		return null;
	}
	
	/**
	 * Recursive method called to break open the conditions and only delete the requested rows in the database
	 * @param c
	 * @param ar
	 * @param dq
	 * @return
	 */
	public ArrayList<Row> recursiveDelete(Condition c, ArrayList<Row> ar, DeleteQuery dq)
	{
		ArrayList<Row> results = null;
				if(!(c.getLeftOperand() instanceof Condition) && !(c.getRightOperand() instanceof Condition)){
					results = ar;
					deleteSpecific(c, results);
				}
				if(c.getLeftOperand() instanceof Condition && c.getRightOperand() instanceof Condition && c.getOperator().toString().equals("AND")){
					results = ar;
					results = getFirstGroup((Condition) c.getLeftOperand(), results, dq);
					results = recursiveDelete((Condition) c.getRightOperand(), results, dq);
					deleteSpecific((Condition) c.getLeftOperand(), results);
					System.out.println(this.table.toString());
				//loop through the results and check if all of them meet all the criteria suggested
					}
				if(c.getLeftOperand() instanceof Condition && c.getRightOperand() instanceof Condition && c.getOperator().toString().equals("OR")){
					results = ar;
					results = recursiveDelete((Condition) c.getLeftOperand(), results, dq);//Build up our array of results which are going to be deleted from the final table
					results = recursiveDelete((Condition) c.getRightOperand(), results, dq);
					System.out.println(this.table.toString());
				}
				return results;
	}
	
	/**
	 * called in an and method to get the first group of rows that fit the criteria in our case
	 * and then go back to our recursive delete method which will make sure everything fits with our second batch 
	 * @param c
	 * @param results
	 * @param dq
	 * @return
	 */
	public ArrayList<Row> getFirstGroup(Condition c, ArrayList<Row> checker, DeleteQuery dq)
	{
		ArrayList<Row> results = null;
		if(c.getOperator().toString().equals("=")){
			results = checker;
			results = getEquals(c, checker);
	}
	else if(c.getOperator().toString().equals(">")){
		results = checker;
		results = getGreater(c, checker);
}
	else if(c.getOperator().toString().equals("<")){
		results = checker;
		results = getLess(c, checker);
}
	else if(c.getOperator().toString().equals("<>")){
		results = checker;
		results = getNotEquals(c, checker);
}
	else if(c.getOperator().toString().equals(">=")){
		results = checker;
		results = getGreaterEquals(c, checker);
}
	else if(c.getOperator().toString().equals("<=")){
		results = checker;
		results = getLessEquals(c, checker);
}
		return results;
	}
	
	/**
	 * Deletes specified pieces of information from the table
	 */
	
	public ArrayList<Row> deleteSpecific(Condition c, ArrayList<Row> results)
	{
		BTree bt = null;
		for(int p = 0; p < bts.size(); p++){
			bt = bts.get(p);//get rid of it from our BTrees as well, and off we go
		}
		for(int i = 0; i < results.size(); i++){
			Row row = results.get(i);
			String[] realRow = row.getRow();
			for(int p = 0; p < realRow.length; p++){//found our rows that we put together in the recursive delete call, and then used that array to delete them from our table
				if(ctq.getColumnDescriptions()[p].getColumnName().equals(c.getLeftOperand().toString())){
						if(c.getOperator().toString().equals("=")){
						if(realRow[p].equals(c.getRightOperand().toString())){//whatever our information that we specified was, we found it through the recursive call
							this.table.remove(row);//so then go and delete it from the table using the same primaryKey
							Key key = new Key(row.getPrimaryKeyValue(), null);
							bt.delete(key);
							i--;
							break;
						}
					}
						else 
							if(c.getOperator().toString().equals(">")){
								if(realRow[p].compareToIgnoreCase((String) c.getRightOperand()) > 0){
									this.table.remove(row);
									Key key = new Key(row.getPrimaryKeyValue(), null);
									bt.delete(key);
									i--;
									break;
								}
							}
							else 
								if(c.getOperator().toString().equals("<")){
									if(realRow[p].compareToIgnoreCase((String) c.getRightOperand()) < 0){
										this.table.remove(row);
										Key key = new Key(row.getPrimaryKeyValue(), null);
										bt.delete(key);
										i--;
										break;
									}
								}
								else 
									if(c.getOperator().toString().equals(">=")){
										if(realRow[p].compareToIgnoreCase((String) c.getRightOperand()) >= 0){
											this.table.remove(row);
											Key key = new Key(row.getPrimaryKeyValue(), null);
											bt.delete(key);
											i--;
											break;
										}
										else
										if(c.getOperator().equals("<=")){
											if(realRow[p].compareToIgnoreCase((String) c.getRightOperand()) <= 0){
												this.table.remove(row);
												Key key = new Key(row.getPrimaryKeyValue(), null);
												bt.delete(key);
												i--;
												break;
											}
										}
									}
			}
		}
	}
		return this.table;
}
	
	
	
	/**
	 * Updates the data in the table based on the user input
	 */
	public void updateTable(UpdateQuery uq)
	{
		if(!(uq.getWhereCondition().getLeftOperand() instanceof Condition) && !(uq.getWhereCondition().getRightOperand() instanceof Condition)){
			for(int i = 0; i < table.size(); i++){
				Row row = table.get(i);
				String[] realRow = row.getRow();
				for(int p = 0; p < uq.getColumnValuePairs().length; p++){//p gives you the proper spot in the array to put the new value
					String holder = uq.getColumnValuePairs()[p].getColumnID().getColumnName();
					for(int r = 0; r < ctq.getColumnDescriptions().length; r++){
						if(ctq.getColumnDescriptions()[r].getColumnName().equals(holder)){
							realRow[r] = uq.getColumnValuePairs()[p].getValue();
							Key key = new Key(row.getPrimaryKeyValue(), null);
							ArrayList<Row> brow = bt.get(key);
							for(int k = 0; k < brow.size(); k++){
								bt.delete(key);
								Key key2 = new Key(ctq.getColumnDescriptions()[r].getColumnName(), row);
								bt.put(key2);
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * Breaks up the operands that are passed into the select query
	 * @param c
	 * @return
	 */
	public ArrayList<Row> recursiveUpdate(Condition c, ArrayList<Row> checker, UpdateQuery uq)
	{
		ArrayList<Row> results = null;
				if(!(c.getLeftOperand() instanceof Condition) && !(c.getRightOperand() instanceof Condition)){
					results = checker;
						if(c.getOperator().toString().equals("=")){
								results = getEquals(c, checker);
						}
						else if(c.getOperator().toString().equals(">")){
							results = getGreater(c, checker);
					}
						else if(c.getOperator().toString().equals("<")){
							results = getLess(c, checker);
					}
						else if(c.getOperator().toString().equals("<>")){
							results = getNotEquals(c, checker);
					}
						else if(c.getOperator().toString().equals(">=")){
							results = getGreaterEquals(c, checker);
					}
						else if(c.getOperator().toString().equals("<=")){
							results = getLessEquals(c, checker);
					}
		}
				if(c.getLeftOperand() instanceof Condition && c.getRightOperand() instanceof Condition && c.getOperator().toString().equals("AND")){
					results = recursiveUpdate((Condition) c.getLeftOperand(), this.getTable(), uq);
					results = recursiveUpdate((Condition) c.getRightOperand(), results, uq);
				//loop through the results and check if all of them meet all the criteria suggested
					}
				if(c.getLeftOperand() instanceof Condition && c.getRightOperand() instanceof Condition && c.getOperator().toString().equals("OR")){
					results = recursiveUpdate((Condition) c.getLeftOperand(), results, uq);//if it's only a case of or, we can just print out the results as we receieve them
					results = recursiveUpdate((Condition) c.getRightOperand(), this.getTable(), uq);
				}
		return results;
	}
	
}