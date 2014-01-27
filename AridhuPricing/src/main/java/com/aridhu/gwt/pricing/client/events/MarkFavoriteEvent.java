
package com.aridhu.gwt.pricing.client.events;

import com.aridhu.gwt.pricing.shared.ProductItemProxy;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.web.bindery.requestfactory.shared.EntityProxyId;

/**
 * Used client-side to add entries to the favorites list.
 */
public class MarkFavoriteEvent extends GwtEvent<MarkFavoriteEvent.Handler> {
  /**
   * Handles {@link MarkFavoriteEvent}.
   */
  public interface Handler extends EventHandler {
    void onMarkFavorite(MarkFavoriteEvent event);
  }

  public static final Type<Handler> TYPE = new Type<Handler>();

  private final EntityProxyId<ProductItemProxy> id;
  private final boolean isFavorite;

  public MarkFavoriteEvent(EntityProxyId<ProductItemProxy> id, boolean isFavorite) {
    this.id = id;
    this.isFavorite = isFavorite;
  }

  @Override
  public Type<Handler> getAssociatedType() {
    return TYPE;
  }

  public EntityProxyId<ProductItemProxy> getId() {
    return id;
  }

  public boolean isFavorite() {
    return isFavorite;
  }

  @Override
  protected void dispatch(Handler handler) {
    handler.onMarkFavorite(this);
  }
}
