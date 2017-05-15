package com.unir.grupo2.myzeancoach.ui.MEssentialInfo.questionList;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.Question;

import java.util.List;

import static com.unir.grupo2.myzeancoach.R.id.answer2_radio;

/**
 * Created by Cesar on 26/02/2017.
 */

public class QuestionListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Question> questionItemList;
    private String videoName;
    private boolean[] userAnswers;

    private class VIEWS_TYPES{
        public static final int Header = 1;
        public static final int Normal = 2;
        public static final int Footer = 3;
    }

    public interface OnButtonClickListener{
        public void onButtonClick(int testRate);
    }

    private final QuestionListAdapter.OnButtonClickListener listener;

    public QuestionListAdapter(Context context,List<Question> quesionItemList, String videoName,
                               OnButtonClickListener listener){
        this.context = context;
        this.questionItemList = quesionItemList;
        this.videoName = videoName;
        this.listener = listener;
        this.userAnswers = new boolean[questionItemList.size()];
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEWS_TYPES.Normal){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item_card_layout, parent, false);
            return new QuestionItemViewHolder (view);
        }else if (viewType == VIEWS_TYPES.Header){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_testlist_header_layout, parent, false);
            return new QuestionHeaderViewHolder (view);
        }else if (viewType == VIEWS_TYPES.Footer){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_testlist_footer_layout, parent, false);
            return new QuestionFooterViewHolder (view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof QuestionHeaderViewHolder) {
            QuestionHeaderViewHolder headerHolder = (QuestionHeaderViewHolder) holder;
            headerHolder.videoName.setText(videoName);

        } else if(holder instanceof QuestionFooterViewHolder) {
            QuestionFooterViewHolder footerHolder = (QuestionFooterViewHolder) holder;
            footerHolder.videoNameButton.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick (View view) {
                    int score = 0;
                    for (int i = 0; i < userAnswers.length; i++){
                        if (userAnswers[i] == true){
                            score ++;
                        }
                    }
                   listener.onButtonClick(score);
                }
            });
        } else if(holder instanceof QuestionItemViewHolder) {
            final Question questionItem = questionItemList.get(position - 1);
            final QuestionItemViewHolder itemHolder = (QuestionItemViewHolder) holder;
            itemHolder.questionNumberTextView.setText(String.format(context.getString(R.string.question_number),
                    position, questionItemList.size()));
            itemHolder.questionTextView.setText(questionItem.getDescription());
            if (questionItem.getAnswers().size() > 0){
                itemHolder.answer1Radio.setText(questionItem.getAnswers().get(0).getDescription());
                if (questionItem.getAnswers().size() > 1){
                    itemHolder.answer2Radio.setText(questionItem.getAnswers().get(1).getDescription());
                }
                if (questionItem.getAnswers().size() > 2){
                    itemHolder.answer3Radio.setText(questionItem.getAnswers().get(2).getDescription());
                }
            }
            itemHolder.answerRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                    //if (userAnswers[position - 1] )
                    if (checkedId == R.id.answer1_radio){
                        if (questionItemList.get(position - 1).getAnswers().size() >= 1){
                            userAnswers[position - 1] = questionItemList.get(position - 1).getAnswers().get(0).getIsRight();
                        }else{
                            showDialogNoAnswer();
                            itemHolder.answer1Radio.setChecked(false);
                        }
                    }else if (checkedId == answer2_radio){
                        if (questionItemList.get(position - 1).getAnswers().size() >= 2){
                            userAnswers[position - 1] = questionItemList.get(position - 1).getAnswers().get(1).getIsRight();
                        }else{
                            showDialogNoAnswer();
                            itemHolder.answer2Radio.setChecked(false);
                        }

                    }else if (checkedId == R.id.answer3_radio){
                        if (questionItemList.get(position - 1).getAnswers().size() >= 3){
                            userAnswers[position - 1] = questionItemList.get(position - 1).getAnswers().get(2).getIsRight();
                        }else{
                            showDialogNoAnswer();
                            itemHolder.answer3Radio.setChecked(false);
                        }
                    }
                }
            });

        }
    }

    @Override
    public int getItemViewType(int position){
        if(isPositionHeader (position)) {
            return VIEWS_TYPES.Header;
        } else if(isPositionFooter (position)) {
            return VIEWS_TYPES.Footer;
        }
        return VIEWS_TYPES.Normal;

    }

    @Override
    public int getItemCount() {
        return questionItemList.size() + 2;
    }

    private boolean isPositionHeader (int position) {
        boolean isHeader = false;
        if (position == 0){
            isHeader = true;
        }
        return isHeader;
    }

    private boolean isPositionFooter (int position) {
        if (position == questionItemList.size () + 1){
            return true;
        }else{
            return false;
        }
    }

    private void showDialogNoAnswer() {
        new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.message_title_no_answer_video))
                .setMessage(context.getString(R.string.message_description_no_answer_video))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

}
