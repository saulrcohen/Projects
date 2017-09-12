import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.ColumnDescription;
import java.util.List;
import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.ColumnID;

public class Columns {
	private ColumnID columnHeaders;
	private ColumnDescription cdr;
	
	public static void Main(String[] args)
	{
		ColumnID cid= new ColumnID("Students", "SSN");
		
	}
	private Columns(ColumnID cid, ColumnDescription cdr)
	{
	columnHeaders = cid;
	this.cdr = cdr;
	}
	
	public void setName(ColumnID cd)
	{
		this.columnHeaders = columnHeaders;
	}
	
	public ColumnID getDescription()
	{
		return columnHeaders;
	}
	
	public void setType(ColumnDescription cdr)
	{
		this.cdr = cdr;
	}
	
	public ColumnDescription getType()
	{
		return cdr;
	}
}
	
