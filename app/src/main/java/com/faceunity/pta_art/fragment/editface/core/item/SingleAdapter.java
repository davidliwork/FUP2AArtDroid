package com.faceunity.pta_art.fragment.editface.core.item;

import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.faceunity.pta_art.R;

/**
 * Created by tujh on 2018/11/7.
 */
public abstract class SingleAdapter extends RecyclerView.Adapter<SingleAdapter.ItemHolder> {

    private Context mContext;
    private int mLayoutId;

    protected int mSelectPosition = -1;

    private SingleAdapter.ItemSelectListener itemSelectListener;

    public SingleAdapter(Context context, @LayoutRes int layoutId) {
        mContext = context;
        mLayoutId = layoutId;
    }

    public SingleAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(mContext).inflate(mLayoutId == 0 ? R.layout.layout_single_item : mLayoutId, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int pos) {
        final int position = holder.getLayoutPosition();
        Glide.with(holder.itemView.getContext()).load(getRes(position)).into(holder.mItemImg);
        holder.mSelect.setVisibility(mSelectPosition == position ? View.VISIBLE : View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemSelectListener != null && itemSelectListener.itemSelectListener(mSelectPosition, position)) {
                    setSelectPosition(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return getSize();
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        ImageView mItemImg;
        View mSelect;

        public ItemHolder(View itemView) {
            super(itemView);
            mItemImg = itemView.findViewById(R.id.bottom_item_img);
            mSelect = itemView.findViewById(R.id.bottom_item_img_select);
        }
    }

    public void setSelectPosition(int selectPos) {
        if (mSelectPosition == selectPos) return;
        int oldSelectId = mSelectPosition;
        mSelectPosition = selectPos;
        notifyItemChanged(mSelectPosition);
        notifyItemChanged(oldSelectId);
    }

    public void setItemSelectListener(SingleAdapter.ItemSelectListener itemSelectListener) {
        this.itemSelectListener = itemSelectListener;
    }

    public interface ItemSelectListener {

        boolean itemSelectListener(int lastPos, int position);
    }

    public abstract int getRes(int pos);

    public abstract int getSize();

}