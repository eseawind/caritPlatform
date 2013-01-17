package cn.com.carit.platform.request.market;

public class CheckAppUpdated {
	private String packageName;
	private String version;
	
	public CheckAppUpdated() {
	}
	public CheckAppUpdated(String packageName, String version) {
		super();
		this.packageName = packageName;
		this.version = version;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
}
