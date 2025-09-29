package meetingteam.notificationservice.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import meetingteam.notificationservice.configs.AnomalyConfig;
import meetingteam.notificationservice.constraints.AnomalyTypes;
import meetingteam.notificationservice.utils.AnomalyUtil;

@Component
@RequiredArgsConstructor
public class TraceInterceptor implements HandlerInterceptor {
    private final AnomalyConfig anomalyConfig;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (anomalyConfig.enableStressNg()) {
            AnomalyUtil.markAnomalySpan(AnomalyTypes.STRESS_NG);
        }
        if (anomalyConfig.enableTc()){
            AnomalyUtil.markAnomalySpan(AnomalyTypes.TRAFFIC_CONTROL);
        }
        if(anomalyConfig.enableStrangeFlowCall()){
            if(request.getRequestURI().toString().contains("/anomaly")){
                AnomalyUtil.markAnomalySpan(AnomalyTypes.STRANGE_FLOW_CALL);
            }
        }
        
        return true;
    }
}
