package com.github.antoniazzi.inc.backend.commons.model.dto.productms;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class FollowupEmail implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long[] emailIds;

	private Boolean sendDirect;

	private Short sendOnWeekDay; // based on java.time.DayOfWeek (MONDAY = 1)

	private Date fixedDate;

	private Long sendAfterDays;

	@JsonFormat(pattern = "HH:mm")
	private LocalTime sendTime;

	public Long[] getEmailIds() {
		return emailIds;
	}

	public void setEmailIds(Long[] emailIds) {
		this.emailIds = emailIds;
	}

	public Boolean getSendDirect() {
		return sendDirect;
	}

	public void setSendDirect(Boolean sendDirect) {
		this.sendDirect = sendDirect;
	}

	public Short getSendOnWeekDay() {
		return sendOnWeekDay;
	}

	public void setSendOnWeekDay(Short sendOnWeekDay) {
		this.sendOnWeekDay = sendOnWeekDay;
	}

	public Date getFixedDate() {
		return fixedDate;
	}

	public void setFixedDate(Date fixedDate) {
		this.fixedDate = fixedDate;
	}

	public Long getSendAfterDays() {
		return sendAfterDays;
	}

	public void setSendAfterDays(Long sendAfterDays) {
		this.sendAfterDays = sendAfterDays;
	}

	public LocalTime getSendTime() {
		return sendTime;
	}

	public void setSendTime(LocalTime sendTime) {
		this.sendTime = sendTime;
	}

}
