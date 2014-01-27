
package com.aridhu.gwt.pricing.client.widgets;

import com.aridhu.gwt.pricing.shared.AridhuRequestFactory;
import com.aridhu.gwt.pricing.shared.AridhuRequestFactory.ScheduleRequest;
import com.aridhu.gwt.pricing.shared.AttributeProxy;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.EditorDelegate;
import com.google.gwt.editor.client.ValueAwareEditor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Edits a list of time slots.
 */
public class TimeSlotListWidget extends Composite implements ValueAwareEditor<List<AttributeProxy>> {

  interface TableResources extends CellTable.Resources {
    @Override
    @Source(value = {CellTable.Style.DEFAULT_CSS, "CellTablePatch.css"})
    CellTable.Style cellTableStyle();
  }

  interface TimeSlotListWidgetUiBinder extends UiBinder<Widget, TimeSlotListWidget> {
  }
  
  private class ScheduleRow {
    int hour;
    
    ScheduleRow(int hour) {
      this.hour = hour;
    }

    public int getHour() {
      return hour;
    }

    public boolean isInUse(WeekDay day) {
      return currentSchedule.contains(new TimeSlotKey(day, hour));
    }
    
    public void toggleInUse(WeekDay day) {
      final TimeSlotKey key = new TimeSlotKey(day, hour);
      if (currentSchedule.contains(key)) {
        currentSchedule.remove(key);
        table.redraw();
      } else if (!existingSlots.containsKey(key)) {
        acceptClicks = false;
        ScheduleRequest context = factory.scheduleRequest();
        context.createTimeSlot(day.ordinal(), hour * 60, hour * 60 + 50).fire(
            new Receiver<AttributeProxy>() {
              @Override
              public void onSuccess(AttributeProxy slot) {
                existingSlots.put(key, slot);
                backing.add(slot);
                currentSchedule.add(key);
                table.redraw();
                acceptClicks = true;
              }
        });
      } else {
        currentSchedule.add(key);        
        table.redraw();
      }
    }
  }

  private static class TimeSlotKey {
    private int hour;
    private WeekDay day;
    
    TimeSlotKey(WeekDay day, int hour) {
      this.day = day;
      this.hour = hour;
    }
    
    TimeSlotKey(AttributeProxy slot) {
      day = WeekDay.fromInt(slot.getDayOfWeek());
      hour = slot.getStartMinutes() / 60;
    }
    
    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      TimeSlotKey other = (TimeSlotKey) obj;
      if (day == null) {
        if (other.day != null) {
          return false;
        }
      } else if (!day.equals(other.day)) {
        return false;
      }
      if (hour != other.hour) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      return 31 * (31 + ((day == null) ? 0 : day.hashCode())) + hour;
    }
  }

  private class WeekDayColumn extends Column<ScheduleRow, String> {
    private WeekDay day;

    public WeekDayColumn(WeekDay day) {
      super(new ClickableTextCell());
      this.day = day;
    }

    @Override
    public String getValue(ScheduleRow row) {
      if (day == null) {
        int hour = row.getHour();
        return Integer.toString(hour <= 12 ? hour : hour - 12) + ":00" 
            + ((hour < 12) ? "AM" : "PM");
      }
      return row.isInUse(day) ? "X" : ".";
    }
  }
  
  private static final int ROWS_IN_A_DAY = 9;
  private static final int FIRST_HOUR = 8;
  
  private static TimeSlotListWidgetUiBinder uiBinder = GWT.create(
      TimeSlotListWidgetUiBinder.class);

  @UiField(provided = true)
  CellTable<ScheduleRow> table;
  
  private enum WeekDay {
    Hardware("HW"), Software("SW"), Networking("NW"), Cloud("Cd"), 
    Mobile("Mo"), BigData("BD"), Enterprise("ERP");
  
    public static WeekDay fromInt(int ordinal) {
      return values()[ordinal];
    }

    private String shortName;
    
    WeekDay(String shortName) {
      this.shortName = shortName;
    }
  
    public String getShortName() {
      return shortName;
    }
  }
  
  private List<AttributeProxy> backing;
  private HashSet<TimeSlotKey> currentSchedule;
  private HashMap<TimeSlotKey, AttributeProxy> existingSlots;
  private AridhuRequestFactory factory;
  private HashSet<TimeSlotKey> initialSchedule;
  private boolean acceptClicks = true;
  
  public TimeSlotListWidget(AridhuRequestFactory factory) {
    this.factory = factory;
    table = new CellTable<TimeSlotListWidget.ScheduleRow>(ROWS_IN_A_DAY,
        GWT.<TableResources> create(TableResources.class));
    table.addColumn(new WeekDayColumn(null), "Category");      
    for (WeekDay day : WeekDay.values()) {
      WeekDayColumn col = new WeekDayColumn(day);
      
      class Updater implements FieldUpdater<ScheduleRow, String> {
        private WeekDay columnDay;
        
        public Updater(WeekDay day) {
          columnDay = day;
        }
        
        @Override
        public void update(int index, ScheduleRow row, String value) {
          if (acceptClicks) {
            row.toggleInUse(columnDay);
          }
        }
      }
      
      FieldUpdater<ScheduleRow, String> fieldUpdater = new Updater(day);
      col.setFieldUpdater(fieldUpdater);
      table.addColumn(col, day.getShortName());      
    }
    
    table.setRowCount(ROWS_IN_A_DAY, false);
    table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  @Override
  public void flush() {
    HashMap<AttributeProxy, TimeSlotKey> index = new HashMap<AttributeProxy, TimeSlotKey>();
    
    for (AttributeProxy slot : backing) {
      index.put(slot, new TimeSlotKey(slot));
    }

    // Compute slots that need to be removed from the backing
    initialSchedule.removeAll(currentSchedule);
    
    for (Iterator<AttributeProxy> iterator = backing.iterator(); iterator.hasNext();) {
      AttributeProxy slot = iterator.next();
      TimeSlotKey key = index.get(slot);
      if (initialSchedule.contains(key)) {
        iterator.remove();
      }
    }    
  }

  @Override
  public void onPropertyChange(String... paths) {
  }

  @Override
  public void setDelegate(EditorDelegate<List<AttributeProxy>> delegate) {
  }

  @Override
  public void setValue(List<AttributeProxy> value) {
    backing = value;
    currentSchedule = new HashSet<TimeSlotKey>();
    existingSlots = new HashMap<TimeSlotKey, AttributeProxy>();
    
    initialSchedule = new HashSet<TimeSlotKey>();
    
    for (AttributeProxy slot : backing) {
      TimeSlotKey key = new TimeSlotKey(slot);
      currentSchedule.add(key);
      existingSlots.put(key, slot);
      initialSchedule.add(new TimeSlotKey(slot));
    }
    
    ArrayList<ScheduleRow> rows = new ArrayList<ScheduleRow>(ROWS_IN_A_DAY);
    for (int i = 0; i < ROWS_IN_A_DAY; i++) {
      rows.add(new ScheduleRow(FIRST_HOUR + i));
    }
    table.setRowData(rows);
  }

}
