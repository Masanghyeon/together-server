package kr.co.nexters.together.service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import kr.co.nexters.together.common.PagingList;
import kr.co.nexters.together.common.TogetherPreconditions;
import kr.co.nexters.together.common.datastore.ArticleQuery;
import kr.co.nexters.together.common.datastore.QueryResult;
import kr.co.nexters.together.common.service.AbstractService;
import kr.co.nexters.together.protocol.ArticleState;
import kr.co.nexters.together.protocol.OrderBy;
import kr.co.nexters.together.protocol.TravelPreference;
import kr.co.nexters.together.protocol.dto.request.ArticleAddRequest;
import kr.co.nexters.together.protocol.models.MArticle;
import kr.co.nexters.together.protocol.models.MRegion;
import kr.co.nexters.together.protocol.models.MTravelPreference;
import kr.co.nexters.together.protocol.models.MUser;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.List;
import java.util.Set;

public class ArticlesService extends AbstractService {

    public PagingList<MArticle> get(GetArticleOptions options) throws Exception {
        TogetherPreconditions.checkArgument(
                (options.getLatitude() != null && options.getLongitude() != null && options.getRadius() != null)
                        || options.getRegionId() != null
                        || options.getUserId() != null, "Options is insufficient.");
        Session session = beginTransaction();
        ArticleQuery query = new ArticleQuery(session);
        query.addRestriction(Restrictions.eq("state", ArticleState.PUBLIC));
        if (options.getRegionId() != null) {
            // 리스트 뷰 : 해당 region의 articles
            query.addRestriction(Restrictions.eq("region.id", options.getRegionId()));
        } else if(options.getUserId() != null) {
            // user의 articles을 가져올 때
            query.addRestriction(Restrictions.eq("author.id", options.getUserId()));
        } else {
            // 지도 뷰 : 중심점 반경 articles
            // 1000m 당 0.0139 정도 증가
            query.addRestriction(Restrictions.ge("latitude", options.getLatitude() - 0.0139 * (options.getRadius() / 1000)));
            query.addRestriction(Restrictions.le("latitude", options.getLatitude() + 0.0139 * (options.getRadius() / 1000)));
            query.addRestriction(Restrictions.ge("longitude", options.getLongitude() - 0.0139 * (options.getRadius() / 1000)));
            query.addRestriction(Restrictions.le("longitude", options.getLongitude() + 0.0139 * (options.getRadius() / 1000)));
        }
        OrderBy orderBy = OrderBy.RECENTLY_CREATED;
        if (options.getOrderBy() != null) {
            orderBy = options.getOrderBy();
        }

        switch (orderBy) {
            case REVERSE_RECENTLY_CREATED: query.addOrder(Order.asc("createdAt")); break;
            default: query.addOrder(Order.desc("createdAt"));
        }
        query.setSkip(getPosition(options.getPageToken()));

        QueryResult<MArticle> results = getDatastore().list(query);
        PagingList<MArticle> pagingList = toPagingList(results);
        session.close();
        return pagingList;
    }

    public MArticle add(Long userId, ArticleAddRequest addRequest) throws Exception {
        TogetherPreconditions.checkArgument(addRequest.getLatitude() != null && addRequest.getLongitude() != null, "Location Information must not be null.");
        TogetherPreconditions.checkArgument(!Strings.isNullOrEmpty(addRequest.getContent()), "Content must not be null.");
        TogetherPreconditions.checkArgument(addRequest.getRegionId() != null, "Region must not be null.");
        Session session = beginTransaction();
        MUser user = session.get(MUser.class, userId);

        MArticle article = new MArticle();
        user.getArticles().add(article);
        article.setAuthor(user);

        MRegion region = session.get(MRegion.class, addRequest.getRegionId());
        region.getArticles().add(article);
        article.setRegion(region);

        article.setState(ArticleState.PUBLIC);
        article.setLatitude(addRequest.getLatitude());
        article.setLongitude(addRequest.getLongitude());
        article.setContent(addRequest.getContent());
        article.setLocation(addRequest.getLocation());
        Set<MTravelPreference> articlesTravelPreferences = Sets.newHashSet();
        if (addRequest.getTravelPreferences() != null && addRequest.getTravelPreferences().size() > 0) {
            for (TravelPreference tp : addRequest.getTravelPreferences()) {
                MTravelPreference travelPreference = (MTravelPreference)session.createCriteria(MTravelPreference.class).add(Restrictions.eq("value", tp)).uniqueResult();
                travelPreference.getArticles().add(article);
                articlesTravelPreferences.add(travelPreference);
            }
        }
        article.setTravelPreferences(articlesTravelPreferences);
        article.setCreatedAt(new DateTime(DateTimeZone.UTC).getMillis());
        session.save(article);
        commitAndClose(session);

        return article;
    }

    public static class GetArticleOptions {
        private Double latitude;
        private Double longitude;
        private Integer radius;
        private Long regionId;
        private Long userId;
        private String pageToken;
        private Integer limit;
        private OrderBy orderBy;

        public Double getLatitude() {
            return latitude;
        }

        public GetArticleOptions setLatitude(Double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Double getLongitude() {
            return longitude;
        }

        public GetArticleOptions setLongitude(Double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Integer getRadius() {
            return radius;
        }

        public GetArticleOptions setRadius(Integer radius) {
            this.radius = radius;
            return this;
        }

        public Long getRegionId() {
            return regionId;
        }

        public GetArticleOptions setRegionId(Long regionId) {
            this.regionId = regionId;
            return this;
        }

        public Long getUserId() {
            return userId;
        }

        public GetArticleOptions setUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public String getPageToken() {
            return pageToken;
        }

        public GetArticleOptions setPageToken(String pageToken) {
            this.pageToken = pageToken;
            return this;
        }

        public Integer getLimit() {
            return limit;
        }

        public void setLimit(Integer limit) {
            this.limit = limit;
        }

        public OrderBy getOrderBy() {
            return orderBy;
        }

        public void setOrderBy(OrderBy orderBy) {
            this.orderBy = orderBy;
        }
    }
}
