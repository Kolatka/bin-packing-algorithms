/**
 * 
 */
package binpacking;

import java.util.ArrayList;

/**
 * @author Kolatka
 *
 */
public class BranchAndBound {
	DataManager dm;
	ArrayList<Integer> list;
	ArrayList<Integer> optlist;
	int optcost = Integer.MAX_VALUE; 

	
	public BranchAndBound(ArrayList<Item> items){
		dm = new DataManager();
		dm.setItems(items);
		optlist = new ArrayList<Integer>();
		list = new ArrayList<Integer>();
	}
	

	
	public DataManager solve(){
		if(Parameters.stop == false){
			int bins = 1;
	
			for (int i=0; i < dm.getItems().size(); i++){
				list.add(dm.getItems().get(i).getWeight());
			}
			packBins(0);
			
			for (int i=0; i < list.size(); i++){
				dm.getItems().get(i).setWeight(optlist.get(i));
			}
			
		}
		DataManager dmnf = new NextFit(dm.getItems()).solve();
		return dmnf;
	}

	
	   //--------binCount---------------------------
    // calculate next fit cost of list[0..k] inclusive
    int binCount( int k) {
		int count = 1; 
		int curcap = dm.getContainerVolume();  //capacity of open bin             // number of calls to bincount
	
		for (int i = 0; i <= k ; i++) {
		    // inv: current bin is open, has been counted
		    if (curcap < list.get(i).intValue()) {
				count++;                  // make new bin 
				curcap = dm.getContainerVolume() - list.get(i);  
		    } else {
		    	curcap = curcap - list.get(i); //put in current bin 
		    }
		}
		return count;
    }

    int binCount () { 
    	return binCount( list.size()-1 ); 
    }

    int sumWeights(int s) { 
	int sum = 0;
	for (int i=s; i< list.size(); i++) 
	    sum += list.get(i).intValue();
	return sum; 
    }
    
    
    long pb=0;
    void packBins (int k) { 
    	if(Parameters.stop == false){
	    	if(pb%10000000==0)System.out.println(pb);
	    	pb++;
	        int s; 
			int b; 
			int c; 
			int bound; 
			int bcount; 
			int capacity = dm.getContainerVolume();
	
			if(optcost > dm.getLowerBound()){
			
				if (k == list.size()) {
				    bcount = binCount(); 
				    System.out.println("running: " + bcount + " | " + optcost);
				    if (bcount < optcost) {
				    	optcost = bcount;  
				    	optlist = new ArrayList<Integer>();
		                for (int i = 0; i < list.size(); i++) {
		                    System.out.print(list.get(i).intValue() + "  ");
		                }
		                System.out.println();
				        for (int i = 0; i < list.size(); i++ ) 
				        	optlist.add(list.get(i).intValue()); 
				    	}
				} else {
				    for (int i=k; i < list.size(); i++) { 
						swap (k, i);  
				
						b = binCount(k);
						s = sumWeights(k+1); 
						c = dm.getContainerVolume() - list.get(k).intValue() ;   
						
						bound = (int) (b + Math.ceil((s - c)/capacity));
		                
						if (bound < optcost) 
							packBins(k+1); 
				
						swap(k, i); 
				    }
				}
	    	}
    	}
    }
	
	
    void swap(int a, int b) {
        int x = list.get(a);
        list.set(a, list.get(b));
        list.set(b, x);
    }
}
