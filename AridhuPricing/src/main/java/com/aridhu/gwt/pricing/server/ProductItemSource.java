/*
 * Copyright 2010 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.aridhu.gwt.pricing.server;

import static com.aridhu.gwt.pricing.shared.AridhuRequestFactory.SchoolCalendarRequest.ALL_DAYS;
import static com.aridhu.gwt.pricing.shared.AridhuRequestFactory.SchoolCalendarRequest.NO_DAYS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.aridhu.gwt.pricing.domain.ProductItem;
import com.aridhu.gwt.pricing.domain.Category;
import com.aridhu.gwt.pricing.server.mongo.MongoConnection;
import com.aridhu.gwt.pricing.server.mongo.MongoManageItems;

/**
 * Provides a number of Person objects as a demonstration datasource. Many of
 * the operations in this implementation would be much more efficient in a real
 * database, but are implemented is a straightforward fashion because they're
 * not really important for understanding the RequestFactory framework.
 */
public abstract class ProductItemSource {
  static class Backing extends ProductItemSource {
    private Long serial = 0L;
    private final CategorySource scheduleStore;
    
    public Backing(CategorySource scheduleStore) {
      this.scheduleStore = scheduleStore;
    }

    @Override
    public int countPeople() {
      return people.size();
    }

    @Override
    public ProductItem findProductItem(String id) {
      return people.get(id);
    }

    @Override
    public List<ProductItem> getPeople(int startIndex, int maxCount,
        List<Boolean> daysFilter) {
      int peopleCount = countPeople();

      int start = startIndex;
      if (start >= peopleCount) {
        return Collections.emptyList();
      }

      int end = Math.min(startIndex + maxCount, peopleCount);
      if (start == end) {
        return Collections.emptyList();
      }

      // If there's a simple filter, use a fast path
      if (ALL_DAYS.equals(daysFilter)) {
        return new ArrayList<ProductItem>(people.values()).subList(start, end);
      } else if (NO_DAYS.equals(daysFilter)) {
        return new ArrayList<ProductItem>();
      }

      /*
       * Otherwise, iterate from the start position until we collect enough
       * People or hit the end of the list.
       */
      Iterator<ProductItem> it = people.values().iterator();
      int skipped = 0;
      List<ProductItem> toReturn = new ArrayList<ProductItem>(maxCount);
      while (toReturn.size() < maxCount && it.hasNext()) {
    	  ProductItem person = it.next();
        if (person.getScheduleWithFilter(daysFilter).length() > 0) {
          if (skipped++ < startIndex) {
            continue;
          }
          toReturn.add(person);
        }
      }
      return toReturn;
    }

    @Override
    public void persist(ProductItem person) {
      if (person.getId() == null) {
        person.setId(Long.toString(++serial));
      }
      person.setVersion(person.getVersion() + 1);
      if (person.getClassSchedule() != null) {
        scheduleStore.persist(person.getClassSchedule());
      }
      ProductItem existing = people.get(person.getId());
      if (existing != null) {
        existing.copyFrom(person);
      } else {
        people.put(person.getId(), person);
      }
    }
  }

  static class CopyOnRead extends ProductItemSource {
    private final ProductItemSource backingStore;
    private final CategorySource scheduleStore;

    public CopyOnRead(ProductItemSource backingStore, CategorySource scheduleStore) {
      this.backingStore = backingStore;
      this.scheduleStore = scheduleStore;
    }

    @Override
    public int countPeople() {
      return backingStore.countPeople();
    }

    @Override
    public ProductItem findProductItem(String id) {
    	ProductItem toReturn = people.get(id);
      if (toReturn == null) {
        toReturn = backingStore.findProductItem(id);
        if (toReturn != null) {
          toReturn = toReturn.makeCopy();
          
          Integer scheduleKey = toReturn.getClassSchedule().getKey();
          Category scheduleCopy = scheduleStore.find(scheduleKey);
          toReturn.setClassSchedule(scheduleCopy);
        }
        people.put(id, toReturn);
      }
      return toReturn;
    }

    @Override
    public List<ProductItem> getPeople(int startIndex, int maxCount,
        List<Boolean> daysFilter) {
      List<ProductItem> toReturn = new ArrayList<ProductItem>(maxCount);
      for (ProductItem person : backingStore.getPeople(startIndex, maxCount,
          daysFilter)) {
    	ProductItem copy = findProductItem(person.getId());
        copy.setDaysFilter(daysFilter);
        toReturn.add(copy);
      }
      return toReturn;
    }

    @Override
    public void persist(ProductItem person) {
      backingStore.persist(person);
    }
  }

  /**
   * Create a PersonSource that will act directly on the given list.
   */
  public static List<ProductItem> getProductItems() {
    List<ProductItem> toReturn = null;
    try {
		MongoConnection.connect();
		toReturn = MongoManageItems.getItems();
		MongoConnection.close();
	} catch (Exception e) {
		e.printStackTrace();
	}
    return toReturn;
  }
  
  public static ProductItemSource of(List<ProductItem> productItems,CategorySource schedules) {
    ProductItemSource backing = new Backing(schedules);
    for (ProductItem pitem : productItems) {
      backing.persist(pitem);
    }
    return backing;
  }

  /**
   * Create a PersonSource that will read through to the given source and make
   * copies of any objects that are requested.
   */
  public static ProductItemSource of(ProductItemSource backing, CategorySource scheduleBacking) {
    return new CopyOnRead(backing, scheduleBacking);
  }

  final Map<String, ProductItem> people = new LinkedHashMap<String, ProductItem>();

  public abstract int countPeople();

  public abstract ProductItem findProductItem(String id);

  public abstract List<ProductItem> getPeople(int startIndex, int maxCount,
      List<Boolean> daysFilter);

  public abstract void persist(ProductItem person);
}
