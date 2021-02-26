package com.github.antoniazzi.inc.backend.commons.gbucket;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageClass;
import com.google.cloud.storage.StorageOptions;

public class BucketHelper {

	private static final String GOOGLE_STORAGE_API_URL = "https://storage.googleapis.com/";

	private static final String BASE_NAME = "nl-wpsoft-cloud-storage";

	// adminId
	private String name;

	private static final String KEY_VAL = "enter key val here";

	private String projectId = "poc-dec2016";

	private Storage storage;

	public BucketHelper(String name) {
		this.name = BASE_NAME + "-" + name;
		try {
			// new FileInputStream(JSON_KEY_PATH)
			// new ByteArrayInputStream(KEY_VAL.getBytes(StandardCharsets.UTF_8))
			this.storage = StorageOptions.newBuilder().setProjectId(projectId)
					.setCredentials(GoogleCredentials
							.fromStream(new ByteArrayInputStream(KEY_VAL.getBytes(StandardCharsets.UTF_8))))
					.build().getService();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = BASE_NAME + "-" + name;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public void initAdministration() {
		storage.create(
				BucketInfo.newBuilder(name).setStorageClass(StorageClass.MULTI_REGIONAL).setLocation("eu").build());

	}

	public String uploadImage() {
		return null;
	}

	public String uploadText() {
		return null;
	}

	public String upload(BucketFile file) {
		BlobInfo.Builder builder = BlobInfo.newBuilder(name, file.getPath());

		if (file.getContentType() != null) {
			builder.setContentType(file.getContentType());
		}

		if (file.getPublicAccess()) {
			builder.setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER))));
		}

		Blob blob = storage.create(builder.build(), file.getBytes());

		if (file.getDownloadable())
			return blob.getMediaLink();

		return GOOGLE_STORAGE_API_URL + name + "/" + file.getPath();
	}

	public Boolean delete(String path) {
		return storage.delete(BlobId.of(name, path));
	}

	public String getUrlForAdministration() {
		return GOOGLE_STORAGE_API_URL + this.name;
	}

}
