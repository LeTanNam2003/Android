package com.example.portal3;

import android.widget.BaseExpandableListAdapter;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    Context context;
    List<Map<String, String>> groupData;
    List<List<Map<String, String>>> childData;

    public CustomExpandableListAdapter(Context context,
                                   List<Map<String, String>> groupData,
                                   List<List<Map<String, String>>> childData) {
        this.context = context;
        this.groupData = groupData;
        this.childData = childData;
    }

    @Override
    public int getGroupCount() {
        return groupData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childData.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childData.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.expandable_list_item, parent, false);
        }

        TextView tvClass = convertView.findViewById(R.id.subject_name);
        TextView tvDate = convertView.findViewById(R.id.subject_time);

        Map<String, String> group = groupData.get(groupPosition);
        tvClass.setText(group.get("className"));
        tvDate.setText(group.get("date"));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.child_item_clickable_text, parent, false);
        }

        TextView tvFunction = convertView.findViewById(R.id.tv_child);

        Map<String, String> child = childData.get(groupPosition).get(childPosition);
        tvFunction.setText(child.get("function"));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}


