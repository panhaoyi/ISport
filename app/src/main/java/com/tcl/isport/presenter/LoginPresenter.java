package com.tcl.isport.presenter;

<<<<<<< HEAD:app/src/main/java/com/tcl/isport/presenter/LoginPresenter.java
import com.tcl.isport.imodel.IUserModel;
=======
import com.tcl.isport.iModel.IUserModel;
>>>>>>> 97d18ceb2b153c6824083d9d5cb27c5bd8c1cb5b:app/src/main/java/com/tcl/isport/presenter/LoginPresenter.java
import com.tcl.isport.iView.ILoginActivity;
import com.tcl.isport.model.UserModel;

/**
 * Created by haoyi.pan on 17-9-18.
 */
public class LoginPresenter {
    private ILoginActivity iLoginActivity;
    private IUserModel iUserModel;

    public LoginPresenter(ILoginActivity view){
        this.iLoginActivity=view;
        iUserModel=new UserModel();
    }

}
