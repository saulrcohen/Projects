import java.io.IOException;
import java.lang.Comparable;
import java.util.ArrayList;

import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.Condition;
import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.SelectQuery;

public class BTree{
	private Node root;
	private int height;
	private static Node firstRoot;
	private String primaryKey;
	private String name;

	public static void main(String[] args)
	
	{
		 /**BTree bt = new BTree(node1, "hi");
		 Key key1 = new Key("aa","1");
		 Key key2 = new Key("b","2");
		 Key key3 = new Key("c","3");
		 Key key4 = new Key("d","4");
		 Key key5 = new Key("e","6");
		 Key key6 = new Key("f","6");
		 Key key7 = new Key("g","7");
		 Key key8 = new Key("h","8");
		 Key key9 = new Key("i","9");
		 Key key10 = new Key("j","10");
		 Key key11 = new Key("k","11");
		 Key key12 = new Key("l","12");
		 Key key13 = new Key("m","13");
		 Key key14 = new Key("n","14");
		 Key key15 = new Key("o","15");
		 Key key16 = new Key("p","16");
		 Key key17 = new Key("q","17");
		 Key key18 = new Key("r","18");
		 Key key19 = new Key("s","19");
		 Key key20 = new Key("t","20");
		 Key key21 = new Key("u","21");
		 Key key22 = new Key("v","22");
		 Key key23 = new Key("w","23");
		 Key key24 = new Key("x","24");
		 Key key25 = new Key("y","25");
		 Key key26 = new Key("y", null);
		 bt.put(key1);
		 bt.put(key2);
		 bt.put(key6);
		 bt.put(key4);
		 bt.put(key22);
		 bt.put(key3);
		 bt.put(key7);
		 bt.put(key8);
		 bt.put(key9);
		 bt.put(key10);
		 bt.put(key11);
		 bt.put(key17);
		 bt.put(key12);
		 bt.put(key13);
		 bt.put(key14);
		 bt.put(key15);
		 bt.put(key16);
		 bt.put(key18);
		 bt.put(key19);
		 bt.put(key5);
		 bt.put(key20);
		 bt.put(key21);
		 bt.put(key23);
		 bt.put(key24);
		 bt.put(key25);
		System.out.println(bt.get(key5));
		System.out.println(bt.get(key20));
		System.out.println(bt.get(key26));
		System.out.println(firstRoot.getArray().toString());
	*/
	}
	
		
	public BTree(Node root, String primaryKey, String name)
	 
	{
		this.root = root;
		height =  0;
		Node node = new Node(0);
		Key key0 = new Key("a", null);
		this.firstRoot = node;
		this.firstRoot.getArray()[0] = key0;
		this.firstRoot.setTop(1);
		this.primaryKey = primaryKey;
		this.name = name;
	}
	
	/**
	 * Puts a new key in the B-Tree
	 * @param key
	 * @return
	 */
	public Node put(Key key)
	{
	Node x =  root.put(root, key);
	if(x.hasParents() == false){
		root = x;
		return root;
	}
	if(root.hasParents()){
		root = root.getParents();
	}
	return root;
	}

	/**
	 * Gets a specific value from the B Tree
	 * @param key
	 * @return
	 */
	public ArrayList<Row> get(Key key)
	{
		return (ArrayList<Row>) root.get(root, key);
	}
	
	
	/**
	 * deletes a key from the BTree
	 * @param key
	 */
	 public void delete(Key key)

	{
		try {
			root.delete(root, key);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the root of the BTree
	 * @return
	 */
	 public Node getRoot()
	{
		return root;
	}
	
	/**
	 * Sets a new Root for the BTree
	 * @param newRoot
	 */
	 public void setRoot(Node newRoot)
	{
		root = newRoot;
	}
	 
	 /**
	  * Returns the smallest node in the tree
	  */
	 public Node getSentinel()
	 {
		 return firstRoot;
	 }
	 
	/**
	 * Returns the primaryKey of this specific BTree
	 * @return
	 */
	 public String getPrimaryKey()
	 {
		 return this.primaryKey;
	 }
	 
	 /**
	  * Gets any values greater than the current value inserted
	  */
	 public ArrayList<Row> getGreater(Key key, Condition c)
	 {
		return (ArrayList<Row>) root.getGreater(key, root, c);
	 }
	 
	 /**
	  * sets a new primaryKey for this table
	  * @param s
	  */
	 public void setPrimaryKey(String s)
	 {
		 this.primaryKey = s;
	 }
	 
	 public ArrayList<Row> getLess(Key key, Condition c)
	 {
		 
		 return (ArrayList<Row>) root.getLess(key, root, c);
	 }
}
