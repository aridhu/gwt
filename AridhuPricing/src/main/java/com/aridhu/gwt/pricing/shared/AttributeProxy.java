
package com.aridhu.gwt.pricing.shared;

import com.aridhu.gwt.pricing.domain.Attribute;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

/**
 * TimeSlot DTO.
 */
@ProxyFor(Attribute.class)
public interface AttributeProxy extends ValueProxy {
  int getDayOfWeek();
  
  int getEndMinutes();
  
  int getStartMinutes();

  void setDayOfWeek(int zeroBasedDayOfWeek);

  void setEndMinutes(int endMinutes);
  
  void setStartMinutes(int startMinutes);
}
