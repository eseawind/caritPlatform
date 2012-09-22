package cn.com.carit.platform.request.account;

import com.rop.request.UploadFile;

public class UploadUserPhotoRequest extends AccountRequest {

    private UploadFile photo;
    
    

	public UploadUserPhotoRequest() {
	}

	public UploadUserPhotoRequest(String email, String password) {
		super(email, password);
	}

	public UploadFile getPhoto() {
		return photo;
	}

	public void setPhoto(UploadFile photo) {
		this.photo = photo;
	}
    
}

