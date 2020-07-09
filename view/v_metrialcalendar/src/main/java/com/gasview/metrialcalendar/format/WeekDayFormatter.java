package com.gasview.metrialcalendar.format;

import com.gasview.metrialcalendar.MaterialCalendarView;

import org.threeten.bp.DayOfWeek;

/**
 * Supply labels for a given day of the week.
 */
public interface WeekDayFormatter {
  /**
   * Convert a given day of the week into a label.
   *
   * @param dayOfWeek the day of the week as returned by {@linkplain DayOfWeek#getValue()}.
   * @return a label for the day of week.
   */
  CharSequence format(DayOfWeek dayOfWeek);

  /**
   * Default implementation used by {@linkplain MaterialCalendarView}
   */
  WeekDayFormatter DEFAULT = new CalendarWeekDayFormatter();
}
