package com.example.yogeshtikam.loadermanagerexample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Yogesh N. Tikam on 9/8/2017.
 */

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> {

    private List<ContactsObject> mContactsList;

    private Context mContext;

    public ContactListAdapter(List<ContactsObject> contactsList, Context context) {
        mContactsList = contactsList;
        mContext = context;
    }

    @Override
    public ContactListAdapter.ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_contact_item, null);
        ContactViewHolder contactViewHolder = new ContactViewHolder(view);
        return contactViewHolder;
    }

    @Override
    public void onBindViewHolder(ContactListAdapter.ContactViewHolder holder, int position) {
        ContactsObject contact = mContactsList.get(position);
        holder.contactName.setText(contact.getmContactName());
        holder.contactName.setText(contact.getmContactNumber());
    }

    @Override
    public int getItemCount() {
        return mContactsList.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        TextView contactName;
        TextView contactNumber;

        public ContactViewHolder(View itemView) {
            super(itemView);

            contactName = (TextView) itemView.findViewById(R.id.contact_name);
            contactNumber = (TextView) itemView.findViewById(R.id.contact_no);
        }
    }
}
