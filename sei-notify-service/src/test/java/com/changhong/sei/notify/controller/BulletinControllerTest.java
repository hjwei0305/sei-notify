package com.changhong.sei.notify.controller;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageInfo;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.notify.dto.TargetType;
import com.changhong.sei.util.EnumUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static org.junit.Assert.*;

/**
 * 实现功能:
 *
 * @author 王锦光 wangjg
 * @version 2020-02-27 8:58
 */
public class BulletinControllerTest extends BaseUnitTest {
    @Autowired
    private BulletinController controller;

    @Test
    public void findByPage() {
        Search search = new Search();
        search.setPageInfo(new PageInfo());
        List<TargetType> targetTypes = new ArrayList<>();
        targetTypes.add(TargetType.ORG);
        targetTypes.add(TargetType.POS);
        SearchFilter filter = new SearchFilter("targetType", targetTypes, SearchFilter.Operator.IN);
        search.addFilter(filter);
        // 打印参数
        System.out.println(JsonUtils.toJson(search));
        ResultData resultData = controller.findByPage(search);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void findByPageViaJson() {
        String json = "{\n" +
                "\t\"filters\": [\n" +
                "\t\t{\n" +
                "\t\t\t\"fieldName\": \"targetType\",\n" +
                "\t\t\t\"value\": [\n" +
                "\t\t\t\t\"ORG\",\n" +
                "\t\t\t\t\"POS\"\n" +
                "\t\t\t],\n" +
                "\t\t\t\"operator\": \"IN\"\n" +
                "\t\t}\n" +
                "\t],\n" +
                "\t\"sortOrders\": null,\n" +
                "\t\"pageInfo\": {\n" +
                "\t\t\"page\": 1,\n" +
                "\t\t\"rows\": 15\n" +
                "\t}\n" +
                "}";
        Search search = JsonUtils.fromJson(json, Search.class);
//        // 处理枚举字段
//        Set<SearchFilter> filters = new HashSet<>(search.getFilters());
//        Optional<SearchFilter> filterResult = filters.stream().filter(f->f.getFieldName().equals("targetType")).findFirst();
//        if (filterResult.isPresent()) {
//            SearchFilter filter = filterResult.get();
//            filters.remove(filter);
//            SearchFilter enumfilter = new SearchFilter("targetType", EnumUtils.getEnum(TargetType.class, (String)filter.getValue()));
//            filters.add(enumfilter);
//        }
//        search.setFilters(new ArrayList<>(filters));
        ResultData resultData = controller.findByPage(search);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }
}