package com.tdr.wisdome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.tdr.wisdome.R;
import com.tdr.wisdome.model.ChildEntity;
import com.tdr.wisdome.model.GroupEntity;
import com.tdr.wisdome.util.Utils;

import java.util.List;

/**
 * ExpandableListView适配器
 * Created by Linus_Xie on 2016/8/7.
 */
public class ExpandableAdapter extends BaseExpandableListAdapter {
    private LayoutInflater inflater = null;
    private List<GroupEntity> groupList;
    private Context context;

    /**
     * 构造方法
     *
     * @param context
     * @param group_list
     */
    public ExpandableAdapter(Context context,
                             List<GroupEntity> group_list) {
        this.context = context;
        this.groupList = group_list;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * 返回一级Item总数
     */
    @Override
    public int getGroupCount() {
        // TODO Auto-generated method stub
        return groupList.size();
    }

    /**
     * 返回二级Item总数
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        if (groupList.get(groupPosition).getChildEntities() == null) {
            return 0;
        } else {
            return groupList.get(groupPosition).getChildEntities().size();
        }
    }

    /**
     * 获取一级Item内容
     */
    @Override
    public Object getGroup(int groupPosition) {
        // TODO Auto-generated method stub
        return groupList.get(groupPosition);
    }

    /**
     * 获取二级Item内容
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groupList.get(groupPosition).getChildEntities().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        // TODO Auto-generated method stub
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        GroupViewHolder holder = new GroupViewHolder();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.group_status_item, null);
        }
        holder.groupName = (TextView) convertView
                .findViewById(R.id.one_status_name);

        holder.groupName.setText(groupList.get(groupPosition).getGroupName());

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder viewHolder = null;
        final ChildEntity entity = (ChildEntity) getChild(groupPosition,
                childPosition);
        if (convertView != null) {
            viewHolder = (ChildViewHolder) convertView.getTag();
        } else {
            viewHolder = new ChildViewHolder();
            convertView = inflater.inflate(R.layout.child_status_item, null);
            viewHolder.childTitle = (TextView) convertView
                    .findViewById(R.id.tv_title);
            viewHolder.swipeLayout = (SwipeLayout) convertView.findViewById(R.id.sample);
            viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
            viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewWithTag("Bottom2"));
            viewHolder.iv_trash = (ImageView) convertView.findViewById(R.id.trash);
            if (groupPosition == 0) {
                viewHolder.iv_trash.setImageResource(R.mipmap.trash);
            } else {
                viewHolder.iv_trash.setImageResource(R.mipmap.add);
            }

            convertView.setTag(viewHolder);
        }
        if (entity != null && groupPosition == 0) {

            viewHolder.childTitle.setText(entity.getChildTitle());
            final ChildViewHolder finalViewHolder = viewHolder;
            viewHolder.iv_trash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //toast("click trash");
                    //do something
                    int index = childPosition;
                    ChildEntity entity = (ChildEntity) getChild(groupPosition,
                            index);
                    groupList.get(0).getChildEntities().remove(index);
                    Utils.myToast(context, "删除成功");
                    groupList.get(1).getChildEntities().add(entity);

                    ExpandableAdapter.this.notifyDataSetChanged();

                }
            });
            convertView.findViewById(R.id.item_surface).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //toast(entity.getChildTitle());
                    //do something
                }
            });
        } else {
            viewHolder.childTitle.setText(entity.getChildTitle());
            viewHolder.iv_trash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // toast("click start");
                    //do something
                    int index = childPosition;
                    ChildEntity entity = (ChildEntity) getChild(groupPosition,
                            index);
                    groupList.get(1).getChildEntities().remove(index);
                    Utils.myToast(context, "添加成功");
                    groupList.get(0).getChildEntities().add(entity);
                    ExpandableAdapter.this.notifyDataSetChanged();
                }
            });
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return false;
    }

    private class GroupViewHolder {
        TextView groupName;
    }

    private class ChildViewHolder {
        public SwipeLayout swipeLayout;
        public ImageView iv_trash;
        public TextView childTitle;
    }


}
