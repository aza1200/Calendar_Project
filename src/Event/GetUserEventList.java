package Event;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class GetUserEventList {
    List<EventData> userEventList = new ArrayList<>();

    public static void main(String[] args){
        GetUserEventList userEventListGetter = new GetUserEventList("jhkim");
        List<EventData> events = userEventListGetter.getUserEventList();
            // 이제 events 리스트에 데이터가 들어있을 것입니다.
    for (EventData event : events) {
        System.out.println("Event Name: " + event.getEventName());
        System.out.println("Host Name: " + event.retrieveHostName());
        // Start Time
        LocalDateTime startTime = event.getStartTime();
        int startYear = startTime.getYear();
        int startMonth = startTime.getMonthValue();
        int startDay = startTime.getDayOfMonth();
        System.out.println("Start Time: " + startYear + "-" + startMonth + "-" + startDay);

        // End Time
        LocalDateTime endTime = event.getEndTime();
        int endYear = endTime.getYear();
        int endMonth = endTime.getMonthValue();
        int endDay = endTime.getDayOfMonth();

        System.out.println("End Time: " + endYear + "-" + endMonth + "-" + endDay);
        System.out.println("Is All Day: " + event.getIsAllDay());
        System.out.println("------------------------------");
    }
    }

    public GetUserEventList(String UserId){
        try{
            Connection con = (Connection) DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/Calendar", "dbms_practice", "dbms_practice"
            );

            PreparedStatement st = (PreparedStatement) con.prepareStatement(
                "select * from public.event " +
                "where event_id in ( " +
                "select event_id from public.user_event where user_id = ?" +
                ")"
            );
            st.setString(1, UserId);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                String eventName = rs.getString("name");
                String hostID = rs.getString("host_id");

                Timestamp stimestamp = rs.getTimestamp("start_time");
                LocalDateTime startTime = stimestamp.toLocalDateTime();

                Timestamp etimestamp = rs.getTimestamp("end_time");
                LocalDateTime endTime = etimestamp.toLocalDateTime();

                Boolean isALlDay = rs.getBoolean("is_all_day");

                EventData tmpUserEvent = new EventData(
                    hostID, eventName, startTime, endTime, isALlDay
                );
                userEventList.add(tmpUserEvent);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<EventData> getUserEventList(){
        return userEventList;
    }

    public class EventData {
        private String hostID;
        private String eventName;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private boolean isAllDay;

        public EventData(String hostID, String eventName, 
        LocalDateTime startTime, LocalDateTime endTime, boolean isAllDay) {
            this.hostID = hostID;
            this.eventName = eventName; 
            this.startTime = startTime;
            this.endTime = endTime;
            this.isAllDay = isAllDay;
        }
    
        public String retrieveHostName() {
            try (Connection con = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/Calendar", "dbms_practice", "dbms_practice"
                    );
                 PreparedStatement st = con.prepareStatement(
                    "SELECT name FROM public.user WHERE user_id = ?");
            ) {
                st.setString(1, this.hostID);
        
                try (ResultSet rs = st.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("name");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        
            return "";
        }
    
        public String getEventName() {
            return this.eventName;
        }

        public LocalDateTime getStartTime(){
            return this.startTime;
        }

        public LocalDateTime getEndTime(){
            return this.endTime;
        }

        public boolean getIsAllDay(){
            return this.isAllDay;
        }
    }

}
