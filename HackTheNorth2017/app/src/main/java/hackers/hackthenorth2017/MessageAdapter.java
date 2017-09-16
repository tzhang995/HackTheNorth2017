package hackers.hackthenorth2017;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tony on 16/09/17.
 */

public class MessageAdapter extends ArrayAdapter<BasicMessage> {
    Context mContext;
    int resourceId;
    ArrayList<BasicMessage> data = new ArrayList<BasicMessage>();

    public MessageAdapter(Context context, int resource, List<BasicMessage> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.resourceId = resource;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View itemView = convertView;
        ViewHolder holder = null;

        if (itemView == null)
        {
            final LayoutInflater layoutInflater =
                    (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = layoutInflater.inflate(resourceId, parent, false);

            holder = new ViewHolder();
            holder.photoImageView = (ImageView) itemView.findViewById(R.id.itemPhotoImageView);
            holder.messageTextView = (TextView) itemView.findViewById(R.id.itemMessageTextView);
            holder.authorTextView = (TextView) itemView.findViewById(R.id.itemNameTextView);

            itemView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) itemView.getTag();
        }

        BasicMessage message = getItem(position);
        boolean isPhoto = message.getImageUrl() != null;
        if (isPhoto) {
            holder.messageTextView.setVisibility(View.GONE);
            holder.photoImageView.setVisibility(View.VISIBLE);
            Glide.with(holder.photoImageView.getContext())
                    .load(message.getImageUrl())
                    .into(holder.photoImageView);
        }
//        holder.txtItem.setText(item.getTitle());

        return itemView;


    }

    static class ViewHolder
    {
        ImageView photoImageView;
        TextView messageTextView;
        TextView authorTextView;
    }
}