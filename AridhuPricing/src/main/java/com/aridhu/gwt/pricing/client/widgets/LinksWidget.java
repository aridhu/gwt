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
package com.aridhu.gwt.pricing.client.widgets;

import com.aridhu.gwt.pricing.client.events.FilterChangeEvent;
import com.aridhu.gwt.pricing.shared.AridhuRequestFactory;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * A UI Widget that allows a user to filter the days being displayed in the
 * dynamic table.
 */
public class LinksWidget extends Composite {

  interface Binder extends UiBinder<Widget, LinksWidget> {
  };


  @UiField
  Button all;
  @UiField
  Button none;

  private final EventBus eventBus;
  final AridhuRequestFactory requests = GWT.create(AridhuRequestFactory.class);
 

  @UiConstructor
  public LinksWidget(EventBus eventBus) {
    this.eventBus = eventBus;
    initWidget(GWT.<Binder> create(Binder.class).createAndBindUi(this));
    requests.initialize(eventBus);
  }

  @UiHandler(value = {"all", "none"})
  public void handleAllNoneClick(ClickEvent e) {
    if(e.getSource() == all){
    	Window.alert("Selected All");
    }else {
    	Window.alert("Selected None");
    	new SummaryWidget(eventBus, requests, 5);
    }
  }

 

  private void setAllCheckBoxes(boolean checked) {
    for (int day = 0; day < 7; day++) {
      eventBus.fireEvent(new FilterChangeEvent(day, checked));
    }
  }
}
