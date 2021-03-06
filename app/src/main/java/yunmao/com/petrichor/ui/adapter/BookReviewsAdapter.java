package yunmao.com.petrichor.ui.adapter;

import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import yunmao.com.petrichor.R;
import yunmao.com.petrichor.bean.http.douban.BookReviewResponse;
import yunmao.com.petrichor.bean.http.douban.BookReviewsListResponse;
import yunmao.com.petrichor.utils.common.UIUtils;

import java.util.List;


/**
 * Created by msi on 2018/2/27.
 */
public class BookReviewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_EMPTY = 0;
    private static final int TYPE_DEFAULT = 1;
    private static final int AVATAR_SIZE_DP = 24;
    private final BookReviewsListResponse reviewsListResponse;

    public BookReviewsAdapter(BookReviewsListResponse responses) {
        this.reviewsListResponse = responses;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_DEFAULT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_comment, parent, false);
            return new BookCommentHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty, parent, false);
            return new EmptyHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (reviewsListResponse.getReviews() == null || reviewsListResponse.getReviews().isEmpty()) {
            return TYPE_EMPTY;
        } else {
            return TYPE_DEFAULT;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BookCommentHolder) {
            List<BookReviewResponse> reviews = reviewsListResponse.getReviews();
            Glide.with(UIUtils.getContext())
                    .load(reviews.get(position).getAuthor().getAvatar())
                    .asBitmap()
                    .centerCrop()
                    .into(new BitmapImageViewTarget(((BookCommentHolder) holder).iv_avatar) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(UIUtils.getContext().getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            ((BookCommentHolder) holder).iv_avatar.setImageDrawable(circularBitmapDrawable);
                        }
                    });
            ((BookCommentHolder) holder).tv_user_name.setText(reviews.get(position).getAuthor().getName());
            if (reviews.get(position).getRating() != null) {
                ((BookCommentHolder) holder).ratingBar_hots.setRating(Float.valueOf(reviews.get(position).getRating().getValue()));
            }
            ((BookCommentHolder) holder).tv_comment_content.setText(reviews.get(position).getSummary());
            ((BookCommentHolder) holder).tv_favorite_num.setText(reviews.get(position).getVotes() + "");
            ((BookCommentHolder) holder).tv_update_time.setText(reviews.get(position).getUpdated().split(" ")[0]);
        }
    }

    @Override
    public int getItemCount() {
        return reviewsListResponse.getReviews().size();
    }

    class BookCommentHolder extends RecyclerView.ViewHolder {
        //        private TextView tv_comment_title;
        private ImageView iv_avatar;
        private TextView tv_user_name;
        private AppCompatRatingBar ratingBar_hots;
        private TextView tv_comment_content;
        //        private ImageView iv_favorite;
        private TextView tv_favorite_num;
        private TextView tv_update_time;
//        private TextView tv_more_comment;

        public BookCommentHolder(View itemView) {
            super(itemView);
//            tv_comment_title = (TextView) itemView.findViewById(R.id.tv_comment_title);
            iv_avatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
            tv_user_name = (TextView) itemView.findViewById(R.id.tv_user_name);
            ratingBar_hots = (AppCompatRatingBar) itemView.findViewById(R.id.ratingBar_hots);
            tv_comment_content = (TextView) itemView.findViewById(R.id.tv_comment_content);
//            iv_favorite = (ImageView) itemView.findViewById(R.id.iv_favorite);
            tv_favorite_num = (TextView) itemView.findViewById(R.id.tv_favorite_num);
            tv_update_time = (TextView) itemView.findViewById(R.id.tv_update_time);
//            tv_more_comment = (TextView) itemView.findViewById(R.id.tv_more_comment);
        }
    }

    class EmptyHolder extends RecyclerView.ViewHolder {

        public EmptyHolder(View itemView) {
            super(itemView);
        }
    }
}
