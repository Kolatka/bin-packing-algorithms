package binpacking;

import java.util.ArrayList;
import java.util.Random;

public class DataManager {

	private ArrayList<Item> items;
	private ArrayList<Item> itemsInc;
	private ArrayList<Item> itemsDec;
	private ArrayList<Container> containers;
	
	private int minItemWeight = Parameters.minItemWeight;
	private int maxItemWeight = Parameters.maxItemWeight;
	private int itemsNumber = Parameters.itemsNumber;
	private int containerVolume = Parameters.containerVolume;

	public DataManager(){
		this.containers = new ArrayList<>();
		this.items = new ArrayList<>();
		
		addContainer();
	}
	
	public void generateItems(){
		int max = getRandomNumber(itemsNumber,itemsNumber);
		for(int i=0;i<max;i++) {
			items.add(new Item(i, getRandomNumber(minItemWeight, maxItemWeight)));
		}
	}
	
	public void addContainer(){
		int id;
		if(getContainers().isEmpty()) id = 0;
		else id = getContainers().size();
		getContainers().add(new Container(id, containerVolume));
	}
	
	public boolean addItemIntoContainer(int containerID, int itemID){
		Item item = getItems().get(itemID);
		Container container = getContainers().get(containerID);
		if(container.getLeftVolume()>=item.getWeight()){
			container.getItems().add(item);
			container.setLeftVolume(container.getLeftVolume()-item.getWeight());
			item.setPacked(true);
			return true;
		}else return false;
	}
	
	private int getRandomNumber(int min, int max){
		Random random = new Random();
		return random.nextInt(max - min + 1) + min;
	}

	public String printItems(ArrayList<Item> items){
		StringBuilder s = new StringBuilder("ID\t\tSIZE");
		int size = items.size();
		for(int i=0; i<size; i++){
			s.append("\n").append(items.get(i).getId()).append("\t\t").append(items.get(i).getWeight());
		}
		return s.toString();
	}
	
	public String printContainers(){
		StringBuilder s = new StringBuilder();
		int size = getContainers().size();
		for(int i=0; i<size; i++){
			s.append("\nID\tFREE");
			s.append("\n").append(getContainers().get(i).getId()).append("\t").append(getContainers().get(i).getLeftVolume());
			int itemsSize = getContainers().get(i).getItems().size();
			if (itemsSize>0){
				s.append("\n\t\t\tID\t\tSIZE");
				for(int j=0; j<itemsSize; j++){
					s.append("\n\t\t\t").append(getContainers().get(i).getItems().get(j).getId()).append("\t\t").append(getContainers().get(i).getItems().get(j).getWeight());
				}
			}
			s.append("\n---------------------------------------------------");
		}
		return s.toString();
	}

	public int getResult(){
		return getContainers().size();
	}

	public int getLowerBound(){
		return (int) Math.ceil((double)getTotalWeight()/containerVolume);
	}
	
	public int getTotalWeight(){
		int size = getItems().size();
		int totalWeight=0;
		for(int i=0;i<size;i++){
			totalWeight = totalWeight + getItems().get(i).getWeight();
		}
		return totalWeight;
	}

	public void sort(){
		 int max = maxItemWeight;
		 int size = items.size();
	     for (int i = 1; max/i > 0; i *= 10)
	    	 radixSort(i);	
	}
	
     void radixSort(int exp)
    {
    	int size = items.size();
    	ArrayList<Item> output = new ArrayList<>();
    	Item item = new Item(-1,-1);
    	for (int i = 0; i < size; i++){
    		output.add(item);
    	}
        int count[] = new int[10];
        for (int i = 0; i < size; i++){
            count[ (items.get(i).getWeight()/exp)%10 ]++;
        }
        for (int i = 1; i < 10; i++)
            count[i] += count[i - 1];
        for (int i = size - 1; i >= 0; i--)
        {
        	output.set(count[(items.get(i).getWeight()/exp)%10 ] - 1, items.get(i));
        	count[ (items.get(i).getWeight()/exp)%10]--;
        }
        
        itemsInc = output;
    }

     
     public void sortInc() {
    	 ArrayList<Item> items = this.items;
         Item key;
         for (int i = 1; i < items.size(); i++) {
               key = items.get(i);
               int j = i;
               while (j > 0 && items.get(j-1).getWeight() > key.getWeight()) {
            	   items.set(j, items.get(j-1));
                   j--;

               }
               items.set(j, key);
         }
         itemsInc = items;
     }
     
     public void sortDec() {
    	 ArrayList<Item> items = this.items;
         Item key;
         for (int i = 1; i < items.size(); i++) {
               key = items.get(i);
               int j = i;
               while (j > 0 && items.get(j-1).getWeight() < key.getWeight()) {
            	   items.set(j, items.get(j-1));
                   j--;

               }
               items.set(j, key);
         }
         itemsDec = items;
     }
     
 	void swap(int id1, int id2){
		Item temp = getItems().get(id1);
		getItems().set(id1, getItems().get(id2));
		getItems().set(id2, temp);
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}

	public ArrayList<Item> getItemsInc() {
		return itemsInc;
	}

	public void setItemsInc(ArrayList<Item> itemsInc) {
		this.itemsInc = itemsInc;
	}

	public ArrayList<Item> getItemsDec() {
		return itemsDec;
	}

	public void setItemsDec(ArrayList<Item> itemsDec) {
		this.itemsDec = itemsDec;
	}

	public ArrayList<Container> getContainers() {
		return containers;
	}
	public void setContainers(ArrayList<Container> containers) {
		this.containers = containers;
	}

	public int getMinItemWeight() {
		return minItemWeight;
	}

	public void setMinItemWeight(int minItemWeight) {
		this.minItemWeight = minItemWeight;
	}

	public int getMaxItemWeight() {
		return maxItemWeight;
	}

	public void setMaxItemWeight(int maxItemWeight) {
		this.maxItemWeight = maxItemWeight;
	}

	public int getItemsNumber() {
		return itemsNumber;
	}

	public void setItemsNumber(int itemsNumber) {
		this.itemsNumber = itemsNumber;
	}
	public int getContainerVolume(){
		return containerVolume;
	}
	public void setContainerVolume(int containerVolume) {
		this.containerVolume = containerVolume;
	}

}
