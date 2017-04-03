package com.unir.grupo2.myzeancoach.ui.MCooperativeSol.dilemmaCommentList;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.Comment;
import com.unir.grupo2.myzeancoach.domain.model.Dilemma;
import com.unir.grupo2.myzeancoach.domain.utils.Utils;

import java.util.List;

/**
 * Created by Cesar on 26/02/2017.
 */

public class DilemmaCommentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private Dilemma dilemmaPost;
    private List<Comment> dilemmaCommentItemList;
    private String videoName;
    private boolean[] userAnswers;

    private class VIEWS_TYPES{
        public static final int Header = 1;
        public static final int Normal = 2;
    }

    boolean isHeader = false;

    public interface OnDilemmaCommentClickListener{
        public void onXClick();
    }

    private final DilemmaCommentListAdapter.OnDilemmaCommentClickListener listener;

    public DilemmaCommentListAdapter(Context context, List<Comment> comments,  Dilemma dilemmaPost,
                                     OnDilemmaCommentClickListener listener){
        this.context = context;
        this.dilemmaPost = dilemmaPost;
        this.listener = listener;
        this.dilemmaCommentItemList = comments;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEWS_TYPES.Normal){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coop_sol_comment_list_item_layout, parent, false);
            return new DilemmaCommentItemViewHolder(view);
        }else if (viewType == VIEWS_TYPES.Header){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coop_sol_comment_list_header_layout, parent, false);
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
            final Comment dilemmaComment = dilemmaCommentItemList.get(position - 1);
            DilemmaCommentItemViewHolder itemHolder = (DilemmaCommentItemViewHolder) holder;

            itemHolder.nickTextView.setText(dilemmaComment.getNickUser());
            itemHolder.dateTextView.setText(Utils.dateFormat(dilemmaComment.getDate()));
            itemHolder.descriptionTextView.setText(dilemmaComment.getDescription());

            if (dilemmaComment.getPros() != null && !dilemmaComment.getPros().isEmpty()){
                itemHolder.prosTextView.setText(dilemmaComment.getPros().get(0).getDescription());
            }

            if (dilemmaComment.getCons() != null && !dilemmaComment.getCons().isEmpty()){
                itemHolder.consTextView.setText(dilemmaComment.getCons().get(0).getDescription());
            }

            if (dilemmaComment.getLike()){
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
                itemHolder.feedbackTextView.setText(context.getString(R.string.feedback, dilemmaComment.getFeedback()));
            }

            if (dilemmaPost.getState().equals("completed") && !dilemmaComment.getLike()){
                itemHolder.likeCheckBox.setVisibility(View.GONE);
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
