package com.mpc.iso.model;

/***
 * @author yovi.putra
 */
public class Configuration {
	private String ip;
	private int port;
	private String CustomPackager;
	private HeaderConfig headerConfig;

	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getCustomPackager() {
		return CustomPackager;
	}
	public void setCustomPackager(String customPackager) {
		CustomPackager = customPackager;
	}
	public HeaderConfig getHeaderConfig() {
		return headerConfig;
	}
	public void setHeaderConfig(HeaderConfig headerConfig) {
		this.headerConfig = headerConfig;
	}
	@Override
	public String toString() {
		return "Configuration [ip=" + ip + ", port=" + port + ", CustomPackager=" + CustomPackager + ", headerConfig="
				+ headerConfig + "]";
	}
}
