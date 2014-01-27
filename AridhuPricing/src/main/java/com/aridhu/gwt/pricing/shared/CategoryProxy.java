
package com.aridhu.gwt.pricing.shared;

import com.aridhu.gwt.pricing.domain.Category;
import com.aridhu.gwt.pricing.server.CategoryLocator;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

import java.util.List;

/**
 * Schedule DTO.
 */
@ProxyFor(value = Category.class, locator = CategoryLocator.class)
public interface CategoryProxy extends EntityProxy {
  List<AttributeProxy> getTimeSlots();
  void setTimeSlots(List<AttributeProxy> slots);
}
