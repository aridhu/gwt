
package com.aridhu.gwt.pricing.shared;

import com.aridhu.gwt.pricing.domain.ProductItem;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.EntityProxyId;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

/**
 * Person DTO.
 */
@ProxyFor(ProductItem.class)
public interface ProductItemProxy extends EntityProxy {

  AddressProxy getAddress();

  CategoryProxy getClassSchedule();
  
  String getDescription();

  ProductItemProxy getMentor();

  String getName();

  String getNote();

  String getScheduleDescription();
  
  void setAddress(AddressProxy address);

  void setClassSchedule(CategoryProxy schedule);

  void setDescription(String description);

  void setMentor(ProductItemProxy mentor);

  void setName(String name);

  void setNote(String note);
  
  EntityProxyId<ProductItemProxy> stableId();
}
