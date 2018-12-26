package ducpham.ducpham;

import android.app.Activity;
import android.util.Log;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserBO {
    public String url = "http://192.168.1.11:3000/";
    public static String json = "";
    public static ArrayList<UserBEAN> l = new ArrayList<>();
    private ArrayList<UserBEAN> lst = null;

    public ArrayList<UserBEAN> getLst() {
        return lst;
    }

    public void setLst(ArrayList<UserBEAN> lst) {
        this.lst = lst;
    }

    public ArrayList<UserBEAN> getAll(final Activity app){
        ArrayList<UserBEAN> lit = new ArrayList<>();
        try {
            OkHttpClient client = new OkHttpClient();

            // Create okhttp3 form body builder.
            FormBody.Builder formBodyBuilder = new FormBody.Builder();

            // Build form body.
            FormBody formBody = formBodyBuilder.build();
            // Create a http request object.
            Request request = new Request.Builder().url(url + "SelectAll").get().build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    call.cancel();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    final String myResponse = response.body().string();
                    final int respCode = response.code();
                    Log.d(" 1. RESPONSE CODE: ", Integer.toString(respCode));
                    app.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(respCode == 200){
                                Log.d(" RESPONSE:",myResponse);
                                try {
                                    JSONArray reader = new JSONArray(myResponse);
                                    Log.d("MyHandle", reader.toString());
                                    JSONArray jArray = (JSONArray)reader;
                                    if (jArray != null) {
                                        ArrayList<UserBEAN> lit = new ArrayList<>();
                                        for (int i=0;i<jArray.length();i++){
                                            String _id = jArray.getJSONObject(i).get("_id").toString();
                                            String userName = jArray.getJSONObject(i).get("userName").toString();
                                            String passWord = jArray.getJSONObject(i).get("passWord").toString();
                                            String fullName = jArray.getJSONObject(i).get("fullName").toString();
                                            String email = jArray.getJSONObject(i).get("email").toString();
                                            String phone = jArray.getJSONObject(i).get("phone").toString();
                                            String birthDay = jArray.getJSONObject(i).get("birthDay").toString();
                                            String homeTown = jArray.getJSONObject(i).get("homeTown").toString();
                                            Boolean quyen = jArray.getJSONObject(i).get("quyen").equals(true);
                                            UserBEAN u = new UserBEAN(_id,userName,passWord,fullName,email,phone,birthDay,homeTown,quyen);

                                            lit.add(u);

                                        }
                                        l = lit;
                                    }
                                }
                                catch (Exception e1){
                                    e1.printStackTrace();
                                }
                            }
                        }
                    });

                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }

        Log.d("LIT",String.valueOf(l));
        setLst(l);
        Log.d("setLst",String.valueOf(getLst()));
        return  getLst();
    }
}
