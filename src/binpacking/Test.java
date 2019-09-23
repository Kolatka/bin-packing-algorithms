/**
 * 
 */
package binpacking;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Kolatka
 *
 */
public class Test {

DataManager dm;
	
	Test(ArrayList<Item> items){
		dm = new DataManager();
		dm.setItems(items);
	}
	
	public DataManager solve(){
		//dm.sort();
		int size = dm.getItems().size();
		
		int containerID=0;
		int sum = 0;
		int packed = size;
		while(size>0){
			sum = 0;
			while(sum<dm.getContainerVolume()){
				int itemID=size;
				if (sum + dm.getItems().get(itemID).getWeight()!=dm.getContainerVolume())
					sum += dm.getItems().get(itemID).getWeight();
				itemID--;
			}
		}
		
		return dm;
	}
	
	
	
	private void swap(int id1, int id2){
		Item temp = dm.getItems().get(id1);
		dm.getItems().set(id1, dm.getItems().get(id2));
		dm.getItems().set(id2, temp);
	}
}
