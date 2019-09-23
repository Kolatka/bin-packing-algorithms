/**
 * 
 */
package binpacking;

/**
 * @author Kolatka
 *
 */
public class Item {

	
	private int id;
	private int weight;
	private boolean isPacked;	
	public Item(int id, int weight) {
		this.id = id;
		this.weight = weight;
		this.isPacked = false;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public boolean isPacked() {
		return isPacked;
	}
	public void setPacked(boolean isPacked) {
		this.isPacked = isPacked;
	}
	
	
	
}
