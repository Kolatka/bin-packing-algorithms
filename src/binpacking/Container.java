package binpacking;

import java.util.ArrayList;

public class Container {

	private int id;
	private int maxVolume;
	private int leftVolume;
	private ArrayList<Item> items;
	
	public Container(int id, int maxVolume) {
		this.id = id;
		this.maxVolume = maxVolume;
		this.leftVolume = maxVolume;
		this.items = new ArrayList<>();
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMaxVolume() {
		return maxVolume;
	}
	public void setMaxVolume(int maxVolume) {
		this.maxVolume = maxVolume;
	}
	public int getLeftVolume() {
		return leftVolume;
	}
	public void setLeftVolume(int leftVolume) {
		this.leftVolume = leftVolume;
	}
	public ArrayList<Item> getItems() {
		return items;
	}
	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}
	
	
	
	
	
}
