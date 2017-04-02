package ladsoft.com.popularmoviesapp.fragment;


import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import ladsoft.com.popularmoviesapp.R;
import ladsoft.com.popularmoviesapp.databinding.ImageDialogBinding;

public class ImageShowDialogFragment extends DialogFragment {

    private static String ARG_IMAGE_URL = "arg_image_url";
    private ImageDialogBinding binding;
    private String imageUrl;

    public static ImageShowDialogFragment newInstance(String imageUrl) {
        ImageShowDialogFragment fragment = new ImageShowDialogFragment();

        Bundle args = new Bundle();
        args.putString(ARG_IMAGE_URL, imageUrl);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, 0);

        Bundle args = getArguments();
        imageUrl = args.getString(ARG_IMAGE_URL, null);

        if(imageUrl == null) {
            dismiss();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null)
        {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.image_dialog, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.ic_movie_white)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.content);
    }
}
