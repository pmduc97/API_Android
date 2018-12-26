package ducpham.ducpham;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DangKy extends AppCompatActivity {
    EditText tTaiKhoan,tMatKhau,tHoTen,tEmail,tDienThoai,tNgaySinh,tQueQuan;
    Button butTroVe,butDangKy;

    API apiURL = new API();
    String urldangky = apiURL.url + "InsertUser";
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        butTroVe = findViewById(R.id.butTroVe);
        butDangKy = findViewById(R.id.butDangKy);
        tTaiKhoan = findViewById(R.id.txtTaiKhoan);
        tMatKhau = findViewById(R.id.txtMatKhau);
        tHoTen = findViewById(R.id.txtHoTen);
        tEmail = findViewById(R.id.txtEmail);
        tDienThoai = findViewById(R.id.txtDienThoai);
        tNgaySinh = findViewById(R.id.txtNgaySinh);
        tQueQuan = findViewById(R.id.txtQueQuan);

        tTaiKhoan.requestFocus();
        butDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = tTaiKhoan.getText().toString();
                String passWord = tMatKhau.getText().toString();
                String fullName = tHoTen.getText().toString();
                String email = tEmail.getText().toString();
                String phone = tDienThoai.getText().toString();
                String birthDay = tNgaySinh.getText().toString();
                String homeTown = tQueQuan.getText().toString();
                try {
                    OkHttpClient client = new OkHttpClient();

                    // Create okhttp3 form body builder.
                    FormBody.Builder formBodyBuilder = new FormBody.Builder();

                    // Add form parameter
                    formBodyBuilder.add("userName", userName);
                    formBodyBuilder.add("passWord", passWord);
                    formBodyBuilder.add("fullName", fullName);
                    formBodyBuilder.add("email", email);
                    formBodyBuilder.add("phone", phone);
                    formBodyBuilder.add("birthDay", birthDay);
                    formBodyBuilder.add("homeTown", homeTown);

                    // Build form body.
                    FormBody formBody = formBodyBuilder.build();
                    // Create a http request object.
                    Request request = new Request.Builder()
                            .url(urldangky)
                            .post(formBody)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            call.cancel();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            final String myResponse = response.body().string();
                            final int respCode = response.code();
                            Log.d(" BODY", myResponse);
                            DangKy.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(myResponse.equals("1")){
                                        msg("Thông báo","Đăng ký tài khoản thành công");
                                    }
                                    else if(myResponse.equals("-9")){
                                        msg("Thông báo","Tài khoản đã tồn tại");
                                    }
                                    else if(myResponse.equals("-101")){
                                        msg("Thông báo","Có lỗi. Vui lòng thử lại");
                                    }
                                    else{
                                        msg("Thông báo","Vui lòng điền đầy đủ thông tin đăng ký");
                                    }

                                }
                            });

                        }
                    });
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        butTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một Intent:
                // (Mang nội dung sẽ gửi tới Example1Activity).
                Intent myIntent = new Intent(DangKy.this, MainActivity.class);

                // Các tham số gắn trên Intent (Không bắt buộc).
                //myIntent.putExtra("txtAdmin", _user.getFullName());
                // Yêu cầu chạy Example1Activity.
                DangKy.this.startActivity(myIntent);
                //dialog.dismiss();
            }
        });
    }

    public void msg(String title,String mesenger){
        AlertDialog alertDialog = new AlertDialog.Builder(DangKy.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(mesenger);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,"OK",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    String Base64_Encode(String str){
        byte[] strBytes = str.getBytes();
        byte[] encoded = Base64.encode(
                strBytes, Base64.URL_SAFE | Base64.NO_PADDING | Base64.NO_WRAP);
        return new String(encoded);
    }

    String Base64_Decode(String enStr){
        byte[] decoded = Base64.decode(enStr.getBytes(), Base64.DEFAULT);
        return new String(decoded);
    }
}
