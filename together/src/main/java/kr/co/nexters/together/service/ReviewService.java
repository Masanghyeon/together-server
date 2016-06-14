package kr.co.nexters.together.service;

import kr.co.nexters.together.common.service.AbstractService;
import kr.co.nexters.together.protocol.dto.request.UserReviewPatchRequest;
import kr.co.nexters.together.protocol.models.MReview;
import org.hibernate.Session;

public class ReviewService extends AbstractService {

    public MReview get(long reviewId) throws Exception {
        Session session = getSessionFactory().openSession();
        MReview mReview= session.get(MReview.class, reviewId);
        session.close();
        return mReview;
    }

    public MReview patch(Long reviewID,UserReviewPatchRequest patchRequest) throws Exception {
        Session session = beginTransaction();
        MReview mReview = session.get(MReview.class, reviewID);
        if (patchRequest.getContent() != null) {
            mReview.setContent(patchRequest.getContent());
        }
        commitAndClose(session);
        return mReview;
    }


    public void delete(long reviewID) throws Exception {
        Session session = beginTransaction();
        MReview review= session.get(MReview.class, reviewID);
        if (review == null) return;
        review.getTo().getReviews().remove(review);
        review.getAuthor().getReviews().remove(review);
        session.delete(review);
        commitAndClose(session);

    }
}
