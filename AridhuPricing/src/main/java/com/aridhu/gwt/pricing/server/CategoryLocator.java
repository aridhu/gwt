
package com.aridhu.gwt.pricing.server;

import com.aridhu.gwt.pricing.domain.Category;
import com.google.web.bindery.requestfactory.shared.Locator;

/**
 * This class serves as an example of implementing a Locator to allow
 * RequestFactory to work with entities that don't conform to its expectations of
 * static find*() methods, and getId() and getVersion() methods. In a production
 * application such a Locator might be the bridge to your dependency injection
 * framework, or a data access object.
 * <p>
 * There is a reference to this class in a {@literal @}Service annotation in
 * {@link com.google.gwt.sample.dynatablerf.shared.DynaTableRequestFactory}
 */
public class CategoryLocator extends Locator<Category, String> {
  
  public static CategorySource getThreadLocalObject() {
    return AridhuPricingService.SCHEDULE_SOURCE.get();
  }

  @Override
  public Category create(Class<? extends Category> clazz) {
    return getThreadLocalObject().create(clazz);
  }

  @Override
  public Category find(Class<? extends Category> clazz, String id) {
    return getThreadLocalObject().find(clazz, id);
  }

  @Override
  public Class<Category> getDomainType() {
    return getThreadLocalObject().getDomainType();
  }

  @Override
  public String getId(Category domainObject) {
    return getThreadLocalObject().getId(domainObject);
  }

  @Override
  public Class<String> getIdType() {
    return getThreadLocalObject().getIdType();
  }

  @Override
  public Object getVersion(Category domainObject) {
    return getThreadLocalObject().getVersion(domainObject);
  }

  public void persist(Category domainObject) {
    getThreadLocalObject().persist(domainObject);
  }
}
