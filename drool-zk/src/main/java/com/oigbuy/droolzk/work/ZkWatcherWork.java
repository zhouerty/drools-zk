package com.oigbuy.droolzk.work;

import com.github.pagehelper.util.StringUtil;
import com.oigbuy.droolzk.config.DroolsConfig;
import com.oigbuy.droolzk.dao.RuleMessageMapper;
import com.oigbuy.droolzk.service.ReloadDroolsRules;
import org.kie.internal.utils.KieHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 初始规则加载及版本号修改监听类
 * @author qiang.zhou
 * on 2018-05-10.
 */
@Component
public class ZkWatcherWork {

    private static final Logger LOG = LoggerFactory.getLogger(ZkWatcherWork.class);

    @Autowired
    private RuleMessageMapper ruleMessageMapper;
    @Autowired
    private ReloadDroolsRules reloadDroolsRules;
    @Autowired
    private DroolsConfig droolsConfig;

    /**
     * 是否初始化标记
     */
    private static boolean mark = true;

    @Scheduled(cron = "* * * * * ? ")
    public void zkListenTest() throws Exception {
        //数据库规则版本号
        String baseVersion = ruleMessageMapper.getNewVersion();
        //zk服务器规则版本号
        String serverVersion = droolsConfig.getRuleVersion();
        //如果服务器的版本号不为空且数据库版本号与服务器不一致则更新数据库的规则版本号
        if (!StringUtil.isEmpty(serverVersion) && !serverVersion.equals(baseVersion)) {
            LOG.info("执行更新版本号 " + baseVersion + " ====> " + serverVersion);
            ruleMessageMapper.updateVersion(serverVersion);
            LOG.info("执行规则重新加载");
            //按照新的版本号加载所有该版本下的规则
            reloadDroolsRules.load();
            mark = false;
        }else{
            if(mark){
                //如果项目初始化或重新发布则按最新规则版本号加载所有规则到规则仓库
                LOG.info("项目重新运行,加载所有规则");
                reloadDroolsRules.load();
                mark = false;
            }
        }
    }
}
