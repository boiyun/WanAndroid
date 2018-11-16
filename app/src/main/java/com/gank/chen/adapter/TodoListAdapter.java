package com.gank.chen.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.gank.chen.R;
import com.gank.chen.mvp.model.TodoItemData;
import com.gank.chen.mvp.model.TodoListData;
import com.gank.chen.util.CommenUtil;

import java.util.List;

/**
 * Creat by chen on 2018/11/15
 * Describe:
 *
 * @author chenbo
 */
public class TodoListAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;

    public TodoListAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.item_todo_top_date);
        addItemType(TYPE_LEVEL_1, R.layout.item_todo_list_item);

    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_LEVEL_0:
                TodoListData todoListData = (TodoListData) item;
                String time = CommenUtil.formatDate(todoListData.getDate());
                helper.setText(R.id.tv_time, time);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = helper.getAdapterPosition();
                        if (todoListData.isExpanded()) {
                            collapse(pos);
                        } else {
                            expand(pos);
                        }
                    }
                });
                break;
            case TYPE_LEVEL_1:
                TodoItemData itemData = (TodoItemData) item;

                helper.setText(R.id.tv_item_todo_title, itemData.getTitle())
                        .addOnLongClickListener(R.id.rl_item_todo)
                        .setText(R.id.tv_item_todo_content, itemData.getContent());
                break;

            default:
                break;
        }
    }

//    // 获取当前父级位置
//    int cp = getParentPosition(person);
//// 通过父级位置找到当前list，删除指定下级
// ((Level1Item)getData().get(cp)).removeSubItem(person);
//    // 列表层删除相关位置的数据
//    getData().remove(holder.getLayoutPosition());
//    // 更新视图
//    notifyDataSetChanged();

}
