package com.viettelpost.remoteconfig.fingerprintdialog.ui.main;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kevalpatel2106.fingerprintdialog.AuthenticationCallback;
import com.kevalpatel2106.fingerprintdialog.FingerprintDialogBuilder;
import com.kevalpatel2106.fingerprintdialog.FingerprintUtils;
import com.viettelpost.remoteconfig.fingerprintdialog.R;

import androidx.navigation.Navigation;

public class MainViewModel extends ViewModel {
    private MainViewModel mViewModel;
    private Button button;
    private FingerprintDialogBuilder dialogBuilder;

    private FingerprintDialogBuilder getDialogBuilder(Context context) {
        dialogBuilder = new FingerprintDialogBuilder(context)
                .setTitle("Hello Finger")
                .setSubtitle("Đăng nhập bằng dấu vân tay")
                .setDescription("Xác nhập cmmd")
                .setNegativeButton("Hủy");
        return dialogBuilder;
    }

    public void showFinger(Context context, FragmentManager manager, View view) {
        getDialogBuilder(context).show(manager, showFinger.getBack().HandlingFinger(context, view));
    }
}

class showFinger {
    private static showFinger back;

    public static showFinger getBack() {
        if (back == null) {
            back = new showFinger();
        }
        return back;
    }

    public AuthenticationCallback HandlingFinger(final Context c, final View view) {
        final AuthenticationCallback callback = new AuthenticationCallback() {
            @Override
            public void fingerprintAuthenticationNotSupported() {
                //Thiết bị không hỗ trợ xác thực vân tay.
                // Có thể thiết bị không có phần cứng hoặc
                // thiết bị vân tay đang chạy Android < Marshmallow.
            }

            @Override
            public void hasNoFingerprintEnrolled() {
                //Người dùng không đăng ký bảo mật bằng vân tay.
                //Chuyển đến cài đặt vân tay.
                FingerprintUtils.openSecuritySettings(c);
            }

            @Override
            public void onAuthenticationError(int errorCode, @Nullable CharSequence errString) {
                //Lỗi không thể sử dụng vân tay.
                //Thư viện sẽ ngừng tìm kiếm vân tay khi được gọi lại.
            }

            @Override
            public void onAuthenticationHelp(int helpCode, @Nullable CharSequence helpString) {
                //Cảnh báo trong quá trình xác thực, ví dụ: cảm biến bị bẩn, ngón tay bị ướt,...
                //Thư viện sẽ tiếp tục quét khi gọi lại.
            }

            @Override
            public void authenticationCanceledByUser() {
                //Người dùng ấn nút "Hủy" trên Dialog.
            }

            @Override
            public void onAuthenticationSucceeded() {
                //Xác thực thành công, thực hiện tác vụ.
                Toast.makeText(c, "Xác nhận thành công", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(R.id.finger_succeeded);
            }

            @Override
            public void onAuthenticationFailed() {
                //Quá trình xác thực thất bại
            }
        };
        return callback;
    }
}
