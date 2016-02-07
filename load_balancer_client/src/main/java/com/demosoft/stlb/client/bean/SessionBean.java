package com.demosoft.stlb.client.bean;

import com.demosoft.stlb.client.collection.SimpleMap;
import com.demosoft.stlb.client.scheduler.NodeStatisticTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Created by Andrii_Korkoshko on 06.10.2015.
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionBean {

    public static final List<SessionBean> sessionBeanRegister = new CopyOnWriteArrayList<>();


    private Date startCounting;
    private double loadingLevel = -1;
    private List<SessionStatisticPoint> sessionStatisticPointList = new ArrayList<>();
    private String sessionId;

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private NodeStatisticTask nodeStatisticTask;

    @PostConstruct
    public void init() {
        sessionBeanRegister.add(this);
    }

    public HttpSession getHttpSession() {
        return httpSession;
    }

    public void addActiveTime(Long time) {
        if (startCounting == null) {
            startCounting = new Date();
        }
        cleanUpStatistic();
        sessionStatisticPointList.add(new SessionStatisticPoint(new Date().getTime(), time));
    }

    public void setHttpSession(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    /**
     * @return Time which was wasted per millisecond
     */
    public Double getCurrentLoadLevel() {
        Long checkPoint = System.currentTimeMillis();
        cleanUpStatistic();
        Double loadLevel = 0.0;
        for (SessionStatisticPoint point : sessionStatisticPointList) {
            if (point.getTimePoint() <= checkPoint) {
                loadLevel += point.getTimeSpent();
            } else {
                break;
            }
        }
        loadLevel /= nodeStatisticTask.getSessionLoadLevelCalculationInterval();
        return loadLevel;
    }

    /**
     * Remove statistic point which not in analysed interval
     */
    private void cleanUpStatistic() {
        Long limit = System.currentTimeMillis() - nodeStatisticTask.getSessionLoadLevelCalculationInterval();
        sessionStatisticPointList.removeAll(sessionStatisticPointList.stream().filter(point -> point.getTimePoint() < limit).collect(Collectors.toList()));
    }

    public static Map<String, Double> generateReport() {
        Map<String, Double> report = new SimpleMap<>();
        for (SessionBean sessionBean : sessionBeanRegister) {
            if (sessionBean.getSessionId() != null) {
                report.put(sessionBean.getSessionId(), sessionBean.getCurrentLoadLevel());
            }
        }
        return report;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void accomulateSessionId() {
        try {
            sessionId = this.getHttpSession().getId();
            System.out.println("sessionId acomulated:" + sessionId);
        } catch (IllegalStateException e) {
        }
    }
}
