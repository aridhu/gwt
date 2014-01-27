
package com.aridhu.gwt.pricing.domain;

import static com.aridhu.gwt.pricing.shared.AridhuRequestFactory.SchoolCalendarRequest.ALL_DAYS;

import java.util.List;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.aridhu.gwt.pricing.server.AridhuPricingService;


/**
 * Hold relevant data for Product Item.
 */
public class ProductItem {
  /**
   * New instances could be created on the client, but it's a better demo to
   * send back a Person with a bunch of dummy data.
   */
  public static ProductItem createProductItem() {
    return AridhuPricingService.createProductItem();
  }

  /**
   * The RequestFactory requires a static finder method for each proxied type.
   * Soon it should allow you to customize how instances are found.
   */
  public static ProductItem findProductItem(String id) {
    /*
     * TODO At the moment requestFactory requires a finder method per type It
     * should get more flexible soon.
     */
    return AridhuPricingService.findProductItem(id);
  }

  @NotNull
  private Address address = new Address();

  @NotNull
  private Category classSchedule = new Category();

  @NotNull
  private String description = "DESC";

  private ProductItem mentor;

  @NotNull
  @Size(min = 2, message = "ProductItems aren't just characters")
  private String name;

  // May be null if the ProductItem is newly-created
  private String id;

  @NotNull
  @DecimalMin("0")
  private Integer version = 0;

  private String note;

  private List<Boolean> daysFilters = ALL_DAYS;

  public ProductItem() {
  }

  protected ProductItem(ProductItem copyFrom) {
    copyFrom(copyFrom);
  }

  public void copyFrom(ProductItem copyFrom) {
    address.copyFrom(copyFrom.address);
    classSchedule = copyFrom.classSchedule;
    description = copyFrom.description;
    name = copyFrom.name;
    mentor = copyFrom.mentor;
    id = copyFrom.id;
    version = copyFrom.version;
    note = copyFrom.note;
  }

  public Address getAddress() {
    return address;
  }

  public Category getClassSchedule() {
    return classSchedule;
  }

  public String getDescription() {
    return description;
  }

  /**
   * The RequestFactory requires a Long id property for each proxied type.
   * <p>
   * The requirement for some kind of id object with proper hash / equals
   * semantics is not going away, but it should become possible to use types
   * other than Long, and properties other than "id".
   */
  public String getId() {
    return id;
  }

  public ProductItem getMentor() {
    return mentor;
  }

  public String getName() {
    return name;
  }

  public String getNote() {
    return note;
  }
  
  public String getScheduleDescription() {
    return getScheduleWithFilter(daysFilters);
  }

  public String getScheduleWithFilter(List<Boolean> daysFilter) {
    return classSchedule.getDescription(daysFilter);
  }

  /**
   * The RequestFactory requires an Integer version property for each proxied
   * type, but makes no good use of it. This requirement will be removed soon.
   */
  public Integer getVersion() {
    return version;
  }

  public ProductItem makeCopy() {
    return new ProductItem(this);
  }

  public void persist() {
	  AridhuPricingService.persist(this);
  }

  public void setAddress(Address address) {
    this.address = address;
  }
  
  public void setClassSchedule(Category schedule) {
    this.classSchedule = schedule;
  }

  public void setDaysFilter(List<Boolean> daysFilter) {
    assert daysFilter.size() == this.daysFilters.size();
    this.daysFilters = daysFilter;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setMentor(ProductItem mentor) {
    this.mentor = mentor;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  @Override
  public String toString() {
    return "Product Item [description=" + description + ", id=" + id + ", name="
        + name + ", version=" + version + "]";
  }
}
