/**
 * 
 */
package binpacking;

import java.util.ArrayList;

/**
 * @author Kolatka
 *
 */
public class FirstFit {
	DataManager dm;
	
	public FirstFit(ArrayList<Item> items){
		dm = new DataManager();
		dm.setItems(items);
	}
	public DataManager solve(){
		int itemsCount = dm.getItems().size();
		Item item;
		boolean packed;
		int containerID;
		int containersCount;
		for(int itemID=0;itemID<itemsCount;itemID++){
			item = dm.getItems().get(itemID);
			containersCount = dm.getContainers().size();
			packed = false;
			containerID=0;
			while(containerID<containersCount && !packed){
				if(dm.addItemIntoContainer(containerID,itemID)){
					
					packed = true;	
				}
				containerID++;
			}
			if(!packed){
				dm.addContainer();
				dm.addItemIntoContainer(containerID,itemID);
			}
			
		}
		
		return dm;
	}
}
