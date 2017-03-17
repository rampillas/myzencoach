package com.unir.grupo2.myzeancoach.ui.MCooperativeSol.dilemmaCommentList;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.ui.MCooperativeSol.dilemmaPostList.DilemmaComment;
import com.unir.grupo2.myzeancoach.ui.MCooperativeSol.dilemmaPostList.DilemmaPost;

import java.util.List;

/**
 * Created by Cesar on 26/02/2017.
 */

public class DilemmaCommentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private DilemmaPost dilemmaPost;
    private List<DilemmaComment> dilemmaCommentItemList;
    private String videoName;
    private boolean[] userAnswers;

    private class VIEWS_TYPES{
        public static final int Header = 1;
        public static final int Normal = 2;
    }

    boolean isHeader = false;

    public interface OnButtonClickListener{
        public void onButtonClick(int testRate);
    }

    private final DilemmaCommentListAdapter.OnButtonClickListener listener;

    public DilemmaCommentListAdapter(Context context, DilemmaPost dilemmaPost,
                                     OnButtonClickListener listener){
        this.context = context;
        this.dilemmaPost = dilemmaPost;
        this.listener = listener;
        this.dilemmaCommentItemList = dilemmaPost.getComments();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEWS_TYPES.Normal){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coop_sol_comment_list_header_layout, parent, false);
            return new DilemmaCommentItemViewHolder(view);
        }else if (viewType == VIEWS_TYPES.Header){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coop_sol_comment_list_item_layout, parent, false);
            return new DilemmaCommentHeaderViewHolder(view);
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof DilemmaCommentHeaderViewHolder) {
            DilemmaCommentHeaderViewHolder headerHolder = (DilemmaCommentHeaderViewHolder) holder;
            headerHolder.titleTextView.setText(dilemmaPost.getTitle());
            headerHolder.descriptionTextView.setText(dilemmaPost.getDescription());

        } else if(holder instanceof DilemmaCommentItemViewHolder) {
            final DilemmaComment dilemmaComment = dilemmaCommentItemList.get(position - 1);
            DilemmaCommentItemViewHolder itemHolder = (DilemmaCommentItemViewHolder) holder;

            itemHolder.nickTextView.setText(dilemmaComment.getNick());
            itemHolder.dateTextView.setText(dilemmaComment.getDate());
            itemHolder.descriptionTextView.setText(dilemmaComment.getDescription());

            for (int i = 0; i < dilemmaComment.getPros().size(); i++){
                TextView pro = new TextView(context);
                pro.setText(dilemmaComment.getPros().get(i));
                pro.setTextColor(context.getColor(R.color.greenApp));
                itemHolder.proConLinearLayout.addView(pro);
            }

            for (int i = 0; i < dilemmaComment.getCons().size(); i++){
                TextView con = new TextView(context);
                con.setText(dilemmaComment.getCons().get(i));
                con.setTextColor(context.getColor(R.color.redApp));
                itemHolder.proConLinearLayout.addView(con);
            }

            if (dilemmaComment.isLike()){
                itemHolder.likeCheckBox.setChecked(true);
                itemHolder.feedbackEditText.setVisibility(View.VISIBLE);
            }else{
                itemHolder.likeCheckBox.setChecked(false);
                itemHolder.feedbackEditText.setVisibility(View.GONE);
            }

            if (dilemmaComment.getFeedback() != null){
                itemHolder.likeCheckBox.setClickable(false);
                itemHolder.feedbackEditText.setVisibility(View.GONE);
                itemHolder.feedbackTextView.setVisibility(View.VISIBLE);
                itemHolder.feedbackTextView.setText(dilemmaComment.getFeedback());
            }
        }
    }

    @Override
    public int getItemViewType(int position){
        if(isPositionHeader (position)) {
            return VIEWS_TYPES.Header;
        }
        return VIEWS_TYPES.Normal;

    }

    @Override
    public int getItemCount() {
        return dilemmaCommentItemList.size() + 1;
    }

    private boolean isPositionHeader (int position) {
        boolean isHeader = false;
        if (position == 0){
            isHeader = true;
        }
        return isHeader;
    }

}
