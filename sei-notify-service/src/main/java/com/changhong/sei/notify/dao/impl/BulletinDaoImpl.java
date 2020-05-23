package com.changhong.sei.notify.dao.impl;

import com.changhong.sei.core.dao.impl.BaseEntityDaoImpl;
import com.changhong.sei.core.dto.serach.*;
import com.changhong.sei.notify.dao.BulletinExtDao;
import com.changhong.sei.notify.entity.Bulletin;
import com.changhong.sei.notify.entity.compose.BulletinCompose;
import com.changhong.sei.util.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2019-09-25 13:44
 */
public class BulletinDaoImpl extends BaseEntityDaoImpl<Bulletin> implements BulletinExtDao {

    public BulletinDaoImpl(EntityManager entityManager) {
        super(Bulletin.class, entityManager);
    }

    @Override
    public Long getUnreadCount(String userId, Set<String> targetCodes) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("select count(t.id) from Bulletin t ");
        return (Long) getUnReadQuery(jpql, userId, targetCodes, "").getSingleResult();
    }

    private Query getUnReadQuery(StringBuilder jpql, String userId, Set<String> targetCodes, String order) {
        jpql.append(" where t.del = :del and t.release = :release ");
        jpql.append(" and t.effectiveDate <= :effectiveDate and t.invalidDate >= :invalidDate ");
        jpql.append(" and not exists (select u.id from BulletinUser u where t.id = u.bulletinId and u.userId = :userId and u.read = :read) ");
        jpql.append(" and t.targetCode in (:targetCodes)");

        if (StringUtils.isNotBlank(order)) {
            jpql.append(" ").append(order);
        }

        Query query = entityManager.createQuery(jpql.toString());
        Date date = new Date();
        date = DateUtils.parseDate(DateUtils.formatDate(date));
        query.setParameter("del", Boolean.FALSE);
        query.setParameter("release", Boolean.TRUE);
        query.setParameter("effectiveDate", date, TemporalType.DATE);
        query.setParameter("invalidDate", date, TemporalType.DATE);
        query.setParameter("userId", userId);
        query.setParameter("read", Boolean.TRUE);
        query.setParameter("targetCodes", targetCodes);
        return query;
    }

    @Override
    public List<Bulletin> getUnreadBulletin(String userId, Set<String> targetCodes) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("select t from Bulletin t ");
        return getUnReadQuery(jpql, userId, targetCodes, "").getResultList();
    }

    @Override
    public Bulletin getFirstUnreadBulletin(String userId, Set<String> targetCodes) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("select t from Bulletin t ");

        // 优先级, 发布时间
        String order = " order by t.priority desc, t.releaseDate desc";

        Query query = getUnReadQuery(jpql, userId, targetCodes, order);
        query.setMaxResults(1);
        List<Bulletin> bulletins = query.getResultList();
        if (CollectionUtils.isEmpty(bulletins)) {
            return null;
        } else {
            return bulletins.get(0);
        }
    }

    @Override
    public PageResult<BulletinCompose> findPage4User(Search search, String userId, Set<String> targetCodes) {
        PageInfo pageInfo = search.getPageInfo();
        if (Objects.isNull(pageInfo)) {
            pageInfo = new PageInfo();
        }

        String val = search.getQuickSearchValue();

        StringBuilder where = new StringBuilder();
        where.append(" from Bulletin b left join BulletinUser u on b.id = u.bulletinId and u.userId = :userId ");
        where.append(" where b.del = 0 and b.release = 1  and b.targetCode in (:targetCodes)");
        if (StringUtils.isNotBlank(val)) {
            where.append(" and b.subject like :subject ");
        }

        List<SearchFilter> filters = search.getFilters();
        // 条件
        if (CollectionUtils.isNotEmpty(filters)) {
            for (SearchFilter filter : filters) {
                // 已读/未读
                if (StringUtils.equals("read", filter.getFieldName())) {
                    if (SearchFilter.Operator.EQ == filter.getOperator()) {
                        where.append(" and u.").append(filter.getFieldName()).append(" = ").append(filter.getValue());
                    } else if (SearchFilter.Operator.NU == filter.getOperator()) {
                        where.append(" and u.").append(filter.getFieldName()).append(" is null ");
                    }
                    continue;
                }

                switch (filter.getOperator()) {
                    case GT:
                        where.append(" and b.").append(filter.getFieldName()).append(" > '").append(filter.getValue()).append("'");
                        break;
                    case LT:
                        where.append(" and b.").append(filter.getFieldName()).append(" < '").append(filter.getValue()).append("'");
                        break;
                    case GE:
                        where.append(" and b.").append(filter.getFieldName()).append(" >= '").append(filter.getValue()).append("'");
                        break;
                    case LE:
                        where.append(" and b.").append(filter.getFieldName()).append(" <= '").append(filter.getValue()).append("'");
                        break;
                    case EQ:
                        where.append(" and b.").append(filter.getFieldName()).append(" = '").append(filter.getValue()).append("'");
                        break;
                    case NE:
                        where.append(" and b.").append(filter.getFieldName()).append(" != '").append(filter.getValue()).append("'");
                        break;
                    case LK:
                        where.append(" and b.").append(filter.getFieldName()).append(" like '%").append(filter.getValue()).append("%'");
                        break;
                    default:
                }
            }
        }

        List<BulletinCompose> bulletinComposes = null;
        StringBuilder jpql = new StringBuilder();
        jpql.append("select count(1) ").append(where);
        Query countQuery = entityManager.createQuery(jpql.toString());
        countQuery.setParameter("userId", userId);
        countQuery.setParameter("targetCodes", targetCodes);
        if (StringUtils.isNotBlank(val)) {
            countQuery.setParameter("subject", "%" + val + "%");
        }
        Long countNum = (Long) countQuery.getSingleResult();
        if (Objects.nonNull(countNum)) {
            jpql.delete(0, jpql.length());
            jpql.append("select new com.changhong.sei.notify.entity.compose.BulletinCompose(b, u) ").append(where);
            // 排序
            List<SearchOrder> orders = search.getSortOrders();
            if (CollectionUtils.isNotEmpty(orders)) {
                jpql.append(" order by b.release ");
                for (SearchOrder order : orders) {
                    if (StringUtils.equals("read", order.getProperty())) {
                        jpql.append(", u.").append(order.getProperty()).append(" ").append(order.getDirection().name());
                    } else {
                        jpql.append(", b.").append(order.getProperty()).append(" ").append(order.getDirection().name());
                    }
                }
            }
            Query query = entityManager.createQuery(jpql.toString());
            query.setParameter("userId", userId);
            query.setParameter("targetCodes", targetCodes);
            if (StringUtils.isNotBlank(val)) {
                query.setParameter("subject", "%" + val + "%");
            }

            query.setFirstResult(pageInfo.getPage() - 1);
            query.setMaxResults(pageInfo.getRows());

            bulletinComposes = (List<BulletinCompose>) query.getResultList();
        }
        PageResult<BulletinCompose> pageResult = new PageResult<>();
        pageResult.setRows(bulletinComposes);
        pageResult.setTotal((int) Math.ceil((double) countNum / (double) pageInfo.getRows()));
        pageResult.setRecords(countNum.intValue());
        pageResult.setPage(pageInfo.getPage());
        return pageResult;
    }
}
