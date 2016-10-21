package com.tdr.wisdome.update;

import java.io.Serializable;

public class VersionInfo implements Serializable {

	private static final long serialVersionUID = 6964615323269090504L;

	private double versionCode;
	private String versionName;
	private String packageName;

	public VersionInfo() {
		super();
	}

	public double getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(double versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

}
