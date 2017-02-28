package com.unir.grupo2.myzeancoach.ui.MEssentialInfo.questionList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.unir.grupo2.myzeancoach.R;

import java.util.List;

/**
 * Created by Cesar on 26/02/2017.
 */

public class QuestionListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<QuestionItem> questionItemList;
    private String videoName;

    private class VIEWS_TYPES{
        public static final int Header = 1;
        public static final int Normal = 2;
        public static final int Footer = 3;
    }

    boolean isFooter = false;
    boolean isHeader = false;

    public interface OnButtonClickListener{
        public void onButtonClick(int testRate);
    }

    private final QuestionListAdapter.OnButtonClickListener listener;

    public QuestionListAdapter(Context context,List<QuestionItem> quesionItemList, String videoName,
                               OnButtonClickListener listener){
        this.context = context;
        this.questionItemList = quesionItemList;
        this.videoName = videoName;
        this.listener = listener;
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof QuestionHeaderViewHolder) {
            QuestionHeaderViewHolder headerHolder = (QuestionHeaderViewHolder) holder;
            headerHolder.videoName.setText(videoName);

        } else if(holder instanceof QuestionFooterViewHolder) {
            QuestionFooterViewHolder footerHolder = (QuestionFooterViewHolder) holder;
            footerHolder.videoNameButton.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick (View view) {
                   listener.onButtonClick(4);
                }
            });
        } else if(holder instanceof QuestionItemViewHolder) {
            final QuestionItem questionItem = questionItemList.get(position - 1);
            QuestionItemViewHolder itemHolder = (QuestionItemViewHolder) holder;
            itemHolder.questionNumberTextView.setText(String.format(context.getString(R.string.question_number),
                    questionItem.getNumber(), questionItemList.size()));
            itemHolder.questionTextView.setText(questionItem.getQuestion());
            itemHolder.answer1Radio.setText(questionItem.getAnswer1());
            itemHolder.answer2Radio.setText(questionItem.getAnswer2());
            itemHolder.answer3Radio.setText(questionItem.getAnswer3());
            itemHolder.answerRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                    if (checkedId == R.id.answer1_radio){
                        Toast.makeText(context, "1",Toast.LENGTH_SHORT).show();
                    }else if (checkedId == R.id.answer2_radio){
                        Toast.makeText(context, "2",Toast.LENGTH_SHORT).show();
                    }else if (checkedId == R.id.answer3_radio){
                        Toast.makeText(context, "3",Toast.LENGTH_SHORT).show();
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

}
