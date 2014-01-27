/*
 * Copyright 2007 Google Inc.
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

import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.aridhu.gwt.pricing.domain.Category;
import com.aridhu.gwt.pricing.domain.ProductItem;


/**
 * The server-side service class.
 */
public class AridhuPricingService implements Filter {

  private static final ThreadLocal<ProductItemSource> PERSON_SOURCE = new ThreadLocal<ProductItemSource>();
  static final ThreadLocal<CategorySource> SCHEDULE_SOURCE = new ThreadLocal<CategorySource>();

  public static ProductItem createProductItem() {
	  //the UI create new product item
	  // check has it been already exist
	  // then create it in Mongo
    checkPersonSource();
    ProductItem proitem = new ProductItem();
    PERSON_SOURCE.get().persist(proitem);
    return proitem;
  }

  public static ProductItem findProductItem(String id) {
    checkPersonSource();
    return PERSON_SOURCE.get().findProductItem(id);
  }

  public static List<ProductItem> getPeople(int startIndex, int maxCount,
      List<Boolean> filter) {
    checkPersonSource();
    return PERSON_SOURCE.get().getPeople(startIndex, maxCount, filter);
  }

  public static ProductItem getRandomProductItem() {
	  ProductItemSource source = PERSON_SOURCE.get();
    return source.getPeople(new Random().nextInt(source.countPeople()), 1,
        ALL_DAYS).get(0);
  }

  public static void persist(ProductItem person) {
    checkPersonSource();
    PERSON_SOURCE.get().persist(person);
  }

  private static void checkPersonSource() {
    if (PERSON_SOURCE.get() == null) {
      throw new IllegalStateException(
          "Calling service method outside of HTTP request");
    }
  }

  private ProductItemSource backingStore;
  private CategorySource scheduleStore;

  public void destroy() {
  }

  public void doFilter(ServletRequest req, ServletResponse resp,
      FilterChain chain) throws IOException, ServletException {
    try {
    	CategorySource scheduleBacking = CategorySource.of(scheduleStore);
      SCHEDULE_SOURCE.set(scheduleBacking);
      PERSON_SOURCE.set(ProductItemSource.of(backingStore, scheduleBacking));
      chain.doFilter(req, resp);
    } finally {
      PERSON_SOURCE.set(null);
      SCHEDULE_SOURCE.set(null);
    }
  }

  public void init(FilterConfig config) {
    backingStore = (ProductItemSource) config.getServletContext().getAttribute(
        AridhuPricingService.class.getName());
    if (backingStore == null) {
      List<ProductItem> productItems = ProductItemSource.getProductItems();
      List<Category> schedules = CategorySource.collectCategories(productItems);
      scheduleStore = CategorySource.of(schedules);
      backingStore = ProductItemSource.of(productItems, scheduleStore);
      config.getServletContext().setAttribute(
          AridhuPricingService.class.getName(), backingStore);
    }
  }
}
