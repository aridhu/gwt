/*
 * Copyright 2010 Google Inc.
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
package com.aridhu.gwt.pricing.client;

import com.aridhu.gwt.pricing.client.widgets.DayFilterWidget;
import com.aridhu.gwt.pricing.client.widgets.FavoritesWidget;
import com.aridhu.gwt.pricing.client.widgets.LinksWidget;
import com.aridhu.gwt.pricing.client.widgets.SummaryWidget;
import com.aridhu.gwt.pricing.shared.AridhuRequestFactory;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryLogHandler;
import com.google.web.bindery.requestfactory.shared.LoggingRequest;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The entry point class which performs the initial loading of the DynaTableRf
 * application.
 * @param <DynaTableRf>
 */
public class AridhuPricing implements EntryPoint {
  interface Binder extends UiBinder<Widget, AridhuPricing> {
  }

  private static final Logger log = Logger.getLogger(AridhuPricing.class.getName());

  @UiField(provided = true)
  SummaryWidget calendar;

  EventBus eventBus = new SimpleEventBus();

  //@UiField(provided = true)
  //FavoritesWidget favorites;

  @UiField(provided = true)
  DayFilterWidget filter;
  
  @UiField(provided = true)
  LinksWidget aridhulinks;

  /**
   * This method sets up the top-level services used by the application.
   */
  public void onModuleLoad() {
    GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
      public void onUncaughtException(Throwable e) {
        log.log(Level.SEVERE, e.getMessage(), e);
      }
    });

    final AridhuRequestFactory requests = GWT.create(AridhuRequestFactory.class);
    requests.initialize(eventBus);

    // Add remote logging handler
    RequestFactoryLogHandler.LoggingRequestProvider provider = new RequestFactoryLogHandler.LoggingRequestProvider() {
      public LoggingRequest getLoggingRequest() {
        return requests.loggingRequest();
      }
    };
    Logger.getLogger("").addHandler(new ErrorDialog().getHandler());
    Logger.getLogger("").addHandler(
        new RequestFactoryLogHandler(provider, Level.WARNING,
            new ArrayList<String>()));
    FavoritesManager manager = new FavoritesManager(requests);
    PersonEditorWorkflow.register(eventBus, requests, manager);

    calendar = new SummaryWidget(eventBus, requests, 15);
//    favorites = new FavoritesWidget(eventBus, requests, manager);
    filter = new DayFilterWidget(eventBus);
    aridhulinks = new LinksWidget(eventBus);

    RootLayoutPanel.get().add(
        GWT.<Binder> create(Binder.class).createAndBindUi(this));

    // Fast test to see if the sample is not being run from devmode
    if (GWT.getHostPageBaseURL().startsWith("file:")) {
      log.log(Level.SEVERE, "The DynaTableRf sample cannot be run without its"
          + " server component.  If you are running the sample from a"
          + " GWT distribution, use the 'ant devmode' target to launch"
          + " the DTRF server.");
    }
  }
}
