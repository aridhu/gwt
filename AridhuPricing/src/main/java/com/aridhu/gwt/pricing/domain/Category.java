
package com.aridhu.gwt.pricing.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Holds the relevant data for a Category entity.
 * This entity does not follow the usual pattern of providing getId(), getVersion()
 * and findCategory() methods for RequestFactory's use. 
 * {@link com.google.gwt.sample.dynatablerf.server.ScheduleLocator} handles
 * those responsibilities instead.
 */
public class Category {

  private List<Attribute> timeSlots = new ArrayList<Attribute>();
  
  private Integer key;
  
  private Integer revision;

  public Category() {}

  public void addTimeSlot(Attribute timeSlot) {
    timeSlots.add(timeSlot);
  }

  public String getDescription(List<Boolean> daysFilter) {
    String s = null;
    ArrayList<Attribute> sortedSlots = new ArrayList<Attribute>(timeSlots);
    Collections.sort(sortedSlots);
    for (Attribute timeSlot : sortedSlots) {
      if (daysFilter.get(timeSlot.getDayOfWeek())) {
        if (s == null) {
          s = timeSlot.getDescription();
        } else {
          s += ", " + timeSlot.getDescription();
        }
      }
    }

    if (s != null) {
      return s;
    } else {
      return "";
    }
  }

  public Integer getKey() {
    return key;
  }

  public Integer getRevision() {
    return revision;
  }

  public List<Attribute> getTimeSlots() {
    return timeSlots;
  }

  public void setKey(Integer key) {
    this.key = key;
  }

  public void setRevision(Integer revision) {
    this.revision = revision;
  }

  public void setTimeSlots(List<Attribute> timeSlots) {
    this.timeSlots = new ArrayList<Attribute>(timeSlots);
  }

  @Override
  public String toString() {
    return getDescription(null);
  }

}
