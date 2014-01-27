
package com.aridhu.gwt.pricing.client.widgets;

import com.aridhu.gwt.pricing.shared.AridhuRequestFactory;
import com.aridhu.gwt.pricing.shared.ProductItemProxy;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.editor.client.adapters.OptionalFieldEditor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;

/**
 * This demonstrates how an editor can be constructed to handle optional fields.
 * The Person domain object's mentor property is initially <code>null</code>.
 * This type delegates editing control to an instance of the
 * {@link OptionalValueEditor} adapter class.
 */
public class MentorSelector extends Composite implements
    IsEditor<OptionalFieldEditor<ProductItemProxy, NameLabel>> {

  interface Binder extends UiBinder<Widget, MentorSelector> {
  }

  @UiField
  Button choose;

  @UiField
  Button clear;

  @UiField
  NameLabel nameLabel;

  private final OptionalFieldEditor<ProductItemProxy, NameLabel> editor;
  private final AridhuRequestFactory factory;

  public MentorSelector(AridhuRequestFactory factory) {
    this.factory = factory;
    initWidget(GWT.<Binder> create(Binder.class).createAndBindUi(this));
    editor = OptionalFieldEditor.of(nameLabel);
  }

  public OptionalFieldEditor<ProductItemProxy, NameLabel> asEditor() {
    return editor;
  }

  public void setEnabled(boolean enabled) {
    choose.setEnabled(enabled);
    clear.setEnabled(enabled);
  }

  @Override
  protected void onUnload() {
    nameLabel.cancelSubscription();
  }

  @UiHandler("choose")
  void onChoose(ClickEvent event) {
    setEnabled(false);
    factory.schoolCalendarRequest().getRandomProductItem().to(
        new Receiver<ProductItemProxy>() {
          @Override
          public void onSuccess(ProductItemProxy response) {
            setValue(response);
            setEnabled(true);
          }
        }).fire();
  }

  @UiHandler("clear")
  void onClear(ClickEvent event) {
    setValue(null);
  }

  /**
   * This method is not called by the Editor framework.
   */
  private void setValue(ProductItemProxy person) {
    editor.setValue(person);
    nameLabel.setVisible(person != null);
  }
}
