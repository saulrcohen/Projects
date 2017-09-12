import java.util.ArrayList;

public class Key implements Comparable<Key>{ 
	private String Key;
	private Row valueList;
	private Node children;
	private ArrayList<Row> allValues;
	
	public static void main(String[] args)
	{
	}
	
	public Key(String Key, Row value)
	{
		this.Key = Key;
		children = null;
		allValues = new ArrayList<Row>();
		this.valueList = value;
		this.allValues.add(valueList);
		for(int i = 0; i < allValues.size(); i++){
			if(allValues.get(i) == null){
				allValues.remove(i);
			}
		}
	}
	/**
	 * Returns the key 
	 * @return
	 */
	public String get()
	{
		return Key;
	}
	
	/**
	 * Sets a new Key
	 * @param newKey
	 */
	public void set(String newKey)
	{
		this.Key = newKey;
	}
	/**
	 * Returns the value associated with the key
	 * @param key
	 * @return
	 */
	public Row getValue()
	{
		return valueList;
	}
	
	public ArrayList<Row> getValueList()
	{
		return this.allValues;
	}
	
	/**
	 * Set a new value for the associated key
	 * @param value
	 */
	public void addValue(Row value)
	{
		allValues.add(value);
	}
	
	/**
	 * Sets the value of a specified key to null
	 */
	public void setValueNull()
	{
		allValues.clear();
	}
	
	public Node getChildren()
	{
		return children;
	}
	
	public void setChildren(Node newKid)
	{
		children = newKid;
	}
	
	public boolean hasChildren()
	{
		if(children == null){
			return false;
		}
		return true;
	}
	public void deleteChildren()
	{
		children = null;
	}
	
	public ArrayList<Row> getAllValues()
	{
		return this.allValues;
	}
	
	public String toString() {
		return this.Key;
	}
	//@Override
	public int compareTo(Key k) {
		int hi = this.Key.compareToIgnoreCase(k.Key);
		if(hi > 0){
			return 1;
		}
		if(hi < 0){
			return-1;
		}
		if(hi == 0){
			return 0;
		}
	return 0;
	}
}
