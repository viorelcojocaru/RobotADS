package model;

import java.io.Serializable;

public class Ads implements Serializable {
	private static final long serialVersionUID = 1L;
	int id = 1;
	String title;
	String priceType;
	String description;
	String mapAddress;
	String fileUploadInput;
	String youtubeVideoUrl;
	String imagePath;
	String priceAmount;
	String phoneNumber;
	String locationSelector;
	String adType;
	String www;

	public Ads() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMapAddress() {
		return mapAddress;
	}

	public void setMapAddress(String mapAddress) {
		this.mapAddress = mapAddress;
	}

	public String getFileUploadInput() {
		return fileUploadInput;
	}

	public void setFileUploadInput(String fileUploadInput) {
		this.fileUploadInput = fileUploadInput;
	}

	public String getYoutubeVideoUrl() {
		return youtubeVideoUrl;
	}

	public void setYoutubeVideoUrl(String youtubeVideoUrl) {
		this.youtubeVideoUrl = youtubeVideoUrl;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getPriceAmount() {
		return priceAmount;
	}

	public void setPriceAmount(String priceAmount) {
		this.priceAmount = priceAmount;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getLocationSelector() {
		return locationSelector;
	}

	public void setLocationSelector(String locationSelector) {
		this.locationSelector = locationSelector;
	}

	public String getAdType() {
		return adType;
	}

	public void setAdType(String adType) {
		this.adType = adType;
	}

	public String getWWW() {
		return www;
	}

	public void setWWW(String www) {
		this.www = www;
	}

	@Override
	public String toString() {
		return "Ads [id=" + id + ", title=" + title + ", priceType="
				+ priceType + ", description=" + description + ", mapAddress="
				+ mapAddress + ", fileUploadInput=" + fileUploadInput
				+ ", youtubeVideoUrl=" + youtubeVideoUrl + ", imagePath="
				+ imagePath + ", priceAmount=" + priceAmount + ", phoneNumber="
				+ phoneNumber + ", locationSelector=" + locationSelector
				+ ", adType=" + adType + ", www=" + www + "]";
	}
}
