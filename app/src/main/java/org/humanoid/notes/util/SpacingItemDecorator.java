package org.humanoid.notes.util;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Jugal Mistry on 2/2/2019.
 */
public class SpacingItemDecorator extends RecyclerView.ItemDecoration {

    private final int spaceDimensions;

    public SpacingItemDecorator(int spaceDimensions) {
        this.spaceDimensions = spaceDimensions;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
            @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.left = outRect.right = outRect.bottom
                 = outRect.top = spaceDimensions;
    }
}
