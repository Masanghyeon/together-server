package kr.co.nexters.together.service;

import com.google.common.base.Strings;
import kr.co.nexters.together.common.PagingList;
import kr.co.nexters.together.common.TogetherPreconditions;
import kr.co.nexters.together.common.datastore.QueryResult;
import kr.co.nexters.together.common.datastore.ReviewQuery;
import kr.co.nexters.together.common.service.AbstractService;
import kr.co.nexters.together.protocol.ReviewType;
import kr.co.nexters.together.protocol.dto.request.UserReviewAddRequest;
import kr.co.nexters.together.protocol.models.MReview;
import kr.co.nexters.together.protocol.models.MUser;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class ReviewsService extends AbstractService {

    public PagingList<MReview> get(GetReviewOptions options) throws Exception {
        Session session = getSessionFactory().openSession();
        ReviewQuery query = new ReviewQuery(session);
        if (options.getAuthorId() != null) {
            query.addRestriction(Restrictions.eq("author.id", options.getAuthorId()));
        }
        if (options.getToId() != null) {
            query.addRestriction(Restrictions.eq("to.id", options.getToId()));
        }
        query.setSkip(getPosition(options.getPageToken()));
        query.setLimit(options.getLimit());
        QueryResult<MReview> queryResult = getDatastore().list(query);
        PagingList<MReview> pagingList = toPagingList(queryResult);
        session.close();
        return pagingList;
    }

    public MReview addUserReview(long userId, long authorId, UserReviewAddRequest addRequest) throws Exception {
        TogetherPreconditions.checkArgument(!Strings.isNullOrEmpty(addRequest.getContent()), "Content must not be null.");
        Session session = beginTransaction();
        MUser author = session.get(MUser.class, authorId);
        MUser user = session.get(MUser.class, userId);

        MReview review = new MReview();
        review.setType(ReviewType.USER);
        review.setAuthor(author);
        author.getReviewsWrittenByMe().add(review);
        review.setTo(user);
        user.getReviews().add(review);
        review.setContent(addRequest.getContent());
        review.setCreatedAt(new DateTime(DateTimeZone.UTC).getMillis());

        session.save(review);
        commitAndClose(session);
        return review;
    }

    public static class GetReviewOptions {
        private Long authorId;
        private Long toId;
        private String pageToken;
        private Integer limit;

        public Long getAuthorId() {
            return authorId;
        }

        public GetReviewOptions setAuthorId(Long authorId) {
            this.authorId = authorId;
            return this;
        }

        public Long getToId() {
            return toId;
        }

        public GetReviewOptions setToId(Long toId) {
            this.toId = toId;
            return this;
        }

        public String getPageToken() {
            return pageToken;
        }

        public GetReviewOptions setPageToken(String pageToken) {
            this.pageToken = pageToken;
            return this;
        }

        public Integer getLimit() {
            return limit;
        }

        public void setLimit(Integer limit) {
            this.limit = limit;
        }
    }
}
