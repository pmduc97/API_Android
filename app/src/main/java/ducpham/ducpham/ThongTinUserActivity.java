package ducpham.ducpham;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ThongTinUserActivity extends AppCompatActivity {
    API apiURL = new API();

    public String url = apiURL.url +  "SelectAll";
    Button butDangXuat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UserBO bo = new UserBO();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_user);

        Intent intent = getIntent();

        // Tham số trong Intent truyền sang từ MainActivity
        String txt1 = intent.getStringExtra("txtAdmin");
        Boolean quyen = intent.getBooleanExtra("txtQuyen",true);
        TextView tv1 = findViewById(R.id.txtAdmin);
        if(quyen){
            tv1.setText("Chào ADMIN: " + txt1);
        }
        else {
            tv1.setText("Chào Thành Viên: " + txt1);
        }

        butDangXuat = findViewById(R.id.butDangXuat);
        butDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ThongTinUserActivity.this, MainActivity.class);
                ThongTinUserActivity.this.startActivity(myIntent);
            }
        });


        // Khởi tạo RecyclerView.
        final ListView rvUsers = (ListView)findViewById(R.id.listView);

        // Khởi tạo OkHttpClient để lấy dữ liệu.
        OkHttpClient client = new OkHttpClient();

        // Khởi tạo Moshi adapter để biến đổi json sang model java (ở đây là User)
        Moshi moshi = new Moshi.Builder().build();
        Type usersType = Types.newParameterizedType(List.class, UserBEAN.class);
        final JsonAdapter<List<UserBEAN>> jsonAdapter = moshi.adapter(usersType);

        // Tạo request lên server.
        Request request = new Request.Builder()
                .url(url)
                .build();

        // Thực thi request.
        try {
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("Error", "Network Error");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    // Lấy thông tin JSON trả về. Bạn có thể log lại biến json này để xem nó như thế nào.
                    String json = response.body().string();
                    final List<UserBEAN> users = jsonAdapter.fromJson(json);
                    final List<String> array= new ArrayList<String>();
                    for(UserBEAN u: users){
                        array.add(u.getFullName() + " (" + u.getUserName() + ")" + " - " + (u.isQuyen()?"Admin":"Thành Viên"));
                    }

                    // Cho hiển thị
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //rvUsers.setAdapter(new UserAdapter(users, ThongTinUserActivity.this));
                            ArrayAdapter<String> mHistory = new ArrayAdapter<String>(ThongTinUserActivity.this, android.R.layout.simple_list_item_1, array);
                            rvUsers.setAdapter(mHistory);
                        }
                    });
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }


}
