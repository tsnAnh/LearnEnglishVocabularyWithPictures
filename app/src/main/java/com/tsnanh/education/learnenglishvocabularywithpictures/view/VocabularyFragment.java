package com.tsnanh.education.learnenglishvocabularywithpictures.view;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsnanh.education.learnenglishvocabularywithpictures.R;
import com.tsnanh.education.learnenglishvocabularywithpictures.controller.App;
import com.tsnanh.education.learnenglishvocabularywithpictures.controller.Config;
import com.tsnanh.education.learnenglishvocabularywithpictures.model.Vocabulary;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VocabularyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VocabularyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VocabularyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Vocabulary vocabulary;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public VocabularyFragment() {
        // Required empty public constructor
    }

    public VocabularyFragment(Vocabulary vocabulary) {
        this.vocabulary = vocabulary;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VocabularyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VocabularyFragment newInstance(String param1, String param2) {
        VocabularyFragment fragment = new VocabularyFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vocabulary, container, false);
        TextView lblVocabulary = view.findViewById(R.id.fragment_lbl_vocabulary);
        TextView lblVocabularyPr = view.findViewById(R.id.fragment_lbl_vocabulary_pr);
        TextView lblMeaning = view.findViewById(R.id.fragment_lbl_meaning);
        TextView lblExamples = view.findViewById(R.id.fragment_lbl_examples_vocabulary);
        final EditText edtNote = view.findViewById(R.id.fragment_edt_note_vocabulary);
        ImageView imageView = view.findViewById(R.id.fragment_img_vocabulary);

        lblVocabulary.setText(vocabulary.getEn_us().trim());
        if (!vocabulary.getEn_us_pr().isEmpty()) {
            lblVocabulary.append(" " + "(" + vocabulary.getEn_us_type().trim() + ")");
        }
        lblVocabularyPr.setText(vocabulary.getEn_us_pr().trim());
        lblMeaning.setText(vocabulary.getEn_us_mean().trim());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            lblExamples.setText(Html.fromHtml(vocabulary.getEn_us_ex(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            lblExamples.setText(Html.fromHtml(vocabulary.getEn_us_ex()));
        }
        edtNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = edtNote.getText().toString().trim();
                vocabulary.setNote(text);
                App.getDaoSession().getVocabularyDao().update(vocabulary);
            }
        });
        edtNote.setText(vocabulary.getNote());
        Glide.with(this).load(Config.SERVER_IMAGE_FOLDER + vocabulary.getImage()).fitCenter().into(imageView);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
