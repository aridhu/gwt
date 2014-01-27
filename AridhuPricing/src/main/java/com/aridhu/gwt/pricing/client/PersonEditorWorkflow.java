
package com.aridhu.gwt.pricing.client;

import com.aridhu.gwt.pricing.client.events.EditPersonEvent;
import com.aridhu.gwt.pricing.client.widgets.MentorSelector;
import com.aridhu.gwt.pricing.client.widgets.PersonEditor;
import com.aridhu.gwt.pricing.client.widgets.ScheduleEditor;
import com.aridhu.gwt.pricing.client.widgets.TimeSlotListWidget;
import com.aridhu.gwt.pricing.shared.AridhuRequestFactory;
import com.aridhu.gwt.pricing.shared.AridhuRequestFactory.PersonRequest;
import com.aridhu.gwt.pricing.shared.ProductItemProxy;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;

import java.util.Set;

import javax.validation.ConstraintViolation;

/**
 * This class shows how the UI for editing a person is wired up to the
 * RequestFactoryEditorDelegate. It is also responsible for showing and
 * dismissing the PersonEditor. The use of the FavoriteManager shows integration
 * between a remote service and a local service.
 */
public class PersonEditorWorkflow {
  interface Binder extends UiBinder<DialogBox, PersonEditorWorkflow> {
    Binder BINDER = GWT.create(Binder.class);
  }

  interface Driver extends
      RequestFactoryEditorDriver<ProductItemProxy, PersonEditor> {
  }

  static void register(EventBus eventBus,
      final AridhuRequestFactory requestFactory,
      final FavoritesManager manager) {
    eventBus.addHandler(EditPersonEvent.TYPE, new EditPersonEvent.Handler() {
      public void startEdit(ProductItemProxy person, RequestContext requestContext) {
        new PersonEditorWorkflow(requestFactory, manager, person).edit(requestContext);
      }
    });
  }

  @UiField
  HTMLPanel contents;

  @UiField
  DialogBox dialog;

  @UiField
  CheckBox favorite;

  @UiField(provided = true)
  PersonEditor personEditor;

  private Driver editorDriver;
  private final FavoritesManager manager;
  private ProductItemProxy person;
  private final AridhuRequestFactory requestFactory;

  private PersonEditorWorkflow(AridhuRequestFactory requestFactory,
      FavoritesManager manager, ProductItemProxy person) {
    this.requestFactory = requestFactory;
    this.manager = manager;
    this.person = person;
    TimeSlotListWidget timeSlotEditor = new TimeSlotListWidget(requestFactory);
    ScheduleEditor scheduleEditor = new ScheduleEditor(timeSlotEditor);
    MentorSelector mentorEditor = new MentorSelector(requestFactory);
    personEditor = new PersonEditor(mentorEditor, scheduleEditor);
    Binder.BINDER.createAndBindUi(this);
    contents.addDomHandler(new KeyUpHandler() {
      public void onKeyUp(KeyUpEvent event) {
        if (event.getNativeKeyCode() == KeyCodes.KEY_ESCAPE) {
          onCancel(null);
        }
      }
    }, KeyUpEvent.getType());
    this.favorite.setVisible(false);
  }

  /**
   * Called by the cancel button when it is clicked. This method will just tear
   * down the UI and clear the state of the workflow.
   */
  @UiHandler("cancel")
  void onCancel(ClickEvent event) {
    dialog.hide();
  }

  /**
   * Called by the edit dialog's save button. This method will flush the
   * contents of the UI into the ProductItemProxy that is being edited, check for
   * errors, and send the request to the server.
   */
  @UiHandler("save")
  void onSave(ClickEvent event) {
    // Flush the contents of the UI
    RequestContext context = editorDriver.flush();

    // Check for errors
    if (editorDriver.hasErrors()) {
      dialog.setText("Errors detected locally");
      return;
    }

    // Send the request
    context.fire(new Receiver<Void>() {
      @Override
      public void onConstraintViolation(Set<ConstraintViolation<?>> errors) {
        // Otherwise, show ConstraintViolations in the UI
        dialog.setText("Errors detected on the server");
        editorDriver.setConstraintViolations(errors);
      }

      @Override
      public void onSuccess(Void response) {
        // If everything went as planned, just dismiss the dialog box
        dialog.hide();
      }
    });
  }

  /**
   * Called by the favorite checkbox when its value has been toggled.
   */
  @UiHandler("favorite")
  void onValueChanged(ValueChangeEvent<Boolean> event) {
    manager.setFavorite(person.stableId(), favorite.getValue());
  }

  /**
   * Construct and display the UI that will be used to edit the current
   * ProductItemProxy, using the given RequestContext to accumulate the edits.
   */
  private void edit(RequestContext requestContext) {
    editorDriver = GWT.create(Driver.class);
    editorDriver.initialize(requestFactory, personEditor);
    
    if (requestContext == null) {
      this.favorite.setVisible(true);
      fetchAndEdit();
      return;
    }

    editorDriver.edit(person, requestContext);
    personEditor.focus();
    favorite.setValue(manager.isFavorite(person), false);
    dialog.center();
  }

  private void fetchAndEdit() {
    // The request is configured arbitrarily
    Request<ProductItemProxy> fetchRequest = requestFactory.find(person.stableId());

    // Add the paths that the EditorDrives computes
    fetchRequest.with(editorDriver.getPaths());

    // We could do more with the request, but we just fire it
    fetchRequest.to(new Receiver<ProductItemProxy>() {
      @Override
      public void onSuccess(ProductItemProxy person) {
        PersonEditorWorkflow.this.person = person;
        // Start the edit process
        PersonRequest context = requestFactory.personRequest();
        // Display the UI
        edit(context);
        // Configure the method invocation to be sent in the context
        context.persist().using(person);
        // The context will be fire()'ed from the onSave() method
      }
    }).fire();
  }
}
