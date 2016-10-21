package com.tdr.wisdome.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.tdr.wisdome.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Linus_Xie on 2016/8/18.
 */
public class AddGuardianAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    public ArrayList<String> arr;

    private List<HashMap<String, String>> mData = new ArrayList<>();//存储EditText的值
    public HashMap<String, String> editValue = new HashMap<>();

    //定义一个HashMap，用来存放EditText的值，Key是position
    HashMap<Integer, String> hashMap = new HashMap<Integer, String>();

    public AddGuardianAdapter(Context context) {
        super();
        this.context = context;
        inflater = LayoutInflater.from(context);
        arr = new ArrayList<String>();
        for (int i = 0; i < 1; i++) {    //listview初始化3个子项
            arr.add("");
        }

    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder {
        public TextView text_guardianNum;
        public TextView text_delGuardian;
        public EditText edit_guardianName;
        public EditText edit_guardianIdentity;
        public EditText edit_phone;
        public TextView text_code;
        public EditText edit_guardianCode;
        public EditText edit_guardianAddress;
        public EditText edit_guardianAlternatePhone;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.layout_guardian, null);
            holder.text_guardianNum = (TextView) view.findViewById(R.id.text_guardianNum);
            holder.text_delGuardian = (TextView) view.findViewById(R.id.text_delGuardian);
            holder.edit_guardianName = (EditText) view.findViewById(R.id.edit_guardianName);
            holder.edit_guardianIdentity = (EditText) view.findViewById(R.id.edit_guardianIdentity);
            holder.edit_phone = (EditText) view.findViewById(R.id.edit_phone);
            holder.text_code = (TextView) view.findViewById(R.id.text_code);
            holder.edit_guardianCode = (EditText) view.findViewById(R.id.edit_guardianCode);
            holder.edit_guardianAddress = (EditText) view.findViewById(R.id.edit_guardianAddress);
            holder.edit_guardianAlternatePhone = (EditText) view.findViewById(R.id.edit_guardianAlternatePhone);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.text_delGuardian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arr.remove(i);
                //notifyDataSetChanged();
                notify();
            }
        });

        holder.edit_guardianName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hashMap.put(i, s.toString());
            }
        });

        holder.edit_guardianIdentity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hashMap.put(i, s.toString());
            }
        });

        holder.edit_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hashMap.put(i, s.toString());
            }
        });

        holder.edit_guardianCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hashMap.put(i, s.toString());
            }
        });

        holder.edit_guardianAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hashMap.put(i, s.toString());
            }
        });

        holder.edit_guardianAlternatePhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hashMap.put(i, s.toString());
            }
        });

        if (hashMap.get(i) != null) {
            holder.edit_guardianName.setText(hashMap.get(i));
            holder.edit_guardianIdentity.setText(hashMap.get(i));
            holder.edit_phone.setText(hashMap.get(i));
            holder.edit_guardianCode.setText(hashMap.get(i));
            holder.edit_guardianAddress.setText(hashMap.get(i));
            holder.edit_guardianAlternatePhone.setText(hashMap.get(i));
        }


        holder.text_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewHolder asd = (ViewHolder) (((View) v.getParent()).getTag());
                asd.edit_phone.getText();
            }
        });

        return view;
    }
}
