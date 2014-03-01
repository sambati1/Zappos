package com.zappos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Subsets {
	public static final Set<ArrayList<String>> requiredProductsList = new HashSet<ArrayList<String>>();
	
	//Product list contains all the productIds retrieved 
	//Find the subsets of Size numOfProducts and filtering the subsets whose products are sum up to
	//close to total cost.
	
	public static ArrayList<ArrayList<String>> findSubsets(
			List<String> productslist, int index, int numberOfProducts,
			double totalCost) {

		ArrayList<ArrayList<String>> allSubSets;
		if (productslist.size() == index) {
			allSubSets = new ArrayList<ArrayList<String>>();
			allSubSets.add(new ArrayList<String>());
		} else {
			allSubSets = findSubsets(productslist, index + 1, numberOfProducts,
					totalCost);
			String item = productslist.get(index);
			ArrayList<ArrayList<String>> moreSubSets = new ArrayList<ArrayList<String>>();
			
			for (ArrayList<String> subset : allSubSets) {
				ArrayList<String> newSubSet = new ArrayList<String>();
				newSubSet.addAll(subset);
				newSubSet.add(item);
				moreSubSets.add(newSubSet);
				if(newSubSet.size() <= numberOfProducts){
					moreSubSets.add(newSubSet);
					}
				if (newSubSet.size() == numberOfProducts) {
					
					double sum = 0.0;

					for (int i = 0; i < newSubSet.size(); i++) {
						if (Main.productsMap != null
								|| Main.productsMap.size() > 0){
							
							if (Main.productsMap.containsKey(newSubSet
									.get(i))) {
								Product p = Main.productsMap.get(newSubSet
									.get(i));
								sum += p.getPrice();
							}
						}
					}
					
					if (( sum >= (totalCost - 10) ) && (sum <= (totalCost + 10))) {
						requiredProductsList.add(newSubSet);
					}
					
				}
				if (newSubSet.size() > numberOfProducts){
					return allSubSets;
				}
			}
			allSubSets.addAll(moreSubSets);
		}

		return allSubSets;

	}

}
