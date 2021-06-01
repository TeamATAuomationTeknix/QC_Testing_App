// Generated by view binder compiler. Do not edit!
package com.example.qctestingapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import com.example.qctestingapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityImageRegistrationBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ImageButton btnScanQr;

  @NonNull
  public final ImageButton imgBtnAdd;

  @NonNull
  public final ImageButton imgBtnBroom;

  @NonNull
  public final ImageButton imgBtnCamera;

  @NonNull
  public final ImageButton imgBtnRecamera;

  @NonNull
  public final ImageButton imgBtnRemove;

  @NonNull
  public final ImageButton imgBtnUpdate;

  @NonNull
  public final ImageView imgPreview;

  @NonNull
  public final RecyclerView recyclerView;

  @NonNull
  public final RelativeLayout relativeLayoutImg;

  @NonNull
  public final Spinner spinner;

  @NonNull
  public final TextView txtQr;

  private ActivityImageRegistrationBinding(@NonNull LinearLayout rootView,
      @NonNull ImageButton btnScanQr, @NonNull ImageButton imgBtnAdd,
      @NonNull ImageButton imgBtnBroom, @NonNull ImageButton imgBtnCamera,
      @NonNull ImageButton imgBtnRecamera, @NonNull ImageButton imgBtnRemove,
      @NonNull ImageButton imgBtnUpdate, @NonNull ImageView imgPreview,
      @NonNull RecyclerView recyclerView, @NonNull RelativeLayout relativeLayoutImg,
      @NonNull Spinner spinner, @NonNull TextView txtQr) {
    this.rootView = rootView;
    this.btnScanQr = btnScanQr;
    this.imgBtnAdd = imgBtnAdd;
    this.imgBtnBroom = imgBtnBroom;
    this.imgBtnCamera = imgBtnCamera;
    this.imgBtnRecamera = imgBtnRecamera;
    this.imgBtnRemove = imgBtnRemove;
    this.imgBtnUpdate = imgBtnUpdate;
    this.imgPreview = imgPreview;
    this.recyclerView = recyclerView;
    this.relativeLayoutImg = relativeLayoutImg;
    this.spinner = spinner;
    this.txtQr = txtQr;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityImageRegistrationBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityImageRegistrationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_image_registration, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityImageRegistrationBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_scan_qr;
      ImageButton btnScanQr = rootView.findViewById(id);
      if (btnScanQr == null) {
        break missingId;
      }

      id = R.id.img_btn_add;
      ImageButton imgBtnAdd = rootView.findViewById(id);
      if (imgBtnAdd == null) {
        break missingId;
      }

      id = R.id.img_btn_broom;
      ImageButton imgBtnBroom = rootView.findViewById(id);
      if (imgBtnBroom == null) {
        break missingId;
      }

      id = R.id.img_btn_camera;
      ImageButton imgBtnCamera = rootView.findViewById(id);
      if (imgBtnCamera == null) {
        break missingId;
      }

      id = R.id.img_btn_recamera;
      ImageButton imgBtnRecamera = rootView.findViewById(id);
      if (imgBtnRecamera == null) {
        break missingId;
      }

      id = R.id.img_btn_remove;
      ImageButton imgBtnRemove = rootView.findViewById(id);
      if (imgBtnRemove == null) {
        break missingId;
      }

      id = R.id.img_btn_update;
      ImageButton imgBtnUpdate = rootView.findViewById(id);
      if (imgBtnUpdate == null) {
        break missingId;
      }

      id = R.id.img_preview;
      ImageView imgPreview = rootView.findViewById(id);
      if (imgPreview == null) {
        break missingId;
      }

      id = R.id.recycler_view;
      RecyclerView recyclerView = rootView.findViewById(id);
      if (recyclerView == null) {
        break missingId;
      }

      id = R.id.relative_layout_img;
      RelativeLayout relativeLayoutImg = rootView.findViewById(id);
      if (relativeLayoutImg == null) {
        break missingId;
      }

      id = R.id.spinner;
      Spinner spinner = rootView.findViewById(id);
      if (spinner == null) {
        break missingId;
      }

      id = R.id.txt_qr;
      TextView txtQr = rootView.findViewById(id);
      if (txtQr == null) {
        break missingId;
      }

      return new ActivityImageRegistrationBinding((LinearLayout) rootView, btnScanQr, imgBtnAdd,
          imgBtnBroom, imgBtnCamera, imgBtnRecamera, imgBtnRemove, imgBtnUpdate, imgPreview,
          recyclerView, relativeLayoutImg, spinner, txtQr);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
