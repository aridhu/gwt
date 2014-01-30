package com.aridhu.gwt.pricing.server.mongo.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import com.aridhu.gwt.pricing.domain.Category;
import com.aridhu.gwt.pricing.domain.ProductItem;
import com.aridhu.gwt.pricing.domain.Attribute;
import com.aridhu.gwt.pricing.server.mongo.util.MongoConnection;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class MongoManageItems {
	static DB db;
	static DBCollection plhcol;
	
	public List<String> queryAll(){
		List<String> plhList = new ArrayList<String>();
		db = MongoConnection.getDB();
		plhcol = db.getCollection("PriceListHeader");
		DBCursor cursor = plhcol.find();
		try{
			while(cursor.hasNext()) {
				plhList.add(cursor.next().toString());
			}
		}finally{
			cursor.close();
		}
		return plhList;
	}
	
	public static List<ProductItem> getItems() {
		List<ProductItem> plhList = new ArrayList<ProductItem>();
		db = MongoConnection.getDB();
		plhcol = db.getCollection("itm_items");
		DBCursor cursor = plhcol.find();
		try{
			while(cursor.hasNext()) {
				DBObject obj = cursor.next();
				Set keyset = obj.keySet();
				Iterator it = keyset.iterator();
				while(it.hasNext()){
					String key = (String) it.next();
					System.out.print(key + " - ");
					System.out.println(obj.get(key));
				}
				ProductItem student = new ProductItem();
		        student.setName("" + obj.get("item_number"));
		        student.setDescription((String) obj.get("item_description"));
		        Random rnd = new Random(3);
		        student.setClassSchedule(generateRandomCategory(rnd));
		        plhList.add(student);				
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			cursor.close();
		}
		return plhList;
	}
	private static Category generateRandomCategory(Random rnd) {
	    int range = MAX_SCHED_ENTRIES - MIN_SCHED_ENTRIES + 1;
	    int howMany = MIN_SCHED_ENTRIES + rnd.nextInt(range);

	    ArrayList<Attribute> timeSlots = generateTimeSlots(rnd, howMany);

	    Category sched = new Category();
	    for (Attribute timeSlot : timeSlots) {
	      sched.addTimeSlot(timeSlot);
	    }
	    return sched;
	  }

	  private static Category[] generateSchedules() {
	    Random rnd = new Random();
	    Category[] toReturn = new Category[NUMBER_OF_SCHEDULES];
	    for (int i = 0; i < NUMBER_OF_SCHEDULES; i++) {
	    	Category sched = generateRandomCategory(rnd);
	      toReturn[i] = sched;
	    }
	    return toReturn;
	  }
	  private static ArrayList<Attribute> generateTimeSlots(Random rnd, int howMany) {
		    TreeSet<Attribute> timeSlots = new TreeSet<Attribute>();

		    for (int i = 0; i < howMany; ++i) {
		      int startHrs = 8 + rnd.nextInt(9); // 8 am - 5 pm
		      int dayOfWeek = 1 + rnd.nextInt(5); // Mon - Fri

		      int absStartMins = 60 * startHrs; // convert to minutes
		      int absStopMins = absStartMins + CLASS_LENGTH_MINS;

		      timeSlots.add(new Attribute(dayOfWeek, absStartMins, absStopMins));
		    }
		    
		    return new ArrayList<Attribute>(timeSlots);
		  }
	  private static final int MAX_SCHED_ENTRIES = 5;

	  private static final int MIN_SCHED_ENTRIES = 1;
	  
	  private static final int CLASS_LENGTH_MINS = 50;

	  private static final int NUMBER_OF_SCHEDULES = 100;

}
