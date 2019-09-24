package binpacking;

import java.util.ArrayList;

public class BruteForce {
	DataManager dm;
	ArrayList<Item> optlist;
	int optcost = Integer.MAX_VALUE;
	
	public BruteForce(ArrayList<Item> items){
		dm = new DataManager();
		dm.setItems(items);
	}
	
	public DataManager solve(){
		if(!Parameters.stop){
			packBins(0);
			for (int i=0; i < dm.getItemsNumber(); i++){
				dm.getItems().set(i, optlist.get(i));
			}
		}
		return new NextFit(dm.getItems()).solve();
	}
    
    void packBins (int k) { 
    	if(!Parameters.stop){
			int cost;
			if (k == dm.getItemsNumber()) {
				DataManager dmnf = new NextFit(dm.getItems()).solve();
			    cost = dmnf.getResult();
			    if (cost < optcost) {
			    	optcost = cost;  
			    	optlist = new ArrayList<>();
			        for (int i = 0; i < dm.getItemsNumber(); i++ ) 
			        	optlist.add(dm.getItems().get(i));
			    	}
			} else {
			    for (int i=k; i < dm.getItemsNumber(); i++) { 
					swap (k, i);
					packBins(k+1);
					swap(k, i); 
			    }
			}
    	}
    }

    void swap(int a, int b) {
        Item x = dm.getItems().get(a);
        dm.getItems().set(a, dm.getItems().get(b));
        dm.getItems().set(b,x);
    }
}
