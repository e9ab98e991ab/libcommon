package com.e9ab98e991ab.libcommon.base.glide;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.e9ab98e991ab.libcommon.R;

public class GlideUtlis {

    public GlideUtlis() {
    }

    public RequestOptions glide() {
        return new RequestOptions().centerCrop().error(R.drawable.sys_icon_banner_no_picture);
    }

    public RequestOptions glideRO() {
        return new RequestOptions().error(R.drawable.sys_icon_banner_no_picture);
    }

    public RequestOptions glideRounded() {
        return new RequestOptions()
                .centerCrop()
                .error(R.drawable.sys_icon_banner_no_picture)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.color.color_eeeeee)
                .disallowHardwareConfig()
                .transform(new GlideRoundedCornersTransform(10,
                        GlideRoundedCornersTransform.CornerType.ALL));
    }

    public RequestOptions glideRoundedHead() {
        return new RequestOptions()
                .centerCrop()
                .error(R.drawable.sys_icon_banner_no_picture)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(R.color.color_eeeeee)
                .disallowHardwareConfig()
                .transform(new GlideRoundedCornersTransform(10,
                        GlideRoundedCornersTransform.CornerType.ALL));
    }


}
