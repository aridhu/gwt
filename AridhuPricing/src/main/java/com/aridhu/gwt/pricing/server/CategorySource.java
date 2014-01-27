/*
 * Copyright 2011 Google Inc.
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


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.aridhu.gwt.pricing.domain.Category;
import com.aridhu.gwt.pricing.domain.ProductItem;
import com.aridhu.gwt.pricing.domain.Attribute;

/**
 * Provides a number of Category objects as a demonstration datasource. Many of
 * the operations in this implementation would be much more efficient in a real
 * database, but are implemented is a straightforward fashion because they're
 * not really important for understanding the RequestFactory framework.
 */
public abstract class CategorySource {

  private static int serial = 0;

  /**
   * Backing store of schedule entities. 
   */
  static class Backing extends CategorySource {

    @Override
    public Category create(Class<? extends Category> clazz) {
      return new Category();
    }

    @Override
    public Category find(Class<? extends Category> clazz, String id) {
      if (!Category.class.equals(clazz)) {
        return null;
      }
      return schedules.get(makeKey(id));
    }

    @Override
    public Class<Category> getDomainType() {
      return Category.class;
    }

    @Override
    public String getId(Category domainObject) {
      return Integer.toString(domainObject.getKey());
    }

    @Override
    public Class<String> getIdType() {
      return String.class;
    }

    @Override
    public Object getVersion(Category domainObject) {
      return domainObject.getRevision();
    }

    @Override
    public void persist(Category domainObject) {
      if (domainObject.getKey() == null) {
        domainObject.setKey(newSerial());
      }
      domainObject.setRevision(newSerial());
      Category existing = schedules.get(domainObject.getKey());
      if (existing == null) {
        schedules.put(domainObject.getKey(), domainObject);
      } else {
        copyCategoryFields(domainObject, existing);
      }
    }    
  }

  /**
   * Provides copy-on-read access to a ScheduleLocator.
   */
  public static class CopyOnRead extends Backing {
    private final CategorySource backingStore;
    
    public CopyOnRead(CategorySource backingStore) {
      this.backingStore = backingStore;
    }

    @Override
    public Category find(Class<? extends Category> clazz, String id) {
      if (!Category.class.equals(clazz)) {
        return null;
      }
      Integer key = makeKey(id);
      Category toReturn = schedules.get(key);
      if (toReturn == null) {
        Category original = backingStore.find(clazz, id);
        if (original != null) {
          toReturn = makeCopy(original);
        }
        schedules.put(key, toReturn);
      }
      return toReturn;
    }

    @Override
    public void persist(Category domainObject) {
      backingStore.persist(domainObject);
    }

    private Category makeCopy(Category source) {
      Category destination = new Category();
      copyCategoryFields(source, destination);
      return destination;
    }
  }

  static void copyCategoryFields(Category source, Category destination) {
    destination.setKey(source.getKey());
    destination.setRevision(source.getRevision());
    destination.setTimeSlots(new ArrayList<Attribute>(source.getTimeSlots()));
  }

  public static Backing createBacking() {
    return new Backing();
  }

  public static CategorySource getThreadLocalObject() {
    return AridhuPricingService.SCHEDULE_SOURCE.get();
  }

  /**
   * Create a CategoryLocator that will act directly on the given list.
   */
  public static List<Category> collectCategories(List<ProductItem> items) {
    List<Category> toReturn = new ArrayList<Category>();
    for (ProductItem item : items) {
      toReturn.add(item.getClassSchedule());
    }
    return toReturn;
  }
  
  public static CategorySource of(List<Category> schedules) {
    CategorySource backing = createBacking();
    for (Category schedule : schedules) {
      backing.persist(schedule);
    }
    return backing;
  }

  /**
   * Create a ScheduleLocator that will read through to the given source and make
   * copies of any objects that are requested.
   */
  
  
  
  public static CategorySource of(CategorySource backing) {
    return new CopyOnRead(backing);
  }

  private static Integer makeKey(String id) {
    return Integer.valueOf(id);
  }
  
  private static int newSerial() {
    return ++serial;
  }

  final Map<Integer, Category> schedules = new LinkedHashMap<Integer, Category>();

  public abstract Category create(Class<? extends Category> clazz);

  public Category find(Integer key) {
    return find(Category.class, key.toString());
  }
  
  public abstract Category find(Class<? extends Category> clazz, String id);

  public abstract Class<Category> getDomainType();

  public abstract String getId(Category domainObject);

  public abstract Class<String> getIdType();

  public abstract Object getVersion(Category domainObject);
  
  public abstract void persist(Category domainObject);

}
