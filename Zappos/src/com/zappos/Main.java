package com.zappos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Main {
	public static final HashMap<String, Product> productsMap = new HashMap<String, Product>();
	public static final List<String> productsList = new ArrayList<String>();

	@SuppressWarnings("null")
	public static void main(String[] args) {

		System.out.println("Welcome !!!!");
		System.out.println("Enter 1 : start \t 2:Exit");

		Scanner sc = new Scanner(System.in);
		String input = "";

		while ((input = sc.nextLine()) != null) {
			int choice = Integer.parseInt(input);
			switch (choice) {
			case 1:
				System.out.println("Enter the number of products:");
				int numOfProducts = Integer.parseInt(sc.nextLine());
				System.out.println("Enter total cost:");
				double totalCost = Double.parseDouble(sc.nextLine());
				computeResults(numOfProducts, totalCost);
				break;
			case 2:
				System.out.println("Exiting Application");
				System.exit(0);
			default:
				System.out.println("Exiting Application");
				System.exit(0);
			}
		}

	}

	public static void computeResults(int numOfProducts, double totalCost) {

		try {
			URL urldemo;
			StringBuffer jsonString = new StringBuffer();
			JSONArray products = new JSONArray();
			for (int i = 0; i < 20; i++) {

				urldemo = new URL(
						"http://api.zappos.com/Search?page="
								+ i
								+ "&limit=100&key=12c3302e49b9b40ab8a222d7cf79a69ad11ffd78");

				URLConnection yc = urldemo.openConnection();
				BufferedReader in = new BufferedReader(new InputStreamReader(
						yc.getInputStream()));
				String inputLine;

				while ((inputLine = in.readLine()) != null) {
					// System.out.println(inputLine);
					jsonString.append(inputLine);
				}
				in.close();
				String response = jsonString.toString();
				JSONObject json = new JSONObject(response);
				JSONArray temp = json.getJSONArray("results");
				for (int j = 0; j < temp.length(); j++) {
					JSONObject product = (JSONObject) temp.get(j);
					products.put(product);
				}

			}

			for (int i = 0; i < products.length(); i++) {
				JSONObject product = (JSONObject) products.get(i);
				String productId = product.getString("productId");
				String priceString = product.getString("price");
				String productName = product.getString("productName");
				String productUrl = product.getString("productUrl");
				StringTokenizer st = new StringTokenizer(priceString, "$");
				double price = Double.parseDouble((String) st.nextElement());
				if(price < totalCost){
				Product p = new Product();
				p.setPrice(price);
				p.setProductId(productId);
				p.setProductName(productName);
				
				p.setProductUrl(productUrl);
				productsMap.put(productId, p);
				productsList.add(productId);
				}
			}

			Subsets s = new Subsets();
			int cnt = 0;
			Subsets.findSubsets(productsList, 0, 3, 150);
			Set<ArrayList<String>> retrivedProdutsSet = Subsets.requiredProductsList;
			if (retrivedProdutsSet != null || retrivedProdutsSet.size() > 0) {
				Iterator<ArrayList<String>> iterator = retrivedProdutsSet
						.iterator();
				while (iterator.hasNext()) {
					ArrayList<String> productIdsList = iterator.next();
					System.out.print(++cnt + ".");
					for (int j = 0; j < productIdsList.size(); j++) {
						System.out.print(productIdsList.get(j) + "\t \t");
					}
					System.out.println();
				}

			}
		} catch (IOException e) {
			System.out.println("exception:" + e.getMessage());
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
