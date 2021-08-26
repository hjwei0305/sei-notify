package com.changhong.sei.notify.dao.impl;

import com.changhong.sei.core.dao.impl.BaseEntityDaoImpl;
import com.changhong.sei.core.dto.serach.*;
import com.changhong.sei.notify.dao.MessageUserExtDao;
import com.changhong.sei.notify.dto.NotifyType;
import com.changhong.sei.notify.entity.Message;
import com.changhong.sei.notify.entity.MessageUser;
import com.changhong.sei.notify.entity.compose.MessageCompose;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2019-09-25 13:44
 */
public class MessageUserDaoImpl extends BaseEntityDaoImpl<MessageUser> implements MessageUserExtDao {

    public MessageUserDaoImpl(EntityManager entityManager) {
        super(MessageUser.class, entityManager);
    }

    @Override
    public Long getUnreadCount(String userId, Set<String> targetValues) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("select count(t.id) from Message t ");
        return (Long) getUnReadQuery(jpql, userId, targetValues, "", "").getSingleResult();
    }

    private Query getUnReadQuery(StringBuilder jpql, String userId, Set<String> targetValues, String extendWhere, String order) {
        jpql.append(" where t.del = :del and t.publish = :publish and t.effective = :effective ");
        jpql.append(" and not exists (select u.id from MessageUser u where t.id = u.msgId and u.userId = :userId and u.read = :read) ");
        jpql.append(" and t.targetValue in :targetValues");
        if (StringUtils.isNotBlank(extendWhere)) {
            jpql.append(extendWhere);
        }

        if (StringUtils.isNotBlank(order)) {
            jpql.append(" ").append(order);
        }

        Query query = entityManager.createQuery(jpql.toString());
        query.setParameter("del", Boolean.FALSE);
        query.setParameter("publish", Boolean.TRUE);
        query.setParameter("effective", Boolean.TRUE);
        query.setParameter("userId", userId);
        query.setParameter("read", Boolean.TRUE);
        query.setParameter("targetValues", targetValues);
        return query;
    }

    @Override
    public List<Message> getUnreadMessage(String userId, Set<String> targetValues) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("select t from Message t ");

        // 优先级, 发布时间
        String order = " order by t.priority desc, t.publishDate desc";
        Query query = getUnReadQuery(jpql, userId, targetValues, "", order);

        query.setFirstResult(0);
        // 设置最大返回未读消息数
        query.setMaxResults(10);
        return query.getResultList();
    }

    @Override
    public Message getFirstUnreadMessage(String userId, Set<String> targetValues) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("select t from Message t ");

        String extendWhere = " and t.category = '" + NotifyType.SEI_BULLETIN + "' ";

        // 优先级, 发布时间
        String order = " order by t.priority desc, t.publishDate desc";

        Query query = getUnReadQuery(jpql, userId, targetValues, extendWhere, order);
        query.setMaxResults(1);
        List<Message> messageList = query.getResultList();
        if (CollectionUtils.isEmpty(messageList)) {
            return new Message();
        } else {
            return messageList.get(0);
        }
    }

    @Override
    public PageResult<MessageCompose> findPage4User(Search search, String userId, Set<String> targetCodes) {
        PageInfo pageInfo = search.getPageInfo();
        if (Objects.isNull(pageInfo)) {
            pageInfo = new PageInfo();
        }
        if (pageInfo.getPage() < 1) {
            pageInfo.setPage(1);
        }

        String val = search.getQuickSearchValue();

        StringBuilder where = new StringBuilder();
        where.append(" from Message b left join MessageUser u on b.id = u.msgId and u.userId = :userId ");
        where.append(" where b.del = false and b.publish = true  and b.targetValue in :targetValues ");
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
                        where.append(" and (u.").append(filter.getFieldName()).append(" is null or u.").append(filter.getFieldName()).append(" = false) ");
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

        List<MessageCompose> messageComposes = null;
        StringBuilder jpql = new StringBuilder();
        jpql.append("select count(1) ").append(where);
        Query countQuery = entityManager.createQuery(jpql.toString());
        countQuery.setParameter("userId", userId);
        countQuery.setParameter("targetValues", targetCodes);
        if (StringUtils.isNotBlank(val)) {
            countQuery.setParameter("subject", "%" + val + "%");
        }
        Long countNum = (Long) countQuery.getSingleResult();
        if (Objects.nonNull(countNum)) {
            jpql.delete(0, jpql.length());
            jpql.append("select new com.changhong.sei.notify.entity.compose.MessageCompose(b, u) ").append(where);
            // 排序
            jpql.append(" order by b.publishDate desc, b.priority ");
            List<SearchOrder> orders = search.getSortOrders();
            if (CollectionUtils.isNotEmpty(orders)) {
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
            query.setParameter("targetValues", targetCodes);
            if (StringUtils.isNotBlank(val)) {
                query.setParameter("subject", "%" + val + "%");
            }

            query.setFirstResult((pageInfo.getPage() - 1) * pageInfo.getRows());
            query.setMaxResults(pageInfo.getRows());

            messageComposes = (List<MessageCompose>) query.getResultList();
        }
        PageResult<MessageCompose> pageResult = new PageResult<>();
        pageResult.setRows(messageComposes);
        pageResult.setTotal((int) Math.ceil((double) countNum / (double) pageInfo.getRows()));
        pageResult.setRecords(countNum.intValue());
        pageResult.setPage(pageInfo.getPage());
        return pageResult;
    }
}
