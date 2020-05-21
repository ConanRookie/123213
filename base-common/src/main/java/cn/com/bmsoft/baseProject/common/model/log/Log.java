package cn.com.bmsoft.baseProject.common.model.log;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author: Boxing
 * @version: 1.0
 **/
@ApiModel(value="Log")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "t_log")
public class Log implements Serializable {
    /**
     * 标识
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value="标识")
    private Integer id;

    /**
     * 用户名
     */
    @Column(name = "username")
    @ApiModelProperty(value="用户名")
    private String username;

    /**
     * 操作
     */
    @Column(name = "operation")
    @ApiModelProperty(value="操作")
    private String operation;

    /**
     * 执行时间
     */
    @Column(name = "time")
    @ApiModelProperty(value="执行时间")
    private Long time;

    /**
     * 方法名
     */
    @Column(name = "method")
    @ApiModelProperty(value="方法名")
    private String method;

    /**
     * 参数
     */
    @Column(name = "params")
    @ApiModelProperty(value="参数")
    private String params;

    /**
     * ip
     */
    @Column(name = "ip")
    @ApiModelProperty(value="ip")
    private String ip;

    /**
     * 日志时间
     */
    @Column(name = "create_time")
    @ApiModelProperty(value="日志时间")
    private Date createTime;

    /**
     * 地点
     */
    @Column(name = "location")
    @ApiModelProperty(value="地点")
    private String location;

    /**
     * 参考表
     */
    @Column(name = "ref_table")
    @ApiModelProperty(value="参考表")
    private String refTable;

    /**
     * 操作状态
     */
    @Column(name = "success")
    @ApiModelProperty(value="操作状态")
    private Boolean success;

    /**
     * 操作代码
     */
    @Column(name = "code")
    @ApiModelProperty(value="操作代码")
    private Integer code;

    /**
     * 操作信息
     */
    @Column(name = "message")
    @ApiModelProperty(value="操作信息")
    private String message;

    /**
     * 堆栈信息
     */
    @Column(name = "trace")
    @ApiModelProperty(value="堆栈信息")
    private String trace;

    private static final long serialVersionUID = 1L;
}
