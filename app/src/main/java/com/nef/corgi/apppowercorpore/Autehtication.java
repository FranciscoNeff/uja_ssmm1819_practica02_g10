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



public class Autehtication extends Fragment {
    private userDTO user;
    private OnFragmentInteractionListener mListener;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public Autehtication(){}
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(userDTO userDTO);
    }
    public static Autehtication newInstance(String param1, String param2) {
        Autehtication fragment = new Autehtication();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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
               /* String s_user = name.getEditableText().toString();
                user = new userDTO(s_user);
                Intent intent = new Intent(getActivity(),ServiceActivity.class);
                intent.putExtra(ServiceActivity.PARAMETER_USER,s_user);
                intent.putExtra("name","Hola"+s_user );
                startActivity(intent);
                mListener.onFragmentInteraction(user);*///Codigo de PRUEBAS(Basura)

               String s_user = name.getEditableText().toString();
               String s_pass = pass.getEditableText().toString();
                String s_email = email.getEditableText().toString();

                user = new userDTO(s_user, s_pass, s_email);
                //Toast.makeText(getActivity(),s_user+"Hola",Toast.LENGTH_LONG);
                Intent intent = new Intent(getActivity(),ServiceActivity.class);
                intent.putExtra(ServiceActivity.PARAMETER_USER,s_user);
                intent.putExtra("name",s_user);
                intent.putExtra("email",s_email);
                intent.putExtra("pass",s_pass);
                startActivity(intent);//con esto lanzamos el menu lateral, se dejara comentado ya que no se usa(solo tiene los estilos)
                //Preguntar como quitar(en la barra de aplicacion)el ServiceActivity
                mListener.onFragmentInteraction(user);
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