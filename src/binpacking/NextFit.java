package binpacking;

import java.util.ArrayList;

public class NextFit {

	DataManager dm;
	
	public NextFit(ArrayList<Item> items){
		dm = new DataManager();
		dm.setItems(items);
	}
	
	public DataManager solve(){
		int itemsCount = dm.getItems().size();
		int containerID = 0;
		Item item;
		for(int itemID=0;itemID<itemsCount;itemID++){
			if(!dm.addItemIntoContainer(containerID,itemID)){
				dm.addContainer();
				containerID++;
				dm.addItemIntoContainer(containerID,itemID);
			}
		}
		return dm;
	}
	
}
