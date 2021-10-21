package com.ych.day01;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

/**
 * TODO:
 *  当我们需要使用DataBinding绑定图片时，我们可以使用这种方式，来进行使用。
 */
@SuppressLint("AppCompatCustomView")
public class PPJockImageView extends ImageView {

    public PPJockImageView(Context context) {
        super(context);
    }

    public PPJockImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PPJockImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * BindingAdapter注解
     *  value：相当于ImageView的自定义属性
     *  requireAll：表示所有属性都设置了才有效。默认就是为true，我们可以设置为false
     * @param view
     * @param imageUrl
     * @param isCircle
     */
    @BindingAdapter(value = {"image_url","isCircle"},requireAll = false)
    public static void setImageSrc(PPJockImageView view,String imageUrl,boolean isCircle){
        //编写加载图片的逻辑，
//       RequestBuilder<Drawable> builder = Glide.with(view).load(imageUrl);
//        if (isCircle){
//            builder.transform(new CircleCrop());
//        }
//        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
//        if (layoutParams!=null && layoutParams.width>0 && layoutParams.height > 0){
//            builder.override(layoutParams.width,layoutParams.height);
//        }
//        builder.into(view);
    }
}
