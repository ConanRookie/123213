package cn.com.bmsoft.baseProject.common.model.request;

import lombok.Data;
import lombok.ToString;

/**
 * @author: Boxing
 * @version: 1.0
 **/
@Data
@ToString
public class LogListRequest extends RequestData {
    //关键字
    private String keyword;
    //等级（0：错误，1：信息）
    private Integer level;
    //日志时间
    private Long[] logDates;
}
