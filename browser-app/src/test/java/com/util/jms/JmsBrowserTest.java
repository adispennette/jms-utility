package com.util.jms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.util.jms.JmsUtility;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.class)
public class JmsBrowserTest {
    @Autowired
    JmsUtility browser;

    @Test
    public void browseTest() {
        try {
            browser.browse("wealth-sms-send");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Test
    public void sendTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String message = mapper.writeValueAsString(new WealthSms("1002", "This is a test message", null));
        
        browser.send("wealth-sms-send", message);
    }
    
    private static class WealthSms {
        private String targetType;
        private String targetId;
        private String message;
        private Long activityId;
        
        public WealthSms() {
            targetType = "USER";
        }

        public WealthSms(String targetId, String message, Long activityId) {
            this();
            this.targetId = targetId;
            this.message = message;
            this.activityId = activityId;
        }
        
        public WealthSms(String targetType, String targetId, String message, Long activityId) {
            this(targetId, message, activityId);
            this.targetType = targetType;
        }

        public String getTargetType() {
            return targetType;
        }

        public void setTargetType(String targetType) {
            this.targetType = targetType;
        }

        public String getTargetId() {
            return targetId;
        }

        public void setTargetId(String targetId) {
            this.targetId = targetId;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Long getActivityId() {
            return activityId;
        }

        public void setActivityId(Long activityId) {
            this.activityId = activityId;
        }
    }
}
