package com.github.antoniazzi.inc.backend.commons.gbucket;

public class BucketFile {

	private String fileName;

	private String directory;

	private String contentType;

	private Boolean publicAccess = false;

	private byte[] bytes;

	private Boolean downloadable = false;

	public BucketFile() {

	}

	public BucketFile(String fileName, String directory, byte[] bytes) {
		this.fileName = fileName;
		this.directory = directory;
		this.bytes = bytes;
	}

	public BucketFile(String fileName, String directory, Boolean publicAccess, byte[] bytes) {
		this.fileName = fileName;
		this.directory = directory;
		this.publicAccess = publicAccess;
		this.bytes = bytes;
	}

	public BucketFile(String fileName, String directory, String contentType, Boolean publicAccess, byte[] bytes, Boolean downloadable) {
		this.fileName = fileName;
		this.directory = directory;
		this.contentType = contentType;
		this.publicAccess = publicAccess;
		this.bytes = bytes;
		this.downloadable = downloadable;
	}

	public String getPath() {
		if (directory != null)
			return directory + "/" + fileName;

		return fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Boolean getPublicAccess() {
		return publicAccess;
	}

	public void setPublicAccess(Boolean publicAccess) {
		this.publicAccess = publicAccess;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public Boolean getDownloadable() {
		return downloadable;
	}

	public void setDownloadable(Boolean downloadable) {
		this.downloadable = downloadable;
	}

}