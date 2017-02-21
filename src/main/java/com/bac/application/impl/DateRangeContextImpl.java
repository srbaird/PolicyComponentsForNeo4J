package com.bac.application.impl;

import java.util.Date;
import java.util.Objects;

import com.bac.application.DateRangeContext;

/**
 * 
 * 
 * @author simon
 *
 */
public class DateRangeContextImpl implements DateRangeContext {

	private final Date fromDate;
	private final Date toDate;

	public DateRangeContextImpl(Date fromDate, Date toDate) {

		if (fromDate == null && toDate != null) {
			this.fromDate = toDate;
			this.toDate = toDate;
		} else if (fromDate != null && toDate == null) {
			this.fromDate = fromDate;
			this.toDate = fromDate;
		} else {
			this.fromDate = fromDate;
			this.toDate = toDate;
		}
		Objects.requireNonNull(fromDate, "From date cannot be null");
		Objects.requireNonNull(toDate, "To date cannot be null");
	}

	@Override
	public boolean accept(Date date) {

		if (date == null) {
			return false;
		}
		return (date.compareTo(fromDate) >= 0 && date.compareTo(toDate) <= 0);
	}
}
