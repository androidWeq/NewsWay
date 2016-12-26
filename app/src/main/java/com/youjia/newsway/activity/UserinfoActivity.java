package com.youjia.newsway.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youjia.newsway.R;
import com.youjia.newsway.base.BaseActivity;
import com.youjia.newsway.review.FirstDatePickerDialog;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Wenjian on 2016/12/7.
 */

public class UserinfoActivity extends BaseActivity {

    @InjectView(R.id.userinfo_rel_touxiang)
    RelativeLayout userinfoRelTouxiang;
    @InjectView(R.id.userinfo_nicheng)
    TextView userinfoNicheng;
    @InjectView(R.id.userinfo_rel_nicheng)
    RelativeLayout userinfoRelNicheng;
    @InjectView(R.id.userinfo_sex)
    TextView userinfoSex;
    @InjectView(R.id.userinfo_rel_sex)
    RelativeLayout userinfoRelSex;
    @InjectView(R.id.userinfo_britn)
    TextView userinfoBritn;
    @InjectView(R.id.userinfo_rel_brith)
    RelativeLayout userinfoRelBrith;
    @InjectView(R.id.userinfo_address)
    TextView userinfoAddress;
    @InjectView(R.id.userinfo_rel_address)
    RelativeLayout userinfoRelAddress;
    @InjectView(R.id.userinfo_profession)
    TextView userinfoProfession;
    @InjectView(R.id.userinfo_rel_profession)
    RelativeLayout userinfoRelProfession;
    @InjectView(R.id.userinfo_educational)
    TextView userinfoEducational;
    @InjectView(R.id.userinfo_rel_educational)
    RelativeLayout userinfoRelEducational;
    @InjectView(R.id.userinfo_worship)
    TextView userinfoWorship;
    @InjectView(R.id.userinfo_rel_worship)
    RelativeLayout userinfoRelWorship;

    String userSex = "男";
    String usereducation="小学";
    AlertDialog.Builder builder;
    AlertDialog dialog;
    public final int CHOOSE_CITY=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.userinfo_rel_touxiang, R.id.userinfo_rel_nicheng, R.id.userinfo_rel_sex, R.id.userinfo_rel_brith, R.id.userinfo_rel_address, R.id.userinfo_rel_profession, R.id.userinfo_rel_educational,R.id.userinfo_rel_worship})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.userinfo_rel_touxiang:
                builder = new AlertDialog.Builder(this);
                final String[] type=new String[]{"相册","拍照","取消"};
                builder.setItems(type, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0://相册
                                // "从相册选择"按钮被点击了

                                break;
                            case 1://拍照
                                // "拍照"按钮被点击了

                                break;
                            case 2://取消
                                break;
                        }
                        dialog.dismiss();
                    }
                });
                dialog=builder.create();
                builder.show();

                break;
            case R.id.userinfo_rel_nicheng:
                builder = new AlertDialog.Builder(this);
                builder.setTitle("修改昵称");
                final EditText editText=new EditText(this);
                editText.setSingleLine();
                builder.setView(editText);
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userinfoNicheng.setText(editText.getText().toString());
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setCancelable(true);
                dialog=builder.create();
                dialog.setCanceledOnTouchOutside(true);
                builder.show();
                break;
            case R.id.userinfo_rel_sex:
                builder = new AlertDialog.Builder(this);
                final String[] sex=new String[]{"男","女","未确定"};
                builder.setItems(sex, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userinfoSex.setText(sex[which]);
                        dialog.dismiss();
                    }
                });
                dialog=builder.create();
                builder.show();
                break;
            case R.id.userinfo_rel_brith:
                Calendar calendar=Calendar.getInstance();
                new FirstDatePickerDialog(UserinfoActivity.this, 0, new FirstDatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth) {
                        String textString = String.format(
                                "%d-%d-%d",startYear, startMonthOfYear + 1,startDayOfMonth);
                        userinfoBritn.setText(textString);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE),true).show();
            break;
            case R.id.userinfo_rel_address:
                Intent intent = new Intent(UserinfoActivity.this, ChooseCityActivity.class);
                startActivityForResult(intent,CHOOSE_CITY);
                break;
            case R.id.userinfo_rel_profession:
                builder = new AlertDialog.Builder(this);
                final String[] works=new String[]{"无业游民","学生","蓝领","白领","店铺老板","领导","其他"};
                builder.setItems(works, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userinfoProfession.setText(works[which]);
                        dialog.dismiss();
                    }
                });
                dialog=builder.create();
                builder.show();

                break;
            case R.id.userinfo_rel_educational:
                builder = new AlertDialog.Builder(this);
                final String[] educational=new String[]{"放牛娃","小学生","初中生","高中生","大学生","研究生","博士生"};
                builder.setItems(educational, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userinfoEducational.setText(educational[which]);
                        dialog.dismiss();
                    }
                });
                dialog=builder.create();
                builder.show();

                break;
            case R.id.userinfo_rel_worship:
                builder = new AlertDialog.Builder(this);
                final String[] start=new String[]{"迷妹","迷弟","路人甲"};
                builder.setItems(start, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userinfoWorship.setText(start[which]);
                        dialog.dismiss();
                    }
                });
                dialog=builder.create();
                builder.show();
                break;
        }
    }

    @Override
    protected void findViewById() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {

            case CHOOSE_CITY:
                userinfoAddress.setText(data.getStringExtra("address"));
                break;
        }


        super.onActivityResult(requestCode, resultCode, data);


    }











}
