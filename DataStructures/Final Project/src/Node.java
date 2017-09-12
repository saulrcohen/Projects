import java.io.IOException;
import java.lang.Comparable;
import java.util.ArrayList;
import java.util.Arrays;

import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.Condition;
import edu.yu.cs.dataStructures.fall2016.SimpleSQLParser.SelectQuery;

public class Node{
	private final int ARRAY_SIZE;
	private Key[] entryArray;
	private int top;
	private int height;
	private Node parent;
	private Node left;
	private Node right;
	public static void main(String[] args){	
	}
	
	public Node(int height)
	{
		ARRAY_SIZE = 6;
		entryArray = new Key[ARRAY_SIZE];
		top = 0;
		this.height = height;
	}
	
	/**
	 * Returns the height of the specific node
	 * @return
	 */
	public int returnHeight()
	{
		return height;
	}

	/**
	 * Puts a new value in the B-tree 
	 * @param root
	 * @param Key
	 * @param value
	 * @param height
	 */
	public Node put(Node root, Key key)
	{
		Key usingKey = null;
		int l;
		if(top == 0){
			entryArray[0] = key;
			top++;
			root.setTop(top++);
			return root;
		}
		int hi = root.getTop();
		for(int i = 0; i <= hi; i++){//find the right spot to put the new value, the children are held as values which are actually the nodes
		if(key.compareTo(root.entryArray[i]) == 1 && (root.entryArray[i+1] == null|| i == 5)){//perfect spot for a new key, no need to move anything
			if(root.entryArray[i].hasChildren() == false){//doesn't have any kids, so we're at a leaf node
			root.entryArray[i+1] = key; // set our new key at the top of the array
			root.entryArray[i+1].addValue(key.getValue());//set our new value
			int shimon = root.getTop();
			root.setTop(++shimon);
			if(shimon == 6){
				root = root.split(root);
			}
			return root;
			}
			else
			{
			if(root.entryArray[i].hasChildren()){
				usingKey = root.entryArray[i];
			return put((Node) usingKey.getChildren(), key);//recursively call through the method to get us to a leaf 
			}
			}
		}
			if(root.entryArray[i].compareTo(key) == 0 && entryArray[i].hasChildren() == false){
			root.entryArray[i].addValue(key.getValue());
			break;
		}
			else 
				if((root.entryArray[i].compareTo(key) == 0 && entryArray[i].hasChildren() == true)){
				return put(root.entryArray[i].getChildren(), key);
				}
		if(root.entryArray[i].compareTo(key) == -1 && root.entryArray[i+1].compareTo(key) == 1){
				if(root.entryArray[i].hasChildren() == false){
				l = i+1;
				int r= root.getTop();
				for(r = r++; r > l; r--){
				root.entryArray[r] = root.entryArray[r-1];
			}
				root.entryArray[l] = key;
				root.entryArray[l].addValue(key.getValue());
				int shimon = root.getTop();
				root.setTop(++shimon);
				if(root.top == 6){
					return root.split(root);
				}
				break;
			}
			else 
				return put((Node)root.entryArray[i].getChildren(), key);
			}
		if(root.entryArray[i].compareTo(key) == 1){
			if(root.entryArray[i].hasChildren() == false){
				l = i;
				int r= root.getTop();//get the top 
				for(r = r++; r > l; r--){
				root.entryArray[r] = root.entryArray[r-1];
			}
				root.entryArray[i] = key;
				root.entryArray[i].addValue(key.getValue());
				int shimon = root.getTop();
				root.setTop(++shimon);
				if(root.top == 6){
					return root.split(root);
				}
				break;
		}
			else
				return put((Node)root.entryArray[i].getChildren(), key);
		}
		}
		return this;
}
	/**
	 * Splits the current Node putting the top half of the keys in a new node, and sets all the children and parents and all that jazz
	 * @param node
	 * @return
	 */
	public Node split(Node currentNode)
	{
		height++;
		Node newKid = new Node(height);//create a new node to start the split
		Node father = new Node(height- 1);//and a second one to hold the second half of the split
		currentNode.setHeight(height);
		Key[] array = currentNode.getArray();//get the array for the node we're currently working with that needs to be split
		Key key0 = new Key(array[0].get(), null);
		Key newKey2 = new Key(array[3].get(), null);
		Key[] kidArray = newKid.getArray();//the new node that we're going to split into
		Key[] dadArray = father.getArray();//other new node we're splitting into
		for(int i = 0; i < entryArray.length/2; i++){//loop through the two arrays
			kidArray[i] = array[i+3];//second node gets the second half of the keys
			array[i+3] = null;//wipe the top half of our old node clean 
		}
		
		if(currentNode.hasParents() == false){
			dadArray[0] = key0;//parentArray[0] is set as the key
			dadArray[0].setValueNull();
			dadArray[0].setChildren(currentNode);
			dadArray[1] = newKey2;//parentArray[1] is the second key
			dadArray[1].setValueNull();//but it no longer has a value
			dadArray[1].setChildren(newKid);
			father.setTop(1);
			newKid.setParents(father);
			currentNode.setParents(father.getNode());
			newKid.setLeft(currentNode);
			currentNode.setRight(newKid);
			currentNode.setTop(3);
			newKid.setTop(3);
			return father;	
		}
		else
		{
		key0.setChildren(currentNode);
		newKey2.setChildren(newKid);
		currentNode.setRight(newKid);
		newKid.setLeft(currentNode);
		newKid.setParents(currentNode.getParents());
		currentNode.setTop(3);
		newKid.setTop(3);
		if(currentNode.getParents().getRight() != null)
		for(int i = 0; i < currentNode.getParents().getRight().getTop(); i++){
			Node check = currentNode.getParents();//if during the split, the parents got mixed up a little bit, take the old children of the node
			if(check.getRight().entryArray[i].compareTo(newKid.entryArray[0]) == -1){//and compare them to the new node, if they are actually supposed to be a child of that one, move it over to the new parent
				newKid.setParents(currentNode.getParents().getRight());
				recursivePut(currentNode.getParents().getRight(), newKey2);
				return currentNode.getParents();
			}
		}
		recursivePut(currentNode.getParents(), newKey2);
		return currentNode.getParents();
		}
	}
	/**
	 * Puts the new Key at the node directly above the currentNode (only called in the split method) 
	 * @param currentRoot
	 * @param key
	 * @return
	 */
	public Node recursivePut(Node currentRoot, Key key)
	{
		int l;
		for(int i = 0; i <= currentRoot.getTop(); i++){
			if(currentRoot.entryArray[i].compareTo(key) == -1 && (currentRoot.entryArray[i+1] == null)){
				currentRoot.entryArray[i+1]= key;
				int shimon = currentRoot.getTop();
				currentRoot.setTop(++shimon);
				if(currentRoot.getTop() == 5){
					return currentRoot.split(currentRoot);
				}
				break;
			}
			if(currentRoot.entryArray[i].compareTo(key) == -1 && currentRoot.entryArray[i].compareTo(key) == 1){
				l = i;
				for(int r = top++; r > l; r--){
					currentRoot.entryArray[r] = currentRoot.entryArray[r-1];
			}
				currentRoot.entryArray[l] = key;
				currentRoot.entryArray[l].addValue(key.getValue());
				int shimon = currentRoot.getTop();
				currentRoot.setTop(++shimon);
				if(currentRoot.getTop() == 5){
					return currentRoot.split(currentRoot);
				}
			}
			if(currentRoot.entryArray[i].compareTo(key) == 0){
				entryArray[i].addValue(key.getValue());
			}
		}
		if(currentRoot.getTop() == 5){
			return currentRoot.split(currentRoot);
	}
		return currentRoot;
	}
	/**
	 * Responds true if the Array contains the Key 
	 * @param currentNode
	 * @param key
	 * @return
	 */
	public boolean contains(Node currentNode, Key key)
	{
		for(int i = 0; i < 6; i++){
			if(entryArray[i] == (key)){
				return true;
			}
		}
		return false;
	}
	/**
	 * Returns the array within each node
	 * @return
	 */
	public Key[] getArray()
	{
		return entryArray;
	}
	
	/**
	 * Returns the place we're up to in the Array 
	 * @return
	 */
	public int getTop()
	{
		return top;
	}
	
	/**
	 * Sets the top of the Array (where we're up to)
	 * @param shimon
	 */
	public void setTop(int shimon)
	{
		this.top = shimon;
	}
	/**
	 * Get method, gets the value associated with the requested key
	 * @param currentNode
	 * @param key
	 * @param height
	 * @return
	 */
	public Object get(Node currentNode, Key key)
	{
		for(int i = 0; i < currentNode.getTop(); i++){//find the key we're looking 
			if(currentNode.entryArray[i].compareTo(key) == 0 && currentNode.entryArray[i].hasChildren() == false){//found it!
					System.out.println(currentNode.entryArray[i].getValue());
					return currentNode.entryArray[i].getAllValues();
				}
			if(currentNode.entryArray[i].compareTo(key) == 0 && currentNode.entryArray[i].hasChildren() == true){
				return get(currentNode.entryArray[i].getChildren(), key);
			}
			if(currentNode.entryArray[i].compareTo(key) == -1 && (currentNode.entryArray[i+1] == null || key.compareTo(currentNode.entryArray[i+1]) == -1) && currentNode.entryArray[i].hasChildren() == true){//not external, so check the children	
				return get((Node) currentNode.entryArray[i].getChildren(), key);
				}
			}
		return null;
}


	
	@Override
	public String toString() {
		return "Node [ARRAY_SIZE=" + ARRAY_SIZE + ", entryArray=" + Arrays.toString(entryArray) + ", top=" + top
				+ ", height=" + height + ", parent=" + parent + ", left=" + left + ", right=" + right + "]";
	}
	/**
	 * Deletes the key node by placing null as a value
	 * @param currentNode
	 * @param key
	 * @param height
	 * @return
	 * @throws IOException 
	 *
	 */
	public Object delete(Node currentNode, Key key) throws IOException
	{
	for(int i = 0; i < currentNode.getTop(); i++){
		Key[] blah = currentNode.getArray();
		if(currentNode.entryArray[i].equals(key) && currentNode.entryArray[i].hasChildren() == false){//we've found the key we're looking for
		Key spitBack = currentNode.entryArray[i];//save it as a local variable to output it
		entryArray[i].setValueNull();//set the value equal to null
		return spitBack.getValue();//return the old value just for kicks
			}
		if((currentNode.entryArray[i].compareTo(key) == -1 && (currentNode.entryArray[i+1] == null || currentNode.entryArray[i+1].compareTo(key) == 1)) && currentNode.entryArray[i].hasChildren() == true){
			return delete(currentNode.entryArray[i].getChildren() , key);//we've found the place where it should be, but need to recurse down
			}
		if(currentNode.entryArray[i].equals(key) && entryArray[i].hasChildren() == true){
		return delete(currentNode.entryArray[i].getChildren(), key);
		}
	}
	return null;
}
	/**
	 * Tests if the node has parents
	 * @return
	 */
	public boolean hasParents()
	{
		if(parent == null){
			return false;
		}
		return true;
	}
	/*
	 * Sets the parent of the currentNode
	 */
	public void setParents(Node genderNeutralParent)
	{
		parent = genderNeutralParent;
	}
	
	/**
	 * Returns the parents of the current Node
	 * @return
	 */
	public Node getParents()
	{
		return parent;
	}
	
	/**
	 * Sets the node to the left
	 * @param node
	 */
	public void setLeft(Node node)
	{
		left = node;
	}
	/**
	 * Sets the node to the right
	 * @param node
	 */
	public void setRight(Node node)
	{
		right = node;
	}
	/**
	 * Returns the node to the right
	 * @return
	 */
	public Node getRight()
	{
		return right;
	}
	/**
	 * Returns the node to the left
	 * @return
	 */
	public Node getLeft()
	{
		return left;
	}
	/**
	 * Sets the top of the array 
	 * @param shimon
	 */
	public void setHeight(int shimon)
	{
		height = shimon;
	}
	/**
	 * Returns the node of this array 
	 * @return
	 */
	public Node getNode()
	{
		return this;
	}
	
	/**
	 * gets us to the proper spot in the array to use the greater than query
	 * @param key
	 * @param root
	 * @param sq
	 * @return
	 */
	public Object getGreater(Key key, Node root, Condition c)
	{
			for(int i = 0; i <= root.getTop(); i++){//find the key we're looking 
				if((root.entryArray[i].compareTo(key) == 0 || root.entryArray[i].compareTo(key) == 1) && (root.entryArray[i].hasChildren() == false)){//found it/we're at the first number bigger
						return getGreater2(root, key, c);
					}
				if(root.entryArray[i].compareTo(key) == 0 && root.entryArray[i].hasChildren() == true){
					return get(root.entryArray[i].getChildren(), key);
				}
				if(root.entryArray[i].compareTo(key) == -1 && (root.entryArray[i+1] == null || key.compareTo(root.entryArray[i+1]) == -1) && root.entryArray[i].hasChildren() == true){//not external, so check the children	
					return get((Node) root.entryArray[i].getChildren(), key);
					}
				}
			return null;
	}
	
	/*
	 * returns the actual greater than integers
	 */
	public Object getGreater2(Node root, Key key, Condition c){
		ArrayList<Row> results = new ArrayList<Row>();
		for(int i = 0; i < root.getTop(); i++){
			if(entryArray[i].compareTo(key) == 0 || entryArray[i].compareTo(key) == 1) {
				ArrayList<Row> shimon = entryArray[i].getAllValues();
				for(int p = 0; p < entryArray[i].getAllValues().size(); p++){
					results.add(shimon.get(p));
				}
				results.add(entryArray[i].getValue());
				System.out.println(root.entryArray[i].getAllValues().toString());
			}
		}
		if(root.getRight() != null){
		return getGreater2(root.getRight(), key, c);
		}
	return results;
	}
	
	/**
	 * gets us to the proper spot in the array to use the Less than query
	 * @param key
	 * @param root
	 * @param sq
	 * @return
	 */
	public Object getLess(Key key, Node root, Condition c)
	{
			for(int i = 0; i <= root.getTop(); i++){//find the key we're looking 
				if((root.entryArray[i].compareTo(key) == 0 || root.entryArray[i].compareTo(key) == 1) && (root.entryArray[i].hasChildren() == false)){//found it/we're at the first number bigger
						return getLess2(root, key, c);
					}
				if(root.entryArray[i].compareTo(key) == 0 && root.entryArray[i].hasChildren() == true){
					return get(root.entryArray[i].getChildren(), key);
				}
				if(root.entryArray[i].compareTo(key) == -1 && (root.entryArray[i+1] == null || key.compareTo(root.entryArray[i+1]) == -1) && root.entryArray[i].hasChildren() == true){//not external, so check the children	
					return get((Node) root.entryArray[i].getChildren(), key);
					}
				}
			return null;
	}
	
	/*
	 * returns any rows that are less than the inputed values
	 */
	public Object getLess2(Node root, Key key, Condition c){
		ArrayList<Row> results = new ArrayList<Row>();
		for(int i = 0; i < root.getTop(); i++){
			if(entryArray[i].compareTo(key) == 0 || entryArray[i].compareTo(key) == 1) {
				ArrayList<Row> shimon = entryArray[i].getAllValues();
				for(int p = 0; p < entryArray[i].getAllValues().size(); p++){
					results.add(shimon.get(p));
				}
				results.add(entryArray[i].getValue());
				System.out.println(root.entryArray[i].getAllValues().toString());
			}
		}
		if(root.getLeft() != null){
		return getGreater2(root.getLeft(), key, c);
		}
	return results;
	}
}

