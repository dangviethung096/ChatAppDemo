package net.vitcon.activity.ChatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.vitcon.R;
import net.vitcon.model.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    // Init message variable
    private List<Message> messages;

    public ChatAdapter(List<Message> messages) {
        this.messages = messages;
    }

    public ChatAdapter() {
        this.messages = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Return layout view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (messages.size() > 0) {
            holder.setMessage(messages.get(messages.size() - position - 1).message);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTvMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvMessage = itemView.findViewById(R.id.tv_message);
        }

        public void setMessage(String message) {
            mTvMessage.setText(message);
        }

    }

    public void addMessage(Message message) {
        messages.add(message);
        this.notifyDataSetChanged();
    }
}
