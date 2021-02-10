package com.zzg.common.convert;

import java.beans.PropertyEditorSupport;

import com.zzg.common.util.DateUtils;

public class DateEditor extends PropertyEditorSupport {
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		setValue(DateUtils.formatDateStr(text));
	}
}
