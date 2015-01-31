package org.itrgroup.itr.main;

import java.util.ArrayList;
import java.util.List;

import org.itrgroup.itr.R;
import org.itrgroup.itr.adapter.MessageAdapter;
import org.itrgroup.itr.model.MessageModel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class MsgFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_msg, container, false);
		
		//List view contains all the published contents on main view
		ListView message_List = (ListView)rootView.findViewById(R.id.message_List);
		
		//To store the false information
		List<MessageModel> messageList = new ArrayList<MessageModel>();
		messageList.add(new MessageModel(R.drawable.avatar_1, "destiny", "I'm in!","12:05pm"));
		messageList.add(new MessageModel(R.drawable.avatar_2, "柴者","I agree with you","8:20am"));
		messageList.add(new MessageModel(R.drawable.avatar_3, "柴者","I agree with you","8:20am"));
		messageList.add(new MessageModel(R.drawable.avatar_3, "柴者","I agree with you","8:20am"));
		messageList.add(new MessageModel(R.drawable.avatar_3, "柴者","I agree with you","8:20am"));
		messageList.add(new MessageModel(R.drawable.avatar_3, "柴者","I agree with you","8:20am"));
		
		
		MessageAdapter message_Adapter=new MessageAdapter(getActivity(),messageList);
		 message_List.setAdapter(message_Adapter);
		
		return rootView;
	}
}
