package ducpham.ducpham;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    EditText txtTaiKhoan,txtMatKhau;
    Button butDangNhap,butDangKy1;
    UserBEAN user = null;
    API apiURL = new API();

    public String url = apiURL.url;
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTaiKhoan = findViewById(R.id.txtTaiKhoan);
        txtMatKhau = findViewById(R.id.txtMatKhau);
        butDangNhap = findViewById(R.id.butDangNhap);
        butDangKy1 = findViewById(R.id.butDangKy);

        butDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = txtTaiKhoan.getText().toString();
                String passWord = txtMatKhau.getText().toString();
                try {
                    OkHttpClient client = new OkHttpClient();


                    // Khởi tạo Moshi adapter để biến đổi json sang model java (ở đây là User)
                    Moshi moshi = new Moshi.Builder().build();
                    Type usersType = Types.newParameterizedType(List.class, UserBEAN.class);
                    final JsonAdapter<List<UserBEAN>> jsonAdapter = moshi.adapter(usersType);


                    // Create okhttp3 form body builder.
                    FormBody.Builder formBodyBuilder = new FormBody.Builder();

                    // Add form parameter
                    formBodyBuilder.add("userName", userName);
                    formBodyBuilder.add("passWord", passWord);

                    // Build form body.
                    FormBody formBody = formBodyBuilder.build();
                    // Create a http request object.
                    Request request = new Request.Builder()
                            .url(url + "CheckLogin")
                            .post(formBody)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            call.cancel();
                        }


                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            String myResponse = response.body().string();
                            Log.d(" RESPONSE:",myResponse);
                            if(myResponse == null || myResponse == ""){
                                myResponse = "[]";
                            }
                            final List<UserBEAN> users = jsonAdapter.fromJson(myResponse);


                            final int respCode = response.code();
                            Log.d(" 1. RESPONSE CODE: ", Integer.toString(respCode));
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(respCode == 200){
                                        try {
                                            Log.d("userName",users.get(0).getFullName());
                                            msg2("Thông báo","Đăng nhập thành công\n\nChào: " + users.get(0).getFullName(),ThongTinUserActivity.class,users.get(0));
                                        }
                                        catch (Exception e){
                                            e.printStackTrace();
                                        }

                                    }
                                    if(respCode == 204){
                                        Log.d(" RESPONSE:","Dang nhap that bai");
                                        msg("Thông báo","Đăng nhập thất bại");
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

        butDangKy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một Intent:
                // (Mang nội dung sẽ gửi tới Example1Activity).
                Intent myIntent = new Intent(MainActivity.this, DangKy.class);

                // Các tham số gắn trên Intent (Không bắt buộc).
                //myIntent.putExtra("txtAdmin", _user.getFullName());
                // Yêu cầu chạy Example1Activity.
                MainActivity.this.startActivity(myIntent);
                //dialog.dismiss();
            }
        });
    }
    public void msg(String title,String mesenger){
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
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
    public void msg2(String title, String mesenger, final Class app, final UserBEAN _user){
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(mesenger);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,"OK",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Tạo một Intent:
                // (Mang nội dung sẽ gửi tới Example1Activity).
                Intent myIntent = new Intent(MainActivity.this, app);

                // Các tham số gắn trên Intent (Không bắt buộc).
                myIntent.putExtra("txtAdmin", _user.getFullName());
                myIntent.putExtra("txtQuyen",_user.isQuyen());
                // Yêu cầu chạy Example1Activity.
                MainActivity.this.startActivity(myIntent);
                //dialog.dismiss();
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
