package com.youjia.newsway.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.youjia.newsway.R;
import com.youjia.newsway.tools.HttpUtil;
import com.youjia.newsway.tools.L;
import com.youjia.newsway.tools.Urls;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.adapter.rxjava.Result;


/**
 * Created by Administrator on 2016/11/29.
 */

public class DemoRetrofit extends AppCompatActivity {


    @InjectView(R.id.img)
    ImageView image;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private File file=null;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_retrofit);
        ButterKnife.inject(this);
        url=getSDPath();
        Log.d("路径",url);
        //initData();
        //writeImg();



        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //upLoad(url+"/aa.jpg");
                //upLoadOne(url+"/aa.jpg");
                shangchuan(url+"/Download/b20.png");

            }


        });

        findViewById(R.id.zhuanhuan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap img = BitmapFactory.decodeResource(getResources(), R.drawable.mine_top);

                try {
                    saveBitmapToFile(img,url+"aaa");
                } catch (IOException e) {
                    Toast.makeText(DemoRetrofit.this, "shibai", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * Save Bitmap to a file.保存图片到SD卡。
     *
     * @throws IOException
     */
    public void saveBitmapToFile(Bitmap bitmap, String _file)
            throws IOException {
        BufferedOutputStream os = null;
        try {
            file = new File(_file);
            // String _filePath_file.replace(File.separatorChar +
            // file.getName(), "");
            int end = _file.lastIndexOf(File.separator);
            String _filePath = _file.substring(0, end);
            File filePath = new File(_filePath);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            file.createNewFile();
            os = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    Log.e("保存文件", e.getMessage(), e);
                }
            }
        }
    }

    public static String convertIconToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] appicon = baos.toByteArray();// 转为byte数组
        return Base64.encodeToString(appicon, Base64.DEFAULT);

    }


    public static Bitmap convertStringToIcon(String st) {
        // OutputStream out;
        Bitmap bitmap = null;
        try {
            // out = new FileOutputStream("/sdcard/aa.jpg");
            byte[] bitmapArray;
            bitmapArray = Base64.decode(st, Base64.DEFAULT);
            bitmap =
                    BitmapFactory.decodeByteArray(bitmapArray, 0,
                            bitmapArray.length);
            // bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }

    public String getSDPath(){
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.getPath().toString();
    }





    private void initData() {
        String filePath = "/sdcard/Test/";
        String fileName = "log.txt";

        writeTxtToFile("txt content", filePath, fileName);
    }

    // 将字符串写入到文本文件中
    public void writeTxtToFile(String strcontent, String filePath, String fileName) {
        //生成文件夹之后，再生成文件，不然会出错
        makeFilePath(filePath, fileName);

        String strFilePath = filePath+fileName;
        // 每次写入时，都换行写
        String strContent = strcontent + "\r\n";
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                Log.d("TestFile", "Create the file:" + strFilePath);
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
            Log.d("TestFile", "Error on write File:" + e);
        }
    }

    // 生成文件
    public File makeFilePath(String filePath, String fileName) {

        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("文件",file.toString());
        return file;
    }

    // 生成文件夹
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.d("error:", e+"");
        }
    }

    /**
     * 上传图片
     * create by weiang at 2016/5/20 17:33.
     */
    private void upLoad(String filePath) {
        L.d(filePath+"------路径");
        File file = new File(filePath);//filePath 图片地址
        String token = "3";//用户token
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//表单类型
                .addFormDataPart("user_id", token);//ParamKey.TOKEN 自定义参数key常量类，即参数名
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        builder.addFormDataPart("image", file.getName(), imageBody);//imgfile 后台接收图片流的参数名

        List<MultipartBody.Part> parts = builder.build().parts();
        HttpUtil.uploadMemberIcon(parts).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                L.d(response.toString()+"------成功");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                L.d(t.getMessage()+"-----失败");
            }
        });
    }



    public void writeImg(){
        String path = Environment.getExternalStorageDirectory().getPath();//获取手机本地存//储路径，这个位置可以根据需要自己改。

        InputStream in = getResources().openRawResource(R.raw.aa);//读取程序中的图片

        File file = new File(path + "/aa.jpg");//创建文件
        L.d(file.toString()+"******");
        if (!file.exists()) {
            try {//如果文件不存在就创建文件，写入图片
                file.createNewFile();
                FileOutputStream fo = new FileOutputStream(file);
                int read = in.read();
                while (read != -1) {
                    fo.write(read);
                    read = in.read();
                }
                //关闭流
                fo.flush();
                fo.close();
                in.close();
                L.d("写入图片成功");
            } catch (IOException e) {
               L.d("写入图片失败");
                e.printStackTrace();
            }

        }else {
            L.d("----已存在"+"path + \"/aa.jpg\"");
            image.setImageBitmap(getDiskBitmap(path + "/aa.jpg"));
        }
    }



    private void upLoadOne(String filePath) {
        File file = new File(filePath);
        String token = "3";
        RequestBody tokenBody = RequestBody.create(MediaType.parse("multipart/form-data"), token);
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part imageBodyPart = MultipartBody.Part.createFormData("image", file.getName(), imageBody);
        MultipartBody.Part idBodyPart=MultipartBody.Part.createFormData("user_id",token,tokenBody);

        HttpUtil.uploadMemberIcon(imageBodyPart,tokenBody).enqueue(new Callback<Result<String>>() {
            @Override
            public void onResponse(Call<Result<String>> call, Response<Result<String>> response) {
                L.d(response.body().toString()+"----成功");
            }

            @Override
            public void onFailure(Call<Result<String>> call, Throwable t) {
                L.d(t.getMessage()+"----失败");
            }
        });
    }


    public void upload2(){
        // 创建 RequestBody，用于封装 请求RequestBody
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

// MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);

// 添加描述
        String descriptionString = "3";
        RequestBody description =
                RequestBody.create(
                        MediaType.parse("text/plain"), descriptionString);


// 执行请求
        Call<ResponseBody> call = HttpUtil.upload2(body,description);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                Log.v("Upload", "success");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });
    }

//    /**
//     * 上传多张图片
//     * create by weiang at 2016/5/20 17:33.
//     */
//    private void upLoad() {
//        List<String> pathList = getPathList();//此处是伪代码，获取多张待上传图片的地址列表
//        String token = "ASDDSKKK19990SDDDSS";//用户token
//        MultipartBody.Builder builder = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)//表单类型
//                .addFormDataPart(ParamKey.TOKEN, token);//ParamKey.TOKEN 自定义参数key常量类，即参数名
//        //多张图片
//        for (int i = 0; i < pathList.size(); i++) {
//            File file = new File(pathList.get(i));//filePath 图片地址
//            RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//            builder.addFormDataPart("imgfile"+i, file.getName(), imageBody);//"imgfile"+i 后台接收图片流的参数名
//        }
//
//        List<MultipartBody.Part> parts = builder.build().parts();
//        ApiUtil.uploadMemberIcon(parts).enqueue(new Callback<Result<String>>() {//返回结果
//            @Override
//            public void onResponse(Call<Result<String>> call, Response<Result<String>> response) {
//                AppUtil.showToastInfo(context, response.body().getMsg());
//            }
//
//            @Override
//            public void onFailure(Call<Result<String>> call, Throwable t) {
//                AppUtil.showToastInfo(context, "头像上传失败");
//            }
//        });
//    }



    public void shangchuan(String filePath){
        L.d(filePath+"-----路径");
        File file = new File(filePath);//filePath 图片地址
        //构建body
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("image", file.getName(), RequestBody.create(MediaType.parse("image/*"), file))
                .addFormDataPart("user_id","3")
                .build();
        Call<String> call = HttpUtil.upload2(Urls.base+Urls.uoloadImg, requestBody );
        // 执行
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("上传",response.body().toString()+"-------");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("上传",t.getMessage()+"******");
            }
        });
    }

    private Bitmap getDiskBitmap(String pathString)
    {
        Bitmap bitmap = null;
        try
        {
            File file = new File(pathString);
            if(file.exists())
            {
                bitmap = BitmapFactory.decodeFile(pathString);
            }
        } catch (Exception e)
        {
            // TODO: handle exception
        }


        return bitmap;
    }




}
