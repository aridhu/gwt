
package com.aridhu.gwt.pricing.shared;

import com.aridhu.gwt.pricing.domain.ProductItem;
import com.aridhu.gwt.pricing.server.AridhuPricingService;
import com.aridhu.gwt.pricing.server.CategoryService;
import com.aridhu.gwt.pricing.server.CategoryServiceLocator;
import com.google.web.bindery.requestfactory.shared.InstanceRequest;
import com.google.web.bindery.requestfactory.shared.LoggingRequest;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.RequestFactory;
import com.google.web.bindery.requestfactory.shared.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Request factory for the DynaTable sample. Instantiate via
 * {@link com.google.gwt.core.client.GWT#create}.
 */
public interface AridhuRequestFactory extends RequestFactory {
  /**
   * Source of request objects for the Person class.
   */
  @Service(ProductItem.class)
  interface PersonRequest extends RequestContext {
    InstanceRequest<ProductItemProxy, Void> persist();
  }

  /**
   * Source of request objects for the SchoolCalendarService.
   */
  @Service(AridhuPricingService.class)
  interface SchoolCalendarRequest extends RequestContext {
    List<Boolean> ALL_DAYS = Collections.unmodifiableList(Arrays.asList(true,
        true, true, true, true, true, true));
    List<Boolean> NO_DAYS = Collections.unmodifiableList(Arrays.asList(false,
        false, false, false, false, false, false));

    Request<List<ProductItemProxy>> getPeople(int startIndex, int maxCount,
        List<Boolean> dayFilter);

    Request<ProductItemProxy> getRandomProductItem();
  }

  /**
   * Source of request objects for Schedule entities.
   */
  @Service(value = CategoryService.class, locator = CategoryServiceLocator.class)
  interface ScheduleRequest extends RequestContext {
    Request<AttributeProxy> createTimeSlot(int zeroBasedDayOfWeek, int startMinutes,
        int endMinutes);
  }

  LoggingRequest loggingRequest();

  PersonRequest personRequest();
  
  ScheduleRequest scheduleRequest();

  SchoolCalendarRequest schoolCalendarRequest();
}
