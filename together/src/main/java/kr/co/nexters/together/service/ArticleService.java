package kr.co.nexters.together.service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import kr.co.nexters.together.common.service.AbstractService;
import kr.co.nexters.together.protocol.TravelPreference;
import kr.co.nexters.together.protocol.dto.request.ArticlePatchRequest;
import kr.co.nexters.together.protocol.models.MArticle;
import kr.co.nexters.together.protocol.models.MRegion;
import kr.co.nexters.together.protocol.models.MTravelPreference;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.ws.rs.DELETE;
import java.util.List;
import java.util.Set;

public class ArticleService extends AbstractService {

    public MArticle get(long articleId) throws Exception {
        Session session = beginTransaction();
        MArticle article = session.get(MArticle.class, articleId);
        session.close();
        return article;
    }

    public MArticle patch(Long articleId, ArticlePatchRequest patchRequest) throws Exception {
        Session session = beginTransaction();
        MArticle article = session.get(MArticle.class, articleId);
        if (patchRequest.getLatitude() != null) {
            article.setLatitude(patchRequest.getLatitude());
        }
        if (patchRequest.getLongitude() != null) {
            article.setLongitude(patchRequest.getLongitude());
        }
        if (!Strings.isNullOrEmpty(patchRequest.getContent())) {
            article.setContent(patchRequest.getContent());
        }
        if (patchRequest.getRegionId() != null) {
            MRegion prevRegion = article.getRegion();
            MRegion newRegion = session.get(MRegion.class, patchRequest.getRegionId());
            prevRegion.getArticles().remove(article);
            article.setRegion(newRegion);
            newRegion.getArticles().add(article);
        }
        if (patchRequest.getTravelPreferences() != null && patchRequest.getTravelPreferences().size() > 0) {
            for (MTravelPreference mtp : article.getTravelPreferences()) {
                mtp.getArticles().remove(article);
            }
            Set<MTravelPreference> newTravelPreferences = Sets.newHashSet();
            for (TravelPreference tp : patchRequest.getTravelPreferences()) {
                MTravelPreference travelPreference = (MTravelPreference)session
                        .createCriteria(MTravelPreference.class)
                        .add(Restrictions.eq("value", tp)).uniqueResult();
                newTravelPreferences.add(travelPreference);
                travelPreference.getArticles().add(article);
            }
            article.setTravelPreferences(newTravelPreferences);
        }
        commitAndClose(session);
        return article;
    }

    public void delete(long articleId) throws Exception {
        Session session = beginTransaction();
        MArticle article = session.get(MArticle.class, articleId);
        if (article == null) return;
        article.getAuthor().getArticles().remove(article);
        article.getRegion().getArticles().remove(article);
        Set<MTravelPreference> travelPreferences = article.getTravelPreferences();
        for (MTravelPreference mtp : travelPreferences) {
            mtp.getArticles().remove(article);
        }
        session.delete(article);
        commitAndClose(session);
    }
}
