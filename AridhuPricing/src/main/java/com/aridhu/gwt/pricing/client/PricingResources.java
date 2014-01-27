package com.aridhu.gwt.pricing.client;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.NotStrict;
import com.google.gwt.resources.client.ImageResource;

/**
 * The images and styles used throughout the Showcase.
 */
public interface PricingResources extends ClientBundle {
  ImageResource catI18N();

  ImageResource catLists();

  ImageResource catOther();

  ImageResource catPanels();

  ImageResource catPopups();

  ImageResource catTables();

  ImageResource catTextInput();

  ImageResource catWidgets();

  /**
   * The styles used in LTR mode.
   */
  @NotStrict
  @Source("Showcase.css")
  CssResource css();

  ImageResource gwtLogo();
  ImageResource Idhayam();

  ImageResource gwtLogoThumb();

  ImageResource jimmy();

  ImageResource jimmyThumb();

  ImageResource loading();

  /**
   * Indicates the locale selection box.
   */
  ImageResource locale();
}