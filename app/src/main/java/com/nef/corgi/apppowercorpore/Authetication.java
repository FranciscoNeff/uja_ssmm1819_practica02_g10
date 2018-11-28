package com.nef.corgi.apppowercorpore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.nef.corgi.apppowercorpore.DTO.UserDTO;

import java.text.SimpleDateFormat;


public class Authetication extends Fragment {
    private UserDTO user;
    private OnFragmentInteractionListener mListener;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String PARAM_USER_NAME="name";
    public static final String PARAM_USER_EMAIL="email";
    public static final String PARAM_USER_EXPIRED="expires";
    SimpleDateFormat FORMATO = new SimpleDateFormat("y-M-d-H-m-s");
    // TODO: Rename and change types of parameters
    private String s_user;
    private String s_pass;
    private String s_email;
    private String s_expires;
    public Authetication(){}
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(UserDTO userDTO);
    }
    public static Authetication newInstance(String param1, String param2) {
        Authetication fragment = new Authetication();
        Bundle args = new Bundle();
        args.putString(PARAM_USER_NAME, param1);
        args.putString(PARAM_USER_EMAIL, param2);//revisar formato de fecha
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            s_user = getArguments().getString(PARAM_USER_NAME);
            s_email = getArguments().getString(PARAM_USER_EMAIL);
            s_expires = getArguments().getString(FORMATO.format(PARAM_USER_EXPIRED));
        }
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_fragment_auth, container, false);
        final EditText name = fragment.findViewById(R.id.fragment_auth_edit_name);
        final EditText pass = fragment.findViewById(R.id.fragment_auth_edit_pass);
        final EditText email = fragment.findViewById(R.id.fragment_auth_edit_email);
        Button connect = fragment.findViewById(R.id.fragment_auth_button);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String s_user = name.getEditableText().toString();
               String s_pass = pass.getEditableText().toString();
                String s_email = email.getEditableText().toString();
                user = new UserDTO(s_user, s_pass, s_email);
                mListener.onFragmentInteraction(user);//le pasamos el usuario

            }

        });
        return fragment;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}