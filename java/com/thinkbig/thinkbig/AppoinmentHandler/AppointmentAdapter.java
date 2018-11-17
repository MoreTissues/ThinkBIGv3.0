package com.thinkbig.thinkbig.AppoinmentHandler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.thinkbig.thinkbig.Objects.Appointment;
import com.thinkbig.thinkbig.R;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AdapterViewHolder> {
    private Context mContext;
    private List<Appointment> mUploads;
    private OnItemClickListener mListener;

    public AppointmentAdapter(Context context, List<Appointment> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @Override
    public AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.content_list_layout, parent, false);
        return new AdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdapterViewHolder holder, int position) {
        Appointment appointment = mUploads.get(position);
        holder.txt_title.setText(appointment.getTitle());
        holder.txt_date.setText(appointment.getDate());
        holder.txt_time.setText(appointment.getTime());
        holder.txt_desc.setText(appointment.getDesc());
        holder.txt_lecturer.setText(appointment.getLect_name());

        /*if(appointment.getLect_name().equals("Dr Lam Meng Chun")){
           holder.imageView.setImageResource(R.drawable.lam);
        }else if (appointment.getLect_name().equals("Puan Faridatul")){
            holder.imageView.setImageResource(R.drawable.faridatul);
        }else if (appointment.getLect_name().equals("Puan Masura")){
            holder.imageView.setImageResource(R.drawable.masura);
        }else if (appointment.getLect_name().equals("Dr Zainal")){
            holder.imageView.setImageResource(R.drawable.zainal);
        }else if (appointment.getLect_name().equals(null)){
            holder.imageView.setImageResource(R.drawable.lam);
        }*/

    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView txt_title, txt_date, txt_time, txt_desc, txt_lecturer;
        public ImageView imageView;

        public AdapterViewHolder(View itemView) {
            super(itemView);

            txt_title = itemView.findViewById(R.id.txt_title);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_time = itemView.findViewById(R.id.txt_time1);
            txt_desc = itemView.findViewById(R.id.txt_desc1);
            imageView = itemView.findViewById(R.id.img_lecturer1);
            txt_lecturer = itemView.findViewById(R.id.txt_lectname);


            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem doWhatever = menu.add(Menu.NONE, 1, 1, "Do whatever");
            MenuItem delete = menu.add(Menu.NONE, 2, 2, "Delete");

            doWhatever.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {

                    switch (item.getItemId()) {
                        case 1:
                            mListener.onWhatEverClick(position);
                            return true;
                        case 2:
                            mListener.onDeleteClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onWhatEverClick(int position);

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}
