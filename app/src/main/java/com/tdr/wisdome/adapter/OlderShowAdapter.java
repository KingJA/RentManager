package com.tdr.wisdome.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tdr.wisdome.R;
import com.tdr.wisdome.actvitiy.ModifyActivity;
import com.tdr.wisdome.actvitiy.OlderPhotoActivity;
import com.tdr.wisdome.util.Utils;
import com.tdr.wisdome.view.SelectPicPopupWindow;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Linus_Xie on 2016/8/19.
 */
public class OlderShowAdapter extends BaseAdapter {

    private List<HashMap<String, String>> data;
    private LayoutInflater mInflater;
    private Context mContext;
    private static final int type = 0;
    private String personType = "";
    private SelectPicPopupWindow mSelectPicPopupWindow;

    public OlderShowAdapter(List<HashMap<String, String>> data, Context mContext, String personType) {
        this.data = data;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.personType = personType;

    }

    public class GuardianViewHolder {
        public TextView text_guardianName;
        public TextView text_guardianPhone;
        public TextView text_guardianAddress;
    }

    public class OlderViewHolder {
        public RelativeLayout relative_olderName;
        public TextView text_olderName;
        public RelativeLayout relative_bodyPhoto;
        public ImageView image_bodyPhoto;
        public RelativeLayout relative_olderPhone;
        public TextView text_olderPhone;
        public RelativeLayout relative_olderIdentity;
        public TextView text_odlerIdentity;
        public RelativeLayout relative_olderAddress;
        public TextView text_olderAddress;
        public RelativeLayout relative_olderHealth;
        public TextView text_olderHealth;
        public RelativeLayout relative_olderRemarks;
        public TextView text_olderRemarks;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (position == 0) {
            OlderViewHolder olderViewHolder = null;
            if (olderViewHolder == null) {
                olderViewHolder = new OlderViewHolder();
                convertView = mInflater.inflate(R.layout.layout_oldershare, null);
                olderViewHolder.relative_olderName = (RelativeLayout) convertView.findViewById(R.id.relative_olderName);
                olderViewHolder.text_olderName = (TextView) convertView.findViewById(R.id.text_olderName);
                olderViewHolder.relative_bodyPhoto = (RelativeLayout) convertView.findViewById(R.id.relative_bodyPhoto);
                olderViewHolder.image_bodyPhoto = (ImageView) convertView.findViewById(R.id.image_bodyPhoto);
                olderViewHolder.relative_olderPhone = (RelativeLayout) convertView.findViewById(R.id.relative_olderPhone);
                olderViewHolder.text_olderPhone = (TextView) convertView.findViewById(R.id.text_olderPhone);
                olderViewHolder.relative_olderIdentity = (RelativeLayout) convertView.findViewById(R.id.relative_olderIdentity);
                olderViewHolder.text_odlerIdentity = (TextView) convertView.findViewById(R.id.text_odlerIdentity);
                olderViewHolder.relative_olderAddress = (RelativeLayout) convertView.findViewById(R.id.relative_olderAddress);
                olderViewHolder.text_olderAddress = (TextView) convertView.findViewById(R.id.text_olderAddress);
                olderViewHolder.relative_olderHealth = (RelativeLayout) convertView.findViewById(R.id.relative_olderHealth);
                olderViewHolder.text_olderHealth = (TextView) convertView.findViewById(R.id.text_olderHealth);
                olderViewHolder.relative_olderRemarks = (RelativeLayout) convertView.findViewById(R.id.relative_olderRemarks);
                olderViewHolder.text_olderRemarks = (TextView) convertView.findViewById(R.id.text_olderRemarks);
                convertView.setTag(olderViewHolder);
            } else {
                olderViewHolder = (OlderViewHolder) convertView.getTag();
            }

            olderViewHolder.text_olderName.setText((String) data.get(position).get("customerName"));
            olderViewHolder.image_bodyPhoto.setImageBitmap(Utils.stringtoBitmap((String) data.get(position).get("photo")));
            olderViewHolder.text_olderPhone.setText((String) data.get(position).get("customMobile"));
            olderViewHolder.text_odlerIdentity.setText(Utils.hideID((String) data.get(position).get("customerIdCard")));
            olderViewHolder.text_olderAddress.setText((String) data.get(position).get("customerAddress"));
            olderViewHolder.text_olderHealth.setText((String) data.get(position).get("healthCondition"));
            olderViewHolder.text_olderRemarks.setText((String) data.get(position).get("emtnotice"));

            if (personType.equals("0")) {
                olderViewHolder.relative_olderName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentOlderName = new Intent(mContext, ModifyActivity.class);
                        intentOlderName.putExtra("activityName", "olderName");
                        intentOlderName.putExtra("olderName", (String) data.get(position).get("customerName"));
                        mContext.startActivity(intentOlderName);
                    }
                });

                olderViewHolder.relative_bodyPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentOlderName = new Intent(mContext, OlderPhotoActivity.class);
                        intentOlderName.putExtra("olderPhoto", (String) data.get(position).get("photo"));
                        mContext.startActivity(intentOlderName);
                    }
                });
                olderViewHolder.relative_olderPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentOlderName = new Intent(mContext, ModifyActivity.class);
                        intentOlderName.putExtra("activityName", "olderPhone");
                        intentOlderName.putExtra("olderPhone", (String) data.get(position).get("customMobile"));
                        mContext.startActivity(intentOlderName);
                    }
                });
                olderViewHolder.relative_olderIdentity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentOlderName = new Intent(mContext, ModifyActivity.class);
                        intentOlderName.putExtra("activityName", "olderIdentity");
                        intentOlderName.putExtra("olderIdentity", Utils.hideID((String) data.get(position).get("customerIdCard")));
                        mContext.startActivity(intentOlderName);
                    }
                });
                olderViewHolder.relative_olderAddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentOlderName = new Intent(mContext, ModifyActivity.class);
                        intentOlderName.putExtra("activityName", "olderAddress");
                        intentOlderName.putExtra("olderAddress", (String) data.get(position).get("customerAddress"));
                        mContext.startActivity(intentOlderName);
                    }
                });
                /*olderViewHolder.relative_olderHealth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentOlderName = new Intent(mContext, HealthActivity.class);
                        intentOlderName.putExtra("olderHealth", (String) data.get(position).get("healthCondition"));
                        mContext.startActivity(intentOlderName);
                    }
                });*/
                olderViewHolder.relative_olderRemarks.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentOlderName = new Intent(mContext, ModifyActivity.class);
                        intentOlderName.putExtra("activityName", "olderRemarks");
                        intentOlderName.putExtra("olderRemarks", (String) data.get(position).get("emtnotice"));
                        mContext.startActivity(intentOlderName);
                    }
                });
            }


        } else {
            GuardianViewHolder guardianViewHolder = null;
            if (guardianViewHolder == null) {
                guardianViewHolder = new GuardianViewHolder();
                convertView = mInflater.inflate(R.layout.layout_showguardian, null);
                TextView text_guardianNum = (TextView) convertView.findViewById(R.id.text_guardianNum);
                final TextView text_delGuardian = (TextView) convertView.findViewById(R.id.text_delGuardian);
                text_delGuardian.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (data.size() == 2) {
                            text_delGuardian.setVisibility(View.GONE);
                        } else {
                            data.remove(position);
                            notifyDataSetChanged();
                        }
                    }
                });
                guardianViewHolder.text_guardianName = (TextView) convertView.findViewById(R.id.text_guardianName);
                guardianViewHolder.text_guardianPhone = (TextView) convertView.findViewById(R.id.text_guardianPhone);
                guardianViewHolder.text_guardianAddress = (TextView) convertView.findViewById(R.id.text_guardianAddress);
                convertView.setTag(guardianViewHolder);
            } else {
                guardianViewHolder = (GuardianViewHolder) convertView.getTag();
            }
            guardianViewHolder.text_guardianName.setText((String) data.get(position).get("guardianName"));
            guardianViewHolder.text_guardianPhone.setText((String) data.get(position).get("guardianMobile"));
            guardianViewHolder.text_guardianAddress.setText((String) data.get(position).get("guardianAddress"));
        }
        return convertView;
    }
}
