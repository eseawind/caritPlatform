package cn.com.carit.platform.request.account.v2;

import cn.com.carit.platform.request.account.AccountRequest;

import com.rop.request.UploadFile;

public class UploadUserPhotoRequest extends AccountRequest {

    private UploadFile photo;
    
	public UploadUserPhotoRequest() {
	}

	public UploadUserPhotoRequest(String email) {
		super(email);
	}

	public UploadFile getPhoto() {
		return photo;
	}

	public void setPhoto(UploadFile photo) {
		this.photo = photo;
	}
    
}

