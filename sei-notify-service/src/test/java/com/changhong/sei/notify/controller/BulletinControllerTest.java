package com.changhong.sei.notify.controller;

import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.dto.serach.PageInfo;
import com.changhong.sei.core.dto.serach.Search;
import com.changhong.sei.core.dto.serach.SearchFilter;
import com.changhong.sei.core.test.BaseUnitTest;
import com.changhong.sei.core.util.JsonUtils;
import com.changhong.sei.notify.dto.BulletinDto;
import com.changhong.sei.notify.dto.TargetType;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

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
//        List<TargetType> targetTypes = new ArrayList<>();
//        targetTypes.add(TargetType.ORG);
//        SearchFilter filter = new SearchFilter("targetType", targetTypes, SearchFilter.Operator.IN);
//        search.addFilter(filter);
        // 打印参数
        System.out.println(JsonUtils.toJson(search));
        ResultData resultData = controller.findByPage(search);
        System.out.println(JsonUtils.toJson(resultData));
        Assert.assertTrue(resultData.successful());
    }

    @Test
    public void getBulletin() {
        ResultData<BulletinDto> resultData = controller.getBulletin("FF6F6CC9-9F25-11EA-8047-0242C0A8460B");
        System.out.println(resultData);
    }

    @Test
    public void saveBulletin() {
        String json = "{\"id\":\"206468D1-A148-11EA-81A4-0242C0A8460B\",\"category\":\"SEI_BULLETIN\",\"notifyTypeRemark\":\"通告\",\"subject\":\"fdfdfd\",\"contentId\":\"2F3CCF95-A3D4-11EA-B186-0242C0A8460B\",\"content\":\"<p>fdsgdfgdg</p>\",\"targetValue\":\"SYSTEM\",\"targetName\":\"SYSTEM\",\"targetType\":\"SYSTEM\",\"targetTypeRemark\":\"系统\",\"priority\":\"Urgent\",\"priorityRemark\":\"紧急\",\"publish\":false,\"publishDate\":null,\"publishUserAccount\":null,\"publishUserName\":null,\"read\":false,\"readDate\":null,\"readNum\":0,\"docIds\":[\"5ed4bb1d53ee5500017e9a21\"],\"msgId\":\"20630940-A148-11EA-81A4-0242C0A8460B\",\"cancelUserAccount\":null,\"cancelUserName\":null,\"cancelDate\":null,\"effectiveDate\":\"2020-05-29\",\"invalidDate\":\"2020-06-30\",\"Attachments\":[{\"uid\":\"rc-upload-1590994193854-5\",\"lastModified\":1590658788720,\"lastModifiedDate\":\"2020-05-28T09:39:48.720Z\",\"name\":\"Image_00041.jpg\",\"size\":714848,\"type\":\"image/jpeg\",\"percent\":100,\"originFileObj\":{\"uid\":\"rc-upload-1590994193854-5\"},\"status\":\"done\",\"response\":{\"docId\":\"5ed4bb1d53ee5500017e9a21\",\"fileName\":\"Image_00041.jpg\",\"documentType\":\"Image\",\"ocrData\":null},\"xhr\":{\"uid\":\"rc-upload-1590994193854-5\"},\"thumbUrl\":\"/edm-service/file/thumbnail?docId=5ed4bb1d53ee5500017e9a21\",\"ocrData\":null,\"id\":\"5ed4bb1d53ee5500017e9a21\",\"fileSize\":\"698.09KB\",\"uploadedTime\":\"2020-06-01 16:23:36\"}]}";
        BulletinDto bulletinDto = JsonUtils.fromJson(json, BulletinDto.class);
        ResultData<String> data = controller.saveBulletin(bulletinDto);
        System.out.println(data);
    }

    @Test
    public void findByPageViaJson() {
        String json = "{\n" +
                "\t\"filters\": [\n" +
                "\t\t{\n" +
                "\t\t\t\"fieldName\": \"targetType\",\n" +
                "\t\t\t\"value\": [\n" +
                "\t\t\t\t\"ORG\"\n" +
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