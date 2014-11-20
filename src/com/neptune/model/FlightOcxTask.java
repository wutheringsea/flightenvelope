package com.neptune.model;

import java.io.Serializable;

public class FlightOcxTask implements Serializable {	// Fields

	private String id;
	private String ocxProjectId;
	private String coursename;
	private String statusHeight;
	private String statusSpeed;
	private String statusMachNum;
	private String statusAction;
	private String isfinished;
	private String isadded;
	private String attribute01;
	private String attribute02;
	private String attribute03;
	private String attribute04;
	private String attribute05;
	private String attribute06;
	private String attribute07;
	private String attribute08;
	private String attribute09;
	private String attribute10;

	// Constructors

	/** default constructor */
	public FlightOcxTask() {
	}

	/** minimal constructor */
	public FlightOcxTask(String ocxProjectId) {
		this.ocxProjectId = ocxProjectId;
	}

	/** full constructor */
	public FlightOcxTask(String ocxProjectId, String coursename,
			String statusHeight, String statusSpeed, String statusMachNum,
			String statusAction, String isfinished, String isadded,
			String attribute01, String attribute02, String attribute03,
			String attribute04, String attribute05, String attribute06,
			String attribute07, String attribute08, String attribute09,
			String attribute10) {
		this.ocxProjectId = ocxProjectId;
		this.coursename = coursename;
		this.statusHeight = statusHeight;
		this.statusSpeed = statusSpeed;
		this.statusMachNum = statusMachNum;
		this.statusAction = statusAction;
		this.isfinished = isfinished;
		this.isadded = isadded;
		this.attribute01 = attribute01;
		this.attribute02 = attribute02;
		this.attribute03 = attribute03;
		this.attribute04 = attribute04;
		this.attribute05 = attribute05;
		this.attribute06 = attribute06;
		this.attribute07 = attribute07;
		this.attribute08 = attribute08;
		this.attribute09 = attribute09;
		this.attribute10 = attribute10;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOcxProjectId() {
		return this.ocxProjectId;
	}

	public void setOcxProjectId(String ocxProjectId) {
		this.ocxProjectId = ocxProjectId;
	}

	public String getCoursename() {
		return this.coursename;
	}

	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}

	public String getStatusHeight() {
		return this.statusHeight;
	}

	public void setStatusHeight(String statusHeight) {
		this.statusHeight = statusHeight;
	}

	public String getStatusSpeed() {
		return this.statusSpeed;
	}

	public void setStatusSpeed(String statusSpeed) {
		this.statusSpeed = statusSpeed;
	}

	public String getStatusMachNum() {
		return this.statusMachNum;
	}

	public void setStatusMachNum(String statusMachNum) {
		this.statusMachNum = statusMachNum;
	}

	public String getStatusAction() {
		return this.statusAction;
	}

	public void setStatusAction(String statusAction) {
		this.statusAction = statusAction;
	}

	public String getIsfinished() {
		return this.isfinished;
	}

	public void setIsfinished(String isfinished) {
		this.isfinished = isfinished;
	}

	public String getIsadded() {
		return this.isadded;
	}

	public void setIsadded(String isadded) {
		this.isadded = isadded;
	}

	public String getAttribute01() {
		return this.attribute01;
	}

	public void setAttribute01(String attribute01) {
		this.attribute01 = attribute01;
	}

	public String getAttribute02() {
		return this.attribute02;
	}

	public void setAttribute02(String attribute02) {
		this.attribute02 = attribute02;
	}

	public String getAttribute03() {
		return this.attribute03;
	}

	public void setAttribute03(String attribute03) {
		this.attribute03 = attribute03;
	}

	public String getAttribute04() {
		return this.attribute04;
	}

	public void setAttribute04(String attribute04) {
		this.attribute04 = attribute04;
	}

	public String getAttribute05() {
		return this.attribute05;
	}

	public void setAttribute05(String attribute05) {
		this.attribute05 = attribute05;
	}

	public String getAttribute06() {
		return this.attribute06;
	}

	public void setAttribute06(String attribute06) {
		this.attribute06 = attribute06;
	}

	public String getAttribute07() {
		return this.attribute07;
	}

	public void setAttribute07(String attribute07) {
		this.attribute07 = attribute07;
	}

	public String getAttribute08() {
		return this.attribute08;
	}

	public void setAttribute08(String attribute08) {
		this.attribute08 = attribute08;
	}

	public String getAttribute09() {
		return this.attribute09;
	}

	public void setAttribute09(String attribute09) {
		this.attribute09 = attribute09;
	}

	public String getAttribute10() {
		return this.attribute10;
	}

	public void setAttribute10(String attribute10) {
		this.attribute10 = attribute10;
	}
}
