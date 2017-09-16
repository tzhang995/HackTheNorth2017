package hackers.hackthenorth2017;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by tony on 16/09/17.
 */

public class MessageAdapter extends ArrayAdapter<BasicMessage> {
    public MessageAdapter(Context context, int resource, List<BasicMessage> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_message, parent, false);
        }



        ImageView photoImageView = (ImageView) convertView.findViewById(R.id.itemPhotoImageView);
        TextView messageTextView = (TextView) convertView.findViewById(R.id.itemMessageTextView);
        TextView authorTextView = (TextView) convertView.findViewById(R.id.itemNameTextView);

        BasicMessage message = getItem(position);

        boolean isPhoto = message.getImageUrl() != null;
        if (isPhoto) {
            messageTextView.setVisibility(View.GONE);
            photoImageView.setVisibility(View.VISIBLE);
            Glide.with(photoImageView.getContext())
                    .load(message.getImageUrl())
                    .into(photoImageView);
        } else {
            messageTextView.setVisibility(View.VISIBLE);
            photoImageView.setVisibility(View.GONE);
        }
        authorTextView.setText(message.getName());

        return convertView;
    }
}