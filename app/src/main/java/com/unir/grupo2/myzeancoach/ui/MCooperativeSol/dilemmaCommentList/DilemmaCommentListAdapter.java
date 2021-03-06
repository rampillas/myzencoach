package com.unir.grupo2.myzeancoach.ui.MCooperativeSol.dilemmaCommentList;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.Comment;
import com.unir.grupo2.myzeancoach.domain.model.Dilemma;
import com.unir.grupo2.myzeancoach.domain.utils.FooterSpaceViewHolder;
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
        public static final int Footer = 3;
    }

    boolean isHeader = false;

    public interface OnDilemmaClickListener{
        public void onClickCheckBox(int position);
        public void onClickFeedback(int position, String feedback);
    }

    private final DilemmaCommentListAdapter.OnDilemmaClickListener listener;

    public DilemmaCommentListAdapter(Context context, List<Comment> comments,  Dilemma dilemmaPost,
                                     OnDilemmaClickListener listener){
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
        }else if (viewType == VIEWS_TYPES.Footer) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_space_for_floating_button_layout, parent, false);
            return new FooterSpaceViewHolder(view);
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
            final DilemmaCommentItemViewHolder itemHolder = (DilemmaCommentItemViewHolder) holder;

            itemHolder.nickTextView.setText(dilemmaComment.getNickUser());
            itemHolder.dateTextView.setText(Utils.dateFormat(dilemmaComment.getDate()));
            itemHolder.descriptionTextView.setText(dilemmaComment.getDescription());

            if (dilemmaComment.getPros() != null && !dilemmaComment.getPros().isEmpty()){
                itemHolder.prosTextView.setText(dilemmaComment.getPros().get(0).getDescription());
            }

            if (dilemmaComment.getCons() != null && !dilemmaComment.getCons().isEmpty()){
                itemHolder.consTextView.setText(dilemmaComment.getCons().get(0).getDescription());
            }

            //**************************Variable*******************

            if (dilemmaPost.getState().equals("acepted")){
                if (!dilemmaPost.getNickUser().equals(Utils.getUserFromPreference(context))){
                    itemHolder.likeCheckBox.setVisibility(View.GONE);
                }else{
                    itemHolder.likeCheckBox.setVisibility(View.VISIBLE);
                    itemHolder.likeCheckBox.setChecked(false);
                    itemHolder.likeCheckBox.setClickable(true);
                }
                itemHolder.feedbackLinearLayout.setVisibility(View.GONE);

            }else if (dilemmaPost.getState().equals("feedback")){
                itemHolder.likeCheckBox.setVisibility(View.VISIBLE);
                if (!dilemmaPost.getNickUser().equals(Utils.getUserFromPreference(context))){
                    if (dilemmaComment.getLike()){
                        itemHolder.likeCheckBox.setChecked(true);
                    }else{
                        itemHolder.likeCheckBox.setChecked(false);
                    }
                    itemHolder.likeCheckBox.setEnabled(false);
                    itemHolder.feedbackLinearLayout.setVisibility(View.GONE);
                }else{
                    if (dilemmaComment.getLike()){
                        itemHolder.likeCheckBox.setChecked(true);
                        itemHolder.likeCheckBox.setEnabled(false);
                        itemHolder.feedbackLinearLayout.setVisibility(View.VISIBLE);
                    }else{
                        itemHolder.feedbackLinearLayout.setVisibility(View.GONE);
                        itemHolder.likeCheckBox.setChecked(false);
                        itemHolder.likeCheckBox.setEnabled(true);
                    }
                }
            }else if (dilemmaPost.getState().equals("completed")){
                if (dilemmaComment.getLike()){
                    itemHolder.likeCheckBox.setVisibility(View.VISIBLE);
                    itemHolder.likeCheckBox.setChecked(true);
                    itemHolder.likeCheckBox.setEnabled(false);
                    itemHolder.feedbackLinearLayout.setVisibility(View.VISIBLE);
                    if (dilemmaComment.getFeedback() != null){
                        itemHolder.feedbackTextView.setText(context.getString(R.string.feedback, dilemmaComment.getFeedback()));
                        itemHolder.feedbackEditText.setVisibility(View.GONE);
                    }
                }else{
                    itemHolder.likeCheckBox.setVisibility(View.GONE);
                    itemHolder.feedbackLinearLayout.setVisibility(View.GONE);
                }
            }

            itemHolder.likeCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickCheckBox(position - 1);
                }
            });

            itemHolder.feedbackEditText.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    final int DRAWABLE_RIGHT = 2;

                    String feedbackText = itemHolder.feedbackEditText.getText().toString().trim();

                    if(event.getAction() == MotionEvent.ACTION_UP) {
                        if(event.getRawX() >= (itemHolder.feedbackEditText.getRight() - itemHolder.feedbackEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                            if(feedbackText.length() == 0){
                                showDialogFillOutField();
                            }else{
                                listener.onClickFeedback(position -1, feedbackText);
                            }

                            return true;
                        }
                    }
                    return false;
                }
            });
        } else if (holder instanceof FooterSpaceViewHolder) {

        }
    }

    @Override
    public int getItemViewType(int position){
        if(isPositionHeader (position)) {
            return VIEWS_TYPES.Header;
        }
        if (isPositionFooter(position)) {
            return VIEWS_TYPES.Footer;
        }
        return VIEWS_TYPES.Normal;

    }

    @Override
    public int getItemCount() {
        return dilemmaCommentItemList.size() + 2;
    }

    private boolean isPositionHeader (int position) {
        boolean isHeader = false;
        if (position == 0){
            isHeader = true;
        }
        return isHeader;
    }

    private boolean isPositionFooter (int position) {
        if (position == dilemmaCommentItemList.size() + 1){
            return true;
        }else{
            return false;
        }
    }

    private void showDialogFillOutField(){
        new AlertDialog.Builder(context)
                .setMessage(context.getText(R.string.alert_fill_out_feedback))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
