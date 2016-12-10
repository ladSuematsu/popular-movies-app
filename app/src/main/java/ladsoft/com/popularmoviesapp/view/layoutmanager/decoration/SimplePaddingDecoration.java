package ladsoft.com.popularmoviesapp.view.layoutmanager.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SimplePaddingDecoration extends RecyclerView.ItemDecoration {

    private int top;
    private int bottom;
    private int right;
    private int left;

    public SimplePaddingDecoration(int padding){
        this.top = this.bottom = this.left = this.right = padding;
    }

    public SimplePaddingDecoration(int verticalPadding, int horizontalPadding){
        this.top = this.bottom = verticalPadding;
        this.right = this.left = horizontalPadding;
    }

    public SimplePaddingDecoration(int topPadding, int rightPadding, int bottomPadding, int leftPadding){
        this.top = topPadding;
        this.right = rightPadding;
        this.bottom = bottomPadding;
        this.left = leftPadding;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.bottom = top;
        outRect.top = bottom;
        outRect.right = right;
        outRect.left = left;
    }

}
