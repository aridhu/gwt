// Automatically Generated -- DO NOT EDIT
// com.aridhu.gwt.pricing.shared.AridhuRequestFactory
package com.aridhu.gwt.pricing.shared;
import java.util.Arrays;
import com.google.web.bindery.requestfactory.vm.impl.OperationData;
import com.google.web.bindery.requestfactory.vm.impl.OperationKey;
public final class AridhuRequestFactoryDeobfuscatorBuilder extends com.google.web.bindery.requestfactory.vm.impl.Deobfuscator.Builder {
{
withOperation(new OperationKey("Fd78wHP$GPA1qzp50XaAtX9HyX4="),
  new OperationData.Builder()
  .withClientMethodDescriptor("()Lcom/google/web/bindery/requestfactory/shared/InstanceRequest;")
  .withDomainMethodDescriptor("()V")
  .withMethodName("persist")
  .withRequestContext("com.aridhu.gwt.pricing.shared.AridhuRequestFactory$PersonRequest")
  .build());
withOperation(new OperationKey("Rvm1kRBIqadzRZRDxLBeb__sHZw="),
  new OperationData.Builder()
  .withClientMethodDescriptor("(Ljava/lang/String;)Lcom/google/web/bindery/requestfactory/shared/Request;")
  .withDomainMethodDescriptor("(Ljava/lang/String;)V")
  .withMethodName("logMessage")
  .withRequestContext("com.google.web.bindery.requestfactory.shared.LoggingRequest")
  .build());
withOperation(new OperationKey("iDwuQDbZvrF2Lm6oZEGvl9TwFqc="),
  new OperationData.Builder()
  .withClientMethodDescriptor("(III)Lcom/google/web/bindery/requestfactory/shared/Request;")
  .withDomainMethodDescriptor("(III)Lcom/aridhu/gwt/pricing/domain/Attribute;")
  .withMethodName("createTimeSlot")
  .withRequestContext("com.aridhu.gwt.pricing.shared.AridhuRequestFactory$ScheduleRequest")
  .build());
withOperation(new OperationKey("L1gUVvE6gQutqvPdYhTUQXvCv6A="),
  new OperationData.Builder()
  .withClientMethodDescriptor("(IILjava/util/List;)Lcom/google/web/bindery/requestfactory/shared/Request;")
  .withDomainMethodDescriptor("(IILjava/util/List;)Ljava/util/List;")
  .withMethodName("getPeople")
  .withRequestContext("com.aridhu.gwt.pricing.shared.AridhuRequestFactory$SchoolCalendarRequest")
  .build());
withOperation(new OperationKey("6U5n2xpH0jD06ooHGXo$4uuS2BU="),
  new OperationData.Builder()
  .withClientMethodDescriptor("()Lcom/google/web/bindery/requestfactory/shared/Request;")
  .withDomainMethodDescriptor("()Lcom/aridhu/gwt/pricing/domain/ProductItem;")
  .withMethodName("getRandomProductItem")
  .withRequestContext("com.aridhu.gwt.pricing.shared.AridhuRequestFactory$SchoolCalendarRequest")
  .build());
withRawTypeToken("DdOIEYZHLZ8leva_1gNI3wNlmE8=", "com.aridhu.gwt.pricing.shared.AddressProxy");
withRawTypeToken("FBm_41GEiWAcmUySiiz2drR2Ibk=", "com.aridhu.gwt.pricing.shared.AttributeProxy");
withRawTypeToken("gXJqvubETFglHufD7rpJg2MxSog=", "com.aridhu.gwt.pricing.shared.CategoryProxy");
withRawTypeToken("Byn1dxMpcrbQ9SLwAOziBglmmwY=", "com.aridhu.gwt.pricing.shared.ProductItemProxy");
withRawTypeToken("w1Qg$YHpDaNcHrR5HZ$23y518nA=", "com.google.web.bindery.requestfactory.shared.EntityProxy");
withRawTypeToken("8KVVbwaaAtl6KgQNlOTsLCp9TIU=", "com.google.web.bindery.requestfactory.shared.ValueProxy");
withRawTypeToken("FXHD5YU0TiUl3uBaepdkYaowx9k=", "com.google.web.bindery.requestfactory.shared.BaseProxy");
withClientToDomainMappings("com.aridhu.gwt.pricing.domain.Address", Arrays.asList("com.aridhu.gwt.pricing.shared.AddressProxy"));
withClientToDomainMappings("com.aridhu.gwt.pricing.domain.Attribute", Arrays.asList("com.aridhu.gwt.pricing.shared.AttributeProxy"));
withClientToDomainMappings("com.aridhu.gwt.pricing.domain.Category", Arrays.asList("com.aridhu.gwt.pricing.shared.CategoryProxy"));
withClientToDomainMappings("com.aridhu.gwt.pricing.domain.ProductItem", Arrays.asList("com.aridhu.gwt.pricing.shared.ProductItemProxy"));
}}
