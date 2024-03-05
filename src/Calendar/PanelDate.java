package Calendar;
import java.awt.Component;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import Event.GetUserEventList.EventData;

public class PanelDate extends javax.swing.JLayeredPane {

    private int month;
    private int year;
    private List<EventData> userEventList;

    public PanelDate(int month, int year,List<EventData> userEventList) {
        initComponents();
        this.month = month;
        this.year = year;
        this.userEventList = userEventList; // 받은 값을 멤버 변수에 저장
        init();
    }

    private void init() {
        mon.asTitle();
        tue.asTitle();
        wed.asTitle();
        thu.asTitle();
        fri.asTitle();
        sat.asTitle();
        sun.asTitle();
        setDate();
    }

    private void setDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);  //  month jan as 0 so start from 0
        calendar.set(Calendar.DATE, 1);
        int startDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;  //  get day of week -1 to index
        calendar.add(Calendar.DATE, -startDay);
        ToDay toDay = getToDay();
        for (Component com : getComponents()) {
            Cell cell = (Cell) com;
            if (!cell.isTitle()) {
                String tmpText = "<html>";
                // cell.setText(calendar.get(Calendar.DATE) + "");
                tmpText = tmpText + calendar.get(Calendar.DATE);

                cell.setDate(calendar.getTime());

                int tmpYear = year;
                int tmpMonth = month;
                int tmpDate = calendar.get(Calendar.DATE);
                
                boolean eventFouned = false;
                if (calendar.get(Calendar.MONTH) == month - 1){
                    for (EventData event : userEventList) {
                        int startYear = event.getStartTime().getYear();
                        int startMonth = event.getStartTime().getMonthValue();
                        int startDate = event.getStartTime().getDayOfMonth();

                        int endYear = event.getEndTime().getYear();
                        int endMonth = event.getEndTime().getMonthValue();
                        int endDate = event.getEndTime().getDayOfMonth();
                        
                        boolean isAllDay = event.getIsAllDay();

                        if(startYear <= tmpYear && startMonth <= tmpMonth && startDate <= tmpDate ){
                            if (tmpYear <= endYear && tmpMonth <= endMonth && tmpDate <= endDate){
                                eventFouned = true;
                                if (!isAllDay){
                                    String eventName = event.getEventName();
                                    int startHour = event.getStartTime().getHour();
                                    int startMinute = event.getStartTime().getMinute();

                                    int endHour = event.getEndTime().getHour();
                                    int endMinute = event.getEndTime().getMinute();


                                    tmpText = tmpText + "<br>";
                                    // Concatenate the event information to the cell text
                                    // String eventInfo = eventName + " " + startHour + ":" + startMinute + " - " + endHour + ":" + endMinute;
                                    // cell.setText(cell.getText() + eventInfo);
                                    String eventInfo = eventName + " " + String.format("%02d", startHour) + ":" + String.format("%02d", startMinute) + "~" + String.format("%02d", endHour) + ":" + String.format("%02d", endMinute);
                                    // cell.setText("<html>" + cell.getText() + eventInfo + "</html>");
                                    tmpText = tmpText + eventInfo;
                                }
                                else{
                                    String eventName = event.getEventName();
                                    tmpText = tmpText + "<br>";
                                    String eventInfo = eventName + " AllDay";
                                    tmpText = tmpText + eventInfo;
                                }
                            }
                        }

                    }
                }
                if(!eventFouned){
                    tmpText = tmpText +"</html>";
                }
                cell.setText(tmpText);

                
                cell.currentMonth(calendar.get(Calendar.MONTH) == month - 1);
                if (toDay.isToDay(new ToDay(calendar.get(Calendar.DATE), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR)))) {
                    cell.setAsToDay();
                }
                calendar.add(Calendar.DATE, 1); //  up 1 day
            }
        }
    }

    private ToDay getToDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return new ToDay(calendar.get(Calendar.DATE), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mon = new Cell();
        tue = new Cell();
        sun = new Cell();
        wed = new Cell();
        thu = new Cell();
        fri = new Cell();
        sat = new Cell();
        cell8 = new Cell();
        cell9 = new Cell();
        cell10 = new Cell();
        cell11 = new Cell();
        cell12 = new Cell();
        cell13 = new Cell();
        cell14 = new Cell();
        cell15 = new Cell();
        cell16 = new Cell();
        cell17 = new Cell();
        cell18 = new Cell();
        cell19 = new Cell();
        cell20 = new Cell();
        cell21 = new Cell();
        cell22 = new Cell();
        cell23 = new Cell();
        cell24 = new Cell();
        cell25 = new Cell();
        cell26 = new Cell();
        cell27 = new Cell();
        cell28 = new Cell();
        cell29 = new Cell();
        cell30 = new Cell();
        cell31 = new Cell();
        cell32 = new Cell();
        cell33 = new Cell();
        cell34 = new Cell();
        cell35 = new Cell();
        cell36 = new Cell();
        cell37 = new Cell();
        cell38 = new Cell();
        cell39 = new Cell();
        cell40 = new Cell();
        cell41 = new Cell();
        cell42 = new Cell();
        cell43 = new Cell();
        cell44 = new Cell();
        cell45 = new Cell();
        cell46 = new Cell();
        cell47 = new Cell();
        cell48 = new Cell();
        cell49 = new Cell();

        setLayout(new java.awt.GridLayout(7, 7));

        sun.setForeground(new java.awt.Color(222, 12, 12));
        sun.setText("Sun");
        sun.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(sun);

        mon.setText("Mon");
        mon.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(mon);

        tue.setText("Tue");
        tue.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(tue);

        wed.setText("Wed");
        wed.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(wed);

        thu.setText("Thu");
        thu.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(thu);

        fri.setText("Fri");
        fri.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(fri);

        sat.setText("Sat");
        sat.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(sat);

        cell8.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell8);

        cell9.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell9);

        cell10.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell10);

        cell11.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell11);

        cell12.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell12);

        cell13.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell13);

        cell14.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell14);

        cell15.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell15);

        cell16.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell16);

        cell17.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell17);

        cell18.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell18);

        cell19.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell19);

        cell20.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell20);

        cell21.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell21);

        cell22.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell22);

        cell23.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell23);

        cell24.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell24);

        cell25.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell25);

        cell26.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell26);

        cell27.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell27);

        cell28.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell28);

        cell29.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell29);

        cell30.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell30);

        cell31.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell31);

        cell32.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell32);

        cell33.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell33);

        cell34.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell34);

        cell35.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell35);

        cell36.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell36);

        cell37.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell37);

        cell38.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell38);

        cell39.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell39);

        cell40.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell40);

        cell41.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell41);

        cell42.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell42);

        cell43.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell43);

        cell44.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell44);

        cell45.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell45);

        cell46.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell46);

        cell47.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell47);

        cell48.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell48);

        cell49.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        add(cell49);
    }// </editor-fold>//GEN-END:initComponents


    // Variadeclaration - do not modify//GEN-BEGIN:variables
    private Cell cell10;
    private Cell cell11;
    private Cell cell12;
    private Cell cell13;
    private Cell cell14;
    private Cell cell15;
    private Cell cell16;
    private Cell cell17;
    private Cell cell18;
    private Cell cell19;
    private Cell cell20;
    private Cell cell21;
    private Cell cell22;
    private Cell cell23;
    private Cell cell24;
    private Cell cell25;
    private Cell cell26;
    private Cell cell27;
    private Cell cell28;
    private Cell cell29;
    private Cell cell30;
    private Cell cell31;
    private Cell cell32;
    private Cell cell33;
    private Cell cell34;
    private Cell cell35;
    private Cell cell36;
    private Cell cell37;
    private Cell cell38;
    private Cell cell39;
    private Cell cell40;
    private Cell cell41;
    private Cell cell42;
    private Cell cell43;
    private Cell cell44;
    private Cell cell45;
    private Cell cell46;
    private Cell cell47;
    private Cell cell48;
    private Cell cell49;
    private Cell cell8;
    private Cell cell9;
    private Cell fri;
    private Cell mon;
    private Cell sat;
    private Cell sun;
    private Cell thu;
    private Cell tue;
    private Cell wed;
    // End oiables declaration//GEN-END:variab
}